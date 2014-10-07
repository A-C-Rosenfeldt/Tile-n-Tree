The program tries to combine the experiences on a 8-bit home computer BASIC with modern languages (XML, C#, Scala) and graphs. It is planned to put this in the web using Google Web Toolkit (GWT).
Development uses elements which worked well in the past: A good IDE (this time eclipse) with re-factoring, version control (GIT), unit tests (Junit4), a program which runs without user interaction.
Test driven development does not work. And I do not use UML. In fact there are no documents besides the Code in GIT and I do not miss them.

![Screenshot](https://plus.google.com/photos/+ArneChristianRosenfeldt/albums/6042486578143006305/6043434580258012258?pid=6043434580258012258&oid=101260750722957758932]

Code Style
No bloated files => Tabs, LF only (no CR (ToDo)), ASCII (en.us only)

Features

* tetris
* trans
* seam
* Glyphs on top
* cache parameter
* construct tree
	* top to bottom
	* connector tiles
	* ~clean up
	* cursor
		* (selected) tile
	* ~testing
* references
	* more tiles
	* route
		* simple use case
* bend
* table
* inline reference
* the following features are not yet implemented
* instantiate first level children to second level children
* more references
	* crossings and joins
	* optimize for clarity  not  for comapactness
----
* Box Model
	* paint Boxes in debug mode (similar to HTML Browsers)
* DOM
	* store complete layout with all references
		* for delta
		* to avoid dupes
* foreground layer
	*	crossing (all alternatives look ugly)
	*	nTupel
* folding
* scrolling
	*	open newest part
	*	no scrollbar (game does not have such a thing)
* view
	*	HTML box model
	*	3d