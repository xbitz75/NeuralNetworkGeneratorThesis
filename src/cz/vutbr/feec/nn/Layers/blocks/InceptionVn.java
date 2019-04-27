package cz.vutbr.feec.nn.Layers.blocks;

import java.util.Random;

import cz.vutbr.feec.nn.Layers.AbstractLayer;
import cz.vutbr.feec.nn.NetworkGenerator;

public class InceptionVn extends AbstractLayer {

	private int filters1;
	private int filters2;
	private int filters3;
	private int n;

	public InceptionVn(int id, NetworkGenerator network, int maxFilters) {
		super(id, network);
		filters1 = (int) Math.round((Math.random() * ((maxFilters - 20) + 1)) + 20);// randomly generated amount of neurons
		filters2 = (int) Math.round((Math.random() * ((maxFilters - 20) + 1)) + 20);
		filters3 = (int) Math.round((Math.random() * ((maxFilters - 20) + 1)) + 20);
		n = new Random().nextInt(8) + 3;
		
		layerType = "InceptionVn";
		createConnections();
		setShapesFromPrevLayer();
	}

	@Override
	public String build() {
		return "InceptionVn_1 = Conv2D(" + filters1 + ", (1, 1), padding=\"same\", activation=\"relu\")(" + getPreviousLayers()[0].getLayerId() + ")\n"
				+ "InceptionVn_2_1 = Conv2D(" + (int) filters2 /2 + ", (1, 1), padding=\"same\", activation=\"relu\")(" + getPreviousLayers()[0].getLayerId() + ")\n"
				+ "InceptionVn_2_2 = Conv2D(" + filters2 + ", (1, " + n + "), padding=\"same\", activation=\"relu\")(InceptionVn_2_1)\n"
				+ "InceptionVn_2_3 = Conv2D(" + filters2 + ", (" + n + ", 1), padding=\"same\", activation=\"relu\")(InceptionVn_2_2)\n"
				+ "InceptionVn_3_1 = Conv2D(" + (int) filters3 /2 + ", (1, 1), padding=\"same\", activation=\"relu\")(" + getPreviousLayers()[0].getLayerId() + ")\n"
				+ "InceptionVn_3_2 = Conv2D(" + filters3 + ", (1, " + n + "), padding=\"same\", activation=\"relu\")(InceptionVn_3_1)\n"
				+ "InceptionVn_3_3 = Conv2D(" + filters3 + ", (" + n + ", 1), padding=\"same\", activation=\"relu\")(InceptionVn_3_2)\n"
				+ "InceptionVn_3_4 = Conv2D(" + filters3 + ", (1, " + n + "), padding=\"same\", activation=\"relu\")(InceptionVn_3_3)\n"
				+ "InceptionVn_3_5 = Conv2D(" + filters3 + ", (" + n + ", 1), padding=\"same\", activation=\"relu\")(InceptionVn_3_4)\n"
				+ "layer_" + String.format("%03d", id)+ " = concatenate([InceptionVn_1,InceptionVn_2_3,InceptionVn_3_5],axis=3) # last InceptionVn block";
	}

	@Override
	protected void createConnections() {
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}

	@Override
	public String toString() {
		return "InceptionVn [id=" + id + "]";
	}

}
