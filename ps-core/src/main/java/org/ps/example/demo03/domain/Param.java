package org.ps.example.demo03.domain;

import lombok.Data;

/**
 * Analysis运行的参数
 */
@Data
public class Param {

    /**
     * 待扫描的XML文件的位置
     */
    private String srcDir;

    /**
     * DTD所在的位置
     */
    private String dtdDir;

}
