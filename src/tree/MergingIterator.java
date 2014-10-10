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
package tree;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Arne Rosenfeldt
 *
 */
/**
ToDo: Move a lot of code form frame and here
 */
public class MergingIterator implements Iterator {
	// references to persistent date
	private LayedOutPosition layout;
	private Node node;
	private Node owningParent; // for instancing. My caller may no know it
	
	// On first call set everything to null
	public MergingIterator(	LayedOutPosition layout, Node node, Node owningParent){
		this.layout = layout;
		this.node = node;
		this.owningParent = owningParent;
		
		this.i= node.getIterators();
		 
	}
	
	// volatile
	
	Iterator<Node>[] i;
	private String title; // for debugging at least

	// Iterator for pass1
	
	public MergingIterator(Iterator<Node>[] i, String title) {
		this.i = i;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		if (i[0].hasNext() || i[1].hasNext()) { return true; }
		return false;
	}

	@Override
	public Object next() {
		return this.nextNode();
	}
	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public Node nextNode() {
		if (this.hasNext()) {
			this.feedTrough = false;
			Node i_next;
			if (i[0].hasNext()) {
				i_next = new Node(i[0].next(), 1, true);
				//i_next.g.children=new ArrayList<Node>(); // ToDo: Ugly .  But according to design. Copy should appear a deep at first sight. Just save mem internally, but do not leak. Here choosing a value means actively deciding against other values

				// null pointer or not null pointer? Since enums are supposed to
				// have entry "none", I guess null=none is okay.
				if (i[1].hasNext()) {
					// ia_next.
					Node t = i[1].next();
					//Node mirror = new Node(t.getValue(), 2, false); // internally the instance points to the global address of the value
					LayedOutPosition layout=new LayedOutPosition();
					///mirror.setTitle(t.getTitle() + " / " + mirror.getTitle());
					// ToDo: Use LayedOutPosition instead of real node. Worst thing that can happen is a data member named "parent"
					// But then this is internal to first pass. First pass does not know of  layout, so what
					// Right now referenceHistory is used
					layout.value_children.add(layout); // ToDo: Is it possible to store skeleton in MergingIterator? No many refs! (even with forced PreOrder!)
					//i_next.getChildren().add(mirror); // ToDo: This link is not needed, instead: add child to layout, .   Bug: Has side-effect!  ToDo: Allow multi-select ?
					//i_next.setValue(mirror); // for the UI this would lead to link spaghetti. Thus: a mirror. And valueOf works!
					layout.value = t; // ToDo: Base Class for  Node and LayedOutPosition. Or new class: SkeletonNode? But SkeletonNodes and Layout are produced at the same time
					System.out.println("set mirror on " + this.title + " / " + t.getTitle());
				}

				return i_next  layout  ||  i[1].next();
			} else {
				this.feedTrough = true;
				return i[1].next(); // feed through custom fields
			}

		}
		return null;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 * I use this interface because of the better names and the possibility of an expansion in the future
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

	private boolean feedTrough;
	
	/*
	 *  For pass two: where to attach the layout information to
	 */
	public boolean lastWasFeedthrough() {
		return this.feedTrough;
	}
	
	// Interface > 20141008
	// Iterator for generating the tree, relies on above iterators
	// in frame: layout =new LayedOutPosition(positionInGridcount)
	public MergingIterator rGenerateNext(LayedOutPosition layout){
		
		// ToDo add this to iterator, to run the same code in Frame(pass1) and here (pass2)
		LayedOutPosition layoutChild = null;
		if (node.getReferenceHistory() == 2) { // ToDo layout reference as parameter
			node.layout = layout;
		}else{				
			// some ancestor already got inlined
			if (layout != null) {
				layoutChild = layout;
				layout.value_children.add(layoutChild);
			} else {
				if (iterator.lastWasFeedthrough()) { // ToDo layout reference as parameter
					node.layout = layout;
				} else {
					parent.layout.value_children.add(layout);
				}
			}
		}		
		
		
		return null;
		// ToDo: Use Type system to stay in read-only mode?? Typically I would search and then modifie.. does not make no sense
	}
	
	// Java7 does not allow different return types (when calling use the one which fits best with lowest number of casts
	public MergingIterator rNext(){
		// Apparently cannot work: return new MergingIterator(this.nextNode(), this.layout.value_children);

		if (this.hasNext()) {
			this.feedTrough = false;
			Node i_next;
			if (i[0].hasNext()) {
				i_next = new Node(i[0].next(), 1, true);
				//i_next.g.children=new ArrayList<Node>(); // ToDo: Ugly .  But according to design. Copy should appear a deep at first sight. Just save mem internally, but do not leak. Here choosing a value means actively deciding against other values

				// null pointer or not null pointer? Since enums are supposed to
				// have entry "none", I guess null=none is okay.
				if (i[1].hasNext()) {
					// ia_next.
					Node t = i[1].next();
					Node mirror = new Node(t.getValue(), 2, false); // internally the instance points to the global address of the value
					mirror.setTitle(t.getTitle() + " / " + mirror.getTitle());
					// ToDo: Use LayedOutPosition instead of real node. Worst thing that can happen is a data member named "parent"
					// But then this is internal to first pass. First pass does not know of  layout, so what
					// Right now referenceHistory is used
					i_next.getChildren().add(mirror); // Bug: Has side-effect!  ToDo: Allow multi-select ?
					i_next.setValue(mirror); // for the UI this would lead to link spaghetti. Thus: a mirror. And valueOf works!
					System.out.println("set mirror on " + this.title + " / " + t.getTitle());
				}

				return new MergingIterator(this.layout, i_next);
			} else {
				this.feedTrough = true;
				return new MergingIterator(this.layout, i[1].next());
			}

		}
		return null;		
		
	}
	
	public boolean hasRNext(){
		return this.hasNext();		
	}	
}
