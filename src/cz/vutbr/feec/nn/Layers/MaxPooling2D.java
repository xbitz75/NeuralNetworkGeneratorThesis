package cz.vutbr.feec.nn.layers;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import cz.vutbr.feec.nn.NetworkGenerator;

public class MaxPooling2D extends AbstractLayer {
	private enum PADDING {
		same, valid
	}

	private int poolSize0;
	private int poolSize1;
	private PADDING padding;

	public MaxPooling2D(int id, NetworkGenerator network) {
		super(id, network);
		layerType = "MaxPooling2D";
		createConnections();
		setShapesFromPrevLayer();
		shape0 = shape0 / poolSize0;
		shape1 = shape1 / poolSize1;
	}

	@Override
	public String build() {
		return "layer_" + String.format("%03d", id) + " = MaxPooling2D((" + poolSize0 + ", "+ poolSize1 + "), padding=\"" + padding + "\")("
				+ getPreviousLayers()[0].getLayerId() + ")";
	}

	@Override
	protected void createConnections() {
		padding = PADDING.values()[new Random().nextInt(PADDING.values().length)];
		poolSize0 = ThreadLocalRandom.current().nextInt(1, 5);
		poolSize1 = ThreadLocalRandom.current().nextInt(1, 5);
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}

	@Override
	public String toString() {
		return "MaxPooling2DLayer [id=" + id + "]";
	}


}