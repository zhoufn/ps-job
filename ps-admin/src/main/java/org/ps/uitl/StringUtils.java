package org.ps.uitl;

import java.util.UUID;

public class StringUtils {
    public static String createUUID(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
