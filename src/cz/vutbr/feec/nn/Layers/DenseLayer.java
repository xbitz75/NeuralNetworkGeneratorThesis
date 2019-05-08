package cz.vutbr.feec.nn.Layers;

import java.util.Random;

import cz.vutbr.feec.nn.NetworkGenerator;

public class DenseLayer extends AbstractLayer {
	private enum ACTIVATION {
		relu, tanh, sigmoid
	}

	private int maxUnits;
	private int units;
	private ACTIVATION activation;

	public DenseLayer(int id, NetworkGenerator network, int maxUnits) {
		super(id, network);
		this.maxUnits = maxUnits;
		layerType = "Dense";
		createConnections();
		setShapesFromPrevLayer();
		if (network.getDimensions() == 1) {
			shape0 = units;
		} else if (network.getDimensions() == 2) {
			shape1 = units;
		} else if (network.getDimensions() == 3) {
			shape2 = units;
		}
	}

	@Override
	public String build() {
		return "layer_" + String.format("%03d", id) + " = Dense(" + units + ", activation='" + activation + "')("
				+ getPreviousLayers()[0].getLayerId() + ")";
	}

	// randomly selects activation function and number of units, fills list of
	// previous Layers
	@Override
	protected void createConnections() {
		activation = ACTIVATION.values()[new Random().nextInt(ACTIVATION.values().length)];
		units = (int) Math.round((Math.random() * ((maxUnits - 100) + 1)) + 100);
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}

	@Override
	public String toString() {
		return "DenseLayer [id=" + id + "]";
	}

}
