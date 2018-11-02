package cz.vutbr.feec.nn;

public class Dropout extends Layer {

	private float droupoutRate;

	public Dropout(int id, NetworkGenerator network) {
		super(id, network);
		this.droupoutRate = (float) Math.random(); // randomly generated rate parameter for dropout
		layerType = LAYERTYPE.Dropout;
		randomize();
	}

	@Override
	public String build() {
		return "layer_" + String.format("%03d", id) + " = Dropout(\"" + droupoutRate + "\")(" + getPreviousLayers()[0].getLayerId() + ")";
	}

	@Override
	public void randomize() {
		for (int i = 0; i < prevLayers.size(); i++) {
			randomize(i);
		}
	}

	@Override
	public String toString() {
		return "Dropout [id=" + id + "]";
	}

	@Override
	public int dimension() {
		return 0;
	}
}
