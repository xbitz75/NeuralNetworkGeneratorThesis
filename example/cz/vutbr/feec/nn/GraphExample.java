package cz.vutbr.feec.nn;

import java.util.ArrayList;

import graph.Edge;
import graph.Graph;
import graph.Node;

public class GraphExample {

	public static void main(String[] args) {
		Graph graph = new Graph(20, 4, 0.8);
		
		ArrayList<Node> nodes = graph.getNodes();
		for (Node node : nodes) {
			System.out.println(node.getID());
		}
		
		printEdges(graph);
		
		ArrayList<Edge> edgesFromNode1 = graph.getEdgesStartingAt(nodes.get(0));
		System.out.println("Printing edges starting at node"+nodes.get(0).getID());
		for (Edge edge : edgesFromNode1) {
			System.out.println(edge.toString());
		}
		
		ArrayList<Edge> edgesExceptNode1 = graph.getEdgesWithout(nodes.get(0));
		System.out.println("Printing edges not containing node"+nodes.get(0).getID());
		for (Edge edge : edgesExceptNode1) {
			System.out.println(edge.toString());
		}
	}

	private static void printEdges(Graph graph) {
		ArrayList<Edge> edges = graph.getEdges();
		System.out.println("Printing all edges");
		for (Edge edge : edges) {
			System.out.println(edge.toString());
		}
	}

}
