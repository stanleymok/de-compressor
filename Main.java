import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;
//import Compressor;



public class Main {

    private static int readBufferSize = 16384;
    private String helpMsg1 = "Compression   Usage: java Main <input dir> <output dir> <max compressed size in MB>";
    private String helpMsg2 = "Decompression Usage: java Main <input dir> <output dir>";

	public static void main(String[] args) {
        if (args.length == 3) {
            Compressor compressor = new Compressor(args[0], args[1], args[2]);
            compressor.compress();
        } else if (args.length == 2) {
            decompressFile(args[0], args[1]);
        } else {
            System.out.println(helpMsg1 + "\n" + helpMsg2);
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

            byte[] buffer = new byte[readBufferSize];
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
