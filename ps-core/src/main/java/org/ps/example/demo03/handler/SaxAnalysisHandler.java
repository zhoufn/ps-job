package org.ps.example.demo03.handler;

import org.apache.commons.lang3.StringUtils;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.ps.example.demo03.domain.xpath.Column;
import org.ps.example.demo03.domain.xpath.Table;
import org.ps.example.demo03.domain.xpath.Xpath;
import org.springframework.stereotype.Component;
import org.xml.sax.InputSource;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用apache xpath 流式解析xml
 */
@Component
public class SaxAnalysisHandler extends AnalysisHandler {

    /**
     * 初始化规则库
     *
     * @param roleTableFile 规则配置文件
     * @return 规则集合
     */
    private  List<Table> initTables(String roleTableFile) throws Exception {
        List<Table> tables = new ArrayList<>();
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(roleTableFile);
        SAXReader reader = new SAXReader();
        Document document = reader.read(is);
        is.close();
        Element rootElement = document.getRootElement();
        List<Element> tableElements = rootElement.elements("table");
        for (Element tableElement : tableElements) {
            Table table = new Table();
            table.setName(tableElement.attributeValue("name"));
            table.setContextPath(tableElement.attributeValue("contextPath"));
            List<Column> columns = new ArrayList<>();
            List<Element> columnElements = tableElement.elements("col");
            for (Element columnElement : columnElements) {
                Column column = new Column();
                column.setColName(columnElement.attributeValue("colName"));
                column.setType(columnElement.attributeValue("type"));
                column.setHandler(columnElement.attributeValue("handler"));
                column.setMethod(columnElement.attributeValue("method"));
                List<Xpath> xpaths = new ArrayList<>();
                Element valueElement = columnElement.element("value");
                if (valueElement == null) {
                    continue;
                }
                List<Element> xpathElements = valueElement.elements("xpath");
                for (Element xpathElement : xpathElements) {
                    Xpath xpath = new Xpath();
                    xpath.setXpath(getRelativePath(table.getContextPath(), xpathElement.attributeValue("xpath")));
                    xpath.setNodeType(xpathElement.attributeValue("nodeType"));
                    xpaths.add(xpath);
                }
                column.setXpaths(xpaths);
                columns.add(column);
            }
            table.setColumns(columns);
            tables.add(table);
        }
        return tables;
    }


    /**
     * 解析
     */
    @Override
    public void analysis(String xpathConfig, String srcFile) {
        SAXReader reader = new SAXReader();
        reader.setValidation(false);
        // 忽略DTD检查
        reader.setEntityResolver((String publicId, String systemId) -> {
            return new InputSource(new ByteArrayInputStream("<?xml version='1.0' encoding='UTF-8'?>".getBytes()));
        });
        reader.setDefaultHandler(new ElementHandler() {
            @Override
            public void onStart(ElementPath elementPath) {
            }

            @Override
            public void onEnd(ElementPath elementPath) {
                Element element = elementPath.getCurrent();
                //专利节点
                if (element.getName().equals("tsip")) {
                    try {
                        List<Table> roleTables = initTables(xpathConfig);
                        //库节点
                        for (Table table : roleTables) {
                            List<Column> columns = table.getColumns();
                            //列节点
                            for (Column column : columns) {
                                //列下的Xpath节点
                                List<Xpath> xpaths = column.getXpaths();
                                for (Xpath xpath : xpaths) {
                                    if ("STRING".equals(xpath.getNodeType())) {
                                        Node node = element.selectSingleNode(xpath.getXpath());
                                        if (node == null) {
                                            xpath.setValue(null);
                                        } else {
                                            xpath.setValue(node.getText());
                                        }
                                    } else if ("NODEDOM".equals(xpath.getNodeType())) {
                                        Node node = element.selectSingleNode(xpath.getXpath());
                                        xpath.setNode(node);
                                    } else if ("XMLSTRING".equals(xpath.getNodeType())) {
                                        Node node = element.selectSingleNode(xpath.getXpath());
                                        if (node == null)
                                            xpath.setValue(null);
                                        else
                                            xpath.setValue(node.asXML());
                                    } else if ("CONST".equals(xpath.getNodeType())) {
                                        xpath.setValue(xpath.getXpath());
                                    }
                                }
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    element.detach();
                }
            }
        });
        try {
            InputStream inputStream = new FileInputStream(srcFile);
            reader.read(inputStream);
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取相对于实体节点的路径
     *
     * @param contextPath 上下文路径 /a/b/c/d
     * @param tempPath    相对于上下文的相对路径，类似:../../../test
     * @return /a/test
     */
    private static String getRelativePath(String contextPath, String tempPath) {
        if (StringUtils.isBlank(tempPath)) {
            return tempPath;
        }
        if (StringUtils.isBlank(contextPath)) {
            return tempPath;
        }
        if (".".equals(tempPath)) {
            return contextPath;
        } else if (tempPath.startsWith("./")) {
            tempPath = tempPath.substring(1, tempPath.length());
            return contextPath + tempPath;
        } else if (tempPath.startsWith("..")) {
            String[] tempPathNodes = tempPath.split("/");
            int i = 0;
            List<String> pathNodes = new ArrayList<>();
            for (String tempPathNode : tempPathNodes) {
                if ("..".equals(tempPathNode)) {
                    i++;
                } else {
                    pathNodes.add(tempPathNode);
                }
            }
            String[] contextPathNodes = contextPath.split("/");
            String path = "";
            for (int j = 0, l = contextPathNodes.length - i; j < l; j++) {
                path += contextPathNodes[j] + "/";
            }
            for (String pathNode : pathNodes) {
                path += pathNode + "/";
            }
            path = path.substring(0, path.length() - 1);
            return path;
        }
        return "";
    }
}
