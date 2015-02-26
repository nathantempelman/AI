package assignment2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Scanner;


public class AStar {
	char[][] maze;
	int goalX;
	int goalY;
	int [][] g;
	int height;
	int width;
	
	public class Node implements Comparable {
		int x = -1;
		int y = -1;
		int g = -1;
		int f = -1;
		public Node(int x, int y, int g, int f)	{
			this.x=x;
			this.y=y;
			this.g=g;
			this.f=f;
		}
		public Node(int x, int y){
			this.x=x;
			this.y=y;
		}
		public boolean equals(Object o)	{
			if(o instanceof Node){
				Node other = (Node) o;
				return x==other.x&&y==other.y;
			}
			return false;		
		}
		public int compareTo(Object o)	{
			if(o instanceof Node){
				Node other = (Node) o;
				return f-other.f;
			}
			return -1;
		}

	}

	public AStar(int height, int width){
		maze = new char[height][width];
		g = new int[height][width];
		this.height = height;
		this.width = width;
		
		for(int i = 0;i<height;i++)	{
			for(int j = 0; j<width;j++)	{
				maze[i][j]='~';
			}
		}
	}

	public String AstarSearch(int x, int y){
		for(int i = 0;i<height;i++)	{
			for(int j = 0; j<width;j++)	{
				g[i][j]=Integer.MAX_VALUE;
			}
		}
		PriorityQueue<Node> q = new PriorityQueue<Node>();
		q.add(new Node(x,y,0,f(x,y,0)));
		g[x][y]=0;
		while(!q.isEmpty())	{
			Node current = q.poll();
			System.out.println(current.x+ " " + current.y+ " " + current.f + " "+ current.g);
			Node[] neighbor = getNeighbors(current);
			for(int i = 0;i<neighbor.length;i++){
				if(neighbor[i].x==-1||maze[neighbor[i].x][neighbor[i].y]=='o'){
					continue;
				}
				if(neighbor[i].x==goalX&&neighbor[i].y==goalY)	{
					g[neighbor[i].x][neighbor[i].y]=current.g+1;
					neighbor[i].g=current.g+1;
					return getPath(x,y);
				}
				
				if(g[neighbor[i].x][neighbor[i].y]>current.g+1)	{
					if(q.contains(neighbor[i]))	
						q.remove(neighbor[i]);
					g[neighbor[i].x][neighbor[i].y]=current.g+1;
					neighbor[i].g=current.g+1;
					neighbor[i].f = f(neighbor[i]);
					q.add(neighbor[i]);
				}
				
				
			}
			
			
			
		}
		
		return "No Path Possible";
		
	}
	public Node[] getNeighbors(Node current){
		Node[] neighbors = new Node[4];
		//left
		if(current.y-1<0)
			neighbors[0]=new Node(-1,-1);
		else
			neighbors[0]=new Node(current.x, current.y-1);
		//right
		if(current.y+1>=width)
			neighbors[1]=new Node(-1,-1);
		else
			neighbors[1]=new Node(current.x, current.y+1);
		//up
		if(current.x-1<0)
			neighbors[2]=new Node(-1,-1);
		else
			neighbors[2]=new Node(current.x-1, current.y);
		//down
		if(current.x+1>=height)
			neighbors[3]=new Node(-1,-1);
		else
			neighbors[3]=new Node(current.x+1, current.y);
		return neighbors;
	}
	public int f(Node n){
		return f(n.x,n.y,n.g);
	}
	public int f(int x, int y, int g){
		return g+h(x,y);
	}
	public int h(int x, int y) {
		int h = 0;
		while(true){
			if(x>goalX)
				x--;
			else if(x<goalX)
				x++;
			else{
				//h++;
				break;
			}
			switch (maze[x][y]){
				case 'o': h++;
				case '~': h++;
			}
		}
		while(true){
			if(y>goalY)
				y--;
			else if(y<goalY)
				y++;
			else{
				h++;
				break;
			}
			switch (maze[x][y]){
				case 'o': h++;
				case '~': h++;
			}
		}
		return h;
	}
	public String getPath(int startX, int startY){
		int x=goalX;
		int y=goalY;
		int length = 0;
		List<String> l = new ArrayList<String>();
		while(true)	{
			length++;
			if(x==startX&&y==startY){
				break;
			}
			//left
			if(y-1>=0)
				if(g[x][y-1]==g[x][y]-1){
					y--;
					l.add("R");
					continue;
				}
			//right
			if(y+1<width)
				if(g[x][y+1]==g[x][y]-1){
					y++;
					l.add("L");
					continue;
				}
			//up
			if(x-1>=0)
				if(g[x-1][y]==g[x][y]-1){
					x--;
					l.add("D");
					continue;
				}
			//down
			if(x+1<height)
				if(g[x+1][y]==g[x][y]-1){
					x++;
					l.add("U");
					continue;
				}
		}
		Collections.reverse(l);
		return l.toString().replace("[", "").replace("]", " Length: "+length);
		
	}
	public String toString() {
		StringBuilder str = new StringBuilder();
		for(int i = 0;i<height;i++)	{
			for(int j = 0; j<width;j++)	{
				str.append(maze[i][j]+" ");
			}
			str.append("\n");
		}
		return str.toString();
		
	}
	public static void main(String[] args) {
		AStar maze = new AStar(7,7);
		//add obstacles and set goal
		maze.goalX=3;
		maze.goalY=3;
		maze.maze[1][1] = maze.maze[2][1] = maze.maze[3][1]= maze.maze[3][2]= maze.maze[4][2]= maze.maze[5][2]= maze.maze[5][3]= maze.maze[5][4]
				= maze.maze[0][3]= maze.maze[1][4]= maze.maze[2][4]= maze.maze[3][4]= 'o';
		maze.maze[3][3]='@';
		
		Scanner input = new Scanner(System.in);
		int x1,x2,x3,x4;
		int y1,y2,y3,y4;
		System.out.print(maze);
		while(true)
		{
			System.out.println("Enter the vertical (row) position of the first racer");
			x1 = input.nextInt();
			System.out.println("Enter the horizontal (column) position of the first racer");
			y1 = input.nextInt();
			if(y1>=0&&x1>=0&&x1<maze.height&&y1<maze.width){
				break;
			}
		}
		while(true)
		{
			System.out.println("Enter the vertical (row) position of the second racer");
			x2 = input.nextInt();
			System.out.println("Enter the horizontal (column) position of the second racer");
			y2 = input.nextInt();
			if(y2>=0&&x2>=0&&x2<maze.height&&y2<maze.width){
				break;
			}
		}
		while(true)
		{
			System.out.println("Enter the vertical (row) position of the third racer");
			x3 = input.nextInt();
			System.out.println("Enter the horizontal (column) position of the third racer");
			y3 = input.nextInt();
			if(y3>=0&&x3>=0&&x3<maze.height&&y3<maze.width){
				break;
			}
		}
		while(true)
		{
			System.out.println("Enter the vertical (row) position of the fourth racer");
			x4 = input.nextInt();
			System.out.println("Enter the horizontal (column) position of the fourth racer");
			y4 = input.nextInt();
			if(y4>=0&&x4>=0&&x4<maze.height&&y4<maze.width){
				break;
			}
		}
		
		String result1, result2,result3,result4;
		result1=maze.AstarSearch(x1, y1);
		result2=maze.AstarSearch(x2, y2);
		result3=maze.AstarSearch(x3, y3);
		result4=maze.AstarSearch(x4, y4);
		System.out.println("First racer path:");
		System.out.println(result1);
		System.out.println("Second racer path:");
		System.out.println(result2);
		System.out.println("Third racer path:");
		System.out.println(result3);
		System.out.println("Fourth racer path:");
		System.out.println(result4);
		
		
		int[] winner = new int[]{result1.length(),result2.length(),result3.length(),result4.length()};
		List<Integer> wini=new ArrayList<Integer>();
		int winl=Integer.MAX_VALUE;
		for(int i = 0;i<winner.length;i++)	{
			if(winner[i]<winl)	{
				wini.clear();
				wini.add(i+1);
				winl=winner[i];
			}else if(winner[i]==winl)
				wini.add(i+1);
		}
		if(wini.size()==1)
		{
			System.out.println("The winner is racer "+wini.get(0));
		}else{
			System.out.print(wini.size()+" way tie between racers " + wini.remove(0));
			while(wini.size()>1) {
				System.out.print(", "+wini.remove(0));
				if(wini.size()==1)
					System.out.print(", ");
			}
			System.out.print(" and "+wini.remove(0));
		}
		
		//repeated for Maze 2
		maze = new AStar(9,9);
		//add obstacles and set goal
		maze.goalX=3;
		maze.goalY=3;
		maze.maze[1][1] = maze.maze[2][1] = maze.maze[3][1]= maze.maze[3][2]= maze.maze[4][2]= maze.maze[5][2]= maze.maze[5][3]= maze.maze[5][4]
				= maze.maze[0][3]= maze.maze[1][4]= maze.maze[2][4]= maze.maze[3][4]= 'o';
		//new obstacles
		maze.maze[6][5] = maze.maze[2][8]= maze.maze[3][8]= maze.maze[4][8]= maze.maze[5][8]= maze.maze[6][8]
				= maze.maze[2][7]= maze.maze[3][7]= maze.maze[4][7]= maze.maze[5][7]= maze.maze[6][7] = 'o';
		maze.maze[3][3]='@';
		

		System.out.print(maze);
		while(true)
		{
			System.out.println("Enter the vertical (row) position of the first racer");
			x1 = input.nextInt();
			System.out.println("Enter the horizontal (column) position of the first racer");
			y1 = input.nextInt();
			if(y1>=0&&x1>=0&&x1<maze.height&&y1<maze.width){
				break;
			}
		}
		while(true)
		{
			System.out.println("Enter the vertical (row) position of the second racer");
			x2 = input.nextInt();
			System.out.println("Enter the horizontal (column) position of the second racer");
			y2 = input.nextInt();
			if(y2>=0&&x2>=0&&x2<maze.height&&y2<maze.width){
				break;
			}
		}
		while(true)
		{
			System.out.println("Enter the vertical (row) position of the third racer");
			x3 = input.nextInt();
			System.out.println("Enter the horizontal (column) position of the third racer");
			y3 = input.nextInt();
			if(y3>=0&&x3>=0&&x3<maze.height&&y3<maze.width){
				break;
			}
		}
		while(true)
		{
			System.out.println("Enter the vertical (row) position of the fourth racer");
			x4 = input.nextInt();
			System.out.println("Enter the horizontal (column) position of the fourth racer");
			y4 = input.nextInt();
			if(y4>=0&&x4>=0&&x4<maze.height&&y4<maze.width){
				break;
			}
		}
		

		result1=maze.AstarSearch(x1, y1);
		result2=maze.AstarSearch(x2, y2);
		result3=maze.AstarSearch(x3, y3);
		result4=maze.AstarSearch(x4, y4);
		System.out.println("First racer path:");
		System.out.println(result1);
		System.out.println("Second racer path:");
		System.out.println(result2);
		System.out.println("Third racer path:");
		System.out.println(result3);
		System.out.println("Fourth racer path:");
		System.out.println(result4);
		
		
		winner = new int[]{result1.length(),result2.length(),result3.length(),result4.length()};
		winl=Integer.MAX_VALUE;
		for(int i = 0;i<winner.length;i++)	{
			if(winner[i]<winl)	{
				wini.clear();
				wini.add(i+1);
				winl=winner[i];
			}else if(winner[i]==winl)
				wini.add(i+1);
		}
		if(wini.size()==1)
		{
			System.out.println("The winner is racer "+wini.get(0));
		}else{
			System.out.print(wini.size()+" way tie between racers " + wini.remove(0));
			while(wini.size()>1) {
				System.out.print(", "+wini.remove(0));
				if(wini.size()==1)
					System.out.print(", ");
			}
			System.out.print(" and "+wini.remove(0));
		}
	}
	

}
