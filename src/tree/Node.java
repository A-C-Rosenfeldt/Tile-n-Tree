/* Copyright 2014   Arne Christian  Rosenfeldt

This file is part of Tile'n'Tree.

Tile'n'Tree is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

Tile'n'Tree is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with Tile'n'Tree.  If not, see <http://www.gnu.org/licenses/>.
 */

// ToDo
// Manage access permissions. Paint should get an readOnly Interface, while util and mod need to write

package tree;

import java.util.ArrayList;
import java.util.Iterator;

public class Node {
	private String title;
	// Aggregation / persistent	
	private ArrayList<Node> children=new ArrayList<Node>();
	// needed for tables. Useful for layout
	//private ArrayList<Node> childrenSwapCoordinates=new ArrayList<Node>();
	private int swapCoordinates=0; // see  class Tiles  for definition (not yet fixed) 
	private boolean chicane=false; //a special for tables
	
	// References
	private Node link; // unused! appears to the left 
	private Node value; // used! appears  stores values
	private Node classObject; //unused! appears below as  T-junction
	private boolean InlineReferenced; // read like  "Reference  is   inlined"
	
	// volatile. ToDo: Put some (where oo does not work well) into extra class Cache
	private Node valueOf; // internally every link is bidirectional  because otherwise the code has to search all the time  
	private int referenceHistory=0; // for dupes
	
	public Node(String title){
		this.setTitle(title);
	}

	public Node(Node node, int event) {
		// { sort of   duplicate of field declaration. ToDo: Group
		this.title=node.title;
		// Aggregation / persistent	
		this.children=node.children;
		// needed for tables. Useful for layout
		//private ArrayList<Node> childrenSwapCoordinates=new ArrayList<Node>();
		this.swapCoordinates=node.swapCoordinates; // see  class Tiles  for definition (not yet fixed) 
		this.chicane=node.chicane; //a special for tables
		
		// References
		this.link=node.link; // unused! appears to the left 
		this.value=node.value; // used! appears  stores values
		this.classObject=node.classObject; //unused! appears below as  T-junction
		this.InlineReferenced=node.InlineReferenced; 		
		// }
		
		this.referenceHistory=(node.referenceHistory<<2)|(event & 3); // I want to limit the types of links to 4  because this is a difficult concept for the user and I may have to mark them graphically  which gets confusing fast
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// ToDo: getChildren with out inlining makes no sense in context, but is needed for Getter Setter behaviour
	public ArrayList<Node> getChildren() {
		// Merge duplicates a lot and would hinders debugging
		// Daisy chain
		
		if (this.value==null){
			return this.children;
		}
		
		ArrayList<Node> c=new ArrayList<Node>( this.value.getChildren().size()+this.children.size() ); // I would like to have a temporary array to avoid bugs to increase the lenght uncontrolled
		// Some map, zip function needed here, but hey it is java, so
		// Incompatible with write:  Iterator<Node> ic=c.iterator();
		Iterator<Node> ia=this.value.getChildren().iterator();
		
		while(ia.hasNext()){
			Node ia_next=new Node(ia.next(),1);
			c.add(ia_next);			
		}

		c.addAll(this.children);
	//	return this.inlineReferenced?this.value.children:children;
		// Merge needed for inheritance
		// So: record path? Nodes with instance-aggregate in their path would have their value limited to their children (children in type def)
		return c;
		//return new this.children;
		
		// ToDo: Inform children about this indirection => write protection.
		// I will have to leak some info for the  rasterizer  to  count levels on  and to pass to node
	}

	public void setChildren(ArrayList<Node> children) {
		this.children = children;
	}

//	public ArrayList<Node> getChildrenSwapCoordinates() {
//		return childrenSwapCoordinates;
//	}
//
//	public void setChildrenSwapCoordinates(ArrayList<Node> childrenSwapCoordinates) {
//		this.childrenSwapCoordinates = childrenSwapCoordinates;
//	}

	public int getSwapCoordinates() {
		return swapCoordinates;
	}

	public void setSwapCoordinates(int swapCoordinates) {
		this.swapCoordinates = swapCoordinates;
	}

	public Node getLink() {
		return link;
	}

	public void setLink(Node link) {
		this.link = link;
	}

	public Node getValue() {
		return value;
	}

	public void setValue(Node value) {
		value.valueOf=this;
		this.value = value;
	}

	public Node getClassObject() {
		return classObject;
	}

	public void setClassObject(Node classObject) {
		this.classObject = classObject;
	}

	public Node getValueOf() {
		return valueOf;
	}

	public boolean isChicane() {
		return chicane;
	}

	public void setChicane(boolean chicane) {
		// ToDo: Check trans and parents and siblings
		this.chicane = chicane;
	}

	public int getReferenceHistory() {
		return referenceHistory;
	}

	public boolean isInlineReferenced() {
		return InlineReferenced;
	}

	public void setInlineReferenced(boolean inlineReferenced) {
		InlineReferenced = inlineReferenced;
	}
}
