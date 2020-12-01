package xyz.kwin.voa.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author 蓝波 <lanbo.jing@tuya.com>
 * @since 2020/12/1 8:18 下午
 */
@Getter
@AllArgsConstructor
public enum SupportedFileType {
    UNKNOWN(0, "unknown", "unknown/"),
    MP3(1, "mp3", "mp3/"),
    TXT(2, "txt", "txt/"),
    DOC(3, "doc", "doc/"),
    DOCX(4, "docx", "doc/"),
    ;

    @EnumValue
    private final Integer value;
    private final String dir;
    private final String path;

    public static String getPath(String fileSuffix) {
        return getBySuffix(fileSuffix).getPath();
    }

    public static SupportedFileType getBySuffix(String fileSuffix) {
        if (fileSuffix == null) {
            return UNKNOWN;
        }
        switch (fileSuffix) {
            case "mp3":
                return MP3;
            case "txt":
                return TXT;
            case "doc":
            case "docx":
                return DOC;
            default:
                return UNKNOWN;
        }
    }
}
