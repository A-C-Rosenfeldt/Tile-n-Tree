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
	private Iterator<LayedOutPosition> layout;
	private Node node;
	private Node owningParent; // for instancing. My caller may no know it

	// volatile

	Iterator<Node>[] i;
	private String title; // for debugging at least

	// Iterator for pass1
	// Layout iterator will be iterated in sync
	public MergingIterator(Iterator<Node>[] i, String title, Iterator<LayedOutPosition> layout) {
		this.i = i;
		this.title = title;
		this.layout = layout;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		if (i[0].hasNext() || i[1].hasNext()) {
			return true;
		}
		return false;
	}

	@Override
	public Object next() {
		return this.nextNode();
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	public NodeBase nextNode() {
		if (this.hasNext()) {
			this.feedTrough = false;
			NodeBase i_next;
			if (i[0].hasNext()) {
				i_next = new NodeInstance(i[0].next(), this.layout.next()); // ToDo: If null, create new layout (pass0, pass1)
				if (i[1].hasNext()) {
					i_next.setValue(i[1].next());
				}

				return i_next;
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

}
