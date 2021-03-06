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

/*
 * Vector here means: relative position. Everything is relative and I want to reduce the number of references to a global origin (which is relative to the computer hw).
 */
public class Vector extends Tupel {

	public Vector(int l0, int l1) {
		super(l0, l1);
		// TODO Auto-generated constructor stub
	}

	public Vector(Vector a, RectSize b, int x, int y) {
		super();
		for(int i=this.s.length-1;i>=0;i--){
			this.s[i]=a.s[i]+b.s[i]*y;
			y=x; // ToDo this hack does not generalize to nTupel
		}
	}

	public Vector(Vector a, RectSize b, Vector grid) {
		this(a,b,grid.s[0],grid.s[1]);
	}
	
	// I really miss  operator overloading  
	public void add(Vector d) {
		for(int i=this.s.length-1;i>=0;i--){
			this.s[i]+=d.s[i];
		}
		
	}
}
