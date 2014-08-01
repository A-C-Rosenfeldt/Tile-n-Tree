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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Configuration extends JFrame {
public Configuration(String title)
{
	super(title);
	JPanel p=new JPanel();
	this.rootPane.add(p);
	p.add(new JLabel("	"+
"Copyright 2014   Arne Christian  Rosenfeldt "+System.lineSeparator()+
""+System.lineSeparator()+
 "   This program is free software: you can redistribute it and/or modify"+System.lineSeparator()+
 "   it under the terms of the GNU General Public License as published by"+System.lineSeparator()+
 "   the Free Software Foundation, either version 3 of the License, or"+System.lineSeparator()+
 "   (at your option) any later version."+System.lineSeparator()+
 "   "+System.lineSeparator()+
 "   This program is distributed in the hope that it will be useful,"+System.lineSeparator()+
 "   but WITHOUT ANY WARRANTY; without even the implied warranty of"+System.lineSeparator()+
 "   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the"+System.lineSeparator()+
 "   GNU General Public License for more details."+System.lineSeparator()+
 "   "+System.lineSeparator()+
 "   You should have received a copy of the GNU General Public License"+System.lineSeparator()+
 "   along with this program.  If not, see <http://www.gnu.org/licenses/>."+System.lineSeparator()
));
}

	private static final long serialVersionUID = -4231498755200978778L;

}
