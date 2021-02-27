package com.kuma.im.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

/**
 * ID 工具类
 *
 * @author kuma 2021-02-27
 */
@UtilityClass
public class IDUtils {

    public static String randomId() {
        return UUID.randomUUID().toString().split("-")[0];
    }
}
