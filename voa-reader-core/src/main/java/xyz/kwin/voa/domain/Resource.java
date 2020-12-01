package xyz.kwin.voa.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author 蓝波 <lanbo.jing@tuya.com>
 * @since 2020/12/1 10:18 上午
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class Resource extends BaseDO {
    private String name;
    private String ossKey;
    private String ossPath;
    private Integer fileType;
    private Double fileSize;
    private String contentType;
}
