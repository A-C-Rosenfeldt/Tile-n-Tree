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

// ToDo: Allow arbitrary min and max.

package tree;

import tile.Tile;

public class Buffer {
	// should be owned by LayedOutPosition
	private Tile[] line; // ToDo: Reduce unused capacity via limited capacity sharing along the Tree. Implicit and based on min and max
	private int[] boundary={0, -1};
	private int offset;

	private Tile surroundedBy;
	public Buffer(Tile surroundedBy){
		this.surroundedBy=surroundedBy;
	}
	
	// needed for coordinate swap
	public Buffer(int y) {
		this.boundary = new int[]{y,y-1};
	}

	// for text labels: draw right to left
	// Clean interface wins over  "if" . Compiler knows how to optimize.
	public int getBoundary(int i){
		return this.boundary[i];
		/*
		if (i==0){ return this.min;}
		else {return this.min+this.line.size();}
		*/
	}
	
	public Tile get(int i){
		if (i<this.boundary[0] || i>this.boundary[1]){
			return this.surroundedBy; // This class is supposed to work with the tile renderer, which is supposed to be an opaque background-layer (retro style).
		}
		
		Tile t= this.line[i+this.offset];
		if (t==null) {
			return this.surroundedBy;
		}
		
		return t;
	}
	
	// ToDo: Make parent? But access should be private
	private class Skeleton{
		public Tile[] tiles;
		public int offset;
	}
	
	private Skeleton newOffset(int size, int[] boundary){
		Skeleton s=new Skeleton();
		s.tiles=new Tile[size << 1];
		s.offset= (size >> 1) - boundary[0];
		return s;		
	}
	
	private void realloc(int[] boundary) {
		int sizeOld = this.boundary[1] + 1 - this.boundary[0]; // ToDo: Import Span
		int sizeNew = boundary[1] + 1 - boundary[0]; // ToDo: Import Span
		Skeleton s = this.newOffset(sizeNew, boundary); // ToDo remove dupe of size heurisitc
		System.out.println(this.line+", "+ boundary[0] +", "+ this.offset+", "+ s.tiles+", "+ boundary[0] +", "+ s.offset+", "+ sizeOld);
		System.out.println(this.line+", "+ (boundary[0] + this.offset)+", "+ s.tiles+", "+ (boundary[0] + s.offset)+", "+ sizeOld);
		// Bug: the parameter boundary is not the old boundary but a mix of new and old. Should fail every test. ToDo: Unit test
		// System.arraycopy(this.line,      boundary[0] + this.offset, s.tiles, boundary[0] + s.offset, sizeOld);
		int t=Math.max(this.boundary[0],boundary[0] );
		System.out.println((this.line).toString()+", "+((Integer) (t + this.offset)).toString()+", "+( s.tiles).toString()+", "+((Integer)( t + s.offset)).toString()+", "+
				(
						(Integer) (Math.min(this.boundary[1], boundary[1])+1-t )
				).toString()
		);
		   System.arraycopy(this.line, t + this.offset, s.tiles, t + s.offset, Math.min(this.boundary[1], boundary[1])+1-t );
		this.line = s.tiles;
		this.offset = s.offset;
	}	
	
	public void set(int i, Tile value)  {	
		if (this.line == null) {
			this.boundary[0] = this.boundary[1] = i;
			Skeleton s = this.newOffset(8, this.boundary); // the set operation occurs inside a loop. We cannot avoid uninitialized objects
			this.line = s.tiles;
			this.offset = s.offset;
		}
		
		this.expand(i);

		this.line[i + this.offset] = value;
	}

	private boolean expand(int i) {
		if (i < this.boundary[0]) {
			if (i + this.offset < 0) {
				this.realloc(new int[]{i, this.boundary[1]});
			}
			
			for (int k = this.boundary[0] - 1 + this.offset; k > i + this.offset; k--) {
				this.line[k] = null;
			}
			
			this.boundary[0] = i;
			
			return true;
		} else {
			if (i > this.boundary[1]) {
				if (i + this.offset >= this.line.length) {
					this.realloc(new int[] { this.boundary[0], i });
				}
				
				for (int k = this.boundary[1] + 1 + this.offset; k < i + this.offset; k++) {
					this.line[k] = null;
				}
				
				this.boundary[1] = i;
				return true;				
			}
		}
		
		return false;		
	}
	
	public void uniteAt(int i, Tile value) throws Exception {
		if (this.line == null) {
			this.set(i, value);
		} else {
			if (this.expand(i)){
				this.set(i, value);
				return;
			}

			Tile old = this.line[i + this.offset];
			if (old == null) {
				this.set(i, value); // ToDo: try to avoid null
			} else {
				old.uniteWith(value);
			}
		}
	}	
}
