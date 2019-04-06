package cz.vutbr.feec.nn.layers;

import cz.vutbr.feec.nn.NetworkGenerator;

public class InceptionV1 extends AbstractLayer {

	private int neurons1;
	private int neurons2;
	private int neurons3;

	public InceptionV1(int id, NetworkGenerator network, int neuronsUpperBound) {
		super(id, network);
		neurons1 = (int) Math.round((Math.random() * ((neuronsUpperBound - 20) + 1)) + 20);// randomly generated amount of neurons
		neurons2 = (int) Math.round((Math.random() * ((neuronsUpperBound - 20) + 1)) + 20);
		neurons3 = (int) Math.round((Math.random() * ((neuronsUpperBound - 20) + 1)) + 20);
		
		layerType = "InceptionV1";
		createConnections();
		setShapesFromPrevLayer();
	}

	@Override
	public String build() {
		return "InceptionV1_1x1 = Conv2D(" + neurons1 + ", (1, 1), padding=\"same\", activation=\"relu\")(" + getPreviousLayers()[0].getLayerId() + ")\n" 
				+ "InceptionV1_3x3_redu = Conv2D(" + (int)neurons2/2 + ", (1, 1), padding=\"same\", activation=\"relu\")(" + getPreviousLayers()[0].getLayerId() + ")\n"
				+ "InceptionV1_3x3 = Conv2D(" + neurons2 + ", (3, 3), padding=\"same\", activation=\"relu\")(InceptionV1_3x3_redu)\n"
				+ "InceptionV1_5x5_redu = Conv2D(" + (int)neurons3/2 + ", (5, 5), padding=\"same\", activation=\"relu\")(" + getPreviousLayers()[0].getLayerId() + ")\n"
				+ "InceptionV1_5x5 = Conv2D(" + neurons3 + ", (5, 5), padding=\"same\", activation=\"relu\")(InceptionV1_5x5_redu)\n"
				//+ "InceptionV1_max = MaxPooling2D(pool_size=(3,3))(" + getPreviousLayers()[0].getLayerId() + ")\n"
				//+ "InceptionV1_con = Conv2D(" + neurons3 + ", (1, 1), padding=\"same\", activation=\"relu\")(InceptionV1_max)\n"
				+ "layer_" + String.format("%03d", id)+ " = concatenate([InceptionV1_1x1,InceptionV1_3x3,InceptionV1_5x5],axis=3) # last InceptionV1 block";
			//	+ "layer_" + String.format("%03d", id)+ " = concatenate([InceptionV1_1x1,InceptionV1_3x3,InceptionV1_5x5,InceptionV1_con],axis=1) # last InceptionV1 block";
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
