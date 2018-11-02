package cz.vutbr.feec.nn;

public class BatchNormalization extends Layer {

	public BatchNormalization(int id, NetworkGenerator network) {
		super(id, network);
		layerType = LAYERTYPE.BatchNormalization;
		randomize();
	}

	@Override
	public String build() {
		return "layer_" + String.format("%03d", id) + " = BatchNormalization()(" + getPreviousLayers()[0].getLayerId() + ")";
	}

	@Override
	public void randomize() {
		for (int i = 0; i < prevLayers.size(); i++) {
			randomize(i);
		}
	}

	@Override
	public String toString() {
		return "BatchNormalization [id=" + id + "]";
	}

	@Override
	public int dimension() {
		return 0;
	}

}
