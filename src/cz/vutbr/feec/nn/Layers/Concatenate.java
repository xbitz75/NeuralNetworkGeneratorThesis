package cz.vutbr.feec.nn.Layers;

import cz.vutbr.feec.nn.NetworkGenerator;

public class Concatenate extends AbstractLayer {
	private String merge;

	public Concatenate(int id, NetworkGenerator network) {
		super(id, network);
		layerType = "Concatenate";
		createConnections();
		setShapesFromPrevLayer();
		selectMerge();
	}
	
	private void selectMerge() {
		int rng = (int) Math.round((Math.random() * (((network.merges2D.size() - 2) - 0) + 1)) + 0);
		merge = network.merges2D.get(rng);
	}

	@Override
	public String build() {
		System.out.println("Network can merge " + merge);
		return "layer_" + String.format("%03d", id) + " = concatenate([" + merge + "])";
	}

	@Override
	protected void createConnections() {
		for (int i = 0; i < prevLayers.size(); i++) {
			setPrevLayers(i);
		}
	}
	
	@Override
	public String toString() {
		return "Concatenate [id=" + id + "]";
	}
}
