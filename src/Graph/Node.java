package Graph;

public class Node {

	private int ID;
	
	public Node(int iD) {
		super();
		ID = iD;
	}

	public int getID() {
		return ID;
	}
	
	@Override
	public String toString() {
		return "Node with ID: "+ID;
	}
	
}
