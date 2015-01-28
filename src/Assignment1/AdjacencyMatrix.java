package Assignment1;
import java.util.LinkedList;


public class AdjacencyMatrix {
	int[][] mat;
	int edges;
	int vertices;
	
	public AdjacencyMatrix(int vertices, int edges)
	{
		mat = new int[vertices][vertices];
		this.vertices = vertices;
		this.edges = 0;
		
		if(edges>vertices*vertices) throw new RuntimeException("Too many edges");
		
		while(this.edges<edges)
		{
			int a = (int)(vertices*Math.random());
			int b = (int)(vertices*Math.random());
			if(a!=b)
			{
				int weight = (int)(200*Math.random());
				addUndirectedEdge(a,b,weight);
			}
		}
		
	}
	public void addUndirectedEdge(int a, int b, int weight)
	{
		mat[a][b] = weight;
		mat[b][a] = weight;
		this.edges++;
	}
	public String toString()
	{
		StringBuilder str = new StringBuilder();
		for(int i = 0;i<vertices;i++)
		{
			for(int j = 0; j<vertices;j++)
			{
				str.append(mat[i][j]+"\t");
			}
			str.append("\n\n");
		}
		return str.toString();
	}
	public boolean breadthFirstSearch(int start, int end)
	{
		LinkedList<Integer> q = new LinkedList<Integer>();
		boolean[] checked = new boolean[vertices];
		q.add(start);
		
		while(!q.isEmpty())
		{
			int current = q.removeFirst();
			if(current == end)
			{
				return true;
			}
			for(int i = 0;i<vertices;i++)
			{
				
				if(mat[current][i]!=0 && checked[i]==false )
				{
					checked[i]=true;
					q.add(i);
				}
			}
		}
		
		return false;
	}
	public boolean depthFirstSearch(int start, int end)
	{
		LinkedList<Integer> q = new LinkedList<Integer>();
		boolean[] checked = new boolean[vertices];
		q.add(start);
		
		while(!q.isEmpty())
		{
			int current = q.removeLast();
			if(current == end)
			{
				return true;
			}
			for(int i = 0;i<vertices;i++)
			{
				
				if(mat[current][i]!=0 && checked[i]==false )
				{
					checked[i]=true;
					q.add(i);
				}
			}
		}
		
		return false;
	}
	
	
}
