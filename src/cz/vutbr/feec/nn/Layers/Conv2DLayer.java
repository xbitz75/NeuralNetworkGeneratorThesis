package cz.vutbr.feec.nn.Layers;

import java.util.Random;

import cz.vutbr.feec.nn.NetworkGenerator;

public class Conv2DLayer extends AbstractLayer {
	private enum ACTIVATION {
		relu, tanh, sigmoid
	}

	private int filters;
	private String kernelSize = "(3, 3)";
	private ACTIVATION activation;

	public Conv2DLayer(int id, NetworkGenerator network, int filters) {
		super(id, network);
		this.filters = filters;
		createConnections();
		layerType = "Conv2D";
		setShapesFromPrevLayer();
		shape0 = shape0 - (3 - 1);
		shape1 = shape1 - (3 - 1);
		shape2 = this.filters;
	}

	@Override
	public String build() {
		return "layer_" + String.format("%03d", id) + " = Conv2D(" + filters + ", " + kernelSize + ", activation='"
				+ activation + "', padding=\"same\")(" + getPreviousLayers()[0].getLayerId() + ")";
	}

	@Override
	protected void createConnections() {
		activation = ACTIVATION.values()[new Random().nextInt(ACTIVATION.values().length)];
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}

	@Override
	public String toString() {
		return "Conv2D [id=" + id + "]";
	}

}
