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

// Todo: Template / generic to remove int  (not boxed right now)

public class Tupel {
	public int[] s = new int[2]; // Scalar, (or ordinate? or component? direction?)
	
	// shorten notation
	public Tupel(int l0, int l1){
		this.s[0]=l0;
		this.s[1]=l1;
	}
	
	protected Tupel() {
		// do not create initialized objects! But this is needed for constructors in derived classes
	}

	public void AddAt(int x, int i)
	{
		this.s[i]+=x;
	}
}
