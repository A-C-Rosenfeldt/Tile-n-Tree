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

// Todo
// 

package adHocRouter;

import tree.Node;
import vector2.ClosedInterval;
import vector2.Tupel;

public class LinkWith2Bends {
	// x and y are supposed to behave differently
	public ClosedInterval y;
	public Tupel x;
	public Integer xRight;
	public Node node;

	public LinkWith2Bends(Tupel x, ClosedInterval y2, Node node) {
		this.x=x;
		this.y=y2;
		this.node=node;
	}
	
	public void setDestination(int x_anchor, int y){
		this.x.s[1]=x_anchor;
		this.y.s[1]=y;
	}
	
	@Override
	public String toString(){
		return "Link( y:"+y.toString()+", x: "+x.toString()+" xRight: "+xRight+")";
	}

	public int whichSide(int y) {
		for (int side = 1; side >= 0; side--) {
			if (this.y.s[side] == y) {
				return side;
			}
		}
		
		return -1;
	}
}
