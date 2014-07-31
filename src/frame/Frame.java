package frame;


import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.List;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.ColorModel;
import java.awt.image.VolatileImage;
import java.util.ArrayList;

import javax.swing.JFrame;

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
	  }


	
	/* (non-Javadoc)
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		
		//g.hint(antialias)
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		 g2.setStroke(new BasicStroke(3));
		 
		// TODO Auto-generated method stub
	//	super.paint(arg0);  changes background from grey to white. There is nothing to draw for super in the client area as I want a full tile-based display, do I?
		Rectangle bounds=g.getClipBounds();
		RectSize rs=new RectSize(bounds);
		
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
		
	
		tiles.updateCache( this, rs);
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
		      
		      g.drawImage(tiles.vImg, 100, 0, this);
		 } while (tiles.vImg.contentsLost());

		 
		
		
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
