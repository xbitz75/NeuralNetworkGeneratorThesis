package cz.vutbr.feec.nn.layers;

import java.util.Random;

import cz.vutbr.feec.nn.NetworkGenerator;

public class Conv1DLayer extends AbstractLayer {
	private enum ACTIVATION {
		relu, tanh, sigmoid
	}

	private int neuronsUpperBound;
	private int neurons;
	private int kernelSize = 3;
	private ACTIVATION activation;

	public Conv1DLayer(int id, NetworkGenerator network, int neuronsUpperBound) {
		super(id, network);
		this.neuronsUpperBound = neuronsUpperBound;
		createConnections();
		layerType = "Conv1D";
		setShapesFromPrevLayer();
		shape0 = shape0 - (kernelSize-1);
		shape1 = neurons;
	}

	@Override
	public String build() {
			return "layer_" + String.format("%03d", id) + " = Conv1D(" + neurons + ", " + kernelSize + ", activation='"
					+ activation + "')(" + getPreviousLayers()[0].getLayerId() + ")";
	}

	// randomly selects activation function and number of neurons, fills list of previous layers
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
		return "Conv1D [id=" + id + "]";
	}


}
