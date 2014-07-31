package frame;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.JFrame;

import vector2.RectSize;


public class Frame  extends JFrame{
	  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Frame(String titel) {
	        super(titel);
	        setDefaultCloseOperation(EXIT_ON_CLOSE);
	        setSize(300, 300);
	        // Premature optimization (or aero non portable look and feel)	        
	        ///setBackground(new Color(0,0,0,0)); // Unuseable: http://docs.oracle.com/javase/7/docs/api/java/awt/Frame.html#setOpacity%28float%29 
	        
	        setVisible(true);
	  }

	public RectSize tileSize=new RectSize(16,16);
	
	/* (non-Javadoc)
	 * @see java.awt.Window#paint(java.awt.Graphics)
	 */
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
	//	super.paint(arg0);  changes background from grey to white. There is nothing to draw for super in the client area as I want a full tile-based display, do I?
		Rectangle bounds=g.getClipBounds();
		RectSize rs=new RectSize(bounds);
		
		// Todo: Make enumeration out of this (parameter of direction)
		for(vector2.Vector v=new vector2.Vector(0,0);v.s[1]<rs.s[1];v.AddAt(tileSize.s[1], 1))
		{
			for(v.s[0]=0;v.s[0]<rs.s[0];v.AddAt(tileSize.s[0], 0))
			{
				g.setColor(new Color(v.s[0] & 255, v.s[1] & 255, v.s[0] & 255));
				
				g.fillRect(v.s[0], v.s[1], tileSize.s[0], tileSize.s[1]);
			}			
		}
		
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
