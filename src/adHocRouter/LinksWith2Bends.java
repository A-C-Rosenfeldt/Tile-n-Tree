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

import vector2.Tupel;
import vector2.Vector;

public class LinksWith2Bends implements Link {
	private ArrayList<Integer> bedrock= new ArrayList<Integer>();

	@Override
	public void addBedrock(int x) {
		bedrock.add(x);
	}
	
	private ArrayList<LinkWith2Bends> y;
	
	private LinkWith2Bends[] ya;

	@Override
	public void addLink(Tupel x, Tupel y) {
		this.y.add(new LinkWith2Bends(x,y));
	}

	@Override
	public void sort() {
		this.ya=(LinkWith2Bends[])y.toArray(); // as docu tells us, Collections.sort would do this anyway. May be useful
		
		// not obviously a natural comparator
		Arrays.sort(this.ya, new Comparator<LinkWith2Bends>(){

			@Override
			public int compare(LinkWith2Bends l0, LinkWith2Bends l1) {		
				Tupel a0=l0.;
				Tupel a1;
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
	@Override
	public Vector getNext() {
		// route around bedrock
		int b=0;
		for (int y = this.ya[this.s].y.s[0]; y <= this.ya[this.s].y.s[1]; y++) {
			if (this.bedrock.get(y)>b){
				b=this.bedrock.get(y);
			}
		}
		
		// route around other links
		for (int i)// for over all routed links (smaller index) in this collection
		
		// return
		return new Vector(b,this.ya[this.s++].s[0]);
	}


}
