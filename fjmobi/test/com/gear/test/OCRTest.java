package com.gear.test;

import static org.junit.Assert.*;

import java.io.File;

import javax.imageio.ImageIO;

import ocr.OCR;

import org.junit.Test;

public class OCRTest {

	@Test
	public void testRecognizeText() {
		OCR ocr = new OCR();
		File file = new File("/home/linq/Workspace/temp/test.jpg");
		try {
			System.out.println(ocr.recognizeText(file, "jpeg"));

			String[] names = ImageIO.getWriterFormatNames();
			for (String name : names) {
				System.out.println(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail("识别失败");
		}
	}

}
