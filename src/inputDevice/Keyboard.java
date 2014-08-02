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
package inputDevice;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import vector2.Vector;

public class Keyboard implements KeyListener {

	private Mapping m;

	public Keyboard(Mapping m){
		this.m=m;
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		System.out.println("pressed "+e.getKeyCode()+" vs "+KeyEvent.VK_KP_RIGHT);
		switch (e.getKeyCode()) {
		case KeyEvent.VK_F1:
			m.escape();
			break;
		case KeyEvent.VK_RIGHT:
			m.move(new Vector(1,0), null);
			System.out.println("right");
			break;
		case KeyEvent.VK_LEFT:
			m.move(new Vector(-1,0), null);
			break;
		case KeyEvent.VK_DOWN:
			m.move(new Vector(0,1), null);
			break;
		case KeyEvent.VK_UP:
			m.move(new Vector(0,-1), null);
			break;
		}
		

	}

	@Override
	public void keyReleased(KeyEvent e) {
		//System.out.println("released");
	}

	@Override
	public void keyTyped(KeyEvent e) {
		//System.out.println("typed");
	}

}
