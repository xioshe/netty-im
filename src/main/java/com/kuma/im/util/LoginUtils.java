package com.kuma.im.util;

import com.kuma.im.arrtibute.Attributes;
import io.netty.channel.Channel;
import io.netty.util.Attribute;
import lombok.experimental.UtilityClass;

/**
 * 通道登录状态工具类
 *
 * @author kuma 2021-02-26
 */
@UtilityClass
public class LoginUtils {

    public static void markAsLogin(Channel channel) {
        channel.attr(Attributes.LOGIN).set(true);
    }

    public static boolean hasLogin(Channel channel) {
        Attribute<Boolean> loginAttribute = channel.attr(Attributes.LOGIN);
        return loginAttribute.get() != null;
    }
}
