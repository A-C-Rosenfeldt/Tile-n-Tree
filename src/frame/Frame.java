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
package frame;


import inputDevice.Keyboard;
import inputDevice.Mapping;
import inputDevice.Modifier;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.RenderingHints;
import java.util.List;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.AffineTransform;
import java.awt.image.ColorModel;
import java.awt.image.VolatileImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import rasterizer.MakeTheBestOfSwing;
import tile.Tile;
import tile.Tiles;
import tree.Node;
import tree.Util;
import vector2.RectSize;
import vector2.Tupel;
import vector2.Vector;


public class Frame  extends JFrame implements Mapping{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private RectSize tileSize=new RectSize(16,16); // technically this is a property of each tile (it the ImageBuffer). But since it is so important for the map, we have a master here.
	private Tiles tiles;

	public Frame(String titel) {
		super(titel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 300);
		// Premature optimization (or aero non portable look and feel)	        
		///setBackground(new Color(0,0,0,0)); // Unuseable: http://docs.oracle.com/javase/7/docs/api/java/awt/Frame.html#setOpacity%28float%29 

		//setVisible(true);

		this.tiles=new Tiles(this.tileSize, this);

		// ToDo: make it not work (basics only)
		setFocusable(true);
		setFocusTraversalKeysEnabled(false);

		
		
		// I cannot use bindings since this code is supposed to be String free
		addKeyListener(new Keyboard(this));

		tiles.generate( this);
	}



	/* (non-Javadoc)
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {

		//g.hint(antialias)
		// now inside tile. Interferes with seam. MakeTheBestOutOfSwing.configure2((Graphics2D) g);

		// TODO Auto-generated method stub
		//	super.paint(arg0);  changes background from grey to white. There is nothing to draw for super in the client area as I want a full tile-based display, do I?


		// What point?
		Point pos = this.getContentPane().getLocationOnScreen();
		Rectangle bounds = this.getContentPane().getBounds();

		RectSize rs=new RectSize(bounds);
		bounds.setLocation(pos);

		System.out.println("add next to second");
		System.out.println(pos.y);
		System.out.println(bounds.y);

		/*
		 depreciated
		 

		///System.out.print( rs.s[0]/2); System.out.print(" ");System.out.print( rs.s[1]/2); System.out.print(" ");System.out.print( rs.s[0]); System.out.print(" ");System.out.print( rs.s[1]); System.out.print(" ");System.out.print( 0);System.out.print( 90);
		g.drawArc(rs.s[0]/2, rs.s[1]/2, rs.s[0], rs.s[1], 0, 90);
		//bounds.getMinX();


		// Todo: Make enumeration out of this (parameter of direction)
		for(vector2.Vector v=new vector2.Vector(0,(int)bounds.getMinY());v.s[1]<(int)bounds.getMaxY();v.AddAt(tileSize.s[1], 1))
		{
			for(v.s[0]=(int)bounds.getMinX();v.s[0]<(int)bounds.getMaxX();v.AddAt(tileSize.s[0], 0))
			{
				g.setColor(new Color(v.s[0] & 255, v.s[1] & 255, v.s[0] & 255));

				g.fillRect(v.s[0], v.s[1], tileSize.s[0], tileSize.s[1]);
			}			
		}

		for (int i = 0; i < tiles.vImg.length; i++) {
			VolatileImage vi = tiles.vImg[i];
			do {	

				int returnCode = vi.validate(getGraphicsConfiguration());
				if (returnCode == VolatileImage.IMAGE_RESTORED) {
					// Contents need to be restored
					tiles.updateTile( this, i);      // restore contents
				} else if (returnCode == VolatileImage.IMAGE_INCOMPATIBLE) {
					// old vImg doesn't work with new GraphicsConfig; re-create it
					this.tiles=new Tiles(this.tileSize, this);
					tiles.updateTile( this, i);  // only affected tiles!! New parameter! ToDo
				}

					for (int j = 0; j < 8; j++) {
						this.flip((Graphics2D) g, vi,
								new Vector((int) bounds.getMinX() + i * 16 ,
										(int) bounds.getMinY() + j * 32), j);
				}
			} while (vi.contentsLost());
		}
		*/
		
		// String
		//((Graphics2D) g).setTransform( new AffineTransform(1,0,0,1,(int)bounds.getMinX(),(int)bounds.getMinY()));
		//this.topLevelx=pos.x+432;
		this.gForRec=g;
		this.treepos=new Vector(pos.x+300, pos.y);
		//this.drawTree( this.topLevelx + 2*this.tileSize.s[0],pos.y+48,Util.createSampleTree()); // root == frame window
		this.drawTree( 2,3,Util.createSampleTree(),false,0,0); // root == frame window // Dupe (2,3)

		this.treepos=new Vector(pos.x, pos.y);
		//this.drawTree( this.topLevelx + 2*this.tileSize.s[0],pos.y+48,Util.createSampleTree()); // root == frame window
		this.drawTree( 2,3,Util.createSampleTree(),false,0,4); // root == frame window // Dupe (2,3)
		
		
		// ToDo try{} around resources

		// probably now the
		// Compositing window manager
		// renders my client area on the screen
		// But mind fullscreen (eg on mobile or TV)!
	}
	// Parameter from this.paint to this.drawTree
	private Graphics gForRec; // ToDo: Extra class?
	//private int topLevelx;
	private Vector treepos;
	
	private Tupel drawTree(int x_anchor, int y, Node current, boolean linkPasses, int x_min, int trans) {
		Tupel t=new Tupel(x_anchor,y);
		if (current.getChildrenSwapCoordinates().size()>0){
			this.shade=0;
			this.transformation=trans;
			int xi=x_anchor-1;
			drawVI(xi, y, 3, 2);
			xi--;
			drawVI(xi, y, current.getChildren().size()>0?0:1, 6);
			xi--;
			t=  drawTreeInner( y+2,x_anchor  ,  current.getChildrenSwapCoordinates(),  linkPasses,  y+2,y+2,  trans^current.getSwapCoordinates());
			y+=2;
		}
		return  drawTreeInner( x_anchor,  y,  current.getChildren(),  linkPasses,  x_min, x_min, trans);
		
	}
	
	private Tupel drawTreeInner(int x_anchor, int y, ArrayList<Node> nodes, boolean linkPasses, int x_min, int x_min2, int trans) {
		// Tree
		/*
			 i,j  
		0,1
		1,6
		1,2
		3,4
		 */

		// ToDo: Here the coordinates are not equally handled. Stop using an Array s[2] ? 

		int x_max=x_anchor;;

		
		for (int i=0;i< nodes.size();i++) {
			Node node=nodes.get(i);
			
			this.shade=0;
			this.transformation=trans;
			int xi;
		
			{
				this.shade ^= 2; // ToDo: A parameter after all? Hide hack in
									// tiles!
				xi = 10;// x + 4;
				if (node.getValueOf() != null) {

					drawVI(xi, y, 0, 2);
					xi--;
					drawVI(xi, y, 3, 2);
					xi--;

					while (xi > x_anchor+1) {
						drawVI(xi, y, 3, 2);
						xi--;
					}

					drawVI(xi, y, 2, 0);
					xi--;

					linkPasses = true;
				}

				if (linkPasses) {
					drawVI(xi, y, 3, 4);
				}

				if (node.getValue() != null) {

					drawVI(xi, y, 0, 0);
					xi--;
					drawVI(xi, y, 3, 2);
					xi--;
					drawVI(xi, y, 3, 2);
					xi--;
					while (xi > x_anchor) {
						drawVI(xi, y, 3, 2);
						xi--;
					}

					linkPasses = false;
				}
				this.shade ^= 2; // ToDo: A parameter after all? Hide hack in
									// tiles!
			}

			xi=x_anchor;
			drawVI(xi, y, 2, 0);			
			xi--;
			drawVI(xi, y, node.getChildren().size()+node.getChildrenSwapCoordinates().size() != 0 ? 1 : 3, 2);
			
			Vector v;
			if ((transformation & 4) == 0) {
				v = new Vector(this.treepos, this.tileSize, x_anchor, y + 1);
			} else {
				v = new Vector(this.treepos, this.tileSize, y, x_anchor + 1);
			}
			
			this.gForRec.drawString(node.getTitle(),  v.s[0]+1, v.s[1]-3); // May flicker without doubleBuffering
			xi--;
			if (i + 1 == nodes.size()) {
				drawVI(xi, y, 0, 6);
				if (xi==x_min){
					x_min++;
				}
			} else {
				drawVI(xi, y, 1, 6);
			}
			xi--;
			
			while(xi>=x_min){	
				drawVI(xi, y, 3, 4);
				xi--;
			}
	
			while(xi>=x_min2){	
				drawVI(xi, y, 2, 0);
				xi--;
			}			
			
			y++;
			Tupel t;
		
				t=this.drawTree( x_anchor+1,y, node,linkPasses, x_min, trans);
		
			
			y=t.s[1];
			x_max=Math.max(x_max, t.s[0]);
		}

		return new Tupel(x_max,y); // ToDo: Why does boxing not work?
	}

	// This is a low priority candidate for speed optimization (remove multiplication)
	private int shade; // ToDo: Use Setter to limit access to lower bits
	private Vector cursor=new Vector(2,3); // Dupe (2,3)
	private int transformation;
	private void drawVI(int x, int y, int i, int j) {
		
		if ((this.transformation & 4)==0){
		drawVolatileImage(new Vector(this.treepos, this.tileSize,x,y),  i<<2 | (shade) | ((x==this.cursor.s[0] && y==this.cursor.s[1])? 1:0), j^this.transformation);
		}else{
		drawVolatileImage(new Vector(this.treepos, this.tileSize,y,x),  i<<2 | (shade) | ((x==this.cursor.s[0] && y==this.cursor.s[1])? 1:0), j^this.transformation);
		}
	}

	private void drawVolatileImage(Vector v, int i, int j) {
		VolatileImage vi = tiles.vImg[i];
		do {	

			int returnCode = vi.validate(getGraphicsConfiguration());
			if (returnCode == VolatileImage.IMAGE_RESTORED) {
				tiles.updateTile( this,  i);      // restore contents
			} else if (returnCode == VolatileImage.IMAGE_INCOMPATIBLE) {
				this.tiles=new Tiles(this.tileSize, this);
				tiles.updateTile( this,  i);
			}

			this.flip((Graphics2D) this.gForRec,vi,v,j);
		} while (vi.contentsLost());
	}



	// with the help of source, the seam can be taken or omitted
	// straigth line on seam
	// ToDo: remove dupes? Extract and JUnitTest for dangling bounds
	private void flip(Graphics2D g, VolatileImage vi, Vector v , int f){
		
		AffineTransform backup=g.getTransform(); // TextOut wants a different rotation
		
		int seamWidth=this.tiles.getSeamWidht(); // ToDo: Change into flag to indicate odd seamWidth


		/*		g.drawImage(vi, v.s[0]+16,v.s[1], 32+v.s[0],v.s[1]+ 16, 0, 0, 16, 16, this);
		 */

		int[][] c={{0,0,tileSize.s[0],tileSize.s[1]},{0,0,0,0}};
		for (int i = 0; i < c[0].length; i++) {
			c[1][i]=c[0][i];
		}

		int m=1;		
		if ((f & m)!=0) {
			c[1][0]=c[0][2];
			c[1][2]=c[0][0];
		}

		m<<=1;
		if ((f & m)!=0) {
			c[1][1]=c[0][3];
			c[1][3]=c[0][1];
		}

		m<<=1;
		// swap the coordinates. ToDo: Works only for square tiles (square in px)
		//AffineTransform trans;
		if ((f & m)!=0) { 
			g.setTransform( new AffineTransform(0,1,1,0,v.s[0],v.s[1]));
		}else
		{
			g.setTransform( new AffineTransform(1,0,0,1,v.s[0],v.s[1]));
		}
		//g.drawImage(vi,  16,16, this);

		g.drawImage(vi,0,0,tileSize.s[0],tileSize.s[1]
				,c[1][0]+ ((f>>0) & 1),c[1][1]+ ((f>>1) & 1),c[1][2]+  ((f>>0) & 1),c[1][3]+ ((f>>1) & 1),           this); // remove double seam / allow for odd width

		g.setTransform(backup); // TextOut wants a different rotation
	}



	@Override
	public void move(Vector d, Modifier m) {
		System.out.println("In move: "+this.cursor.s);
		this.cursor.add(d);
		this.repaint();
	}

	// ToDo: uh ugly  External file? C# ?
	@Override
	public void escape() {
		JOptionPane.showMessageDialog(this,
				"Copyright 2014   Arne Christian  Rosenfeldt "+System.lineSeparator()+
				""+System.lineSeparator()+
				 "This program is free software: you can redistribute it and/or modify"+System.lineSeparator()+
				 "it under the terms of the GNU General Public License as published by"+System.lineSeparator()+
				 "the Free Software Foundation, either version 3 of the License, or"+System.lineSeparator()+
				 "(at your option) any later version."+System.lineSeparator()+
				 ""+System.lineSeparator()+
				 "This program is distributed in the hope that it will be useful,"+System.lineSeparator()+
				 "but WITHOUT ANY WARRANTY; without even the implied warranty of"+System.lineSeparator()+
				 "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the"+System.lineSeparator()+
				 "GNU General Public License for more details."+System.lineSeparator()+
				 ""+System.lineSeparator()+
				 "You should have received a copy of the GNU General Public License"+System.lineSeparator()+
				 "along with this program.  If not, see <http://www.gnu.org/licenses/>."+System.lineSeparator()
,
			    "about",
			    JOptionPane.WARNING_MESSAGE);
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
