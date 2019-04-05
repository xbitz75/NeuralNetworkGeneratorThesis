package Graph;

import java.util.ArrayList;

public class Graph {

	private IDGenerator idGenerator;
	private ArrayList<Edge> edgeTable;
	private ArrayList<Node> nodes;
	
	
	
	public Graph(int numberOfNodes, int numberOfNeighbors, double probability) {
		if (numberOfNeighbors%2 != 0) {
			throw new IllegalArgumentException("numberOfNeighbors must be even");
		}
		generateNodes(numberOfNodes);
		generateBasicConnections(numberOfNeighbors);
		
		
	}

	private void generateBasicConnections(int numberOfNeighbors) {
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 1; j <= numberOfNeighbors/2; j++) {
				Edge tempEdge;
				int sum = i+j;
				if (sum >= nodes.size()) {
					tempEdge = new Edge(nodes.get(i), nodes.get(sum-nodes.size()));
				}else {
					tempEdge = new Edge(nodes.get(i), nodes.get(i+j));
				}
				edgeTable.add(tempEdge);
				Edge tempEdge2;
				int diff = i-j;
				if (diff < 0) {
					tempEdge2 = new Edge(nodes.get(i), nodes.get(nodes.size()+diff));
				}else {
					tempEdge2 = new Edge(nodes.get(i), nodes.get(diff));
				}
				edgeTable.add(tempEdge2);
			}
		}
	}

	private void generateNodes(int numberOfNodes) {
		idGenerator = new IDGenerator();
		edgeTable = new ArrayList<>();
		nodes = new ArrayList<>();
		for (int i = 0; i < numberOfNodes; i++) {
			nodes.add(new Node(idGenerator.generateID()));
		}
	}
	
	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	public ArrayList<Edge> getEdges() {
		return edgeTable;
	}

	public ArrayList<Edge> getEdgesStartingAt(Node node) {
		ArrayList<Edge> result = new ArrayList<>();
		for (Edge edge : edgeTable) {
			if (edge.startsAtNode(node)) {
				result.add(edge);
			}
		}
		return result;
	}
}
