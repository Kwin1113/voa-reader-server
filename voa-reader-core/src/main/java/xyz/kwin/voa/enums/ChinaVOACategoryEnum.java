package xyz.kwin.voa.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * chinaVOA类别枚举类
 *
 * @author Kwin
 * @since 2020/12/5 下午4:05
 */
@Getter
@AllArgsConstructor
public enum ChinaVOACategoryEnum implements CategoryEnum {
    SHOW_SLOW_SPEED_ENGLISH(0, "VOA慢速英语"),
    SHOW_NORMAL_SPEED_ENGLISH(1, "VOA常速英语"),
    ;

    /**
     * value
     */
    @EnumValue
    private final Integer value;
    /**
     * 名称
     */
    private final String name;
}
