package xyz.kwin.voa.storage.oss;

import lombok.Builder;
import lombok.Data;

/**
 * @author 蓝波 <lanbo.jing@tuya.com>
 * @since 2020/12/1 3:45 下午
 */
@Data
@Builder
public class OssResp {
    private String ossKey;
    private String ossPath;
    private Integer fileSize;
}
