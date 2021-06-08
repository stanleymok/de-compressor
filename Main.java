import java.io.File;
import java.io.IOException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.util.zip.ZipOutputStream;
import java.util.zip.ZipEntry;


public class Main {
	public static void main(String[] args) {
        if (args.length == 3) {
            compressDir(args[0], args[1], args[2]);
        } else if (args.length == 2) {
            decompressDir(args[0], args[1]);
        } else {
            String helpMsg1 = "Compression   Usage: java Main <input dir> <output dir> <max compressed size in MB>";
            String helpMsg2 = "Decompression Usage: java Main <input dir> <output dir>";
            System.out.println(helpMsg1 + "\n" + helpMsg2);
        }
	}

    static void compressDir(String inputDir, String outputDir, String maxComprSize) {
        int maxSize = Integer.parseInt(maxComprSize);  
        final File folder = new File(inputDir);
        listFiles(folder, inputDir, outputDir, maxSize);
    }

    static void listFiles(final File folder, String inputDir, String outputDir, int maxSize) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                listFiles(new File(fileEntry.getAbsolutePath()), 
                                    fileEntry.getAbsolutePath(), outputDir, maxSize);
            } else {
                System.out.println(fileEntry.getName());
                compressFile(fileEntry.getName(), inputDir, outputDir, maxSize);
            }
        }
    }

    static void compressFile(String fileName, String inputDir, String outputDir, int maxSize) {
        try {
            String outFileName = outputDir + "/" + fileName + ".zippy1";
            FileOutputStream fileOutStream = new FileOutputStream(outFileName);
            ZipOutputStream zipOut = new ZipOutputStream(fileOutStream);
            File fileToZip = new File(inputDir + "/" + fileName);
            FileInputStream fileInStream = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(outFileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while((length = fileInStream.read(bytes)) >= 0)
                zipOut.write(bytes, 0, length);
            zipOut.close();
            fileInStream.close();
            fileOutStream.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    static void decompressDir(String inputDir, String outputDir) {
        System.out.println("DeCompress");
    }

}
