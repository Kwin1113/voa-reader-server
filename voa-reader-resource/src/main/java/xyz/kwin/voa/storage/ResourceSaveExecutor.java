package xyz.kwin.voa.storage;

import org.springframework.stereotype.Component;
import xyz.kwin.voa.domain.Resource;
import xyz.kwin.voa.domain.ResourceDescription;
import xyz.kwin.voa.mapper.ResourceMapper;
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
    private final ResourceMapper resourceMapper;

    public ResourceSaveExecutor(ResourceDomainService resourceDomainService, ResourceMapper resourceMapper) {
        this.resourceDomainService = resourceDomainService;
        this.resourceMapper = resourceMapper;
    }

    private static final ThreadPoolExecutor executor =
            new ThreadPoolExecutor(3, 5, 200, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100));

    public void submit(ResourceDescription desc) {
        executor.submit(new ResourceWorker(desc));
    }

    public class ResourceWorker implements Runnable {

        private final ResourceDescription desc;

        public ResourceWorker(ResourceDescription desc) {
            this.desc = desc;
        }

        @Override
        public void run() {
            try {
                // upload resource to Ali OSS through desc.url
                OssResp uploadResp = AliOss.upload(desc.getFileName(), desc.getUrl());
                Resource resource = Resource.builder()
                        .title(desc.getTitle())
                        .url(desc.getUrl())
                        .fileName(desc.getFileName())
                        .fileType(desc.getFileType())
                        .origin(desc.getOrigin())
                        .remark(desc.getRemark())
                        .ossKey(uploadResp.getOssKey())
                        .ossPath(uploadResp.getOssPath())
                        .fileSize(uploadResp.getFileSize())
                        .build();

                // save resource.
                resourceDomainService.save(resource);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
