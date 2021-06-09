import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;

public class Main {

	public static void main(String[] args) {
        if (args.length == 3) {
            compressFileDriver(args[0], args[1], args[2]);
        } else if (args.length == 2) {
            decompressFile(args[0], args[1]);
        } else {
            String helpMsg1 = "Compression   Usage: java Main <input dir> <output dir> <max compressed size in MB>";
            String helpMsg2 = "Decompression Usage: java Main <input dir> <output dir>";
            System.out.println(helpMsg1 + "\n" + helpMsg2);
        }
	}

    static void compressFileDriver(String inputDir, String outputDir, String maxMB) {
        int maxBytes = Integer.parseInt(maxMB) + 1000000; 
        File outFolder = new File(outputDir);
        if (!outFolder.exists())
            outFolder.mkdir();
        try {
            FileOutputStream fis = new FileOutputStream(outputDir + "/" + "file.agozip");
            ZipOutputStream zos = new ZipOutputStream(fis);
            File fileToZip = new File(inputDir);
            compressFile(fileToZip, "", outputDir, maxBytes, zos);
            zos.close();
            fis.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    static void compressFile(File fileToZip, String fileName, String outputDir, int maxBytes, 
                                ZipOutputStream zos) throws IOException {
        if (fileToZip.isHidden())
            return;
        if (fileToZip.isDirectory()) {
            for (File childFile : fileToZip.listFiles()) {
                compressFile(childFile, fileName + "/" + childFile.getName(), 
                                                    outputDir, maxBytes, zos);
            }
            return;
        } else {
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileName);
            zos.putNextEntry(zipEntry);
            byte[] bytes = new byte[16384];
            int length;
            File zipFile = new File(outputDir + "/" + "file.agozip");
            while ((length = fis.read(bytes)) >= 0) {
                System.out.println(zipFile.length());
                zos.write(bytes, 0, length);
            }
            fis.close();
        }
    }

    static void decompressFile(String inputDir, String outputDir) {
        File outFolder = new File(outputDir);
        if (!outFolder.exists())
            outFolder.mkdir();

        String ZipFile = inputDir + "/" + "file.agozip";
        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(ZipFile));
            ZipEntry zipEntry = zis.getNextEntry();

            byte[] buffer = new byte[16384];
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

    public static File newFile(File destDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destDir, zipEntry.getName());
        String destDirPath = destDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("WARNING: ZipSlip vulnerability. Entry is outside of the target dir: " + zipEntry.getName());
        }
        return destFile;
    }
}
