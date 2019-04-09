package cz.vutbr.feec.nn;

import java.util.ArrayList;

import Graph.Edge;
import Graph.Graph;
import Graph.Node;

public class GraphExample {

	public static void main(String[] args) {
		Graph graph = new Graph(20, 4, 0.4);
		
		ArrayList<Node> nodes = graph.getNodes();
		System.out.println("Printing IDs of generated nodes:");
		for (Node node : nodes) {
			System.out.println(node.getID());
		}
		
		printEdges(graph);
		System.out.println("Number of edges: "+graph.getEdges().size());
	}

	private static void printEdges(Graph graph) {
		ArrayList<Edge> edges = graph.getEdges();
		System.out.println("Printing all edges:");
		for (Edge edge : edges) {
			System.out.println(edge.toString());
		}
	}

}
