import java.io.File;

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
                listFiles(fileEntry, inputDir, outputDir, maxSize);
            } else {
                System.out.println(fileEntry.getName());
                compressFile(fileEntry, inputDir, outputDir, maxSize);
            }
        }
    }

    static void compressFile(final File file, String inputDir, String outputDir, int maxSize) {
        System.out.println("compress code");
        // compress from input dir/file to output dir/file

    }

    static void decompressDir(String inputDir, String outputDir) {
        System.out.println("DeCompress");
    }

}
