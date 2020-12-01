package xyz.kwin.voa.domain;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 蓝波 <lanbo.jing@tuya.com>
 * @since 2020/12/1 10:19 上午
 */
@Data
public class BaseDO {
    private Long id;
    private LocalDateTime ctime;
    private LocalDateTime mtime;
    private Boolean isvalid;
}
