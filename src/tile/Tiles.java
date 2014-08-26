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
package tile;

import java.awt.Toolkit;
import java.awt.image.ColorModel;
import java.util.ArrayList;
import java.util.List;
import java.awt.image.VolatileImage;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.Polygon;
import java.awt.Rectangle;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

import vector2.RectSize;

import java.awt.Window;

import rasterizer.MakeTheBestOfSwing;




public class Tiles {

	private ColorModel cm=null;
	//private List<VolatileImage> a = new ArrayList<VolatileImage>();

	public VolatileImage[] vImg; //=new VolatileImage();
	private GraphicsConfiguration mgc;
	private RectSize tileSize;
	private int seamWidht=1;
	/**
	 * @return the seamWidht
	 */
	public int getSeamWidht() {
		return seamWidht;
	}

	public Tiles(RectSize tileSize, Window w){
		this.tileSize=tileSize;
		/*		super();

	      Rectangle virtualBounds = new Rectangle();
	      GraphicsEnvironment ge = GraphicsEnvironment.
	              getLocalGraphicsEnvironment();
	      GraphicsDevice[] gs =
	              ge.getScreenDevices();
	      GraphicsConfiguration[] gc;
	      // apparently there is a problem with multiple monitors
	      for (int j = 0; j < gs.length; j++) {
	          GraphicsDevice gd = gs[j];
	          gc =
	              gd.getConfigurations();
	          for (int i=0; i < gc.length; i++) {
	              virtualBounds =
	                  virtualBounds.union(gc[i].getBounds());
	          }
	      } */

		this.mgc=w.getGraphicsConfiguration();

		this.vImg=new VolatileImage[6*4]; // ToDo explain 10 ->
		for (int i = 0; i < vImg.length; i++) {

			this.vImg[i]=this.mgc.createCompatibleVolatileImage(tileSize.s[0]+seamWidht,tileSize.s[1]+seamWidht);
		}  
	}

	public void generate(Window w)
	{

		// ToDo. look for changes. Lots of changes!

		/*	// tile cache needs refresh after switching to beamer / zoom / drag to other monitor
	Toolkit t=Toolkit.getDefaultToolkit();
	ColorModel cmS=t.getColorModel();
	if (this.cm==null ) {  // | !this.cm.quals(cmS)){
		VolatileImage b=*/

		// rendering to the image
		this.mgc=w.getGraphicsConfiguration();


		for (int i = 0; i < vImg.length; i++) {			
			updateGivenTile( i, this.vImg[i]);
		}

		//this.cm=cmS;
	}
	
	public void updateTile(Window w,int i)
	{

		// ToDo. look for changes. Lots of changes!

		/*	// tile cache needs refresh after switching to beamer / zoom / drag to other monitor
	Toolkit t=Toolkit.getDefaultToolkit();
	ColorModel cmS=t.getColorModel();
	if (this.cm==null ) {  // | !this.cm.quals(cmS)){
		VolatileImage b=*/

		// rendering to the image
		this.mgc=w.getGraphicsConfiguration();


		updateGivenTile( i, this.vImg[i]);
		

		//this.cm=cmS;
	}
	
	private void updateGivenTile( int i, VolatileImage vi) {
		int[] s=this.tileSize.s;
		do {
			if (vi.validate(this.mgc) ==
					VolatileImage.IMAGE_INCOMPATIBLE)
			{
				// old vImg doesn't work with new GraphicsConfig; re-create it
				vi = this.mgc.createCompatibleVolatileImage(s[0]+seamWidht, s[1]+seamWidht);
			}
			Graphics2D g = vi.createGraphics();
			
			// ToDo  g.Widht= this.seamWidht
			g.setColor(new Color(0.4f, 0.4f, 0.4f));
			g.drawRect(0,0, s[0],s[1]);
			MakeTheBestOfSwing.configure2(g);				
			
			

			// The layout does not need all possible combinations. Ad hoc reduction. 
			int m=1;
			if ((i & m )!=0){
				g.setColor(new Color(0.9f, 0.9f, 0.9f));
				g.fillRect(this.seamWidht, this.seamWidht, s[0]-this.seamWidht, s[1]-this.seamWidht);
			}
			m<<=1;			
			if ((i & m )!=0){
				g.setColor(new Color(0.9f, 0.4f, 0.0f));
			}else{
				g.setColor(new Color(0.0f, 0.4f, 0.9f));
			}
						
			m<<=1;
			if ((i & m )!=0) g.drawLine(0, s[1]/2, s[0], s[1]/2);
			m<<=1;
			if ((i & m )==0) g.drawArc(-s[0]/2, -s[1]/2, s[0], s[1], -90,90);
			m<<=1;
			if ((i & m )!=0) g.drawArc(-s[0]/2, +s[1]/2, s[0], s[1], +90,360); // ToDo explain 10 ->

			// remove dupe and add arrow
			if ((i >> 1)==5){
				g.drawLine(0, s[1]/2, s[0], s[1]/2); // dupe
				
				Polygon p=new Polygon();
				p.addPoint(0,s[1]/2); // Todo use trans here (but linewidth?)
				p.addPoint(s[0]/2,0); // Todo use trans here
				p.addPoint(s[0]/2,s[1]);
			
				g.fillPolygon(p);
			}
			
			// Ramp used instead: if ((i & 8 )!=0) g.drawLine( s[0]/2,0,  s[0]/2, s[1]);

			// g.drawArc(0,0, 16, 16, -90,90);
			//
			g.dispose();
		} while (vi.contentsLost());
	}
	
	// Returns a minimal bitmap (3x3) of the character. Needed for union
	// Adresses are bottom-up  right-left  for BigEndian  compatibility
	// It is copy and paste from this.updateGivenTile()
	public static int getBitmap(int i){
		int r=0;
		int m=1; // shading is shifted out. Different shades cannot be combined. TileSet layout is to be capsulated more and more (ToDo) .  Todo separete shading from shape also in this class
		if ((i & m )!=0) r|=7<<3;//g.drawLine(0, s[1]/2, s[0], s[1]/2);
		m<<=1;
		if ((i & m )==0) r|=4<<6;//g.drawArc(-s[0]/2, -s[1]/2, s[0], s[1], -90,90);
		m<<=1;
		if ((i & m )!=0) r|=4;//g.drawArc(-s[0]/2, +s[1]/2, s[0], s[1], +90,360); // ToDo explain 10 ->
		
		return r;
	}	
	
}
