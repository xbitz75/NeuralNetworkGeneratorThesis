package cz.vutbr.feec.nn.Layers.blocks;

import cz.vutbr.feec.nn.Layers.AbstractLayer;
import cz.vutbr.feec.nn.NetworkGenerator;

public class InceptionV1 extends AbstractLayer {

	private int filters1;
	private int filters2;
	private int filters3;

	public InceptionV1(int id, NetworkGenerator network, int maxFilters) {
		super(id, network);
		filters1 = (int) Math.round((Math.random() * ((maxFilters - 20) + 1)) + 20);// randomly generated amount of neurons
		filters2 = (int) Math.round((Math.random() * ((maxFilters - 20) + 1)) + 20);
		filters3 = (int) Math.round((Math.random() * ((maxFilters - 20) + 1)) + 20);
		
		layerType = "InceptionV1";
		createConnections();
		setShapesFromPrevLayer();
	}

	@Override
	public String build() {
		return "InceptionV1_1x1 = Conv2D(" + filters1 + ", (1, 1), padding=\"same\", activation=\"relu\")(" + getPreviousLayers()[0].getLayerId() + ")\n"
				+ "InceptionV1_3x3_redu = Conv2D(" + Math.floorDiv(filters2, 2) + ", (1, 1), padding=\"same\", activation=\"relu\")(" + getPreviousLayers()[0].getLayerId() + ")\n"
				+ "InceptionV1_3x3 = Conv2D(" + filters2 + ", (3, 3), padding=\"same\", activation=\"relu\")(InceptionV1_3x3_redu)\n"
				+ "InceptionV1_5x5_redu = Conv2D(" + Math.floorDiv(filters3, 2)+ ", (5, 5), padding=\"same\", activation=\"relu\")(" + getPreviousLayers()[0].getLayerId() + ")\n"
				+ "InceptionV1_5x5 = Conv2D(" + filters3 + ", (5, 5), padding=\"same\", activation=\"relu\")(InceptionV1_5x5_redu)\n"
				+ "layer_" + String.format("%03d", id)+ " = concatenate([InceptionV1_1x1,InceptionV1_3x3,InceptionV1_5x5],axis=3) # last InceptionV1 block";
	}

	@Override
	protected void createConnections() {
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}

	@Override
	public String toString() {
		return "InceptionV1 [id=" + id + "]";
	}

}
