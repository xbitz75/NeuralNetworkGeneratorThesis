package cz.vutbr.feec.nn.layers;

import java.util.Random;

import cz.vutbr.feec.nn.NetworkGenerator;

public class InceptionVn extends AbstractLayer {

	private int neurons1;
	private int neurons2;
	private int neurons3;
	private int n;

	public InceptionVn(int id, NetworkGenerator network, int neuronsUpperBound) {
		super(id, network);
		neurons1 = (int) Math.round((Math.random() * ((neuronsUpperBound - 20) + 1)) + 20);// randomly generated amount of neurons
		neurons2 = (int) Math.round((Math.random() * ((neuronsUpperBound - 20) + 1)) + 20);
		neurons3 = (int) Math.round((Math.random() * ((neuronsUpperBound - 20) + 1)) + 20);
		n = new Random().nextInt(8) + 3;
		
		layerType = "InceptionVn";
		createConnections();
		setShapesFromPrevLayer();
	}

	@Override
	public String build() {
		return "InceptionVn_1 = Conv2D(" + neurons1 + ", (1, 1), padding=\"same\", activation=\"relu\")(" + getPreviousLayers()[0].getLayerId() + ")\n" 
				+ "InceptionVn_2_1 = Conv2D(" + (int)neurons2/2 + ", (1, 1), padding=\"same\", activation=\"relu\")(" + getPreviousLayers()[0].getLayerId() + ")\n"
				+ "InceptionVn_2_2 = Conv2D(" + neurons2 + ", (1, " + n + "), padding=\"same\", activation=\"relu\")(InceptionVn_2_1)\n"
				+ "InceptionVn_2_3 = Conv2D(" + neurons2 + ", (" + n + ", 1), padding=\"same\", activation=\"relu\")(InceptionVn_2_2)\n"
				+ "InceptionVn_3_1 = Conv2D(" + (int)neurons3/2 + ", (1, 1), padding=\"same\", activation=\"relu\")(" + getPreviousLayers()[0].getLayerId() + ")\n"
				+ "InceptionVn_3_2 = Conv2D(" + neurons3 + ", (1, " + n + "), padding=\"same\", activation=\"relu\")(InceptionVn_3_1)\n"
				+ "InceptionVn_3_3 = Conv2D(" + neurons3 + ", (" + n + ", 1), padding=\"same\", activation=\"relu\")(InceptionVn_3_2)\n"
				+ "InceptionVn_3_4 = Conv2D(" + neurons3 + ", (1, " + n + "), padding=\"same\", activation=\"relu\")(InceptionVn_3_3)\n"
				+ "InceptionVn_3_5 = Conv2D(" + neurons3 + ", (" + n + ", 1), padding=\"same\", activation=\"relu\")(InceptionVn_3_4)\n"
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
