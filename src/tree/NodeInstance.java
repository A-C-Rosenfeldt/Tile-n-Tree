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
	public NodeInstance(Node node, LayedOutPosition layout) {
		super(node); // method needs to be inherited somewhere to exist
	}

	@Override
	public Iterator<? extends NodeBase> iterator() {
		List<Node> list = new ArrayList<Node>();
		list.add(this.value); // Degenerated
		return list.iterator();
	}

	@Override
	public int getSwapCoordinates() {
		return 0;
	}

	@Override
	public void setValue(Node value) {
		// Java does not allow me to implement some of it in abstract class or so (indirect error)
		this.value = value;
	}

	@Override
	public boolean isChicane() {
		return false;
	}
}
