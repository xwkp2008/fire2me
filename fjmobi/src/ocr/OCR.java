package ocr;

/*
 * OCR.java
 *
 * Created on December 24, 2007, 12:38 AM
 */

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class OCR {
	protected transient final Logger logger = Logger.getLogger(OCR.class);
	private final String LANG_OPTION = "-l";
	private final String EOL = System.getProperty("line.separator");
	
	private String getOS() {
		String os = System.getProperty("os.name");
		if (os.startsWith("Windows"))
			return "W";
		else if (os.startsWith("Linux"))
			return "L";
		return "U";
	}
	
	public String recognizeText(File imageFile, String imageFormat)
			throws Exception {
		File tempImage = ImageIOHelper.createImage(imageFile, imageFormat);

		File outputFile = new File(imageFile.getParentFile(), "output");
		StringBuffer strB = new StringBuffer();
		
		
		List<String> cmd = new ArrayList<String>();
		if(getOS().equals("W")){
			String tessPath = new File("tesseract").getAbsolutePath();
			cmd.add(tessPath+"/"+"tesseract");
		}else{
			cmd.add("tesseract");
		}
		cmd.add("");
		cmd.add(outputFile.getName());
		cmd.add(LANG_OPTION);
		cmd.add("eng");

		ProcessBuilder pb = new ProcessBuilder();
		pb.directory(imageFile.getParentFile());

		cmd.set(1, tempImage.getName());
		pb.command(cmd);
		pb.redirectErrorStream(true);
		Process process = pb.start();

		int w = process.waitFor();
		logger.debug("Exit value = {"+w+"}");

		// delete temp working files
		//tempImage.delete();

		if (w == 0) {
			BufferedReader in = new BufferedReader(new InputStreamReader(
					new FileInputStream(outputFile.getAbsolutePath() + ".txt"),
					"UTF-8"));

			String str;

			while ((str = in.readLine()) != null) {
				strB.append(str).append(EOL);
			}
			in.close();
		} else {
			String msg;
			switch (w) {
			case 1:
				msg = "Errors accessing files. There may be spaces in your image's filename.";
				break;
			case 29:
				msg = "Cannot recognize the image or its selected region.";
				break;
			case 31:
				msg = "Unsupported image format.";
				break;
			default:
				msg = "Errors occurred.";
			}
			tempImage.delete();
			throw new RuntimeException(msg);
		}

		new File(outputFile.getAbsolutePath() + ".txt").delete();
		logger.info("图像识别结果:{"+strB.toString().trim()+"}");
		return strB.toString().trim();
	}
}
