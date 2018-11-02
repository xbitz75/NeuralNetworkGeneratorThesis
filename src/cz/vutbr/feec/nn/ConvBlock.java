package cz.vutbr.feec.nn;

import java.util.Random;

public class ConvBlock extends Layer {
	
	// TODO fix ValueError: Input 0 is incompatible with layer conv2d_1: expected ndim=4, found ndim=3

	private int neurons;
	private float droupoutRate;

	private enum ACTIVATION {
		relu, tanh, sigmoid
	}

	private String kernelSize = "(3, 3)";
	private ACTIVATION activation;

	public ConvBlock(int id, NetworkGenerator network) {
		super(id, network);
		this.droupoutRate = (float) Math.random(); // randomly generated rate parameter for dropout
		this.neurons = (int) Math.round((Math.random() * ((700 - 200) + 1)) + 200); // randomly generated amount of neurons
		layerType = LAYERTYPE.ConvBlock;
		randomize();
		// getting shape from network
		this.shape = network.getInputShape();

	}

	@Override
	public String build() {
		return "conv_block01 = Conv2D(" + neurons + ", " + kernelSize + ", padding=\"same\", activation=\"" + activation + "\")(" + getPreviousLayers()[0].getLayerId() + ")\n"
				+ "conv_block02 = Conv2D(" + neurons + ", " + kernelSize + ", activation=\"" + activation + "\")(conv_block01)\n" 
				+ "conv_block03 = MaxPooling2D(pool_size=(2,2))(conv_block02)\n" 
				+ "layer_" + String.format("%03d", id) + " = Dropout(" + droupoutRate + ")(conv_block03) # last Conv block";
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
		return "ConvBlock [id=" + id + "]";
	}

	@Override
	public int dimension() {
		return 2; // any
	}

}
