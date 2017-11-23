package org.ps.example.demo02.handler;

/**
 * 加压文件抽象类
 */
public abstract class UnCompressHandler {

    /**
     * 解压
     * @param srcFilePath
     * @param destDir
     * @throws Exception
     */
    public abstract void unCompress(String srcFilePath,String destDir) throws Exception;

}
