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

import java.util.Iterator;



/**
 * @author Arne Rosenfeldt
 *
 */
/**
ToDo: Move a lot of code form frame and here
 */
public class MergingIterator implements Iterator<NodeBase> {
	// references to persistent date
	///private Iterator<LayedOutPosition> layout;

	// volatile

	Iterator<NodeBase> value;
	Iterator<NodeBase> children;
	// Iterator for pass1
	// Layout iterator will be iterated in sync
	public MergingIterator(Iterator<NodeBase> iterator, Iterator<NodeBase> iterator2, String title) {
		this.value = iterator;
		this.children = iterator2;
		///this.layout = layout;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		return (value != null && value.hasNext()) || (children!= null && children.hasNext());
	}

	@Override
	public NodeBase next() {
		return this.nextNode();
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public NodeBase nextNode() {
		if (this.hasNext()) {
			this.feedTrough = false;
			NodeBase i_next;
			
			// new added code
			NodeBase children_next=null;
			if (children.hasNext()){
				children_next=children.next();
				if (children_next instanceof NodeInstance){
					value.next(); // dump to save location in NodeInstance from garbage. ToDo: Actively tell this method if it is called in Pass 0 or 1
					return children_next;
				}
			}
			
			// old code stays almost the same (except for children.next())
			if (value != null && value.hasNext()) {
				i_next = new NodeInstance(value.next()); ///, this.layout.next()); // ToDo: If null, create new layout (pass0, pass1)
				if (children.hasNext()) {
					i_next.setValue(children_next /*children.next()*/);
				}

				return i_next;
			} else {
				this.feedTrough = true;
				return children_next; //children.next(); // feed through custom fields
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

}
