package cz.vutbr.feec.nn;

public class InputLayer extends Layer {

	public InputLayer(int id, NetworkGenerator network) {
		super(id, network);
		// ulozi si tvar z vstupniho parametru
		this.shape = network.getInputShape();
		layerType = LAYERTYPE.Input;
	}

	@Override
	public String build() {
		return "layer_" + String.format("%03d", id) + " = Input(" + network.getInputShape() + ")";
	}

	@Override
	public void randomize() {
		for (int i = 0; i < prevLayers.size(); i++) {
			randomize(i);
		}
	}

	@Override
	public String toString() {
		return "DenseLayer [id=" + id + "]";
	}

	@Override
	public int dimension() {
		return 0; // any
	}

}
