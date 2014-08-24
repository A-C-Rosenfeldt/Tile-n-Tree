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

import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

// Gather RGB layout from font rendered and captured
// Robot().createScreenCapture ToDo: Expand interface
// Need subpixel coordinates. What about tile borders? Go to black?

// Check Gamma correction!
// meter <-> pixel  : Anisotropie  vs   Retina display  vs  Beamer
// I make a JPanel for that

public class Tile {
	public int shape;
	public int transformation;
	public int shade;

	public Tile(int shape, int transformation, int shade) {
		this.shape = shape;
		this.transformation = transformation;
		this.shade = shade;
	}
	
	// No clone in C++ thus no clone in my Java. And then maybe I do not want to clone element-wise
	public Tile(Tile original) {
		this.shape = original.shape;
		this.transformation = original.transformation;
		this.shade = original.shade;
	}
	
	public final static Tile space(){
		return new Tile(2,0,0);
	}
	
	@Override
	public boolean equals(Object that) {
		if (that != null && that.getClass() == getClass()

		&& ((Tile) that).shape == this.shape && (((Tile) that).transformation == this.transformation || (this.shape==2 && this.shade==0)) && ((Tile) that).shade == this.shade

		) {
			return true;
		}
		return super.equals(that);
	}
	

	private int getBitmap()
	{
		int map=Tiles.getMap(this.shape);
		
		// Code copied from Frame.flip() 
		// modified using a screenshot as guide
		int m=1;		
//		if ((f & m) != 0) {
//			int t = map;
//			final int column= (1<<3|1)<<3|1;
//			map = (map & (1 | 2 << 3 | 3 << 6)) | (map & (2 | 2 << 3 | 3 << 6)) | (map & (2 | 2 << 3 | 3 << 6));
//		}
//
//		m<<=1;
//		if ((f & m)!=0) {
//			c[1][1]=c[0][3];
//			c[1][3]=c[0][1];
//		}
//
//		m<<=1;
//		// swap the coordinates. ToDo: Works only for square tiles (square in px)
//		//AffineTransform trans;
//		if ((f & m)!=0) { 
//			g.setTransform( new AffineTransform(0,1,1,0,v.s[0],v.s[1]));
//		}else
//		{
//			g.setTransform( new AffineTransform(1,0,0,1,v.s[0],v.s[1]));
//		}		
		
		return shape;
	}
	
	
	
	// In buffer we need in-place union
	public void uniteWith(Tile that){
		if (that.equals(Tile.space())){
			return;
		}
				
		if (this.equals(Tile.space())){
			this.shape = that.shape;
			this.transformation = that.transformation;
			this.shade = that.shade;
			return;
		}
	
		
		
//		Tile[] t=new Tile[]{this, that};
//		if (this.shape>that.shape){t[0]=that;t[1]=this;}
//			
//		// shape != 2 at this point
//		
//		if (t[0].shape == 0) {
//			switch(t[1].shape) {
//			case 0:
//				this.shape = 4;
//				int[] trans = new int[] { this.transformation, that.transformation };
//				if (trans[0] > trans[1]) {
//					trans = new int[] { that.transformation, this.transformation };
//				}
//
//				// seems to be too complicated
//				// ToDo: let the code compare bitmaps?
//				switch (trans[0]) {
//				case 0:
//					switch (trans[1]) {
//					case 0:
//						this.transformation = 0;
//						break;
//					case 1:
//						this.transformation = 4;
//						break;
//					case 2:
//						this.transformation = 2;
//						break;
//					case 5:
//						this.transformation = 0;
//						break;
//					case 6:
//						this.transformation = 4;
//						break;
//					}
//					break;
//				}
//				break;
//			case 3:
//				this.shape=1;
//				switch(t[0].transformation){
//				case 0:
//					switch(t[1].transformation){
//					case 0: break;
//					}
//					break;
//				}
//				break;
//			}
//		}
//		
//		
//		//this.transformation|=that.transformation;
	}
}
