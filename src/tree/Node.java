package tree;

import java.util.ArrayList;

public class Node {
	public String title;
	public ArrayList<Node> children=new ArrayList<Node>();
	
	public Node(String title){
		this.title=title;
	}
}
