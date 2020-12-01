package xyz.kwin.voa.spider.pipelines;

import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;
import xyz.kwin.voa.domain.ResourceDescription;
import xyz.kwin.voa.enums.SupportedFileType;
import xyz.kwin.voa.storage.ResourceSaveExecutor;
import xyz.kwin.voa.utils.VOACommonUtil;

import javax.annotation.Resource;
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

    @Resource
    private ResourceSaveExecutor executor;

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
        System.out.println("request url: " + url);

        String title = null;
        String origin = null;
        String remark = null;
        for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
            if (StringUtils.isEmpty(entry.getValue().toString()) || "null".equals(entry.getValue().toString().toLowerCase())) {
                continue;
            }
            // 获取标题
            if (TITLE.equals(entry.getKey())) {
                title = entry.getValue().toString();
            }
            // 获取来源
            else if (ORIGIN.equals(entry.getKey())) {
                if (entry.getValue() instanceof ArrayList) {
                    origin = StringUtils.join((ArrayList) entry.getValue(), "/");
                } else {
                    origin = entry.getValue().toString();
                }
            }
            // 获取备注
            else if (REMARK.equals(entry.getKey())) {
                remark = entry.getValue().toString();
            }
            // 存在可下载的文件格式（爬虫解析时指定）
            else if (FILE_FORMAT_SET.contains(entry.getKey())) {
                // 如果URL为//开头，则增加当前页面协议头
                String resourceUrl = entry.getValue().toString();
                if (resourceUrl.startsWith("//")) {
                    String protocol = url.substring(0, url.indexOf(":") + 1);
                    resourceUrl = protocol + resourceUrl;
                }

                // 匹配的URL格式 - 下载资源
                if (resourceUrl.matches(URL_REGEX)) {
                    String fileName = VOACommonUtil.getFileNameFromUrl(resourceUrl);
                    String fileType = VOACommonUtil.getFileSuffixFromFileName(fileName);
                    ResourceDescription resourceDesc = ResourceDescription.builder()
                            .title(title)
                            .url(resourceUrl)
                            .fileName(fileName)
                            .fileType(SupportedFileType.getBySuffix(fileType))
                            .origin(origin)
                            .remark(remark)
                            .build();

                    executor.submit(resourceDesc);
                }
            }
        }
    }

}
