
public class Main {
	public static void main(String[] args) {
        if (args.length == 3) {
            System.out.println("Compress");
        } else if (args.length == 2) {
            System.out.println("DeCompress");
        } else {
            System.out.println("Compression   Usage: java Main <input dir> <output dir> <max compressed size in MB> \nDecompression Usage: java Main <input dir> <output dir> ");
        }
	}
}
