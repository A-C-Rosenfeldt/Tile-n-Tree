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
package frame;


public class Start {

	/**
	 * 
	 I need a place for properties before I let someone else test this on
	 * their system
	 */

	public static void main(String[] args) {
		// In Swing you must execute all code that modifies the user interface
		// in the event dispatching thread
		// Maybe VolatileImage may be processed in another thread
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Frame(
						"Tile'n'Tree   ---  please press cursor keys and ctrl, shift, alt")
						.setVisible(true);

			}
		});

	}
}
