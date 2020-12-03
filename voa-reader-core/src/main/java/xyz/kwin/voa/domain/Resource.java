package xyz.kwin.voa.domain;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.kwin.voa.domain.base.BaseDO;

/**
 * @author 蓝波 <lanbo.jing@tuya.com>
 * @since 2020/12/2 9:59 上午
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
public class Resource extends BaseDO {
    private String title;
    private String url;
    private String origin;
    private String remark;
}
