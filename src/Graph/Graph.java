package Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Graph {

	private ArrayList<Edge> edgeTable;
	private ArrayList<Node> nodes;
	private int numberOfNeighbors;
	private int numberOfNodes;
	private double probability;

	public Graph(int numberOfNodes, int numberOfNeighbors, double probability) {
		if (numberOfNeighbors % 2 != 0) {
			throw new IllegalArgumentException("numberOfNeighbors must be even");
		}
		this.numberOfNeighbors = numberOfNeighbors;
		this.numberOfNodes = numberOfNodes;
		this.probability = probability;
		generateNodes();
		generateBasicConnections();
		//sortEdges(edgeTable);
		//rewireNodes(0);
		rewireNodesInRounds();
		sortEdges(edgeTable);

	}

	private void generateBasicConnections() {
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
				// undirected Graph
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

	private void generateNodes() {
		IDGenerator idGenerator = new IDGenerator();
		edgeTable = new ArrayList<>();
		nodes = new ArrayList<>();
		for (int i = 0; i < numberOfNodes; i++) {
			nodes.add(new Node(idGenerator.generateID()));
		}
	}

	public ArrayList<Node> getNodes() {
		return nodes;
	}
	
	public Node getNodesByID(int ID) {
		for (Node node : nodes) {
			if (node.getID() == ID) {
				return node;
			}
		}
		return null;
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
			return null;
		} else {
			return result;
		}
	}
	
	public ArrayList<Edge> getEdgesEndingAt(Node node) {
		ArrayList<Edge> result = new ArrayList<>();
		for (Edge edge : edgeTable) {
			if (edge.endsAtNode(node)) {
				result.add(edge);
			}
		}
		if (result.isEmpty()) {
			return null;
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
			return null;
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
			return null;
		} else {
			return result;
		}
	}

	public void sortEdges(ArrayList<Edge> edgeTable) {
		for (Edge edge : edgeTable) {
			Node temp = edge.getNodes().get(1);
			if (temp.getID() < edge.getNodes().get(0).getID()) {
				edge.getNodes().set(1, edge.getNodes().get(0));
				edge.getNodes().set(0, temp);
			}
		}
		edgeTable.sort(new EdgeComparator());
	}

	public boolean containsEdge(Edge edge) {
		Node temp1 = edge.getNodes().get(0);
		Node temp2 = edge.getNodes().get(1);
		for (Edge edge2 : edgeTable) {
			if (edge2.containsNode(temp1) && edge2.containsNode(temp2)) {
				return true;
			}
		}
		return false;

	}

	public void rewireNodes(int round) {
		for (Node node : nodes) {
			ArrayList<Edge> edgesWithNode = getEdgesStartingAt(node);
			Edge edgeToRewire = edgesWithNode.get(round);
			double rng = new Random().nextDouble();
			if (rng <= probability) {
				while (true) {
					Node randomNode = nodes.get(new Random().nextInt(nodes.size()));
					if (node != randomNode) {
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

	public void rewireNodesInRounds() {
		int numberOfRounds = numberOfNeighbors / 2;
		for (int i = 0; i < numberOfRounds; i++) {
			rewireNodes(i);
		}
	}

}
