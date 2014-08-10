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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import tree.Node;
import vector2.ClosedInterval;
import vector2.Tupel;
import vector2.Vector;

public class LinksWith2Bends implements Link, LinkDebug {
	// public for debug. I certainly do not want a global "DEBUG" option. ToDo: set to private
	public ArrayList<Integer> bedrock= new ArrayList<Integer>();

	@Override
	public void addBedrock(int x) {
		bedrock.add(x);
	}
	
	private ArrayList<LinkWith2Bends> y=new ArrayList<LinkWith2Bends>();
	
	private LinkWith2Bends[] ya;

	@Override
	public void addLink(Tupel x, ClosedInterval y, Node node) {
		this.y.add(new LinkWith2Bends(x, y, node));
	}

	@Override
	public void sort() {
		this.ya=(LinkWith2Bends[])y.toArray(); // as docu tells us, Collections.sort would do this anyway. May be useful
		
		// not obviously a natural comparator
		Arrays.sort(this.ya, new Comparator<LinkWith2Bends>(){

			@Override
			public int compare(LinkWith2Bends l0, LinkWith2Bends l1) {		
				Tupel a0=l0.y;
				Tupel a1=l1.y;
				return Math.abs(a0.s[1]-a0.s[0])-Math.abs(a1.s[1]-a1.s[0]);
			}
			
		});
		
//		Collections.sort(this.y, new Comparator<Tupel>(){
//
//			@Override
//			public int compare(Tupel o1, Tupel o2) {
//				// TODO Auto-generated method stub
//				return 0;
//			}});
		this.s=0;
	}
	
	private int s;

	// Just need to tell the starting point. End is known to node
	// This implementation is meant as reference and documentation and thus abstains from recursive code and optimized interfaces
	@Override
	public LinkWith2Bends getNext() {
		// route around bedrock // ToDo: Use data that bubbles up in the tree
		int[] this_ya_this_s__y_s=this.ya[this.s].y.s;
		int this_ya_this_s__xLeft=0;
		for (int y = this_ya_this_s__y_s[0]; y <= this_ya_this_s__y_s[1]; y++) {
			this_ya_this_s__xLeft=Math.max(this_ya_this_s__xLeft,this.bedrock.get(y));
		}
	
		// route around other links
		for (int other=0;other < this.s;other++){// for over all routed links (smaller index) in this collection
				if (this.ya[this.s].y.isOverlapingWith(this.ya[other].y)){
					this_ya_this_s__xLeft=this.ya[other].xRight+1; // no need to revisit older routes since they are ordered by x. ToDo: Make a UnitTest out of this
				}			
		}
	
		this.ya[this.s].xRight=this_ya_this_s__xLeft;
			
		// ToDo implement enumerator interface
		// now paint it (ToDo: May need to make a copy to write protect the original)
		return this.ya[this.s++];
	}

	// ToDo: Optimize  Tree  or  if really necessary: hash
	@Override
	public LinkWith2Bends get(Node node) {
		if (this.ya!=null)
		for (LinkWith2Bends linkWith2Bends : this.ya) {
			if (linkWith2Bends.node==node){
				return linkWith2Bends;
			}
		}
		return null;
	}

	@Override
	public ArrayList<Integer> getBedrock() {
		return this.bedrock;
	}

	@Override
	public boolean hasNext() {
		return this.ya.length>this.s++;
	}



}
