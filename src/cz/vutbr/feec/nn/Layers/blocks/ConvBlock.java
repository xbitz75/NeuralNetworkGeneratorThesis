package cz.vutbr.feec.nn.Layers.blocks;

import java.util.Random;

import cz.vutbr.feec.nn.Layers.AbstractLayer;
import cz.vutbr.feec.nn.NetworkGenerator;

public class ConvBlock extends AbstractLayer {

	private int filters;

	private enum ACTIVATION {
		relu, tanh, sigmoid
	}

	private ACTIVATION activation;

	public ConvBlock(int id, NetworkGenerator network, int filters) {
		super(id, network);
		this.filters = filters;
		layerType = "ConvBlock";
		createConnections();
		setShapesFromPrevLayer();
		shape0 = shape0 - (6 - 2); // TODO make kernelSize a variable
		shape1 = shape1 - (6 - 2); // TODO (kernelSize - 1)
		shape2 = this.filters;
		int poolSize = 2;
		shape0 = shape0 / poolSize;
		shape1 = shape1 / poolSize;
	}

	@Override
	public String build() {
		String kernelSize = "(3, 3)";
		return  "conv_block01 = Conv2D(" + filters + ", " + kernelSize + ", padding=\"same\", activation=\"" + activation
				+ "\")(" + getPreviousLayers()[0].getLayerId() + ")\n" +
				"layer_" + String.format("%03d", id) + " = BatchNormalization()(conv_block01) # last Conv block";
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
		return "ConvBlock [id=" + id + "]";
	}

}
