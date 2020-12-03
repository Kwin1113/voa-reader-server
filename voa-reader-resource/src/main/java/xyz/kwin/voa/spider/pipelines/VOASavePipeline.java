package xyz.kwin.voa.spider.pipelines;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import xyz.kwin.voa.domain.AttachmentDesc;
import xyz.kwin.voa.domain.Resource;
import xyz.kwin.voa.enums.SupportedFileType;
import xyz.kwin.voa.service.ResourceDomainService;
import xyz.kwin.voa.storage.ResourceSaveExecutor;
import xyz.kwin.voa.utils.VOACommonUtil;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * VOA保存操作管道流
 *
 * @author Kwin
 * @since 2020/11/28 上午11:52
 */
@Component
public class VOASavePipeline implements Pipeline {

    private final ResourceDomainService resourceDomainService;
    private final ResourceSaveExecutor executor;

    public VOASavePipeline(ResourceDomainService resourceDomainService, ResourceSaveExecutor executor) {
        this.resourceDomainService = resourceDomainService;
        this.executor = executor;
    }

    private static final String URL_REGEX = "(https?|ftp|file)://[-A-Za-z0-9+&@#/%?=~_|!:,.;]+[-A-Za-z0-9+&@#/%=~_|]";

    public static final String TITLE = "title";
    public static final String ORIGIN = "origin";
    public static final String REMARK = "remark";

    public static final String MP3 = "mp3";
    public static final String DOC = "doc";
    public static final String TXT = "txt";

    private static final Set<String> FILE_FORMAT_SET;

    static {
        FILE_FORMAT_SET = Sets.newHashSet(MP3, DOC, TXT);
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String url = resultItems.getRequest().getUrl();

        String title = null;
        String origin = null;
        String remark = null;
        Map<String, Object> resultMap = resultItems.getAll();
        // title prop.
        if (resultMap.containsKey(TITLE)) {
            title = resultMap.get(TITLE).toString();
        }
        // origin prop.
        if (resultMap.containsKey(ORIGIN)) {
            Object originObject = resultMap.get(ORIGIN);
            if (originObject instanceof ArrayList) {
                origin = StringUtils.join((ArrayList) originObject, "/");
            } else {
                origin = originObject.toString();
            }
        }
        // remark prop.
        if (resultMap.containsKey(REMARK)) {
            title = resultMap.get(REMARK).toString();
        }

        Resource resource = Resource.builder()
                .title(title)
                .url(url)
                .origin(origin)
                .remark(remark)
                .build();
        resourceDomainService.save(resource);
        // solve attachment.
        for (String fileFormat : FILE_FORMAT_SET) {
            // if resource url starts with "//", add current web protocol as resource url protocol.
            Object result = resultMap.get(fileFormat);
            if (result == null) {
                continue;
            }
            String resourceUrl = result.toString();
            // spider 解析
            if (StringUtils.isEmpty(resourceUrl) || "null".equals(resourceUrl)) {
                continue;
            }
            if (resourceUrl.startsWith("//")) {
                String protocol = url.substring(0, url.indexOf(":") + 1);
                resourceUrl = protocol + resourceUrl;
            }

            // if resource is url - download resource.
            if (resourceUrl.matches(URL_REGEX)) {
                String fileName = VOACommonUtil.getFileNameFromUrl(resourceUrl);
                String fileType = VOACommonUtil.getFileSuffixFromFileName(fileName);
                AttachmentDesc resourceDesc = AttachmentDesc.builder()
                        .url(resourceUrl)
                        .fileName(fileName)
                        .fileType(SupportedFileType.getBySuffix(fileType))
                        .remark(remark)
                        .build();

                // submit to executor.
                executor.attachmentAction(resourceDesc);
            }
        }
    }

}
