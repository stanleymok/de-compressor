import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;

public class Decompressor {
    private String inputDir;
    private String outputDir;
    private int readBufferSize;
    private String prefix;
    private int fileNumber;
    private String suffix;

    public Decompressor(String inputDir, String outputDir, int readBufferSize,
                        String prefix, String suffix) {
        this.inputDir = inputDir;
        this.outputDir = outputDir;
        this.readBufferSize = readBufferSize;
        this.prefix = prefix;
        this.suffix = suffix;
        this.fileNumber = 1;
    }

    public void decompress() {
        File outFolder = new File(this.outputDir);
        if (!outFolder.exists())
            outFolder.mkdir();

        String zipFileStr = this.inputDir + "/" + this.prefix + this.fileNumber + this.suffix;
        File zipFile = new File(zipFileStr);
        if (zipFile.exists()) {
            try {
                ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFileStr));
                ZipEntry zipEntry = zis.getNextEntry();
                byte[] buffer = new byte[this.readBufferSize];
                while (zipEntry != null) {
                    File newFile = newFile(outFolder, zipEntry);
                    if (zipEntry.isDirectory()) {
                        if (!newFile.isDirectory() && !newFile.mkdirs()) {
                            throw new IOException("Failed to create directory " + newFile);
                        }
                    } else {
                        File parent = newFile.getParentFile();
                        if (!parent.isDirectory() && !parent.mkdirs()) {
                            throw new IOException("Failed to create directory " + parent);
                        }
                        FileOutputStream fis = new FileOutputStream(newFile);
                        int len;
                        while ((len = zis.read(buffer)) > 0)
                            fis.write(buffer, 0, len);
                        fis.close();
                    }
                zipEntry = zis.getNextEntry();
                }
                zis.closeEntry();
                zis.close();
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