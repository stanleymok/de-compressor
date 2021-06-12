package src;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;

public class Compressor {
    private int maxBytes;
    private File outFolder;
    private String outputDir;
    private String inputDir;
    private FileOutputStream fos;
    private ZipOutputStream zos;
    private int readBufferSize;
    private String prefix;
    private int fileNumber;
    private String suffix;

    public Compressor(String inputDir, String outputDir, String maxMB, 
                        int readBufferSize, String prefix, String suffix) {
        this.maxBytes = (int) (Double.parseDouble(maxMB) * 1000000); 
        this.outFolder = new File(outputDir);
        this.outputDir = outputDir;
        this.inputDir = inputDir;
        this.readBufferSize = readBufferSize;
        this.prefix = prefix;
        this.suffix = suffix;
        this.fileNumber = 1;
    }

    public boolean compress() {
        if (!this.outFolder.exists())
            this.outFolder.mkdir();
        try {
            this.fos = new FileOutputStream(this.outputDir + "/" + 
                                            this.prefix + this.fileNumber + this.suffix);
            this.zos = new ZipOutputStream(this.fos);
            File fileToZip = new File(this.inputDir);
            compressFile(fileToZip, "", this.outputDir);
            this.zos.close();
        } catch (IOException e) {
            System.out.println(e);
            return false;
        }
        return true;
    }

    private void compressFile(File fileToZip, String relFileName, String outputDir) throws IOException {
        if (fileToZip.isHidden())
            return;
        if (fileToZip.isDirectory()) {
            for (File childFile : fileToZip.listFiles()) {
                compressFile(childFile, relFileName + 
                            "/" + childFile.getName(), this.outputDir);
            }
            return;
        } else {
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(relFileName);
            this.zos.putNextEntry(zipEntry);
            byte[] bytes = new byte[this.readBufferSize];
            int length;
            File zipFile = new File(this.outputDir + "/" + 
                                    this.prefix + this.fileNumber + this.suffix);
            while ((length = fis.read(bytes)) >= 0) {
                if ((zipFile.length() + this.readBufferSize) >= this.maxBytes) {
                    this.zos.close();
                    this.fileNumber++;
                    this.fos = new FileOutputStream(this.outputDir + "/" + 
                                                    this.prefix + this.fileNumber + this.suffix);
                    this.zos = new ZipOutputStream(this.fos); 
                    zipEntry = new ZipEntry(relFileName);
                    this.zos.putNextEntry(zipEntry);
                    zipFile = new File(this.outputDir + "/" + 
                                        this.prefix + this.fileNumber + this.suffix);
                }
                this.zos.write(bytes, 0, length);
            }
            fis.close();
        }
    }
}