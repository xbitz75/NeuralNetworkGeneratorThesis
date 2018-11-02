package cz.vutbr.feec.nn;

import java.util.Random;

public class DenseLayer extends Layer {
	private enum ACTIVATION {
		relu, tanh, sigmoid
	}

	private int maxNeurons;
	private int neurons;
	private ACTIVATION activation;

	public DenseLayer(int id, NetworkGenerator network, int maxNeurons) {
		super(id, network);
		this.maxNeurons = maxNeurons;
		layerType = LAYERTYPE.Dense;
		randomize();
		// z predchozi vrstvy prevezme jeji tvar
		this.shape = getPreviousLayers()[0].getLayerShape();
	}

	@Override
	public String build() {
		if (getPreviousLayers()[0].dimension() > 1) {
			return "layer_" + String.format("%03d", id) + "b = Flatten()(" + getPreviousLayers()[0].getLayerId() + ")\n"
					+ "layer_" + String.format("%03d", id) + " = Dense(" + neurons + ", activation='" + activation
					+ "')(" + getPreviousLayers()[0].getLayerId() + "b)";
		} else {
			return "layer_" + String.format("%03d", id) + " = Dense(" + neurons + ", activation='" + activation + "')("
					+ getPreviousLayers()[0].getLayerId() + ")";
		}
	}

	@Override
	public void randomize() {
		activation = ACTIVATION.values()[new Random().nextInt(ACTIVATION.values().length)];
		neurons = new Random().nextInt(maxNeurons) + 1;
		for (int i = 0; i < prevLayers.size(); i++) {
			randomize(i);
		}
	}

	@Override
	public String toString() {
		return "DenseLayer [id=" + id + "]";
	}

	@Override
	public int dimension() {
		return 1;
	}

}
