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
 * @author Arne Rosenfeldt
 *
 */
public class MergingIterator implements Iterator {

	Iterator<Node>[] i;
	private String title; // for debugging at least

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

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	@Override
	public Object next() {
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
