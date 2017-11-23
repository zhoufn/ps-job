package org.ps.example.demo03.handler;

import org.ps.platform.core.Task;

/**
 * 扫描Handler
 */
public abstract class ScanHandler {

    /**
     * 获取指定路径下的指定后缀的文件
     *
     * @param srcDir
     * @param suffix
     */
    public abstract void scan(Task waitingTask,String srcDir, String suffix);


}
