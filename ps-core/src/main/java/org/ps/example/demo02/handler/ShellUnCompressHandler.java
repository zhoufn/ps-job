package org.ps.example.demo02.handler;

import org.springframework.stereotype.Component;

/**
 * linux解压
 */
@Component
public class ShellUnCompressHandler extends UnCompressHandler {

    /**
     * 解压
     *
     * @param srcFilePath
     * @param destDir
     * @throws Exception
     */
    @Override
    public void unCompress(String srcFilePath, String destDir) throws Exception {
        String shell = null;
        if (srcFilePath.toLowerCase().endsWith(".gz") || srcFilePath.toLowerCase().endsWith(".tar")) {
            shell = "tar -xvf " + srcFilePath + " -C " + destDir;
        } else if (srcFilePath.toLowerCase().endsWith(".zip")) {
            shell = "unzip " + srcFilePath + " -d " + destDir;
        } else if (srcFilePath.toLowerCase().endsWith(".gz")) {
            shell = "gunzip " + srcFilePath + " > " + destDir;
        }
        Process process = Runtime.getRuntime().exec(shell);
        process.waitFor();
    }
}
