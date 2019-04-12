package cz.vutbr.feec.nn;

import java.io.IOException;
import java.util.stream.IntStream;

public class GeneratorExample {
	private static int capacity;
	private static int dimensions;
	private static String outputShape;
	private static Integer[] inputShape;

	public static void main(String[] args) {

		// Network parameters
		int models = 5; // number of generated models
		capacity = 15; // number of Layers/blocks per model
		inputShape = new Integer[] { 128, 128, 3, 0 }; // input
		dimensions = 3; // number of inputs dimensions
		outputShape = "3472"; // output of NN or "siamese"
				
		generate(models);
	}
	
	/**
	 * Creates .py files with neural network architectures
	 * 
	 * @param models
	 * 			  - number of generated models
	 **/
	private static void generate(int models) {
		IntStream.range(0, models).forEach(i -> {
			NetworkGenerator networkGenerator = new NetworkGenerator(capacity, dimensions, inputShape, outputShape);
			String code = networkGenerator.build();
			FileGenerator fileGenerator = new FileGenerator(String.format("python_model_%d.py", i), code);
			try {
				fileGenerator.generateFile();
			} catch (IOException e) {
				System.out.println("file generator failed");
				e.printStackTrace();
			}
		});
	}	
	
	
}
