package cz.vutbr.feec.nn.Layers;

import java.util.ArrayList;

import cz.vutbr.feec.nn.NetworkGenerator;

public abstract class AbstractLayer {

	public Integer id; // id = index in network
	public Integer shape0;
	public Integer shape1;
	public Integer shape2;
	public Integer shape3;
	protected String layerType;
	protected NetworkGenerator network;
	protected ArrayList<Integer> prevLayers = new ArrayList<>();
	
	// first layer
	protected void addNewPrevious() {
		int value = 0;
		prevLayers.add(value);
	}

	// basic layer creation
	public AbstractLayer(int id, NetworkGenerator network) {
		this.id = id;
		this.network = network;
		addNewPrevious();
		setPrevLayers(0);
	}

	public abstract String build();

	protected abstract void createConnections();
	
	public String getLayerType() { // each layer saves its type, this enables to control which Layers cannot follow each other
		return layerType;
	}

	// creating string with layer_ID
	public String getLayerId() {
		return "layer_" + String.format("%03d", id);
	}


	// returns list of previous Layers
	public AbstractLayer[] getPreviousLayers() {
		if (id == 0) {
			return new AbstractLayer[0];
		}
		AbstractLayer[] res = new AbstractLayer[prevLayers.size()];
		for (int i = 0; i < res.length; i++) {
			res[i] = network.getLayer(prevLayers.get(i));
		}
		return res;
	}

	// Makes list of previous Layers
	public void setPrevLayers(int index) {
		int prevLayerId;
		if (this.id == 0) {
			prevLayerId = -1; // no previous
		} else {
			prevLayerId = this.id-1;
		}
		prevLayers.set(index, prevLayerId);
	}
	
	public void setShapesFromPrevLayer() {
		shape0 = getPreviousLayers()[0].shape0;
		shape1 = getPreviousLayers()[0].shape1;
		shape2 = getPreviousLayers()[0].shape2;
		shape3 = getPreviousLayers()[0].shape3;
	}
	
	/**
	Prints debug information to console
	*/
	public void debug() {
		System.out.println("Debug: "+shape0+", "+shape1+", "+shape2+", "+shape3+" "+toString());
	}

}
