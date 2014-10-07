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
package tree;

import java.util.ArrayList;
import java.util.List;

import vector2.RectSize;
import vector2.Vector;

/**
 * @author Arne Rosenfeldt
 * Only cached -- not persistent
 * ToDo: Duplicate need their own. Links?
 */
public class LayedOutPosition {
	public Vector position; // center would be fair
	public RectSize size; // no affected by move

	///public LayedOutPosition parent;
	public List<LayedOutPosition> value_children=new ArrayList<LayedOutPosition>();

	public LayedOutPosition(Vector position) { ///, LayedOutPosition parent) {
		this.position = position;
		///this.parent = parent;
	}
}
