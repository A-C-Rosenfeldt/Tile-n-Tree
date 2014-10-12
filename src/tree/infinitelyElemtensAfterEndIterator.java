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
public class infinitelyElemtensAfterEndIterator implements Iterator<LayedOutPosition> {

	private int safetyFirst = 100;

	/* (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	@Override
	public boolean hasNext() {
		if (safetyFirst <= 0) {
			return false;
		}

		safetyFirst--;
		return true;
	}

	private LayedOutPosition layout;

	public infinitelyElemtensAfterEndIterator(LayedOutPosition layout) {
		this.layout = layout;
	}

	private infinitelyElemtensAfterEndIterator() {
		// Hide default
	}

	private Iterator<LayedOutPosition> iterator;

	private boolean pastTheEnd = false;

	/* (non-Javadoc)
	 * @see java.util.Iterator#next()
	 */
	@Override
	public LayedOutPosition next() {
		// ToDo: Move some of this into constructor
		if (!this.hasNext()) {
			return null;
		}

		if (this.layout.value_children == null) {
			this.layout.value_children = new ArrayList<LayedOutPosition>();
		}

		if (!this.pastTheEnd) {
			if (this.iterator == null) {
				this.iterator = this.layout.value_children.iterator();
			}

			if (this.iterator.hasNext()) {
				return this.iterator.next();
			}

			this.pastTheEnd = true;
		}

		LayedOutPosition t = new LayedOutPosition();
		this.layout.value_children.add(t);

		return t;
	}

	/* (non-Javadoc)
	 * @see java.util.Iterator#remove()
	 */
	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
