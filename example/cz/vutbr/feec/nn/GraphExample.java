package cz.vutbr.feec.nn;

import java.util.ArrayList;

import Graph.Edge;
import Graph.Graph;
import Graph.Node;

public class GraphExample {

	public static void main(String[] args) {
		Graph graph = new Graph(20, 4, 0.3);
		
		ArrayList<Node> nodes = graph.getNodes();
		for (Node node : nodes) {
			System.out.println(node.getID());
		}
		
		ArrayList<Edge> edges = graph.getEdges();
		for (Edge edge : edges) {
			System.out.println(edge.toString());
		}
		
		ArrayList<Edge> edgesFromNode1 = graph.getEdgesStartingAt(nodes.get(0));
		System.out.println("Printing edges starting at node"+nodes.get(0).getID());
		for (Edge edge : edgesFromNode1) {
			System.out.println(edge.toString());
		}
		
	}

}
