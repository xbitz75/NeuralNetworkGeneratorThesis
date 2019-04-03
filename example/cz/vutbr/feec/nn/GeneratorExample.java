package cz.vutbr.feec.nn;

import java.io.IOException;

import cz.vutbr.feec.nn.Interfaces.INetworkGenerator;

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
		outputShape = "siamese"; // output of NN or "siamese"
		
		// Bellow create new instances of INetworkGenerator and IFileGenerator to simulate IoC
		NetworkGenerator networkGenerator = new NetworkGenerator(capacity, dimensions, inputShape, outputShape);
		// Above create new instances of INetworkGenerator and IFileGenerator to simulate IoC
		
		generate(models, networkGenerator);
	}
	
	/**
	 * Creates .py files with neural network architectures
	 * 
	 * @param models
	 * 			  - number of generated models
	 * @param networkGenerator
	 *            - instance of INetworkGenerator
	 **/
	private static void generate(int models, INetworkGenerator networkGenerator) {
		for (int i = 0; i < models; i++) {
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
