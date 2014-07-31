package vector2;

import java.awt.Rectangle; // ToDo: Look up the other types there! Rimplement Rect?

public class RectSize extends Tupel {

	
	public RectSize(int l0, int l1) {
		super(l0, l1);
		// TODO Auto-generated constructor stub
	}

	// interface with swing.Rect
	public RectSize(Rectangle r){
		super(r.width,r.height);
	}	
}
