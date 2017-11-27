package org.ps.example.demo03.handler;

import java.io.InputStream;

/**
 * 解析基类
 */
public abstract class AnalysisHandler {


    /**
     * 解析
     * @param id 规则ID
     * @param inputStream 带解析文件流
     */
    public abstract void analysis(String id,InputStream inputStream);


}
