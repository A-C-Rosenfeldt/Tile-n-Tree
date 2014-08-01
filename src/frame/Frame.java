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
import java.awt.image.ColorModel;
import java.awt.image.VolatileImage;
import java.util.ArrayList;

import javax.swing.JFrame;

import rasterizer.MakeTheBestOutOfSwing;
import tile.Tile;
import tile.Tiles;
import vector2.RectSize;


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
	        setSize(300, 300);
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
	  }


	
	/* (non-Javadoc)
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		
		//g.hint(antialias)
		MakeTheBestOutOfSwing.configure2((Graphics2D) g);
		 
		// TODO Auto-generated method stub
	//	super.paint(arg0);  changes background from grey to white. There is nothing to draw for super in the client area as I want a full tile-based display, do I?
		Rectangle bounds=this.getContentPane().getBounds();
		RectSize rs=new RectSize(bounds);
		
		// What point?
		Point pos = this.getContentPane().getLocationOnScreen();
		Rectangle clientRect = this.getContentPane().getBounds(); 
		clientRect.add(pos);
		
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
		
		// ToDo: Does not work
		tiles.updateCache( this, tileSize);
		 do {
		      int returnCode = tiles.vImg.validate(getGraphicsConfiguration());
		      if (returnCode == VolatileImage.IMAGE_RESTORED) {
		          // Contents need to be restored
		    	  tiles.updateCache( this, rs);      // restore contents
		      } else if (returnCode == VolatileImage.IMAGE_INCOMPATIBLE) {
		          // old vImg doesn't work with new GraphicsConfig; re-create it
		    	  this.tiles=new Tiles(this.tileSize, this);
		    	  tiles.updateCache( this, rs); 
		      }
		      
		      g.drawImage(tiles.vImg,  30,  30, 16,16, this);
		      g.drawImage(tiles.vImg, 130,  30, -16,16,this);
		      g.drawImage(tiles.vImg,  30, 130, 16,-16,this);
		      g.drawImage(tiles.vImg, 130, 130, -16,-16,this);
		 } while (tiles.vImg.contentsLost());

		 // What did they smoke when defining these parameters?
		 g.setColor(new Color(0.9f, 0.0f, 0.5f));
		 g.drawArc((int)pos.x-8, pos.y-8, 16, 16, -90,90);
		 
		 g.drawArc((int)pos.x, pos.y, 100, 100, 270, 90);
			
		 g.setColor(new Color(0.1f,0.0f,0.0f));
		// g.setColor(new Color(0.7, 0.8, 0.5,0));
		g.drawLine(0, 0, 100, 120);
		g.drawArc(40, 40, 100, 200, 0, 90);
		g.drawRect(40, 40, 100, 120);
		g.drawArc(150, 150, 100, 200, 0, 90);

		// todo try{} around resources
		
		// probably now the
		// Compositing window manager
		// renders my client area on the screen
		// But mind fullscreen (eg on mobile or TV)!
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
