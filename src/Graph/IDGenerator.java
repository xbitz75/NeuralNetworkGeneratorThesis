package graph;

public class IDGenerator {

	private int id;
	
	
	
	public IDGenerator() {
		super();
		id = 0;
	}

	public int generateID() {
		id++;
		return id;
	}

}
