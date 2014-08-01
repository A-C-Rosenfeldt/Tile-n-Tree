package rasterizer;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class MakeTheBestOfSwing {
	
	

	
	public static void configure2(Graphics2D g2){
	g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
	RenderingHints.VALUE_ANTIALIAS_ON);
	
	g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
			RenderingHints.VALUE_INTERPOLATION_BILINEAR);

	 g2.setStroke(new BasicStroke(3));
	}
}
