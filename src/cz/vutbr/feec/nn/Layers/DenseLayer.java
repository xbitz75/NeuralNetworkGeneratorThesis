package cz.vutbr.feec.nn.Layers;

import java.util.Random;

import cz.vutbr.feec.nn.NetworkGenerator;

public class DenseLayer extends AbstractLayer {
	private enum ACTIVATION {
		relu, tanh, sigmoid
	}

	private int neuronsUpperBound;
	private int neurons;
	private ACTIVATION activation;

	public DenseLayer(int id, NetworkGenerator network, int neuronsUpperBound) {
		super(id, network);
		this.neuronsUpperBound = neuronsUpperBound;
		layerType = "Dense";
		createConnections();
		setShapesFromPrevLayer();
		if (network.getDimensions() == 1) {
			shape0 = neurons;
		} else if (network.getDimensions() == 2) {
			shape1 = neurons;
		} else if (network.getDimensions() == 3) {
			shape2 = neurons;
		}
		// network.setShape1(neurons);
	}

	@Override
	public String build() {
		return "layer_" + String.format("%03d", id) + " = Dense(" + neurons + ", activation='" + activation + "')("
				+ getPreviousLayers()[0].getLayerId() + ")";
	}

	// randomly selects activation function and number of neurons, fills list of
	// previous Layers
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
		return "DenseLayer [id=" + id + "]";
	}

}
