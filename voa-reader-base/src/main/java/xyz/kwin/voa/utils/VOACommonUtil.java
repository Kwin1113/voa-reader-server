package xyz.kwin.voa.utils;

import java.util.UUID;

/**
 * VOA逻辑相关通用工具类
 *
 * @author Kwin
 * @since 2020/11/28 下午2:02
 */
public class VOACommonUtil {

    public static String getFileNameFromUrl(String url) {
        return url.endsWith("/") ? "" : url.substring(url.lastIndexOf("/") + 1);
    }

    public static String getFileSuffixFromUrl(String url) {
        return url.endsWith(".") ? "" : url.substring(url.lastIndexOf(".") + 1);
    }

    public static String getFilePrefixFromFileName(String fileName) {
        int prefixIndex = fileName.lastIndexOf(".");
        return prefixIndex == -1 ? UUID.randomUUID().toString() : fileName.substring(0, prefixIndex);
    }

    public static String getFileSuffixFromFileName(String fileName) {
        int suffixIndex = fileName.lastIndexOf(".");
        return suffixIndex == -1 ? "tmp" : fileName.substring(suffixIndex + 1);
    }

    public static void main(String[] args) {
        System.out.println(getFileNameFromUrl("http://online2.tingclass.net/lesson/shi0529/0008/8998/20201126health.mp3"));
        System.out.println(getFileSuffixFromUrl("http://online2.tingclass.net/lesson/shi0529/0008/8998/20201126health.mp3"));
    }
}
