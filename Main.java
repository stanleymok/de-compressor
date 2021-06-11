

public class Main {

    private static int readBufferSize = 16384;
    private static String helpMsg1 = "Compression   Usage: java Main <input dir> <output dir> <max compressed size in MB>";
    private static String helpMsg2 = "Decompression Usage: java Main <input dir> <output dir>";
    private static String prefix = "file";
    private static String suffix = ".agozip";

	public static void main(String[] args) {
        if (args.length == 3) {
            Compressor compressor = new Compressor(args[0], args[1], args[2], 
                                                    readBufferSize, prefix, suffix);
            compressor.compress();
        } else if (args.length == 2) {
            Decompressor decompressor = new Decompressor(args[0], args[1], readBufferSize,
                                                            prefix, suffix);
            decompressor.decompress();
        } else {
            System.out.println(helpMsg1 + "\n" + helpMsg2);
        }
	}

}
