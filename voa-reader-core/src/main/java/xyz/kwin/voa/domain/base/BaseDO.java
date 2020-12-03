package xyz.kwin.voa.domain.base;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author 蓝波 <lanbo.jing@tuya.com>
 * @since 2020/12/1 10:19 上午
 */
@Data
public class BaseDO {
    protected Long id;
    protected LocalDateTime ctime;
    protected LocalDateTime mtime;
    protected Boolean isvalid;
}
