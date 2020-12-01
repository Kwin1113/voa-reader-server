package xyz.kwin.voa.storage.oss;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.sun.istack.internal.Nullable;
import xyz.kwin.voa.enums.SupportedFileType;
import xyz.kwin.voa.utils.VOACommonUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @author Kwin
 * @since 2020/11/28 下午2:34
 */
public class AliOss {

    /**
     * oss params
     */
    private static final String ENDPOINT = "https://oss-cn-hangzhou.aliyuncs.com";
    private static final String ACCESS_KEY_ID = "LTAI4FyRyVz4hZzcM2mTCCcm";
    private static final String ACCESS_KEY_SECRET = "JY0m7fn5vWoTwjSieVckkpnUq0Ynzh";
    private static final String BUCKET_NAME = "voa-reader-storage";

    public static OssResp upload(@Nullable String filename, File file) {
        filename = filename != null ? filename : file.getName();
        String objectName = getObjectName(filename);

        // 创建OSSClient实例。
        OSS ossClient = getOssClient();

        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        ossClient.putObject(BUCKET_NAME, objectName, file);

        // 关闭OSSClient。
        ossClient.shutdown();

        return OssResp.builder()
                .ossKey(objectName)
                .ossPath(objectName)
                .build();
    }

    public static OssResp upload(String filename, String url) throws IOException {
        String objectName = getObjectName(filename);

        // 创建OSSClient实例。
        OSS ossClient = getOssClient();

        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名
        Integer fileSize = null;
        try (InputStream inputStream = new URL(url).openStream()) {
            fileSize = inputStream.available();
            ossClient.putObject(BUCKET_NAME, objectName, inputStream);
        }

        // 关闭OSSClient。
        ossClient.shutdown();

        return OssResp.builder()
                .ossKey(objectName)
                .ossPath(objectName)
                .fileSize(fileSize)
                .build();
    }

    public static OssResp upload(String filename, InputStream is) {
        String objectName = getObjectName(filename);

        // 创建OSSClient实例。
        OSS ossClient = getOssClient();

        // 上传文件到指定的存储空间（bucketName）并将其保存为指定的文件名称（objectName）。
        ossClient.putObject(BUCKET_NAME, objectName, is);

        // 关闭OSSClient。
        ossClient.shutdown();

        return OssResp.builder()
                .ossKey(objectName)
                .ossPath(objectName)
                .build();
    }

    public static FileOutputStream download(String fileName) throws IOException {
        String objectName = getObjectName(fileName);

        // 创建OSSClient实例。
        OSS ossClient = getOssClient();

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        File temp = File.createTempFile("temp", ".tmp");
        ossClient.getObject(new GetObjectRequest(BUCKET_NAME, objectName), temp);

        // 关闭OSSClient。
        ossClient.shutdown();

        return new FileOutputStream(temp);
    }

    private static OSS getOssClient() {
        return new OSSClientBuilder().build(ENDPOINT, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
    }

    private static String getObjectName(String fileName) {
        String fileSuffix = VOACommonUtil.getFileSuffixFromFileName(fileName);
        String path = SupportedFileType.getPath(fileSuffix);
        // 上传文件到OSS时需要指定包含文件后缀在内的完整路径，例如abc/efg/123.jpg。
        return path + fileName;
    }

}
