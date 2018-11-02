package cz.vutbr.feec.nn;

import java.io.FileOutputStream;
import java.io.IOException;

public class FileGenerator {
	private String fileName;
	private String fileData;

	public FileGenerator(String fileName, String fileData) {
		this.fileName = fileName;
		this.fileData = fileData;
	}

	public void generateFile() throws IOException {
		FileOutputStream fos = new FileOutputStream(fileName);
		fos.write(fileData.getBytes());
		fos.flush();
		fos.close();
		System.out.println("Model succesfuly saved to " + fileName + " in project folder");
	}
}
