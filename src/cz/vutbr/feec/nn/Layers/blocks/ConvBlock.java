package cz.vutbr.feec.nn.Layers.blocks;

import java.util.Random;

import cz.vutbr.feec.nn.Layers.AbstractLayer;
import cz.vutbr.feec.nn.NetworkGenerator;

public class ConvBlock extends AbstractLayer {

	private int filters;
	private float droupoutRate;
	private int poolSize = 2;

	private enum ACTIVATION {
		relu, tanh, sigmoid
	}

	private String kernelSize = "(3, 3)";
	private ACTIVATION activation;

	public ConvBlock(int id, NetworkGenerator network, int filters) {
		super(id, network);
		this.droupoutRate = (float) (Math.random() / 2); // randomly generated rate parameter for dropout
		this.filters = filters;
		layerType = "ConvBlock";
		createConnections();
		setShapesFromPrevLayer();
		shape0 = shape0 - (6 - 2); // TODO make kernelSize a variable
		shape1 = shape1 - (6 - 2); // TODO (kernelSize - 1)
		shape2 = this.filters;
		shape0 = shape0 / poolSize;
		shape1 = shape1 / poolSize;
	}

	@Override
	public String build() {
		return "conv_block01 = Conv2D(" + filters + ", " + kernelSize + ", padding=\"same\", activation=\"" + activation
				+ "\")(" + getPreviousLayers()[0].getLayerId() + ")\n" + "conv_block02 = Conv2D(" + filters + ", "
				+ kernelSize + ", activation=\"" + activation + "\", padding=\"same\")(conv_block01)\n"
				+ "conv_block03 = MaxPooling2D(pool_size=(2,2))(conv_block02)\n" + "layer_" + String.format("%03d", id)
				+ " = Dropout(" + droupoutRate + ")(conv_block03) # last Conv block";
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
