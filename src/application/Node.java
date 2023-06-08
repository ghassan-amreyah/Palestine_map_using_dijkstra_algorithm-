package application;


import java.util.ArrayList;

public class Node implements Comparable<Node>
{

	public String name ;
	public boolean known;
	public double distance;
	public Node path;
	public double x;
	public double y;
	ArrayList<Adjacent> list = new ArrayList<>();

	
	
	public Node(String name, boolean known, double distance, Node path, double x, double y)
	{
		this.name = name;
		this.known = known;
		this.distance = distance;
		this.path = path;
		this.x = x;
		this.y = y;
	}
	
	public Node()
	{
		
	}



	@Override
	public int compareTo(Node node) 
	{
		
		if(this.distance > node.distance)
		{
			return 1;
		}
		else if(this.distance < node.distance)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
	
	
	
}
