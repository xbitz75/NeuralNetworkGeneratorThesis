package cz.vutbr.feec.nn;

import java.util.Random;

public class MnistBlock extends Layer {

	private int neurons;
	private float droupoutRate;
	private enum ACTIVATION { // temp
		relu, tanh, sigmoid
	}
	private int kernelSize = 3; // temp
	private ACTIVATION activation; // temp

	public MnistBlock(int id, NetworkGenerator network) {
		super(id, network);
		this.droupoutRate = (float) Math.random(); // randomly generated rate parameter for dropout
		this.neurons = (int) Math.round((Math.random() * ((700 - 200) + 1)) + 200); // randomly generated amount of
																					// neurons
		layerType = LAYERTYPE.MnistBlock;
		randomize();
		// getting shape from network
		this.shape = network.getInputShape();

	}

	@Override
	public String build() {
		if (!network.getMnistBlockFlag()) {
			network.setMnistBlockFlag(true);
			return "mnist_block01 = Flatten()(" + getPreviousLayers()[0].getLayerId() + ")\n"
					+ "mnist_block02 = Dense(" + neurons + ", activation=\"relu\")(mnist_block01)\n"
					+ "mnist_block03 = Dropout(" + droupoutRate	+ ")(mnist_block02)\n"
					+ "layer_" + String.format("%03d", id) + " = Dense(" + neurons + ", activation=\"softmax\")(mnist_block03) # last Mnist block";
		} else {
			// TODO skips building, moves ID and previousLayer
			return "layer_" + String.format("%03d", id) + " = Conv1D(" + neurons + ", " + kernelSize + ", activation='"
					+ activation + "')(" + getPreviousLayers()[0].getLayerId() + ")";
		}

	}

	@Override
	public void randomize() {
		activation = ACTIVATION.values()[new Random().nextInt(ACTIVATION.values().length)];
		for (int i = 0; i < prevLayers.size(); i++) {
			randomize(i);
		}
	}

	@Override
	public String toString() {
		return "MnistBlock [id=" + id + "]";
	}

	@Override
	public int dimension() {
		return 2; // any
	}

}
