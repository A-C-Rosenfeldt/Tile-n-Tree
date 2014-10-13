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

// Todo
// Use F1 to open configuration.java

package adHocRouter;

import tree.NodeBase;
import vector2.ClosedInterval;
import vector2.Tupel;

public interface Link {
	///public void addBedrock(int x);
	
	public void sort();
	public LinkWith2Bends calculateNext();
	void addLink(Tupel x, ClosedInterval y, NodeBase node);
	// node is used to match these two calls
	// indexed via y right now (more paint - centric)
	
	public  LinkWith2Bends get(NodeBase node); // Needed for completion of y

	public boolean hasNext();

	///public void addBedrock(int x, int y);
	public LinkWith2Bends getLinksSortedByYPrevious();
	public void jumpBelowLinks();

	LinkWith2Bends getPrevious();

	void sort(boolean moveToEnd);

	Object[] getY();




}
