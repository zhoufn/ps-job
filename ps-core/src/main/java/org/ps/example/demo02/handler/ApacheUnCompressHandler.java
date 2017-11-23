package org.ps.example.demo02.handler;

import org.apache.commons.compress.archivers.ArchiveEntry;
import org.apache.commons.compress.archivers.ArchiveInputStream;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.archivers.zip.ZipArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * Apache common compress解压
 */
public class ApacheUnCompressHandler extends UnCompressHandler {
    /**
     * 解压
     *
     * @param srcFilePath
     * @param destDir
     * @throws Exception
     */
    @Override
    public void unCompress(String srcFilePath, String destDir) throws Exception {
        FileInputStream fis = new FileInputStream(new File(srcFilePath));
        ArchiveInputStream ais = null;
        if (srcFilePath.endsWith(".zip")) {
            ais = new ZipArchiveInputStream(fis);
        } else if (srcFilePath.endsWith(".gz")) {
            ais = new TarArchiveInputStream(fis);
        } else if (srcFilePath.endsWith(".tar")) {
            GzipCompressorInputStream zci = new GzipCompressorInputStream(fis,true);
            ais = new TarArchiveInputStream(zci);
        }
        ArchiveEntry entry = null;
        while ((entry = ais.getNextEntry()) != null) {
            String name = entry.getName();
            String tempDir = destDir + File.separator + name;
            if (entry.isDirectory()) {
                continue;
            }
            File tempFile = new File(tempDir);
            if(!tempFile.exists()){
                if(!tempFile.getParentFile().exists())
                    tempFile.getParentFile().mkdirs();
                tempFile.createNewFile();
            }
            OutputStream os = new FileOutputStream(tempFile);
            int b ;
            byte[] bytes = new byte[1024];
            while((b=ais.read(bytes)) != -1){
                os.write(bytes,0,b);
            }
            os.flush();
            os.close();
        }
        ais.close();
        fis.close();
    }
}
