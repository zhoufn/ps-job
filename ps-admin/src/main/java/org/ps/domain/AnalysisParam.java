package org.ps.domain;

import lombok.Data;

/**
 * Analysis运行的参数
 */
@Data
public class AnalysisParam {

    /**
     * 待扫描的XML文件的位置
     */
    private String srcDir;

    /**
     * DTD所在的位置
     */
    private String dtdDir;

}
