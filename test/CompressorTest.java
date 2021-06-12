import src.Compressor;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class CompressorTest {
	private static int readBufferSize = 1024;
	private static String filePrefix = "file";
	private static String fileSuffix = ".agozip";
	private static String cwd = System.getProperty("user.dir");
	private static String testInputDir = "/inputs/compress/";
	private static String testOutputDir = "/inputs/output/";
	private static String maxCompressedBytes = "0.1";
	private static String outputFilePrefix = "comp_";

	@Test
	public void testCompressSingleLayerDirectory() {
		String inputFolderName = "test_0";
		Compressor compressor = new Compressor(
			cwd + testInputDir + inputFolderName,
			cwd + testOutputDir + outputFilePrefix + inputFolderName,
			maxCompressedBytes, this.readBufferSize, this.filePrefix, this.fileSuffix);
		assertEquals(compressor.compress(), true);
	}

	@Test
	public void testCompressDoubleLayerDirectory() {
		String inputFolderName = "test_1";
		Compressor compressor = new Compressor(
			cwd + testInputDir + inputFolderName,
			cwd + testOutputDir + outputFilePrefix + inputFolderName,
			maxCompressedBytes, this.readBufferSize, this.filePrefix, this.fileSuffix);
		assertEquals(compressor.compress(), true);
	}

	@Test
	public void testCompressTripleLayerDirectory() {
		String inputFolderName = "test_2";
		Compressor compressor = new Compressor(
			cwd + testInputDir + inputFolderName,
			cwd + testOutputDir + outputFilePrefix + inputFolderName,
			maxCompressedBytes, this.readBufferSize, this.filePrefix, this.fileSuffix);
		assertEquals(compressor.compress(), true);
	}


}
