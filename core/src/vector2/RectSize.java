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
package vector2;

import java.awt.Rectangle; // ToDo: Look up the other types there! Rimplement Rect?

public class RectSize extends Tupel {

	
	public RectSize(int l0, int l1) {
		super(l0, l1);
		// TODO Auto-generated constructor stub
	}

	// interface with swing.Rect
	public RectSize(Rectangle r){
		super(r.width,r.height);
	}	
}
