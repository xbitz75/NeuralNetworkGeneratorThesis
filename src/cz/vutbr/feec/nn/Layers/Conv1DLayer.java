package cz.vutbr.feec.nn.Layers;

import java.util.Random;

import cz.vutbr.feec.nn.NetworkGenerator;

public class Conv1DLayer extends AbstractLayer {
	private enum ACTIVATION {
		relu, tanh, sigmoid
	}

	private int filters;
	private int kernelSize = 3;
	private ACTIVATION activation;

	public Conv1DLayer(int id, NetworkGenerator network, int filters) {
		super(id, network);
		this.filters = filters;
		createConnections();
		layerType = "Conv1D";
		setShapesFromPrevLayer();
		shape0 = shape0 - (kernelSize-1);
		shape1 = this.filters;
	}

	@Override
	public String build() {
			return "layer_" + String.format("%03d", id) + " = Conv1D(" + filters + ", " + kernelSize + ", activation='"
					+ activation + "')(" + getPreviousLayers()[0].getLayerId() + ")";
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
		return "Conv1D [id=" + id + "]";
	}


}
