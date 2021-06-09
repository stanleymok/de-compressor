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
            decompressFileDriver(args[0], args[1]);
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
            FileOutputStream fileOutStream = new FileOutputStream(outputDir + "/" + "file.agozip");
            ZipOutputStream zipOut = new ZipOutputStream(fileOutStream);
            compressFile(new File(inputDir), outputDir, maxBytes, zipOut);
            zipOut.close();
            fileOutStream.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    static void compressFile(File fileToZip, String outputDir, int maxBytes, 
                                ZipOutputStream zipOut) throws IOException {
        String absFileName = fileToZip.getAbsolutePath();
        if (fileToZip.isHidden())
            return;
        if (fileToZip.isDirectory()) {
            if (absFileName.endsWith("/")) {
                zipOut.putNextEntry(new ZipEntry(absFileName));
            } else { 
                zipOut.putNextEntry(new ZipEntry(absFileName + "/"));
            } 
            zipOut.closeEntry();
            for (File childFile : fileToZip.listFiles())
                compressFile(childFile, outputDir, maxBytes, zipOut);
            return;
        } else {
            FileInputStream fileInStream = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(absFileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[16384];
            int length;
            while ((length = fileInStream.read(bytes)) >= 0) {
                System.out.println("compressing");
                zipOut.write(bytes, 0, length);
            }
            fileInStream.close();
        }
    }

    static void decompressFileDriver(String inputDir, String outputDir) {
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
                // fix for Windows-created archives
                File parent = newFile.getParentFile();
                if (!parent.isDirectory() && !parent.mkdirs()) {
                    throw new IOException("Failed to create directory " + parent);
                }
                
                // write file content
                FileOutputStream fos = new FileOutputStream(newFile);
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
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    public static File newFile(File destinationDir, ZipEntry zipEntry) throws IOException {
        File destFile = new File(destinationDir, zipEntry.getName());
        String destDirPath = destinationDir.getCanonicalPath();
        String destFilePath = destFile.getCanonicalPath();
        if (!destFilePath.startsWith(destDirPath + File.separator)) {
            throw new IOException("Entry is outside of the target dir: " + zipEntry.getName());
        }

        return destFile;
    }
}
