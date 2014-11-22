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

/**
 * @author Arne Rosenfeldt
 *
 */
public class LinkedElement {
	private LinkedElement parent;
	private NodeBase node;

	private LinkedElement(){}
	
	public LinkedElement( NodeBase node){
		//this.setParent(parent);
		this.setNode(node);
	}
	
	public LinkedElement child(NodeBase node) {
		if (node==null){
			throw new NullPointerException();
		}
		LinkedElement child = new LinkedElement();
		child.setParent(this);
		child.setNode(node);
		return child;
	}

	public LinkedElement getParent() {
		return parent;
	}

	public void setParent(LinkedElement parent) {
		if (parent==null){
			throw new NullPointerException();
		}
		this.parent = parent;
	}

	public NodeBase getNode() {
		return node;
	}

	public void setNode(NodeBase node) {
		if (node==null){
			throw new NullPointerException();
		}
		this.node = node;
	}
}
