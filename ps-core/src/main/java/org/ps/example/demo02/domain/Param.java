package org.ps.example.demo02.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * Task附件参数
 */
@Data
public class Param implements Serializable{
    /**
     * 带解压文件的存放路径
     */
    private String srcFilePath;

    /**
     * 过滤文件的后缀列表
     */
    private String[] srcFileSuffix = new String[]{".gz",".tar",".zip"};

    /**
     * 解压文件的存放路径
     */
    private String destFilePath;

    /**
     * 判断文件后缀是否满足要求
     * @param fileName
     * @return
     */
    public boolean isSrcFile(String fileName){
        for(String suffix : this.srcFileSuffix){
            if(suffix.equalsIgnoreCase(fileName)){
                return true;
            }
        }
        return false;
    }
}
