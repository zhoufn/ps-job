package org.ps.platform.util;

import java.util.UUID;

/**
 * 字符串工具类
 */
public class StringUtils {

    /**
     * 生成UUID
     * @return
     */
    public static String createUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }

}
