
public class Main {
	public static void main(String[] args) {
        if (args.length == 3) {
            System.out.println("Compress");
            compress(args[0], args[1], args[2]);
        } else if (args.length == 2) {
            System.out.println("DeCompress");
            decompress(args[0], args[1]);
        } else {
            System.out.println("Compression   Usage: java Main <input dir> <output dir> <max compressed size in MB> \nDecompression Usage: java Main <input dir> <output dir> ");
        }
	}

    static void compress(String inputDir, String outputDir, String maxComprSize) {
        int maxSize = Integer.parseInt(maxComprSize);  
        System.out.println(maxSize);
    }

    static void decompress(String inputDir, String outputDir) {
        System.out.println("DeCompress");
    }

}
