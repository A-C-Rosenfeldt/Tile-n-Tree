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
 * Iterable CoVariance does not work
 */
public abstract class NodeBase implements Iterable {

	protected String title;
	protected Node value; // used! appears  stores values

	// References
	protected LayedOutPosition layout; // Position on 2D Screen.
	
	// None of these constructors is suitable for NodeInstance
	protected NodeBase() {
		///ToDo: Initialize Children DataStructre new NodeBase();  Instance is degenerated in this respect
	}

	protected NodeBase(String title) {
		this();
		this.setTitle(title);
	}

	protected NodeBase(NodeBase node) {
		this(node.title);
		// Aggregation / persistent		

		//
		this.value = node.value; // used! appears  stores values
		this.title = node.getTitle();
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public abstract int getSwapCoordinates();

	// Interface to tree layout. It knows about SubType
	///public abstract void setLink(Node link) {

	// Interface to tree layout
	public Node getValue() {
		return value;
	}

	// Interface to tree layout
	public abstract void setValue(Node value);

	public abstract boolean isChicane();

	public LayedOutPosition getLayout() {
		return this.layout;
	}

	public void setLayout(LayedOutPosition layout) {
		this.layout = layout;
	}
}
