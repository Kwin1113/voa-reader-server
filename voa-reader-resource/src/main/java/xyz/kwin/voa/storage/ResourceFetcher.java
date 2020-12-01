package xyz.kwin.voa.storage;

import com.sun.istack.internal.Nullable;
import xyz.kwin.voa.utils.Http;

import java.io.File;
import java.io.InputStream;

/**
 * 获取资源文件
 *
 * @author Kwin
 * @since 2020/11/28 下午1:55
 */
public class ResourceFetcher {

    public static File fetchFile(@Nullable String resourceName, String url) {
        return Http.getFile(url, null, null, resourceName);
    }

    public static InputStream fetchInputStream(String url) {
        return Http.getInputStream(url, null, null);
    }

}
