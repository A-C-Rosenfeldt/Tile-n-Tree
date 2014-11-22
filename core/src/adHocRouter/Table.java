package adHocRouter;

import java.util.ArrayList;

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
// call from Frame. 
// Parameter, return value?   like  when crossing chicane
// member like within
// So: Parameter, but hey no, 
// return value. It is pure upstream
// ToDo: Define return value {tupel, Table}
// huh, what may such a type mean?
// Okay pass reference to Table around via parameter, create new object at chicane.
// No return value!
public class Table {
	private ArrayList<Integer> gap = new ArrayList<Integer>(); // boxing :-(
	//private Deque s; // push and pop address side 0 of the dequeue. The other
						// side is private.

	public void push() {

	}

	public void pop() {

	}

	public void add() {

	}

	private int x;
	private int y;

	public int getFirst() {
		this.x = 0;
		this.y = 0;
		return this.getNext();
	}

	public int getNext() {
		while (this.x < this.gap.size() && this.gap.get(this.x++) > this.y) {
		}

		return this.x;
	}
}