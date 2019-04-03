package cz.vutbr.feec.nn.Layers;

import cz.vutbr.feec.nn.NetworkGenerator;

public class InputLayer extends AbstractLayer {

	public InputLayer(int id, NetworkGenerator network) {
		super(id, network);
		layerType = "Input";
		shape0 = network.getInputShape()[0];
		shape1 = network.getInputShape()[1];
		shape2 = network.getInputShape()[2];
		shape3 = network.getInputShape()[3];
		
	}

	@Override
	public String build() {
		return "layer_" + String.format("%03d", id) + " = Input(" + network.getInputShapeAsString() + ")";
	}

	@Override
	protected void createConnections() {
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}

	@Override
	public String toString() {
		return "InputLayer [id=" + id + "]";
	}


}
