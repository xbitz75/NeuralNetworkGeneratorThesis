package cz.vutbr.feec.nn.Layers;

import cz.vutbr.feec.nn.NetworkGenerator;

public class Dropout extends AbstractLayer {

	private float droupoutRate;

	public Dropout(int id, NetworkGenerator network) {
		super(id, network);
		droupoutRate = (float) (Math.random() / 2); // randomly generated rate parameter for dropout
		layerType = "Dropout";
		createConnections();
		setShapesFromPrevLayer();
	}

	@Override
	public String build() {
		return "layer_" + String.format("%03d", id) + " = Dropout(" + droupoutRate + ")(" + getPreviousLayers()[0].getLayerId() + ")";
	}

	@Override
	protected void createConnections() {
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}

	@Override
	public String toString() {
		return "Dropout [id=" + id + "]";
	}

	
}
