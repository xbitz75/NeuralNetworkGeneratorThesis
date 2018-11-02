package cz.vutbr.feec.nn;

import java.util.Random;

public class MaxPooling1D extends Layer {
	private enum PADDING {
		same, valid
	}

	private int kernelSize = 3;
	private PADDING padding;

	public MaxPooling1D(int id, NetworkGenerator network) {
		super(id, network);
		layerType = LAYERTYPE.MaxPooling1D;
	}

	@Override
	public String build() {
		return "layer_" + String.format("%03d", id) + " = MaxPooling1D(" + kernelSize + ", padding=" + padding + ")("
				+ getPreviousLayers()[0].getLayerId() + ")";
		// tower_3 = MaxPooling2D((3, 3), strides=(1, 1), padding='same')(input_img)
	}

	@Override
	public void randomize() {
		padding = PADDING.values()[new Random().nextInt(PADDING.values().length)];
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
		return 0; // any
	}

}