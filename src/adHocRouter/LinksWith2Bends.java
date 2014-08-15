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
	
	@Override
	public void addBedrock(int x, int y) {
		// not called for
		while (y>this.bedrock.size()){
			this.bedrock.add(0);
		}
		
		// okay
		if (y==this.bedrock.size()){
			this.bedrock.add(x);
		}else{
			this.bedrock.set(y, Math.max(this.bedrock.get(y), x));
		}
	}
	
	private ArrayList<LinkWith2Bends> y=new ArrayList<LinkWith2Bends>();
	
	private Object[] ya; // LinkWith2Bends  . ToDo: hide type inside sort?
	
	private LinkWith2Bends getYa(int i){
		return (LinkWith2Bends)this.ya[i];
	}

	private void setYa(int i,LinkWith2Bends v){
		this.ya[i]=(Object)v;
	}
		
	@Override
	public void addLink(Tupel x, ClosedInterval y, Node node) {
		this.y.add(new LinkWith2Bends(x, y, node));
	}

	@Override
	public void sort() {
		this.ya=y.toArray(); // as docu tells us, Collections.sort would do this anyway. May be useful
		
		// not obviously a natural comparator
		Arrays.sort(this.ya, new Comparator<Object>(){

			@Override
			public int compare(Object l0, Object l1) {		
				Tupel a0=((LinkWith2Bends)l0).y;
				Tupel a1=((LinkWith2Bends)l1).y;
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
		// Strange: Link( y:{23, 0}, x: {4, 0} xRight: 0)

		
		// route around bedrock // ToDo: Use data that bubbles up in the tree
		System.out.println("s is "+this.s+" von "+this.ya.length);
		int[] this_ya_this_s__y_s=this.getYa(this.s).y.s;
		int this_ya_this_s__xRight=0;
		
		// Do not merge with render code because: ToDo: Walk the tree
		int[] ySorted=this_ya_this_s__y_s.clone();
		if (ySorted[0]>ySorted[1]){
			int t=ySorted[0];
			ySorted[0]=ySorted[1];
			ySorted[1]=t;				
		}

		int y=ySorted[0];
		this_ya_this_s__xRight=Math.max(this_ya_this_s__xRight,this.bedrock.get(y)+1); // Space for Endmarkers
		y++;
		while ( y <= ySorted[1]-1) {
			this_ya_this_s__xRight=Math.max(this_ya_this_s__xRight,this.bedrock.get(y));
			y++;
		}		
		this_ya_this_s__xRight=Math.max(this_ya_this_s__xRight,this.bedrock.get(y)+1);
		
		++this_ya_this_s__xRight; // Next to bedrock
		
		System.out.println("Bedrock says"+this_ya_this_s__xRight);
	
		// route around other links
		for (int other=0;other < this.s;other++){// for over all routed links (smaller index) in this collection
				if (this.getYa(this.s).y.isOverlapingWith(this.getYa(other).y)){
					this_ya_this_s__xRight=this.getYa(other).xRight+1; // no need to revisit older routes since they are ordered by x. ToDo: Make a UnitTest out of this
					System.out.println( "["+other+"] = "+ this_ya_this_s__xRight);					
				}
		}
	
		this.getYa(this.s).xRight=this_ya_this_s__xRight;
		
		System.out.println( "GetNext: "+this.getYa(this.s));
			
		// ToDo implement enumerator interface
		// now paint it (ToDo: May need to make a copy to write protect the original)
		return this.getYa(this.s++);
	}

	// ToDo: Optimize  Tree  or  if really necessary: hash
	@Override
	public LinkWith2Bends get(Node node) {
		if (this.y!=null)
		for (Object linkWith2Bends : this.y) {
			if (((LinkWith2Bends)linkWith2Bends).node==node){
				return (LinkWith2Bends)linkWith2Bends;
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
		return this.ya.length>this.s;
	}
}
