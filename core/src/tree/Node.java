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
import java.util.List;


public class Node extends NodeBase {

	// Aggregation / persistent	
	private List<NodeBase> children; // should only contain is <Node>. ToDo: Test // NodeSkeleton Objects are returned by MergeIterator, but not referenced here
	// needed for tables. Useful for layout
	//private ArrayList<Node> childrenSwapCoordinates=new ArrayList<Node>();
	private int swapCoordinates = 0; // see  class Tiles  for definition (not yet fixed) 
	private boolean chicane = false; //a special for tables



	private Node link; // unused! appears to the left 
	
	// replaced by Skeleton : private Node classObject; //unused! appears below as  T-junction
	private boolean InlineReferenced; // read like  "Reference  is   inlined"
	private int referenceHistory = 0; // for dupes	
	
	// Volatile (if null, can be regenerated. Set to null on compression, or for cheap regeneration (short links).
	private Node valueOf; // internally every link is bidirectional  because otherwise the code has to search all the time  
	
	public Node() {
		this.children = new ArrayList<NodeBase>();
		///this.layout = new LayedOutPosition();
	}

	public Node(Node node) {
		this();	
		this.swapCoordinates = node.swapCoordinates; // see  class Tiles  for definition (not yet fixed) 
		this.chicane = node.chicane; //a special for tables

		// References
		this.link = node.link; // unused! appears to the left 

		this.InlineReferenced = node.InlineReferenced;
	}

	public Node(String string) {
		/// implicit only if no other constructor is called: super(string); // ToDo: Test
		super(string);
		this.children = new ArrayList<NodeBase>(); // Cannot call two constructors. Dupe one line of code, so what. ///this();
	}

	// Version with ref return (and with Inline)
	// No Covariance inside template brackets
	@Override
	public MergingIterator iterator() {
		if (this.value == null || !this.InlineReferenced) { // there can be simple references  without instancing
			return new MergingIterator(null, this.children.iterator(), this.title); // What works
			// What is meant, but leads to unsafe casts:  return this.children.iterator();
		}
		
		return new MergingIterator(this.value.children.iterator(), this.children.iterator(), this.title); // Bug: This layout is null
	}

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

	public void setValue(NodeBase value) {
		((Node) value).valueOf = this; // Being value of InstanceNode should not be remembered. Look up contract: Hierarchy is carried by LayedOutPosition
		this.value = (Node)value;
	}

	public void setValueDoNotNotify(Node value) {
		this.value = value;
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
		return this.referenceHistory;
	}

	public boolean isInlineReferenced() {
		return this.InlineReferenced;
	}

	public void setInlineReferenced(boolean inlineReferenced) {
		InlineReferenced = inlineReferenced;
	}

	public List<NodeBase> getChildren() {
		return this.children;
	}

	public List<Integer> getBedrock() {
		// TODO Auto-generated method stub
		return null;
	}


}
