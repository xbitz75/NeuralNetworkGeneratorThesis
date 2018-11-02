package cz.vutbr.feec.nn;

import java.util.ArrayList;
import java.util.Random;

public abstract class Layer {

	protected int id; // id = index in
	protected enum LAYERTYPE {
		BatchNormalization, Conv1D, Conv2D, ConvBlock, Dense, Dropout, Input, MaxPooling1D, Merge, MnistBlock
	}
	protected String kernelSize = "(3, 3)";
	protected LAYERTYPE layerType;
	protected NetworkGenerator network;
	protected ArrayList<Integer> prevLayers = new ArrayList<>();
	// test pridani hodnoty tvaru pro kazdou vrstvu
	protected String shape;

	// vytvoreni defaultniho predchoziho - predchozi = 0
	protected void addNewPrevious() {
		int value = 0;
		prevLayers.add(value);
	}

	// vytvoreni vrstvy, prideli ID a NETWORK do ktere patri, prida nulovou
	// predchozi a pote zavola randomize
	public Layer(int id, NetworkGenerator network) {
		this.id = id;
		this.network = network;
		addNewPrevious();
		randomize(0);
	}

	public abstract String build();

	public abstract void randomize();

	public abstract int dimension();
	
	public LAYERTYPE getLayerType() { // each layer saves its type, this enables to control which layers cannot follow each other
		return layerType;
	}

	// ziskani ID vrstvy do stringu
	public String getLayerId() {
		return "layer_" + String.format("%03d", id);
	}

	// vrati tvar vrstvy
	public String getLayerShape() {
		return shape;
	}

	// list predchozich vrstev pro vrstvu
	public Layer[] getPreviousLayers() {
		if (id == 0) {
			return new Layer[0];
		}
		Layer[] res = new Layer[prevLayers.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = network.getLayer(prevLayers.get(i));
		}
		return res;
	}

	// Pokud se nejedna o prvni vrstvu nastavi predhozi vrstvu vybranim random ID
	public void randomize(int index) {
		int prevLayerId;
		if (this.id == 0) {
			prevLayerId = -1; // no previous
		} else {
			//prevLayerId = new Random().nextInt(this.id);
			prevLayerId = this.id-1;
		}
		prevLayers.set(index, prevLayerId);
	}

}
