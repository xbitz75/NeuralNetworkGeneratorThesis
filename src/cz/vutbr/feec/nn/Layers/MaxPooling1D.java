package cz.vutbr.feec.nn.Layers;


import cz.vutbr.feec.nn.NetworkGenerator;

public class MaxPooling1D extends AbstractLayer {



	public MaxPooling1D(int id, NetworkGenerator network) {
		super(id, network);
		layerType = "MaxPooling1D";
		createConnections();
		setShapesFromPrevLayer();
		shape0 = shape0 / 2;
	}

	@Override
	public String build() {
		return "layer_" + String.format("%03d", id) + " = MaxPooling1D( padding=\"same\")("
				+ getPreviousLayers()[0].getLayerId() + ")";
	}

	@Override
	protected void createConnections() {
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}

	@Override
	public String toString() {
		return "MaxPooling1DLayer [id=" + id + "]";
	}


}
