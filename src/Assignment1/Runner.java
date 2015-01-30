package Assignment1;

public class Runner {

	public static void main(String[] args) {
		AdjacencyMatrix m = new AdjacencyMatrix(400, 30000);
		System.out.print("matrix looks like\n");
		System.out.print(m);
		System.out.println("1 - 4?"+m.breadthFirstSearch(1, 4));
		System.out.println("Depth first agrees?"+m.depthFirstSearch(1, 4));
		System.out.println("And now with the dfs path: "+m.depthFirstSearchWithPath(1, 4));
		System.out.println("And now with the bfs path: "+m.breadthFirstSearchWithPath(1, 4));
		
		AdjacencyMatrix m2 = createRomania();
		System.out.println(m2);
		System.out.println("bfs path from Oradea to Urziceni"+m2.breadthFirstSearchWithPath(0, 14));
		System.out.println("dfs path from Oradea to Urziceni"+m2.depthFirstSearchWithPath(0, 14));
	}

	private static AdjacencyMatrix createRomania() {
		String[] cityNames = {"Oradea","Zerind","Arad","Sibiu","Timisoara",
							"Lugoj","Mehadia","Drobeta","Craiova","Rimnieu Vilcea",
							"Fagaras","Pitesti","Bucharest","Giurgiu","Urziceni",
							"Hirsova","Eforie","Vaslui","Iasi","Neamt"};
		int[][] edges = {{0,1,71},{0,3,151},{1,2,75},{2,3,140},{2,4,118},{4,5,111},
						{5,6,70},{6,7,75},{7,8,120},{8,9,146},{8,11,138},{3,10,99},
						{9,3,80},{9,11,97},{11,12,101},{12,13,90},{12,14,85},{10,12,211},
						{14,15,98},{15,16,86},{14,17,142},{17,18,92},{18,19,87}};
		
		
		return new AdjacencyMatrix(cityNames,edges);
	}

}
