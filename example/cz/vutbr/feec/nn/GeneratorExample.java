package cz.vutbr.feec.nn;

import java.io.IOException;

public class GeneratorExample {
	private static int models;
	private static int capacity;
	private static int dimensions;
	private static String outputShape;
	private static Integer[] inputShape;

	public static void main(String[] args) throws IOException {

		// Network parameters
		models = 5; // number of generated models
		capacity = 10; // number of layers per model
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
		for (int i = 0; i < models; i++) {
			NetworkGenerator networkGenerator = new NetworkGenerator(capacity, dimensions, inputShape, outputShape);
			String code = networkGenerator.build();
			FileGenerator fileGenerator = new FileGenerator("python_model_" + i + ".py", code); // TODO create IoC version
			try {
				fileGenerator.generateFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}	
	
	
}
