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

	public VolatileImage[] vImg; //=new VolatileImage();
	private GraphicsConfiguration mgc;
	private int seamWidht=1;
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

		this.vImg=new VolatileImage[6]; // ToDo explain 10 ->
		for (int i = 0; i < vImg.length; i++) {

			this.vImg[i]=this.mgc.createCompatibleVolatileImage(tileSize.s[0],tileSize.s[1]);
		}  
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


		int[] s = rs.s;
		for (int i = 0; i < vImg.length; i++) {
	
			VolatileImage vi=this.vImg[i];
			do {
				if (vi.validate(w.getGraphicsConfiguration()) ==
						VolatileImage.IMAGE_INCOMPATIBLE)
				{
					// old vImg doesn't work with new GraphicsConfig; re-create it
					vi = this.mgc.createCompatibleVolatileImage(rs.s[0]+seamWidht, rs.s[1]+seamWidht);
				}
				Graphics2D g = vi.createGraphics();
				
				g.drawRect(0,0, rs.s[0],rs.s[1]);
				MakeTheBestOutOfSwing.configure2(g);				
				
				g.setColor(new Color(0.9f, 0.0f, 0.5f));

				// The layout does not need all possible combinations. Ad hoc reduction. 
				if ((i & 1 )!=0) g.drawLine(0, s[1]/2, s[0], s[1]/2);
				if ((i & 2 )==0) g.drawArc(-s[0]/2, -s[1]/2, s[0], s[1], -90,90);
				if ((i & 4 )!=0) g.drawArc(-s[0]/2, +s[1]/2, s[0], s[1], +90,360); // ToDo explain 10 ->

				// Ramp used instead: if ((i & 8 )!=0) g.drawLine( s[0]/2,0,  s[0]/2, s[1]);

				// g.drawArc(0,0, 16, 16, -90,90);
				//
				g.dispose();
			} while (vi.contentsLost());
		}

		//this.cm=cmS;
	}
}
