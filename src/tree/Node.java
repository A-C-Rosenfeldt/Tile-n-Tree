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

public class Node {
	private String title;
	// Aggregation	
	private ArrayList<Node> children=new ArrayList<Node>();
	// needed for tables. Useful for layout
	//private ArrayList<Node> childrenSwapCoordinates=new ArrayList<Node>();
	private int swapCoordinates=0; // see  class Tiles  for definition (not yet fixed) 
	private boolean chicane=false; //a special for tables
	
	// References
	private Node link; // appears to the left 
	private Node value; // appears  stores values
	private Node classObject; // appears below as  T-junction
	
	// Not normalized. ToDo: Put into class Cache
	private Node valueOf; // 
	
	public Node(String title){
		this.setTitle(title);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public ArrayList<Node> getChildren() {
		return children;
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
		this.chicane = chicane;
	}

}
