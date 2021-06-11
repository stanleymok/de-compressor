package src;

import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipEntry;
import java.util.HashSet;

public class Decompressor {
    private String inputDir;
    private String outputDir;
    private int readBufferSize;
    private String prefix;
    private int fileNumber;
    private String suffix;
    private boolean isAppend = true;
    private HashSet<String> fileSet;

    public Decompressor(String inputDir, String outputDir, int readBufferSize,
                        String prefix, String suffix) {
        this.inputDir = inputDir;
        this.outputDir = outputDir;
        this.readBufferSize = readBufferSize;
        this.prefix = prefix;
        this.suffix = suffix;
        this.fileNumber = 1;
        fileSet = new HashSet<String>();
    }

    public void decompress() {
        File outFolder = new File(this.outputDir);
        if (!outFolder.exists())
            outFolder.mkdir();

        String zipFileStr = this.inputDir + "/" + this.prefix + this.fileNumber + this.suffix;
        File zipFile = new File(zipFileStr);
        while (zipFile.exists()) {
            try {
                ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileStr));
                ZipEntry zipEntry = zis.getNextEntry();
                byte[] buffer = new byte[this.readBufferSize];
                while (zipEntry != null) {
                    File newFile = newFile(outFolder, zipEntry);
                    // delete file if it exists, only once
                    if (newFile.exists() && !this.fileSet.contains(newFile.getAbsolutePath())) {
                        newFile.delete();
                        this.fileSet.add(newFile.getAbsolutePath());
                    }
                    if (zipEntry.isDirectory()) {
                        if (!newFile.isDirectory() && !newFile.mkdirs()) {
                            throw new IOException("Failed to create directory " + newFile);
                        }
                    } else {
                        File parent = newFile.getParentFile();
                        if (!parent.isDirectory() && !parent.mkdirs()) {
                            throw new IOException("Failed to create directory " + parent);
                        }
                        FileOutputStream fos = new FileOutputStream(newFile, this.isAppend);
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }
                        fos.close();
                    }
                zipEntry = zis.getNextEntry();
                }
                zis.closeEntry();
                zis.close();
                this.fileNumber++;
                zipFileStr = this.inputDir + "/" + this.prefix + this.fileNumber + this.suffix;
                zipFile = new File(zipFileStr);
            } catch (IOException e) {
                System.out.println(e);
            }
        }
    }

    private File newFile(File destDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destDir, zipEntry.getName());
        String destDirPath = destDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("WARNING: ZipSlip vulnerability. Entry is outside of the target dir: " + zipEntry.getName());
        }
        return destFile;
    }
}