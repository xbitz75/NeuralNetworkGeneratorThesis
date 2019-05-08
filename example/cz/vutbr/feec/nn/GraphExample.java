package cz.vutbr.feec.nn;

import java.util.ArrayList;

import Graph.Edge;
import Graph.Graph;
import Graph.Node;

public class GraphExample {

	public static void main(String[] args) {
		Graph graph = new Graph(20, 4, 0.4);
		
		printNodes(graph);
		printEdges(graph);
		System.out.println("Number of edges: "+graph.getEdges().size());
		printInputEdgesForNode5(graph);
	}

	private static void printNodes(Graph graph) {
		ArrayList<Node> nodes = graph.getNodes();
		System.out.println("Printing IDs of generated nodes:");
		for (Node node : nodes) {
			System.out.println(node);
		}
	}

	private static void printEdges(Graph graph) {
		ArrayList<Edge> edges = graph.getEdges();
		System.out.println("Printing all edges:");
		for (Edge edge : edges) {
			System.out.println(edge);
		}
	}
	
	private static void printInputEdgesForNode5(Graph graph) {
		ArrayList<Edge> edges = graph.getEdgesEndingAt(graph.getNodesByID(5));
		System.out.println("Printing input edges for node 5:");
		for (Edge edge : edges) {
			System.out.println(edge);
		}
		
	}

}
