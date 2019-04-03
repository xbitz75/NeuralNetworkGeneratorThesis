package cz.vutbr.feec.nn.Layers;

import cz.vutbr.feec.nn.NetworkGenerator;

public class BatchNormalization extends AbstractLayer {

	public BatchNormalization(int id, NetworkGenerator network) {
		super(id, network);
		layerType = "BatchNormalization";
		createConnections();
		setShapesFromPrevLayer();
	}

	@Override
	public String build() {
		return "layer_" + String.format("%03d", id) + " = BatchNormalization()(" + getPreviousLayers()[0].getLayerId() + ")";
	}

	@Override
	protected void createConnections() {
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}

	@Override
	public String toString() {
		return "BatchNormalization [id=" + id + "]";
	}


}
