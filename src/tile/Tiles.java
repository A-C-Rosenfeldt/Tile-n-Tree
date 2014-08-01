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
import java.awt.Rectangle;
import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;

import vector2.RectSize;

import java.awt.Window;

import rasterizer.MakeTheBestOutOfSwing;




public class Tiles {
	
	private ColorModel cm=null;
	//private List<VolatileImage> a = new ArrayList<VolatileImage>();
	
	public VolatileImage vImg; //=new VolatileImage();
	private GraphicsConfiguration mgc;
	
	public Tiles(RectSize tileSize, Window w){
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
	      
	      this.vImg=this.mgc.createCompatibleVolatileImage(tileSize.s[0],tileSize.s[1]);
	      
	}
	
	public void updateCache(Window w, RectSize rs)
	{

		// ToDo. look for changes. Lots of changes!

		/*	// tile cache needs refresh after switching to beamer / zoom / drag to other monitor
	Toolkit t=Toolkit.getDefaultToolkit();
	ColorModel cmS=t.getColorModel();
	if (this.cm==null ) {  // | !this.cm.quals(cmS)){
		VolatileImage b=*/

		// rendering to the image
		this.mgc=w.getGraphicsConfiguration();

		do {
			if (vImg.validate(w.getGraphicsConfiguration()) ==
					VolatileImage.IMAGE_INCOMPATIBLE)
			{
				// old vImg doesn't work with new GraphicsConfig; re-create it
				vImg = this.mgc.createCompatibleVolatileImage(rs.s[0], rs.s[1]);
			}
			Graphics2D g = vImg.createGraphics();
			MakeTheBestOutOfSwing.configure2(g);
			//
			// miscellaneous rendering commands...
			 g.setColor(new Color(0.9f, 0.0f, 0.5f));
			 
			 g.drawArc(-rs.s[0]/2, -rs.s[1]/2, rs.s[0], rs.s[1], -90,90);
			// g.drawArc(0,0, 16, 16, -90,90);
			//
			g.dispose();
		} while (vImg.contentsLost());

		//this.cm=cmS;
	}
}
