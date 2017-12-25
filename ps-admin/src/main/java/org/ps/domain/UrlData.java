package org.ps.domain;

import lombok.Data;

@Data
public class UrlData {
    /**
     * 节点metric url
     */
    private String url;
    /**
     * zk 下节点对应的数据
     */
    private String data;
}
