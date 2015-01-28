package Assignment1;

public class Runner {

	public static void main(String[] args) {
		AdjacencyMatrix m = new AdjacencyMatrix(5, 5);
		System.out.print("matrix looks like\n");
		System.out.print(m);
		System.out.println("1 - 4?"+m.breadthFirstSearch(1, 4));
		System.out.println("Depth first agrees?"+m.depthFirstSearch(1, 4));
	}

}
