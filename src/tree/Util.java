package tree;

public class Util {
	public static Node createSampleTree(){
		Node charles=new Node("Charles");
		charles.children.add(new Node("Harry"));
		charles.children.add(new Node("Wiliam"));
		Node node=new Node("Hello again");
		node.children.add(charles);
		node.children.add(new Node("Philip"));	

		return node;
	}
}
