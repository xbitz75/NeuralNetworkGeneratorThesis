package cz.vutbr.feec.nn.Layers;

import java.util.Random;

import cz.vutbr.feec.nn.NetworkGenerator;

public class Conv2DLayer extends AbstractLayer {
	private enum ACTIVATION {
		relu, tanh, sigmoid
	}

	private int neuronsUpperBound;
	private int neurons;
	private String kernelSize = "(3, 3)";
	private ACTIVATION activation;

	public Conv2DLayer(int id, NetworkGenerator network, int neuronsUpperBound) {
		super(id, network);
		this.neuronsUpperBound = neuronsUpperBound;
		createConnections();
		layerType = "Conv2D";
		setShapesFromPrevLayer();
		shape0 = shape0 - (3 - 1); // TODO make kernelSize a variable
		shape1 = shape1 - (3 - 1); // TODO (kernelSize - 1)
		shape2 = neurons;
	}

	@Override
	public String build() {
		return "layer_" + String.format("%03d", id) + " = Conv2D(" + neurons + ", " + kernelSize + ", activation='"
				+ activation + "')(" + getPreviousLayers()[0].getLayerId() + ")";
	}

	// randomly selects activation function and number of neurons, fills list of previous Layers
	@Override
	protected void createConnections() {
		activation = ACTIVATION.values()[new Random().nextInt(ACTIVATION.values().length)];
		neurons = new Random().nextInt(neuronsUpperBound) + 1;
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}

	@Override
	public String toString() {
		return "Conv2D [id=" + id + "]";
	}

}
