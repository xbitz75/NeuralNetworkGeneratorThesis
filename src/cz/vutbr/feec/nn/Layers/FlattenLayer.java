package cz.vutbr.feec.nn.layers;

import cz.vutbr.feec.nn.NetworkGenerator;

public class FlattenLayer extends AbstractLayer {

	public FlattenLayer(int id, NetworkGenerator network) {
		super(id, network);
		layerType = "Flatten";
		createConnections();
		setShapesFromPrevLayer();
		if (network.getDimensions() == 2) { // In case of networks dimensions = 2 (none, x, y)
			shape0 = shape0 * shape1;
			shape1 = 0;
		} else if (network.getDimensions() == 3) { // In case of networks dimensions = 3 (none, x, y, z)
			shape0 = shape0 * shape1 * shape2;
			shape1 = 0;
			shape2 = 0;
		} else if (network.getDimensions() == 4) { // In case of networks dimensions = 4 (none, x, y, z, w)
			shape0 = shape0 * shape1 * shape2 * shape3;
			shape1 = 0;
			shape2 = 0;
			shape3 = 0;
		}
		network.setDimensions(1); // flattens networks dimensions
		network.flattened = true;

	}

	@Override
	public String build() {
		return "layer_" + String.format("%03d", id) + " = Flatten()(" + getPreviousLayers()[0].getLayerId() + ")";
	}

	@Override
	protected void createConnections() {
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}

	@Override
	public String toString() {
		return "Flatten [id=" + id + "]";
	}

}
