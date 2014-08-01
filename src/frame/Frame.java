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


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
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

import rasterizer.MakeTheBestOfSwing;
import tile.Tile;
import tile.Tiles;
import tree.Node;
import tree.Util;
import vector2.RectSize;
import vector2.Vector;


public class Frame  extends JFrame{
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

		setVisible(true);

		this.tiles=new Tiles(this.tileSize, this);

		// ToDo: Does not work 
		addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) { 

				new Configuration("Configuration"); }

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub

			}
		}
				);

		tiles.generateTiles( this);
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

				for(int j=0;j<8;j++){
					this.flip((Graphics2D) g,vi,new Vector((int)bounds.getMinX()+i*32,(int)bounds.getMinY()+j*32),j);
				}
				// seam ToDo only on Background. One px wide on legacy systems. Voids flip 

				//  g.drawImage(vi,  30+i*32,  200, 16,16, this);
				/*g.drawImage(vi, 130,  30, -16,16,this);
		      g.drawImage(vi,  30, 130, 16,-16,this);
		      g.drawImage(vi, 130, 130, -16,-16,this); */
			} while (vi.contentsLost());
		}


		// String
		//((Graphics2D) g).setTransform( new AffineTransform(1,0,0,1,(int)bounds.getMinX(),(int)bounds.getMinY()));

		this.gForRec=g;
		this.drawTree( pos.x+432,pos.y+48,Util.createSampleTree()); // root == frame window

		g.drawString("Hello World",  320+1, 64-3);



		// ToDo try{} around resources

		// probably now the
		// Compositing window manager
		// renders my client area on the screen
		// But mind fullscreen (eg on mobile or TV)!
	}

	private Graphics gForRec; // ToDo: Extra class?
	private int drawTree(int x, int y, Node current) {
		// Tree
		/*
			 i,j  
		0,1
		1,6
		1,2
		3,4
		 */



		for (Node node : current.children) {

			int i=0;
			int j=1;
			// Copied from character set print
			VolatileImage vi = tiles.vImg[i];
			do {	

				int returnCode = vi.validate(getGraphicsConfiguration());
				if (returnCode == VolatileImage.IMAGE_RESTORED) {
					// Contents need to be restored
					tiles.updateTile( this,  i);      // restore contents
				} else if (returnCode == VolatileImage.IMAGE_INCOMPATIBLE) {
					// old vImg doesn't work with new GraphicsConfig; re-create it
					this.tiles=new Tiles(this.tileSize, this);
					tiles.updateTile( this,  i);  // only affected tiles!! New parameter! ToDo
				}
				
				this.flip((Graphics2D) this.gForRec,vi,new Vector(x,y),j);
			} while (vi.contentsLost());

			this.gForRec.drawString(node.title,  x+1, y-3+this.tileSize.s[1]);
			y+=this.tileSize.s[1];
			y=this.drawTree( x+this.tileSize.s[0],y, node);
		}

		return y; // ToDo: Why does boxing not work?
	}



	// with the help of source, the seam can be taken or omitted
	// straigth line on seam
	// ToDo: remove dupes?
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


		//		trans.rotate( Math.toRadians(45) );
		//		g.drawImage(vi, trans, this);


		// source needed for seam
		//	      System.out.printf("%d %d  %d %d   %d %d  %d %d ",v.s[0],v.s[1],v.s[0]+tileSize.s[0],v.s[1]+tileSize.s[1]
		//	     		 ,c[1][0],c[1][1],c[1][2]-seamWidth,c[1][3]-seamWidth); // remove double seam / allow for odd width
		//		System.out.println();
		//	      g.drawImage(vi,v.s[0],v.s[1],v.s[0]+tileSize.s[0],v.s[1]+tileSize.s[1]
		//	     		  ,c[1][0],c[1][1],c[1][2]-seamWidth,c[1][3]-seamWidth,           this); // remove double seam / allow for odd width
		//	     		
		
		g.setTransform(backup); // TextOut wants a different rotation
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
