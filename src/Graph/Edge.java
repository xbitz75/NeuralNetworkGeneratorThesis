package graph;

import java.util.ArrayList;
import java.util.List;

public class Edge {
	
	private ArrayList<Node> nodes;
	
	

	public Edge(Node node1, Node node2) {
		super();
		nodes = new ArrayList<>();
		nodes.add(node1);
		nodes.add(node2);
	}

	public List<Node> getNodes() {
		return nodes;
	}
	
	@Override
	public String toString() {
		String string = "Edge: "+nodes.get(0).getID()+" --> "+nodes.get(1).getID();
		return string;
	} 
	
	public boolean startsAtNode(Node node) {
		if (nodes.get(0) == node) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean endsAtNode(Node node) {
		if (nodes.get(1) == node) {
			return true;
		}else {
			return false;
		}
	}
	
	public boolean containsNode(Node node) {
		if (nodes.contains(node)) {
			return true;
		}else {
			return false;
		}
	}
	
	public void rewire(Node node) {
		nodes.set(1, node);
	}
	
}
