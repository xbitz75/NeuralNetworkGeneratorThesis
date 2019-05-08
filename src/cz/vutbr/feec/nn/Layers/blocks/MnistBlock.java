package cz.vutbr.feec.nn.Layers.blocks;

import cz.vutbr.feec.nn.Layers.AbstractLayer;
import cz.vutbr.feec.nn.NetworkGenerator;

public class MnistBlock extends AbstractLayer {

	private int units;
	private float droupoutRate;

	public MnistBlock(int id, NetworkGenerator network, int units) {
		super(id, network);
		droupoutRate = (float) Math.random(); // randomly generated rate parameter for dropout
		this.units = units;
		layerType = "MnistBlock";
		createConnections();
		setShapesFromPrevLayer();
		shape1 = this.units;

	}

	@Override
	public String build() {
		return "mnist_block02 = Dense(" + units + ", activation=\"relu\")(" + getPreviousLayers()[0].getLayerId()
				+ ")\n" + "mnist_block03 = Dropout(" + droupoutRate + ")(mnist_block02)\n" + "layer_"
				+ String.format("%03d", id) + " = Dense(" + units
				+ ", activation=\"softmax\")(mnist_block03) # last Mnist block";
	}

	@Override
	protected void createConnections() {
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}

	@Override
	public String toString() {
		return "MnistBlock [id=" + id + "]";
	}

}

