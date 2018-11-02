package cz.vutbr.feec.nn;

import java.util.Random;

public class Conv2DLayer extends Layer {
	private enum ACTIVATION {
		relu, tanh, sigmoid
	}

	private int maxNeurons;
	private int neurons;
	private String kernelSize = "(3, 3)";
	private ACTIVATION activation;

	public Conv2DLayer(int id, NetworkGenerator network, int maxNeurons) {
		super(id, network);
		this.maxNeurons = maxNeurons;
		layerType = LAYERTYPE.Conv2D;
		randomize();
	}

	@Override
	public String build() {
		if (getPreviousLayers()[0].dimension() > 1) {
			return "layer_" + String.format("%03d", id) + "b = Flatten()(" + getPreviousLayers()[0].getLayerId() + ")\n"
					+ "layer_" + String.format("%03d", id) + " = Conv2D(" + neurons + ", " + kernelSize
					+ ", activation='" + activation + "')(" + getPreviousLayers()[0].getLayerId() + "b)";
		} else {
			return "layer_" + String.format("%03d", id) + " = Conv2D(" + neurons + ", " + kernelSize + ", activation='"
					+ activation + "')(" + getPreviousLayers()[0].getLayerId() + ")";
		}
	}

	// nahodne nastaveni activacni funkce a poctu neuronu
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
		return 2;
	}

}
