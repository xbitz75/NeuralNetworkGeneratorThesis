package cz.vutbr.feec.nn;

import java.io.IOException;

public class GeneratorExample {
	public static void main(String[] args) throws IOException {

		Integer pocet_modelu = 5; // nastaveni poctu generovanych modelu

		for (int i = 0; i < pocet_modelu; i++) {
			// (int capacity, String inputShape, String outputShape)
			NetworkGenerator generator = new NetworkGenerator(8, "28,28", "10"); //shape=(100,1) default
			String code = generator.build();
			// System.out.println(code);

			FileGenerator fileGenerator = new FileGenerator("python_model" + i + ".py", code);
			fileGenerator.generateFile();

		}

	}
}

// TODO dropout
// TODO pooling
// TODO domluva