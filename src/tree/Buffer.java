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

import vector2.Tupel;

public class Buffer {
	// till now just a typeDef
	private ArrayList<Tupel> line=new ArrayList<Tupel>(); // ToDo: Reduce unused capacity via limited capacity sharing along the Tree. Implicit and based on min and max
	private int min=0;

	private Tupel surroundedBy;
	public Buffer(Tupel surroundedBy){
		this.surroundedBy=surroundedBy;
	}
	
	// for text labels: draw right to left
	public int getMax(){
		return this.min+this.line.size();
	}
	
	public Tupel get(int i){
		if (i<this.min || i>=this.getMax()){ return this.surroundedBy;}
		return this.line.get(i);
	}
	
	public void set(int i, Tupel value) throws Exception {
		if (i < this.min) {
			// shift values. But wait, what if we already have active edge list? Chose implementation later.
			throw new Exception("Not implemented");
		} else {
			// ToDo: Too sparse: Use active edge list instead
			while (i >= this.getMax()) {
				this.line.add(this.surroundedBy);
			} 

			this.line.set(i, value);
			// ToDo: Shrink
		}
	}
}
