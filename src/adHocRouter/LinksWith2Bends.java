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
import java.util.Comparator;
import java.util.List;

import tree.MergingIterator;
import tree.Node;
import tree.NodeBase;
import vector2.ClosedInterval;
import vector2.Tupel;

public class LinksWith2Bends implements Link /*, LinkDebug */{
	// public for debug. I certainly do not want a global "DEBUG" option. ToDo: set to private
	/*	public ArrayList<Integer> bedrock= new ArrayList<Integer>();

		private int getBedrock(int y){
			return this.bedrock.get(y);
		}*/

	public LinksWith2Bends(Node root) {
		this.root = root;
	}

	private Node root;

	///private Node current; // later not all parts of the tree should reside on the client

	private int getBedrock(int y) {
		this.findY = y;
		return this.getBedrockInner(this.root);
	}

	private int findY;

	private int getBedrockInner(NodeBase node) {
		// pass0/1: Frame.drawTreeInner   using Node.getChildrenWithInline();
		// pass1/1: This (LinkWith2Bends.getBedrockInner)

		/*
		 Now moved into MergingIterator. <2014-10-19:
		LayedOutPosition[] layoutChildren = new LayedOutPosition[2];
		/*
		 * 
		 */
		NodeBase[] nodeChildren = new Node[2];
/*
		// ToDo: Reduce to one iterator
		Iterator<LayedOutPosition> layoutRef = null;
		if (layout != null) {
			layoutRef = layout.value_children.iterator();
		}

		///    Iterator<LayedOutPosition> layoutParent = node.getLayout().value_children.iterator();
		*/
		
		// Not really forEach because I have to detect adopted children
		// loop over persistent nodes
		for (MergingIterator iterator = (MergingIterator) node.iterator(); iterator.hasNext();) {
			nodeChildren[0] = (NodeBase) iterator.next();
			nodeChildren[1] = null;
			
			/*
			 * Now moved into MergingIterator. <2014-10-19:
			nodeChildren[0] = (Node) iterator.next();

			// ToDo add this to iterator, to run the same code in Frame(pass1) and here (pass2)
			if (nodeChildren[0].getReferenceHistory() == 2) {
				// owned by grand-parent. // ToDo: Make sure that grandparent is no dupe. History ends after 32 bits! Let bits stack up!
				layoutChildren[0] = nodeChildren[0].getLayout();
			} else {

				// some ancestor already got inlined
				if (layoutRef != null) {
					layoutChildren[0] = layoutRef.next(); //layout.value_children.get(i);
				} else {

					if (iterator.lastWasFeedthrough()) { // ToDo: Is lastWasAggregate. But then merging does not reduce count
						layoutChildren[0] = nodeChildren[0].getLayout();
					} else {

						// first generation Inline
						layoutChildren[0] = layoutParent.next();
					}
				}
			}
			*/

			// Check, if two low. Nodes Positions are upper left corner (due to tree structure)
			if (nodeChildren[0].getLayout().position.s[1] == this.findY) {
				return nodeChildren[0].getLayout().position.s[0];
			} else {
				if (nodeChildren[0].getLayout().position.s[1] > this.findY) {
					return this.getBedrockInner(nodeChildren[1]);
				}
			}

			nodeChildren[1] = nodeChildren[0];
			//layoutChildren[1] = layoutChildren[0];n
		}

		/*
		// Value first (ToDo: Define this at a single place)

		for (int i = 0; i < node0.getValue().getChildren().size(); i++) {
			node2 = node1;
			layout2 = layout1;
			node1 = node0.glayoutChildrenetChildren().get(i);
			layout1 = layout0.value_childrenlayoutChildren		if (layout1.position.s[1] == this.findY)layoutChildrenurn layout1.position.s[0];
			} else {
			layoutChildrens.getBedrockInner(node1, layout1);
			}
		}

		for (int i = 0; i layoutChildrenChildren().size(); i++) {
			node2 = node1;
			layout2 = layout1;
			node1 = node0.getChildren().get(i);layoutChildrenyout0 != null) {
				layout1 = layout0.value_children.get(i); // After filayoutChildreng step (for-loop above): Always use layout
			} else {
				layout1 = node1.layout;
			}
			if (layout1.position.slayoutChildren.findY) {
				return layout1.playoutChildren];
			} else {
				return this.getBedrockIlayoutChildren layout1);
			}
		}
		*/

		throw new ArrayIndexOutOfBoundsException(); // carried over from Array implementation of bedrock
	}

	//	@layoutChildren//	public void addBedrock(layoutChildren//		bedrock.add(x);
	//	}

	//	@Override
	//	public void addBedrock(int x, int y) {
	//		// not called for
	//		while (y>this.bedrock.size()){
	//			this.bedrock.add(0);
	//		}
	//		
	//		// okay
	//		if (y==this.bedrock.size()){
	//			this.bedrock.add(x);
	//		}else{
	//			this.bedrock.set(y, Math.max(this.bedrock.get(y), x));
	//		}
	//	}

	private List<LinkWith2Bends> y = new ArrayList<LinkWith2Bends>();
	private int getIndex;

	@Override
	public void jumpBelowLinks() {
		this.getIndex = this.y.size();
	}

	@Override
	public LinkWith2Bends getLinksSortedByYPrevious() {
		if (--this.getIndex < 0) {
			return null;
		}
		return this.y.get(this.getIndex);
	}

	private Object[] ya; // LinkWith2Bends  . ToDo: hide type inside sort?

	private LinkWith2Bends getYa(int i) {
		return (LinkWith2Bends) this.ya[i];
	}

	@Override
	public void addLink(Tupel x, ClosedInterval y, NodeBase node) {
		this.y.add(new LinkWith2Bends(x, y, node));
	}

	// ToDo: Make more efficient!
	private void connectDanglingBounds() {
		for (LinkWith2Bends l : this.y) {
			// ToDo: -1
			if (l.y.s[1] == 0) {
				Node v = l.node.getValue();
				for (LinkWith2Bends m : this.y) {
					if (m.node.getValue() == v && m.y.s[1] != 0) {
						l.y.s[1] = m.y.s[1]; // ToDo: this code looks better with Point. Maybe add point getter to interface
						l.x.s[1] = m.x.s[1];
					}
				}
			}
		}
	}

	@Override
	public Object[] getY() {
		return this.y.toArray(); // ToDo: Do not leak this ugly Object[]
	}

	@Override
	public void sort(boolean moveToEnd) {
		this.connectDanglingBounds();
		this.ya = this.y.toArray(); // as docu tells us, Collections.sort would do this anyway. May be useful

		// not obviously a natural comparator
		if (moveToEnd) {
			// nonsense. We do not  list all sorts in this one place
		} else {
			Arrays.sort(this.ya, new Comparator<Object>() {

				@Override
				public int compare(Object l0, Object l1) {
					Tupel a0 = ((LinkWith2Bends) l0).y;
					Tupel a1 = ((LinkWith2Bends) l1).y;
					return Math.abs(a0.s[1] - a0.s[0]) - Math.abs(a1.s[1] - a1.s[0]);
				}

			});
		}

		//		Collections.sort(this.y, new Comparator<Tupel>(){
		//
		//			@Override
		//			public int compare(Tupel o1, Tupel o2) {
		//				// TODO Auto-generated method stub
		//				return 0;
		//			}});

		this.s = moveToEnd ? this.ya.length - 1 : 0;
	}

	private int s;

	// Just need to tell the starting point. End is known to node
	// This implementation is meant as reference and documentation and thus abstains from recursive code and optimized interfaces
	@Override
	public LinkWith2Bends calculateNext() {
		// Strange: Link( y:{23, 0}, x: {4, 0} xRight: 0)

		// route around bedrock // ToDo: Use data that bubbles up in the tree
		System.out.println("s is " + this.s + " von " + this.ya.length);
		int[] this_ya_this_s__y_s = this.getYa(this.s).y.s;
		int this_ya_this_s__xRight = 0;

		// Do not merge with render code because: ToDo: Walk the tree
		int[] ySorted = this_ya_this_s__y_s.clone();
		if (ySorted[0] > ySorted[1]) {
			int t = ySorted[0];
			ySorted[0] = ySorted[1];
			ySorted[1] = t;
		}

		int y = ySorted[0];
		this_ya_this_s__xRight = Math.max(this_ya_this_s__xRight, this.getBedrock(y) + 1); // Space for Endmarkers
		y++;
		while (y <= ySorted[1] - 1) {
			this_ya_this_s__xRight = Math.max(this_ya_this_s__xRight, this.getBedrock(y));
			y++;
		}
		this_ya_this_s__xRight = Math.max(this_ya_this_s__xRight, this.getBedrock(y) + 1);

		++this_ya_this_s__xRight; // Next to bedrock

		System.out.println("Bedrock says" + this_ya_this_s__xRight);

		// route around other links
		for (int other = 0; other < this.s; other++) {// for over all routed links (smaller index) in this collection
			System.out.println(this.getYa(this.s).y + " overlaping with " + this.getYa(other).y);
			if (this.getYa(this.s).y.isOverlapingWith(this.getYa(other).y)) {
				this_ya_this_s__xRight = Math.max(this_ya_this_s__xRight, this.getYa(other).xRight + 1); // no need to revisit older routes since they are ordered by x. ToDo: Make a UnitTest out of this
				System.out.println("[" + other + "] = " + this_ya_this_s__xRight);
			}
		}

		this.getYa(this.s).xRight = this_ya_this_s__xRight;

		System.out.println("GetNext: " + this.getYa(this.s));

		// ToDo implement enumerator interface
		// now paint it (ToDo: May need to make a copy to write protect the original)
		return this.getYa(this.s++);
	}

	// Just need to tell the starting point. End is known to node
	// This implementation is meant as reference and documentation and thus abstains from recursive code and optimized interfaces
	// Copied from getNext(); Still sorted by y[0], just reversed. 
	@Override
	public LinkWith2Bends getPrevious() {
		// Strange: Link( y:{23, 0}, x: {4, 0} xRight: 0)

		throw new UnsupportedOperationException();
		// ToDo: No way to get sorted by y[1] here elegantly
		//		// route around bedrock // ToDo: Use data that bubbles up in the tree
		//		System.out.println("s is "+this.s+" von "+this.ya.length);
		//		int[] this_ya_this_s__y_s=this.getYa(this.s).y.s;
		//		int this_ya_this_s__xRight=0;
		//		
		//		// Do not merge with render code because: ToDo: Walk the tree
		//		int[] ySorted=this_ya_this_s__y_s.clone();
		//		if (ySorted[0]>ySorted[1]){
		//			int t=ySorted[0];
		//			ySorted[0]=ySorted[1];
		//			ySorted[1]=t;				
		//		}
		//
		//		int y=ySorted[0];
		//		this_ya_this_s__xRight=Math.max(this_ya_this_s__xRight,this.bedrock.get(y)+1); // Space for Endmarkers
		//		y++;
		//		while ( y <= ySorted[1]-1) {
		//			this_ya_this_s__xRight=Math.max(this_ya_this_s__xRight,this.bedrock.get(y));
		//			y++;
		//		}		
		//		this_ya_this_s__xRight=Math.max(this_ya_this_s__xRight,this.bedrock.get(y)+1);
		//		
		//		++this_ya_this_s__xRight; // Next to bedrock
		//		
		//		System.out.println("Bedrock says"+this_ya_this_s__xRight);
		//	
		//		// route around other links
		//		for (int other=0;other < this.s;other++){// for over all routed links (smaller index) in this collection
		//			System.out.println(this.getYa(this.s).y +" overlaping with "+this.getYa(other).y);
		//				if (this.getYa(this.s).y.isOverlapingWith(this.getYa(other).y)){
		//					this_ya_this_s__xRight=Math.max(this_ya_this_s__xRight, this.getYa(other).xRight+1); // no need to revisit older routes since they are ordered by x. ToDo: Make a UnitTest out of this
		//					System.out.println( "["+other+"] = "+ this_ya_this_s__xRight);					
		//				}
		//		}
		//	
		//		this.getYa(this.s).xRight=this_ya_this_s__xRight;
		//		
		//		System.out.println( "GetNext: "+this.getYa(this.s));
		//			
		//		// ToDo implement enumerator interface
		//		// now paint it (ToDo: May need to make a copy to write protect the original)
		//		return this.getYa(this.s++);
	}

	// ToDo: Optimize  Tree  or  if really necessary: hash
	@Override
	public LinkWith2Bends get(NodeBase node) {
		if (this.y != null)
			for (Object linkWith2Bends : this.y) {
				if (((LinkWith2Bends) linkWith2Bends).node == node) {
					return (LinkWith2Bends) linkWith2Bends;
				}
			}
		return null;
	}

	//	@Override
	//	public List<Integer> getBedrock() {
	//		return this.bedrock;
	//	}

	@Override
	public boolean hasNext() {
		return this.ya.length > this.s;
	}

	@Override
	public void sort() {
		this.sort(false);
	}
}
