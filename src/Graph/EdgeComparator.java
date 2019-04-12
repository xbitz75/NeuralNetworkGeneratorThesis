package Graph;

import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge> {

	@Override
	public int compare(Edge o1, Edge o2) {
		if (o1.getNodes().get(0).getID() == o2.getNodes().get(0).getID() && o1.getNodes().get(1).getID() == o2.getNodes().get(1).getID()) {
			return 0;
		}else if (o1.getNodes().get(0).getID() < o2.getNodes().get(0).getID()) {
			return -1;
		}else if (o1.getNodes().get(0).getID() > o2.getNodes().get(0).getID()) {
			return 1;
		}else if (o1.getNodes().get(0).getID() == o2.getNodes().get(0).getID() && o1.getNodes().get(1).getID() < o2.getNodes().get(1).getID()) {
			return -1;
		}else if (o1.getNodes().get(0).getID() == o2.getNodes().get(0).getID() && o1.getNodes().get(1).getID() < o2.getNodes().get(1).getID()) {
			return 1;
		}
		return 0;
	}
}
