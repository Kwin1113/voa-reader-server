package xyz.kwin.voa.spider.chinavoa;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.PageModelPipeline;
import xyz.kwin.voa.domain.Attachment;
import xyz.kwin.voa.domain.Resource;
import xyz.kwin.voa.enums.ChinaVOACategoryEnum;
import xyz.kwin.voa.enums.SupportedFileType;
import xyz.kwin.voa.mapper.AttachmentMapper;
import xyz.kwin.voa.mapper.ResourceMapper;
import xyz.kwin.voa.service.AttachmentDomainService;
import xyz.kwin.voa.service.ResourceDomainService;
import xyz.kwin.voa.spider.chinavoa.model.SpecialSlowEnglishModel;
import xyz.kwin.voa.storage.oss.AliOss;
import xyz.kwin.voa.storage.oss.OssResp;
import xyz.kwin.voa.utils.VOACommonUtil;

/**
 * @author Kwin
 * @since 2020/12/5 下午5:09
 */
@Component
public class ChinaVOAPageModelPipeline implements PageModelPipeline<SpecialSlowEnglishModel> {

    private final ResourceDomainService resourceDomainService;
    private final AttachmentDomainService attachmentDomainService;

    private int count = 0;

    public ChinaVOAPageModelPipeline(ResourceDomainService resourceDomainService, AttachmentDomainService attachmentDomainService) {
        this.resourceDomainService = resourceDomainService;
        this.attachmentDomainService = attachmentDomainService;
    }

    @SneakyThrows
    @Override
    public void process(SpecialSlowEnglishModel model, Task task) {
        Resource resource = Resource.builder()
                .title(model.getTitle())
                .url(model.getUrl())
                .origin(StringUtils.join(model.getOrigin(), ","))
                .category(ChinaVOACategoryEnum.SHOW_SLOW_SPEED_ENGLISH)
                .build();
        resourceDomainService.save(resource);

        String mp3FileName = VOACommonUtil.getFileNameFromUrl(model.getMp3Url());
        OssResp mp3OssResp = AliOss.upload(mp3FileName, model.getMp3Url());
        Attachment mp3 = Attachment.builder()
                .url(model.getMp3Url())
                .fileName(mp3FileName)
                .fileType(SupportedFileType.getBySuffix(VOACommonUtil.getFileSuffixFromFileName(mp3FileName)))
                .ossKey(mp3OssResp.getOssKey())
                .ossPath(mp3OssResp.getOssPath())
                .fileSize(mp3OssResp.getFileSize())
                .build();
        String docFileName = VOACommonUtil.getFileNameFromUrl(model.getDocUrl());
        OssResp docOssResp = AliOss.upload(docFileName, model.getDocUrl());
        Attachment doc = Attachment.builder()
                .url(model.getDocUrl())
                .fileName(docFileName)
                .fileType(SupportedFileType.getBySuffix(VOACommonUtil.getFileSuffixFromFileName(docFileName)))
                .ossKey(docOssResp.getOssKey())
                .ossPath(docOssResp.getOssPath())
                .fileSize(docOssResp.getFileSize())
                .build();
        attachmentDomainService.saveBatch(Lists.newArrayList(mp3, doc));
    }
}
