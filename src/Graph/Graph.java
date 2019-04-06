package graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Graph {

	private IDGenerator idGenerator;
	private ArrayList<Edge> edgeTable;
	private ArrayList<Node> nodes;

	public Graph(int numberOfNodes, int numberOfNeighbors, double probability) {
		if (numberOfNeighbors % 2 != 0) {
			throw new IllegalArgumentException("numberOfNeighbors must be even");
		}
		generateNodes(numberOfNodes);
		generateBasicConnections(numberOfNeighbors);
		sortEdges(edgeTable);
		//rewireNodes(0, probability);
		sortEdges(edgeTable);

	}

	private void generateBasicConnections(int numberOfNeighbors) {
		for (int i = 0; i < nodes.size(); i++) {
			for (int j = 1; j <= numberOfNeighbors / 2; j++) {
				Edge tempEdge;
				int sum = i + j;
				if (sum >= nodes.size()) {
					tempEdge = new Edge(nodes.get(i), nodes.get(sum - nodes.size()));
				} else {
					tempEdge = new Edge(nodes.get(i), nodes.get(i + j));
				}
				edgeTable.add(tempEdge);
				// undirected graph
				// Edge tempEdge2;
				// int diff = i - j;
				// if (diff < 0) {
				// tempEdge2 = new Edge(nodes.get(i), nodes.get(nodes.size() + diff));
				// } else {
				// tempEdge2 = new Edge(nodes.get(i), nodes.get(diff));
				// }
				// edgeTable.add(tempEdge2);
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
		if (result.isEmpty()) {
			throw new IllegalAccessError("no such edges");
		} else {
			return result;
		}
	}

	public ArrayList<Edge> getEdgesWithout(Node node) {
		ArrayList<Edge> result = new ArrayList<>();
		for (Edge edge : edgeTable) {
			if (!edge.containsNode(node)) {
				result.add(edge);
			}
		}
		if (result.isEmpty()) {
			throw new IllegalAccessError("no such edges");
		} else {
			return result;
		}
	}

	public ArrayList<Edge> getEdgesWithNode(Node node) {
		ArrayList<Edge> result = new ArrayList<>();
		for (Edge edge : edgeTable) {
			if (edge.containsNode(node)) {
				result.add(edge);
			}
		}
		if (result.isEmpty()) {
			throw new IllegalAccessError("no such edges");
		} else {
			return result;
		}
	}

	public void sortEdges(ArrayList<Edge> edgeTable) {
		Collections.sort(edgeTable, new EdgeComparator());
	}

	public boolean containsEdge(Edge edge) {
		Node temp1 = edge.getNodes().get(0);
		Node temp2 = edge.getNodes().get(1);
		Edge tempEdge = new Edge(temp2, temp1);
		if (edgeTable.contains(edge) || edgeTable.contains(tempEdge)) {
			return true;
		} else {
			return false;
		}
	}

	public void rewireNodes(int round, double probability) {
		for (Node node : nodes) {
			ArrayList<Edge> edgesWithNode = getEdgesWithNode(node);
			Edge edgeToRewire = edgesWithNode.get(round);
			double rng = new Random().nextDouble();
			if (rng <= probability) {
				while (true) {
					Node randomNode = nodes.get(new Random().nextInt(nodes.size()));
					Edge randomlyCreatedEdge = new Edge(node, randomNode);
					if (!containsEdge(randomlyCreatedEdge)) {
						edgeToRewire.rewire(randomNode);
						break;
					}
				}
			}
		}
	}

}
