package xyz.kwin.voa.domain;

import lombok.Builder;
import lombok.Data;
import xyz.kwin.voa.enums.SupportedFileType;

/**
 * @author 蓝波 <lanbo.jing@tuya.com>
 * @since 2020/12/1 3:02 下午
 */
@Data
@Builder
public class ResourceDescription {
    private String title;
    private String url;
    private String fileName;
    private SupportedFileType fileType;
    private String origin;
    private String remark;
}
