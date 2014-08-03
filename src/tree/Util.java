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

public class Util {
	public static Node createSampleTree(){
		Node charles=new Node("Charles");
		Node harry=new Node("Harry");
		charles.getChildren().add(harry);
		charles.getChildren().add(new Node("Wiliam"));
		Node node=new Node("Hello again");
		node.getChildren().add(charles);
		Node philip=new Node("Philip");
		philip.setValue(harry);
		node.getChildren().add(philip);	
		Node location=new Node("Place");
		Node time=new Node("Time");
		time.setSwapCoordinates(4);

		philip.getChildren().add(time);
		philip.getChildren().add(location);

		location.getChildren().add(new Node("Buckigham"));
		location.getChildren().add(new Node("in the country"));
		location.getChildren().add(new Node("with friends"));
		
		ArrayList<Node> s=time.getChildren();
		s.add(new Node("during the week"));
		s.add(new Node("Weekend"));
		
		
		return node;
	}
}
