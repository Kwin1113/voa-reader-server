package xyz.kwin.voa.storage.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.sun.istack.internal.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import xyz.kwin.voa.utils.VOACommonUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author Kwin
 * @since 2020/11/28 下午2:34
 */
public class AliOss {

    /**
     * dir prefix
     */
//    private static final String MP3_DIR = "mp3/";
//    private static final String TXT_DIR = "txt/";
//    private static final String DOC_DIR = "doc/";
//    private static final String UNKNOWN_DIR = "unknown/";

    /**
     * oss params
     */
    private static final String endpoint = "https://oss-cn-hangzhou.aliyuncs.com";
    private static final String accessKeyId = "LTAI4FyRyVz4hZzcM2mTCCcm";
    private static final String accessKeySecret = "JY0m7fn5vWoTwjSieVckkpnUq0Ynzh";
    private static final String bucketName = "voa-reader-storage";

    public static String upload(@Nullable String filename, File file) {
        filename = filename != null ? filename : file.getName();
        String objectName = getObjectName(filename);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        ossClient.putObject(bucketName, objectName, file);

        // 关闭OSSClient。
        ossClient.shutdown();

        return filename;
    }

    public static void main(String[] args) {
    }

    public static FileOutputStream download(String fileName) throws IOException {
        String objectName = getObjectName(fileName);

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        File temp = File.createTempFile("temp", ".tmp");
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), temp);

        // 关闭OSSClient。
        ossClient.shutdown();

        return new FileOutputStream(temp);
    }

    private static String getObjectName(String fileName) {
        String fileSuffix = VOACommonUtil.getFileSuffixFromFileName(fileName);
        String path = OssDirEnum.getPath(fileSuffix);
        // 上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        return path + fileName;
    }

    @Getter
    @AllArgsConstructor
    public enum OssDirEnum {
        MP3("mp3", "mp3/"),
        TXT("txt", "txt/"),
        DOC("doc", "doc/"),
        UNKNOWN("unknown", "unknow/");

        private final String dir;
        private final String path;

        public static String getPath(String fileSuffix) {
            if (fileSuffix == null) {
                return UNKNOWN.getPath();
            }
            switch (fileSuffix) {
                case "mp3":
                    return MP3.getPath();
                case "txt":
                    return TXT.getPath();
                case "doc":
                    return DOC.getPath();
                default:
                    return UNKNOWN.getPath();
            }
        }
    }
}
