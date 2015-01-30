package Assignment1;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class AdjacencyMatrix {
	int[][] mat;
	String[] vNames;
	int edges;
	int vertices;
	
	// construct random AdjacencyMatrix
	public AdjacencyMatrix(int vertices, int edges)
	{
		if(edges>vertices*vertices) throw new RuntimeException("Too many edges");
		
		initializeGraph(vertices);
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
	// takes an array of names for vertices - ["Vertex at Index 0's name", "Vertex at 1's name", ...]
	// and a array of edges  				- [[Vertex A index, Vertex B index, edge weight],...] 
	// it's kind of ugly honestly, but it'll work for now.
	public AdjacencyMatrix(String[] vertices, int[][] edges)
	{
		initializeGraph(vertices.length);
		vNames = vertices;

		int i = 0;
		while(this.edges<edges.length)
		{
			addUndirectedEdge(edges[i][0],edges[i][1],edges[i][2]);
			i++;
		}
		
	}
	public void initializeGraph(int vertices)
	{
		mat = new int[vertices][vertices];
		this.vertices = vertices;
		this.edges = 0;
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
	//this would be prettier with a hashmap
	public int getIndexOf(String name)
	{
		for(int i = 0; i<vNames.length;i++)
		{
			if(vNames[i].equals(name))
			{
				return i;
			}
		}
		return -1;
	}
	public boolean breadthFirstSearch(String start, String end)
	{
		return breadthFirstSearch(getIndexOf(start),getIndexOf(end));
	}
	public boolean breadthFirstSearch(int start, int end)
	{
		LinkedList<Integer> q = new LinkedList<Integer>();
		boolean[] checked = new boolean[vertices];
		if(start == end)
			return true;
		checked[start]=true;
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
	public String breadthFirstSearchWithPath(int start, int end)
	{
		LinkedList<Integer> q = new LinkedList<Integer>();
		boolean[] checked = new boolean[vertices];
		int[] visitedFrom = new int[vertices];
		for(int i = 0;i<vertices;i++)
		{
			visitedFrom[i]=-1;
		}
		if(start == end)
			return "Start and end are the same";
		checked[start]=true;
		q.add(start);
		
		while(!q.isEmpty())
		{
			int current = q.removeFirst();
			if(current == end)
			{
				return turnVisitedArrayIntoString(start, end, visitedFrom);
			}
			for(int i = 0;i<vertices;i++)
			{
				
				if(mat[current][i]!=0 && checked[i]==false )
				{
					checked[i]=true;
					visitedFrom[i]=current;
					q.add(i);
				}
			}
		}
		
		return "No Path Possible";
	}
	public boolean depthFirstSearch(String start, String end)
	{
		return depthFirstSearch(getIndexOf(start),getIndexOf(end));
	}
	public boolean depthFirstSearch(int start, int end)
	{
		LinkedList<Integer> q = new LinkedList<Integer>();
		boolean[] checked = new boolean[vertices];
		if(start == end)
			return true;
		checked[start]=true;
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
	public String depthFirstSearchWithPath(int start, int end)
	{
		LinkedList<Integer> q = new LinkedList<Integer>();
		boolean[] checked = new boolean[vertices];
		int[] visitedFrom = new int[vertices];
		for(int i = 0;i<vertices;i++)
		{
			visitedFrom[i]=-1;
		}
		if(start == end)
			return "Start and end are the same";
		checked[start]=true;
		q.add(start);
		
		while(!q.isEmpty())
		{
			int current = q.removeLast();
			if(current == end)
			{
				return turnVisitedArrayIntoString(start, end, visitedFrom);
			}
			for(int i = 0;i<vertices;i++)
			{
				
				if(mat[current][i]!=0 && checked[i]==false )
				{
					checked[i]=true;
					visitedFrom[i]=current;
					q.add(i);
				}
			}
		}
		
		return "No Path Possible";
	}
	public String turnVisitedArrayIntoString(int start, int end, int[] visited)
	{
		List<Integer> path = new ArrayList<Integer>();
		int current=end;
		path.add(current);
		while(true)
		{
			if(current!=start)
			{
				current = visited[current];
				path.add(current);
			}
			else
			{
				break;
			}
		}
		Collections.reverse(path);
		StringBuilder b = new StringBuilder();
		b.append("Path is ");
		Iterator<Integer> i = path.iterator();
		while(true)
		{
			b.append(i.next());
			if(i.hasNext())
				b.append(" -> ");
			else
				break;
		}
		return b.toString();
	}
	// cost     [ , , ]
	// frontier [ , , ]
	public boolean uniformCostSearch(int start, int end)
	{
		LinkedList<Integer> q = new LinkedList<Integer>();
		boolean[] checked = new boolean[vertices];
		if(start == end)
			return true;
		checked[start]=true;
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
	
	
}
