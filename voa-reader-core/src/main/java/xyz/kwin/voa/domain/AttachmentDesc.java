package xyz.kwin.voa.domain;

import lombok.*;
import xyz.kwin.voa.domain.base.BaseDO;
import xyz.kwin.voa.enums.SupportedFileType;

/**
 * @author 蓝波 <lanbo.jing@tuya.com>
 * @since 2020/12/1 3:02 下午
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AttachmentDesc extends BaseDO {
    protected String url;
    protected String fileName;
    protected SupportedFileType fileType;
    protected String remark;
}
