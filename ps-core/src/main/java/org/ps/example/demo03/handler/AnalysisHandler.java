package org.ps.example.demo03.handler;

import java.io.InputStream;

/**
 * 解析基类
 */
public abstract class AnalysisHandler {


    /**
     * 解析
     * @param xpathConfig 规则文件
     * @param srcFile 带解析文件
     */
    public abstract void analysis(String xpathConfig,String srcFile);


}
