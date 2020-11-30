package xyz.kwin.voa.storage;

import xyz.kwin.voa.storage.oss.AliOss;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author Kwin
 * @since 2020/11/28 下午3:57
 */
public class ResourceSaveExecutor {
    private static final ThreadPoolExecutor executor = new ThreadPoolExecutor(3, 5, 200, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(100));

    public static void saveAction(String url) {
        // download resource...
        File singleResource = ResourceFetcher.fetchFile(null, url);

        // upload resource to oss...
        String fileName = AliOss.upload(singleResource.getName(), singleResource);

        // save resource description to local DB...


        // delete tmp file in disk storage...
        singleResource.delete();
    }
}
