package cz.vutbr.feec.nn.Layers;

import cz.vutbr.feec.nn.NetworkGenerator;

public class MnistBlock extends AbstractLayer {

	private int neurons;
	private float droupoutRate;

	public MnistBlock(int id, NetworkGenerator network) {
		super(id, network);
		droupoutRate = (float) Math.random(); // randomly generated rate parameter for dropout
		neurons = (int) Math.round((Math.random() * ((700 - 200) + 1)) + 200); // randomly generated amount of
																				// neurons
		layerType = "MnistBlock";
		createConnections();
		setShapesFromPrevLayer();
		shape1 = neurons;

	}

	@Override
	public String build() {
		return "mnist_block02 = Dense(" + neurons + ", activation=\"relu\")(" + getPreviousLayers()[0].getLayerId()
				+ ")\n" + "mnist_block03 = Dropout(" + droupoutRate + ")(mnist_block02)\n" + "layer_"
				+ String.format("%03d", id) + " = Dense(" + neurons
				+ ", activation=\"softmax\")(mnist_block03) # last Mnist block";
	}

	@Override
	protected void createConnections() {
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}

	@Override
	public String toString() {
		return "MnistBlock [id=" + id + "]";
	}

}

