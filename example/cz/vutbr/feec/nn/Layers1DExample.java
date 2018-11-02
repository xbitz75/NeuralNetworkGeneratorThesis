package cz.vutbr.feec.nn;

public class Layers1DExample {
	public static void main(String[] args) {
		//												(int capacity, String inputShape, String outputShape)
		NetworkGenerator generator = new NetworkGenerator(50, "shape=(100,1)", "10");
		String code = generator.build();
		System.out.println(code);
	}
}
