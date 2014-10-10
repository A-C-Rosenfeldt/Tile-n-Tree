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
	private List<Node> children; // NodeSkeleton Objects are returned by MergeIterator, but not referenced here
	// needed for tables. Useful for layout
	//private ArrayList<Node> childrenSwapCoordinates=new ArrayList<Node>();
	private int swapCoordinates = 0; // see  class Tiles  for definition (not yet fixed) 
	private boolean chicane = false; //a special for tables

	// References
	private Node link; // unused! appears to the left 

	// replaced by Skeleton : private Node classObject; //unused! appears below as  T-junction
	private boolean InlineReferenced; // read like  "Reference  is   inlined"

	// Volatile (if null, can be regenerated. Set to null on compression, or for cheap regeneration (short links).
	private Node valueOf; // internally every link is bidirectional  because otherwise the code has to search all the time  
	private int referenceHistory = 0; // for dupes	

	public Node() {
		this.children = new ArrayList<Node>();
	}

	public Node(Node node) {
			
		this.swapCoordinates = node.swapCoordinates; // see  class Tiles  for definition (not yet fixed) 
		this.chicane = node.chicane; //a special for tables

		// References
		this.link = node.link; // unused! appears to the left 

		this.InlineReferenced = node.InlineReferenced;
	}

	public Node(String string) {
		/// implicit: super(string); // ToDo: Test 
	}

	// Version with ref return (and with Inline)
	// No Covariance inside template brackets
	@Override
	public Iterator iterator() {
		if (this.value == null || !this.InlineReferenced) {
			return this.children.iterator();
		}

		// Merging only inside Node but not inside NodeSkeleton
		// but (2nd) also for NodeSkeleton child!?
		Iterator<Node>[] i = new Iterator[2];
		i[0] = this.value.iterator(); // Values do themselves reference values => no real recursion, but turn to exit. Daisy chain needed for inheritance (test later! Is an advanced feature!)
		i[1] = this.children.iterator(); // No endless loop
		return new MergingIterator(i, this.title);
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

	public void setValue(Node value) {
		value.valueOf = this;
		this.value = value;
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
		return referenceHistory;
	}

	public boolean isInlineReferenced() {
		return InlineReferenced;
	}

	public void setInlineReferenced(boolean inlineReferenced) {
		InlineReferenced = inlineReferenced;
	}

	public List<Node> getChildren() {
		return children;
	}
}
