package xyz.kwin.voa.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.kwin.voa.enums.SupportedFileType;

/**
 * @author 蓝波 <lanbo.jing@tuya.com>
 * @since 2020/12/1 10:18 上午
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Resource extends BaseDO {
    private String title;
    private String url;
    private String fileName;
    private SupportedFileType fileType;
    private String origin;
    private String remark;

    private String ossKey;
    private String ossPath;
    private Integer fileSize;
}
