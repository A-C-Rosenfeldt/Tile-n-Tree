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

/**
 * @author Arne Rosenfeldt
 *
 */
public class ClosedInterval extends Tupel{
	public ClosedInterval(int y, int i) {
		super(y, i);
	}

	public int getLimitsSorted(int i){
		if (this.s[0]>this.s[1]) {i^=1;} 
		return this.s[i];
	}
	
	// ToDo: UnitTest
	public boolean isOverlapingWith(ClosedInterval i)
	{
		return !(this.getLimitsSorted(1)<i.getLimitsSorted(0) || i.getLimitsSorted(1)<this.getLimitsSorted(0));
	}
}
