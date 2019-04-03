package cz.vutbr.feec.nn.Layers;

import java.util.Random;

import cz.vutbr.feec.nn.NetworkGenerator;

public class DenseLayer extends AbstractLayer {
	private enum ACTIVATION {
		relu, tanh, sigmoid
	}

	private int maxNeurons;
	private int neurons;
	private ACTIVATION activation;

	public DenseLayer(int id, NetworkGenerator network, int maxNeurons) {
		super(id, network);
		this.maxNeurons = maxNeurons;
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
	// previous layers
	@Override
	protected void createConnections() {
		activation = ACTIVATION.values()[new Random().nextInt(ACTIVATION.values().length)];
		neurons = new Random().nextInt(maxNeurons) + 40;
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}

	@Override
	public String toString() {
		return "DenseLayer [id=" + id + "]";
	}

}
