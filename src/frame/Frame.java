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
// Use F1 to open configuration.java
// How to draw links defered that are values of two sources?

package frame;

import inputDevice.Keyboard;
import inputDevice.Mapping;
import inputDevice.Modifier;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.awt.image.VolatileImage;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import tile.Tile;
import tile.Tiles;
import tree.Buffer;
import tree.LayedOutPosition;
import tree.Node;
import tree.NodeBase;
import tree.Util;
import vector2.ClosedInterval;
import vector2.RectSize;
import vector2.Tupel;
import vector2.Vector;
import adHocRouter.Link;
import adHocRouter.LinkDebug;
import adHocRouter.LinkWith2Bends;
import adHocRouter.LinksWith2Bends;
import adHocRouter.Table;

public class Frame extends JFrame implements Mapping {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RectSize tileSize = new RectSize(20, 20); // technically this is a property of each tile (it the ImageBuffer). But since it is so important for the map, we have a master here.
	private Tiles tiles;

	public Frame(String titel) {
		super(titel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 800);
		// Premature optimization (or aero non portable look and feel)	        
		///setBackground(new Color(0,0,0,0)); // Unuseable: http://docs.oracle.com/javase/7/docs/api/java/awt/Frame.html#setOpacity%28float%29 

		//setVisible(true);

		this.tiles = new Tiles(this.tileSize, this);

		// ToDo: make it not work (basics only)
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		// I cannot use bindings since this code is supposed to be String free
		addKeyListener(new Keyboard(this));

		tiles.generate(this);
	}

	/* (non-Javadoc)
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		// ToDo: On startup there seems to be a double paint

		// What point?
		Point pos = this.getContentPane().getLocationOnScreen();
		Rectangle bounds = this.getContentPane().getBounds();
		bounds.setLocation(pos);

		this.gForRec = g;
		this.treepos = new Vector(pos.x, pos.y);
		//this.drawTree( this.topLevelx + 2*this.tileSize.s[0],pos.y+48,Util.createSampleTree()); // root == frame window	

		try {
			Node tree = Util.createSampleTree(); // misuse try block to limit scope of local variable
			this.link = new LinksWith2Bends(tree);
			this.drawTree(2, 0, tree, false, new Buffer(Tile.space)/*0,0*/, 0, new Table(), null); // root == frame window // Dupe (2,3)

			this.drawLinks(15, 0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // debug-info: draw bedrock and routes to the left 
			///this.drawTree second pass. Now really drawing using the routing from pass one.
		this.treepos = new Vector(pos.x, pos.y);
		//this.drawTree( this.topLevelx + 2*this.tileSize.s[0],pos.y+48,Util.createSampleTree()); // root == frame window
		//this.drawTree( 2,3,Util.createSampleTree(),false,0,0,4); // root == frame window // Dupe (2,3)

		// ToDo try{} around resources

		// probably now the
		// Compositing window manager
		// renders my client area on the screen
		// But mind fullscreen (eg on mobile or TV)!
	}

	// ToDo: Transformation (as default in Swing, OpenGl ..)
	private void drawLinks(int x, int j) throws Exception {
		List<Integer> b = ((LinkDebug) this.link).getBedrock();

		// to place text labels in front   draw backwards
		for (int y = b.size() - 1; y >= 0; y--) {
			this.drawVI(b.get(y) + x, y + j, 0, 0);
		}

		this.link.sort();

		// ToDo: Links are sorted by y[0]. For paint bottom up they would need y[1]

		// supplement links with layout info. For debugging the loop is in frame. Todo: Remove into test
		while (this.link.hasNext()) {
			this.link.calculateNext(); // sideEffect: Calculation
		}

		Object[] ya = this.link.getY(); // as docu tells us, Collections.sort would do this anyway. May be useful

		Arrays.sort(ya, new Comparator<Object>() {

			@Override
			public int compare(Object l0, Object l1) {
				ClosedInterval[] a = { ((LinkWith2Bends) l0).y, ((LinkWith2Bends) l1).y };
				return a[0].getLimitsSorted(1) - a[1].getLimitsSorted(1);
			}

		});

		int i = ya.length;

		// ToDo: LinkedList with cursor instead of hash
		Set<LinkWith2Bends> passing = new LinkedHashSet<LinkWith2Bends>(); // For each line we draw all links so we should be able to keep pointers on all of them

		link.jumpBelowLinks();
		LinkWith2Bends current = (LinkWith2Bends) ya[--i];///link.getLinksSortedByYPrevious();
		// to place text labels in front   draw backwards
		for (int y = b.size() - 1; y >= 0; y--) {
			Buffer buffer = new Buffer(Tile.space);
			this.shade ^= 2;

			System.out.print("Passing at y = " + y);
			Iterator<LinkWith2Bends> iter = passing.iterator();
			while (iter.hasNext()) {
				LinkWith2Bends linkWith2Bends = iter.next();
				System.out.print(".");
				;
				if (linkWith2Bends.y.getLimitsSorted(0) < y) {
					buffer.uniteAt(linkWith2Bends.xRight, new Tile(3, 4, 2));
				} else {
					drawLinkEnd(linkWith2Bends, buffer, linkWith2Bends.whichSide(y), linkWith2Bends.xRight); // Bug: at y there is not always end 
					iter.remove();
					System.out.print(" remove");
				}

			}

			while (/*current != null*/i >= 0 && current.y.getLimitsSorted(1) >= y) {

				for (int side = 1; side >= 0; side--) {
					if (current.y.s[side] == y) {
						int xi = current.xRight;
						drawLinkEnd(current, buffer, side, xi);
					}
				}

				passing.add(current);
				System.out.print(" add. And get next 'current' from i= " + (i - 1));
				if (--i < 0) {
					break;
				}
				current = (LinkWith2Bends) ya[i];///=link.getLinksSortedByYPrevious();

			}
			;

			System.out.println("");

			// Also draw spaces (inside). ToDo: Draw outside spaces if necessary (window size, (subTile) scrolling etc).
			for (int xPaint = buffer.getBoundary(1); xPaint >= buffer.getBoundary(0); xPaint--) { // b.get(y)
				Tile t = buffer.get(xPaint);
				drawVI(x + xPaint, j + y, t.shape, t.transformation, t.shade);
			}

			this.shade ^= 2;
			this.drawVI(b.get(y) + x - 3, y + j, 0, 0);
		}
	}

	private void drawLinkEnd(LinkWith2Bends current, Buffer buffer, int side, int xi) throws Exception {
		buffer.uniteAt(xi, new Tile(0, (current.y.s[side] < current.y.s[1 - side]) ? 2 : 0, 2)); // and bend

		while (--xi > current.x.s[side] + 1) {
			buffer.uniteAt(xi, new Tile(3, 0, 2)); // horizontal bar. ToDo: Make enum with  trival names
		}

		buffer.set(current.x.s[side] + 1, side == 0 ? new Tile(3, 0, 2) : new Tile(2, 0, 2)); // ToDo: Add a concave start to the link
		//System.out.println("end"+current.x.s[side]);
	}

	// Parameter from this.paint to this.drawTree
	private Graphics gForRec; // ToDo: Extra class?
	//private int topLevelx;
	private Vector treepos;

	private Link link; // links are less local than table-headers

	// Todo: less parameters!
	private Tupel drawTree(int x_anchor, int y, NodeBase node, boolean linkPasses, /*int x_min, int x_min2*/Buffer links, int trans, Table table, LayedOutPosition layout) throws Exception {
		Tupel t = new Tupel(x_anchor, y);
		if ((node.getSwapCoordinates() & 4) > 0) {
			this.shade = 0;
			this.transformation = trans;
			int xi = x_anchor;
			if (!node.isChicane()) {
				drawVI(xi, y, 3, 2);
				xi--;
				drawVI(xi, y, 3, 2);
				xi--;
				drawVI(xi, y, 0, 6);
				xi--;
			}

			xi = x_anchor - 3;

			// ToDo: Somewhere here: Create new table. Problem: above swap which is above swap+chicane

			t = drawTreeInner(y + 2 - (node.isChicane() ? 3 : 0), x_anchor + 1, node, linkPasses, /*y+0,y+0*/new Buffer(y), trans ^ node.getSwapCoordinates(), node.isChicane(), table);

			this.transformation = trans;
			int x3 = xi;
			while (y < t.s[0] - 0) {

				while (xi > x3) {
					drawVI(xi, y, 2, 0);
					xi--;
				}
				//				// dupe {
				//				while (xi >= buffer.getBoundary(1) /*x_min*/) {
				//					drawVI(xi, y, 3, 4);
				//					xi--;
				//				}
				//
				//				while (xi >= buffer.getBoundary(0) /*x_min2*/) {
				//					drawVI(xi, y, 2, 0);
				//					xi--;
				//				}

				while (xi >= links.getBoundary(0)/*x_min2*/) {
					Tile t0 = links.get(xi);
					drawVI(xi, y, t0.shape, t0.transformation);
					xi--;
				}

				y++;

				xi = x3 + 3;
			}
			// dupe }			
			return new Tupel(x_anchor, t.s[0]);
		} else {
			//System.out.println("Y is: "+y);
			return drawTreeInner(x_anchor, y, node, linkPasses, links /*x_min, x_min2*/, trans, node.isChicane(), table);
		}
	}

	private Tupel drawTreeInner(int x_anchor, int y, NodeBase node2, boolean linkPasses, Buffer links, int trans, boolean chicane, Table table) throws Exception {
		// ToDo: One Call for layout and a second one for draw (text). Make two pass optional für debugging purpose
		// ToDo: Here the coordinates are not equally handled. Stop using an Array s[2] ? 
		//Node switchToOwn=parent.getChildren().get(0); // Value comes before children.
		//boolean ownChildren=false;
		///Iterator<Node> nodes=parent.iterator(); //.getChildrenWithInline();
		int x_max = x_anchor;

		// ToDo: Remove cast
		for (Iterator<NodeBase> iterator = node2.iterator(); iterator.hasNext();) {
			NodeBase node = iterator.next(); // Bug: Can be null although hasNext == true

			if (node == null) {
				throw new NullPointerException("Iterator given by NodeInstance is to blame");
			}

			//			// References are okay as these are copied within the current RAM just some time before
			//			if (node == switchToOwn){
			//				ownChildren = true;
			//			}

			this.shade = 0;
			this.transformation = trans;
			int xi;

			// Deferred to routing. ToDo: Change constructor for partial construction
			//this.link.addLink(new Tupel(x_anchor, 0),new ClosedInterval(y,0));
			// get link positions in second pass
			///this.link.get(node); // ToDo: put the burden of y ordering into Interface Links
			// loop over all links passing this y
			// taken live rendering
			linkPasses = drawLink(x_anchor, y, linkPasses, node, 10, 0);

			// table
			if (node.isChicane()) {
				// ToDo: Use loop and use max(x) of this header (info flowing backwards) as the start value
				drawVI(x_anchor + 1, y, 3, 2);
			}

			xi = x_anchor;
			drawVI(xi, y, 2, 0);
			xi--;
			if (!chicane) {
				// ToDo: Cache children view
				if (node.isChicane()) {
					drawVI(xi, y, 3, 2);
				} else {
					Iterator<NodeBase> test = node.iterator();
					if (!test.hasNext()) {
						drawVI(xi, y, 3, 2);
					} else {
						drawVI(xi, y, 1, 2);
					}
				}
			}

			// concept code {
			// live rendering
			//linkPasses = drawReference(x_anchor, y, linkPasses, node);
			// Deferred to routing. 
			// ToDo:
			//   Change constructor for partial construction
			//   extract method to reflect top down?
			//   Change 0 to -1
			if (node.getValue() != null) {
				LinkWith2Bends l = this.link.get(node);
				if (l == null) {
					this.link.addLink(new Tupel(x_anchor, 0), new ClosedInterval(y, 0), node);
					l = this.link.get(node);
				} else {
					l.x.s[0] = x_anchor;
					l.y.s[0] = y;
				}
			}

			if (node.getValueOf() != null) {
				LinkWith2Bends l = this.link.get(node.getValueOf());
				if (l == null) {
					this.link.addLink(new Tupel(0, x_anchor), new ClosedInterval(0, y), node.getValueOf());
					l = this.link.get(node.getValueOf());
				} else {
					l.x.s[1] = x_anchor;
					l.y.s[1] = y;
				}
			}

			// Paint uses Y right now. It is supposed that tree is fixed before routing
			//			if (node.getValueOf() != null) {
			//				LinkWith2Bends this_link_get_node_getValueOf___ = this.link
			//						.get(node.getValueOf());
			//				// ToDo upwards links
			//				if (this_link_get_node_getValueOf___ != null) {
			//					this_link_get_node_getValueOf___
			//							.setDestination(x_anchor, y);
			//				}
			//				// this.link.addLink(new Tupel(x_anchor, 0),new
			//				// ClosedInterval(y,0));
			//			}

			//this.link.addBedrock(x_anchor);

			// } concept code

			Vector positionInGridcount;
			if ((transformation & 4) == 0) {
				///this.link.addBedrock(x_anchor,y); // < 2014-10-06
				// >= 2014-10-07
				// ToDo: Add a new list. Prototype and owner are connected by value, but none has a list for multiple children. I vote for a new list for the owner ... owner.layoutNode!
				positionInGridcount = new Vector(x_anchor, y); // y+1 ??

			} else {
				///this.link.addBedrock(y,x_anchor); // < 2014-10-06
				// >= 2014-10-07
				positionInGridcount = new Vector(y, x_anchor); // x_anchor + 1 ??
			}

			//			// ToDo add this to iterator, to run the same code in Frame(pass1) and here (pass2)
			//			LayedOutPosition layoutChild = null;
			//			if (node.getReferenceHistory() == 2) { // ToDo layout reference as parameter
			//				node.setLayout(new LayedOutPosition(positionInGridcount));
			//			}else{				
			//				// some ancestor already got inlined
			//				if (layout != null) {
			//					layoutChild = ;
			{
				System.out.println("Position "+positionInGridcount.s[0]+", "+positionInGridcount.s[1]);
				Vector p=positionInGridcount;
				assert p!=null;
				LayedOutPosition t = new LayedOutPosition(p);
				assert t != null;
				node.setLayout(t);
			}
			//				} else {
			//					if (iterator.lastWasFeedthrough()) { // ToDo layout reference as parameter
			//						node.setLayout(new LayedOutPosition(positionInGridcount));
			//					} else {
			//						parent.getLayout().value_children.add(new LayedOutPosition(positionInGridcount));
			//					}
			//				}
			//			}

			Vector positionInPixel = new Vector(this.treepos, this.tileSize, positionInGridcount);
			
			this.gForRec.drawString(node.getTitle() + " " + (node.getClass().toString()), positionInPixel.s[0] + 1, positionInPixel.s[1] - 3); // May flicker without doubleBuffering
			xi--;
			if (!chicane) {
				if (!iterator.hasNext()) { //i + 1 == nodes.size()) {
					drawVI(xi, y, 0, 6); // bend
					//if (xi == links.getBoundary(1)/*x_min*/) {
					//	links.set(xi,new Tile(3,4,0)) /*x_min++*/;
					//}else{
					links.set(xi, Tile.space); // limit possible values. Other tiles make no sense
					///throw new UnsupportedOperationException("Lücke in Buffer");
					//}

				} else {
					drawVI(xi, y, 1, 6); // branch
					links.set(xi, new Tile(3, 4, 0)); // vertical bar
				}
				xi--;

				//				// ToDo: Jump over Gaps. x_min has to be an ArrayList
				//				while (xi >= links.getBoundary(1)/*x_min*/) {
				//					drawVI(xi, y, 3, 4);
				//					xi--;
				//				}

				while (xi >= links.getBoundary(0)/*x_min2*/) {
					Tile t = links.get(xi);
					drawVI(xi, y, t.shape, t.transformation);
					xi--;
				}
			}

			// ToDo: Jump over gaps due to "group names" in other header. Reuse chicane marker and rename to "has children inside table body"!??
			y++;
			Tupel t;

			// store gaps  due to "group names" for other header
			table.add();
			t = this.drawTree(x_anchor + 1, y, node, linkPasses, links /*x_min, x_min2*/, trans, table, node.getLayout());

			y = t.s[1];///System.out.println("Y is. "+y); // Bug: y is too large sometimes
			x_max = Math.max(x_max, t.s[0]);
		}

		return new Tupel(x_max, y); // ToDo: Why does boxing not work?
	}

	// ToDo: UnitTest that there are no dangling bonds!
	private boolean drawLink(int x_anchor, int y, boolean linkPasses, NodeBase node, int xRight, int upsideDown) {
		int xi;
		// references. ToDo: Call adHocRouter

		//		System.out.println("x_anchor: " + x_anchor);
		this.shade ^= 2; // ToDo: A parameter after all? Hide hack in
							// tiles!
		xi = xRight;

		if (node.getValue() != null) {
			drawVI(xi, y, 0, 0 ^ upsideDown);
			xi--;
			while (xi > x_anchor) {
				drawVI(xi, y, 3, 2);
				xi--;
			}

			linkPasses = false;
		}

		if (linkPasses) {
			drawVI(xi, y, 3, 4);
		}

		if (node.getValueOf() != null) {
			drawVI(xi, y, 0, 2 ^ upsideDown);
			xi--;
			while (xi > x_anchor + 1) {
				drawVI(xi, y, 3, 2);
				xi--;
			}

			drawVI(xi, y, 2, 0);
			xi--;

			linkPasses = true;
		}

		this.shade ^= 2; // ToDo: A parameter after all? Hide hack in tiles!

		return linkPasses;
	}

	// This is a low priority candidate for speed optimization (remove multiplication)
	private int shade; // ToDo: Use Setter to limit access to lower bits
	private Vector cursor = new Vector(2, 3); // Dupe (2,3)
	private int transformation;

	private void drawVI(int x, int y, int i, int j) {

		if ((this.transformation & 4) == 0) {
			drawVolatileImage(new Vector(this.treepos, this.tileSize, x, y), i << 2 | (this.shade) | ((x == this.cursor.s[0] && y == this.cursor.s[1]) ? 1 : 0), j ^ this.transformation);
		} else {
			drawVolatileImage(new Vector(this.treepos, this.tileSize, y, x), i << 2 | (this.shade) | ((y == this.cursor.s[0] && x == this.cursor.s[1]) ? 1 : 0), j ^ this.transformation);
		}
	}

	private void drawVI(int x, int y, int i, int j, int shade) {

		if ((this.transformation & 4) == 0) {
			drawVolatileImage(new Vector(this.treepos, this.tileSize, x, y), i << 2 | (shade) | ((x == this.cursor.s[0] && y == this.cursor.s[1]) ? 1 : 0), j ^ this.transformation);
		} else {
			drawVolatileImage(new Vector(this.treepos, this.tileSize, y, x), i << 2 | (shade) | ((y == this.cursor.s[0] && x == this.cursor.s[1]) ? 1 : 0), j ^ this.transformation);
		}
	}

	private void drawVolatileImage(Vector v, int i, int j) {
		VolatileImage vi = tiles.vImg[i];
		do {

			int returnCode = vi.validate(getGraphicsConfiguration());
			if (returnCode == VolatileImage.IMAGE_RESTORED) {
				tiles.updateTile(this, i); // restore contents
			} else if (returnCode == VolatileImage.IMAGE_INCOMPATIBLE) {
				this.tiles = new Tiles(this.tileSize, this);
				tiles.updateTile(this, i);
			}

			this.flip((Graphics2D) this.gForRec, vi, v, j);
		} while (vi.contentsLost());
	}

	// with the help of source, the seam can be taken or omitted
	// straigth line on seam
	// ToDo: remove dupes? Extract and JUnitTest for dangling bonds
	private void flip(Graphics2D g, VolatileImage vi, Vector v, int f) {

		AffineTransform backup = g.getTransform(); // TextOut wants a different rotation

		int[][] c = { { 0, 0, tileSize.s[0], tileSize.s[1] }, { 0, 0, 0, 0 } };
		for (int i = 0; i < c[0].length; i++) {
			c[1][i] = c[0][i];
		}

		int m = 1;
		if ((f & m) != 0) {
			c[1][0] = c[0][2];
			c[1][2] = c[0][0];
		}

		m <<= 1;
		if ((f & m) != 0) {
			c[1][1] = c[0][3];
			c[1][3] = c[0][1];
		}

		m <<= 1;
		// swap the coordinates. ToDo: Works only for square tiles (square in px)
		//AffineTransform trans;
		if ((f & m) != 0) {
			g.setTransform(new AffineTransform(0, 1, 1, 0, v.s[0], v.s[1]));
		} else {
			g.setTransform(new AffineTransform(1, 0, 0, 1, v.s[0], v.s[1]));
		}
		//g.drawImage(vi,  16,16, this);
		// g.setXORMode(new Color(1,1,1)); // ToDo: Make UnitTest to check that every screen tile is painted to exactly once
		g.drawImage(vi, 0, 0, tileSize.s[0], tileSize.s[1], c[1][0] + ((f >> 0) & 1), c[1][1] + ((f >> 1) & 1), c[1][2] + ((f >> 0) & 1), c[1][3] + ((f >> 1) & 1), this); // remove double seam / allow for odd width

		g.setTransform(backup); // TextOut wants a different rotation
	}

	@Override
	public void move(Vector d, Modifier m) {
		//System.out.println("In move: "+this.cursor.s);
		this.cursor.add(d);
		this.repaint();
	}

	// ToDo: uh ugly  External file? C# ?
	@Override
	public void escape() {
		JOptionPane.showMessageDialog(this, "Copyright 2014   Arne Christian  Rosenfeldt " + System.lineSeparator() + "" + System.lineSeparator() + "This program is free software: you can redistribute it and/or modify" + System.lineSeparator()
				+ "it under the terms of the GNU General Public License as published by" + System.lineSeparator() + "the Free Software Foundation, either version 3 of the License, or" + System.lineSeparator() + "(at your option) any later version."
				+ System.lineSeparator() + "" + System.lineSeparator() + "This program is distributed in the hope that it will be useful," + System.lineSeparator() + "but WITHOUT ANY WARRANTY; without even the implied warranty of" + System.lineSeparator()
				+ "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the" + System.lineSeparator() + "GNU General Public License for more details." + System.lineSeparator() + "" + System.lineSeparator()
				+ "You should have received a copy of the GNU General Public License" + System.lineSeparator() + "along with this program.  If not, see <http://www.gnu.org/licenses/>." + System.lineSeparator(), "about", JOptionPane.WARNING_MESSAGE);
	}

}

/*

 2 Java front-end implementations
 0) Swing + HyperSql  for testing in ecclipse 
 1) Ajax + Apache + HyperSql  for presentation in web

 */

/*
 * 
 * 
 // Traditional GUI Application paint method:
// This can be called at any time, usually 
// from the event dispatch thread
public void paint(Graphics g) {
    // Use g to draw my Component
}
 * 
 * 
 GraphicsDevice myDevice;
Window myWindow;

try {
    myDevice.setFullScreenWindow(myWindow);
    ...
} finally {
    myDevice.setFullScreenWindow(null);
}



// Avoid tearing
 BufferStrategy myStrategy;

while (!done) {
    Graphics g = myStrategy.getDrawGraphics();
    render(g);
    g.dispose();
    myStrategy.show();
}



 */
