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

		location.getChildren().add(new Node("Bu"));
		location.getChildren().add(new Node("co"));
		
		Node secondBranch=new Node("at");
		secondBranch.setSwapCoordinates(4);
		secondBranch.setChicane(true);
		secondBranch.getChildren().add(new Node("foo"));
		secondBranch.getChildren().add(new Node("bar"));
		secondBranch.getChildren().add(new Node("baz"));		
		location.getChildren().add(secondBranch);
		
		List<Node> s=time.getChildren();
		Node unitOfTime;
		
		s.add(new Node("Sa"));
		s.add(new Node("So"));
		
		unitOfTime=new Node("Mo");
		/*
		 // table body in second branch, which knows the size of the other header
		unitOfTime.setSwapCoordinates(4);
		unitOfTime.setChicane(true);
		unitOfTime.getChildren().add(new Node("X"));
		unitOfTime.getChildren().add(new Node("O"));
		unitOfTime.getChildren().add(new Node("*")); */
		s.add(unitOfTime);		
		
		unitOfTime=new Node("Di");
	//	unitOfTime.getChildren().add(new Node("0"));
		//unitOfTime.getChildren().add(new Node("1"));
		
		
		Node u=new Node("2");
		/*
		 // shows that gaps in column title list need to be respected
		u.setSwapCoordinates(4);
		u.setChicane(true);
		u.getChildren().add(new Node("x"));
		u.getChildren().add(new Node("F"));
		u.getChildren().add(new Node("L"));
		*/
		
		unitOfTime.getChildren().add(u);
//		s.add(unitOfTime);

		
		Node inline=new Node("Inline inside");
		node.getChildren().add(inline);

		Node proto=new Node("struct definition of a complex number");
		inline.getChildren().add(proto);
		Node re=new Node("real part"); // to avoid too fat class node (all those link types are to be removed), content is put a layer below type. => optical clue!
		re.getChildren().add(new Node("0.0"));
		re.getChildren().add(new Node("0.5"));
		Node v=new Node("1.0");
		re.getChildren().add(v);

		proto.getChildren().add(re);
		Node im=new Node("imaginary part");
		proto.getChildren().add(im);

		
		Node a=new Node("instance a");
		a.setValue(proto);
		a.setInlineReferenced(true);
		inline.getChildren().add(a);		

		Node b=new Node("instance b");

		Node selector=new Node("instance real part");
		selector.setValueDoNotNotify(v);
		b.getChildren().add(selector); // ToDo: separate method  getchildren.edit  from   getchildren.view
		b.setValue(proto);
		
		inline.getChildren().add(b);
		//b.re.setValue(v); // ToDo
		
		b.setInlineReferenced(true);
		
		return node;
	}
}
