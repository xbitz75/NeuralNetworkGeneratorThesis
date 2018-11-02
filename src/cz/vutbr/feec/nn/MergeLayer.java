package cz.vutbr.feec.nn;

import java.util.Set;
import java.util.TreeSet;

public class MergeLayer extends Layer {
	private String inputShape;

	public MergeLayer(int id, NetworkGenerator network) {
		super(id, network);
		layerType = LAYERTYPE.Merge;
		addNewPrevious();
		randomize(1);
	}

	// TODO fix ValueError: A `Concatenate` layer requires inputs with
	// matching shapes except for the concat axis. Got inputs shapes:
	// [(None, 94, 2), (None, 90, 8)]
	// possible solution:
	// https://datascience.stackexchange.com/questions/26942/concat-mode-can-only-merge-layers-with-matching-output-shapes-except-for-the-c
	
	// potrebuji vedet tvar vystupu vrstvy
	// pote by si mohla tato vrstva vyzadat tvary vrstev se kterymi chce pracovat

	@Override
	public String build() {
		StringBuilder text = new StringBuilder();
		Layer[] layers = getPreviousLayers();
		for (int i = 0; i < layers.length; i++) {
			Layer l = layers[i];
			text.append(l.getLayerId());
			if (i != layers.length - 1) { // neni posledni
				text.append(", ");
			}
		}
		return "layer_" + String.format("%03d", id) + " = concatenate([" + text.toString() + "])";
	}

	@Override
	public void randomize() {
		Set<Integer> usedLayers = new TreeSet<>();
		for (int i = 0; i < prevLayers.size(); i++) {
			randomize(i);
			if (usedLayers.contains(prevLayers.get(i))) {
				// same input detected
				TreeSet<Integer> available = new TreeSet<>();
				for (int j = 0; j < prevLayers.size(); j++) {
					if (!usedLayers.contains(prevLayers.get(i))) {
						available.add(j);
					}
				}
				prevLayers.set(i, available.first());
			}
			usedLayers.add(prevLayers.get(i));
		}

		// has the same input? Make corrections

	}

	@Override
	public int dimension() {
		return 1;
	}
}
