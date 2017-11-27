package org.ps.example.demo03.domain.xpath;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.ArrayList;
import java.util.List;

/**
 * 对应xml配置项中的col节点
 */
public class Column {

    private String colName;

    @JSONField(serialize = false)
    private String type;

    @JSONField(serialize = false)
    private String handler;

    @JSONField(serialize = false)
    private String method;

    private String value;

    @JSONField(serialize = false)
    private List<Xpath> xpaths = new ArrayList<>();

    public List<Xpath> getXpaths() {
        return xpaths;
    }

    public void setXpaths(List<Xpath> xpaths) {
        this.xpaths = xpaths;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColName() {
        return colName;
    }

    public void setColName(String colName) {
        this.colName = colName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getHandler() {
        return handler;
    }

    public void setHandler(String handler) {
        this.handler = handler;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

}
