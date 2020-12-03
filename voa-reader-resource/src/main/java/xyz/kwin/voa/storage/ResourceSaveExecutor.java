package xyz.kwin.voa.storage;

import org.springframework.stereotype.Component;
import xyz.kwin.voa.domain.Attachment;
import xyz.kwin.voa.domain.AttachmentDesc;
import xyz.kwin.voa.domain.Resource;
import xyz.kwin.voa.service.AttachmentDomainService;
import xyz.kwin.voa.service.ResourceDomainService;
import xyz.kwin.voa.storage.oss.AliOss;
import xyz.kwin.voa.storage.oss.OssResp;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Kwin
 * @since 2020/11/28 下午3:57
 */
@Component
public class ResourceSaveExecutor {

    private final ResourceDomainService resourceDomainService;
    private final AttachmentDomainService attachmentDomainService;

    public ResourceSaveExecutor(ResourceDomainService resourceDomainService,
                                AttachmentDomainService attachmentDomainService) {
        this.resourceDomainService = resourceDomainService;
        this.attachmentDomainService = attachmentDomainService;
    }

    private static final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(3, 5, 200, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100));

    public void attachmentAction(AttachmentDesc desc) {
        executor.submit(new ResourceWorker(desc));
    }

    public void resourceAction(Resource resource) {
        executor.submit(() -> {
            resourceDomainService.save(resource);
        });
    }

    public class ResourceWorker implements Runnable {

        private final AttachmentDesc desc;

        public ResourceWorker(AttachmentDesc desc) {
            this.desc = desc;
        }

        @Override
        public void run() {
            try {
                // upload resource to Ali OSS through desc.url
                OssResp uploadResp = AliOss.upload(desc.getFileName(), desc.getUrl());
                Attachment attachment = new Attachment();
                attachment.setUrl(desc.getUrl());
                attachment.setFileName(desc.getFileName());
                attachment.setFileType(desc.getFileType());
                attachment.setRemark(desc.getRemark());
                attachment.setOssKey(uploadResp.getOssKey());
                attachment.setOssPath(uploadResp.getOssPath());
                attachment.setFileSize(uploadResp.getFileSize());

                // save resource.
                attachmentDomainService.save(attachment);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
