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
import java.util.List;



/**
 * @author Arne Rosenfeldt
 *
 */
public class NodeInstance extends NodeBase {

	// Copy constructor, some info is copied from prototype, layout is copied from layout tree linked to first original parent
	public NodeInstance(NodeBase nodeBase, LayedOutPosition layout) {
		super(nodeBase); // method needs to be inherited somewhere to exist
		this.layout = layout;
	}

	@Override
	public MergingIterator iterator() {
		List<NodeBase> list = new ArrayList<NodeBase>();
		if (this.value != null){
			list.add(this.value); // Degenerated
		}
		
		return new MergingIterator(null, list.iterator(), this.title, this.layout.iterator()); // What works
		// What is meant, but leads to unsafe casts: return list.iterator();
	}

	@Override
	public int getSwapCoordinates() {
		return 0;
	}

	@Override
	public void setValue(NodeBase value) {
		// Java does not allow me to implement some of it in abstract class or so (indirect error)
		this.value = (Node) value;
	}

	@Override
	public boolean isChicane() {
		return false;
	}
}
