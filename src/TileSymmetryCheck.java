import static org.junit.Assert.*;

import org.junit.Test;

import tile.Tile;

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

/**
 * @author Arne Rosenfeldt
 *
 */
public class TileSymmetryCheck {

	@Test
	public void test() {
		Tile a=new Tile(2,0,0);
		Tile b=new Tile(2,0,0);
		if (!a.equals(b)){
			fail("a!=b");
		}

		if (!b.equals(a)){
			fail("b!=a");
		}
	}
	
	@Test
	public void union() {
		Tile a=new Tile(2,0,0);
		Tile b=new Tile(0,0,0);
		try {
			a.uniteWith(b);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail("Exception!");
		}
		if (!a.equals(b)){
			fail("union a!=b");
		}

		if (!b.equals(a)){
			fail("union a!=b");
		}
	}
	

}
