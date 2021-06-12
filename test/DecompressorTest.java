import src.Decompressor;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DecompressorTest {
	private static int readBufferSize = 1024;
	private static String filePrefix = "file";
	private static String fileSuffix = ".agozip";
	private static String cwd = System.getProperty("user.dir");
	private static String testInputDir = "/inputs/decompress/";
	private static String testOutputDir = "/inputs/output/";
	private static String outputFilePrefix = "decomp_";

	@Test
	public void testDecompressSingleLayerDirectory() {
		String inputFolderName = "test_0";
		Decompressor decompressor = new Decompressor(
			cwd + testInputDir + inputFolderName,
			cwd + testOutputDir + outputFilePrefix + inputFolderName,
			readBufferSize,this.filePrefix, this.fileSuffix);
		assertEquals(decompressor.decompress(), true);
	}

	@Test
	public void testDecompressDoubleLayerDirectory() {
		String inputFolderName = "test_1";
		Decompressor decompressor = new Decompressor(
			cwd + testInputDir + inputFolderName,
			cwd + testOutputDir + outputFilePrefix + inputFolderName,
			readBufferSize,this.filePrefix, this.fileSuffix);
		assertEquals(decompressor.decompress(), true);
	}

		@Test
	public void testDecompressTripleLayerDirectory() {
		String inputFolderName = "test_2";
		Decompressor decompressor = new Decompressor(
			cwd + testInputDir + inputFolderName,
			cwd + testOutputDir + outputFilePrefix + inputFolderName,
			readBufferSize,this.filePrefix, this.fileSuffix);
		assertEquals(decompressor.decompress(), true);
	}

}
