package tree;

import static org.junit.Assert.*;

import org.junit.Test;

import tile.Tile;

public class BufferTest {

	@Test
	public void standard() {
		Buffer b=new Buffer(Tile.space);
		if (b.get(2).equals(Tile.space)) return;
		fail("default not working");
	}

	@Test
	public void getset() {
		Buffer b = new Buffer(null);

		b.set(0, new Tile(0, 0, 0));

		if (b.get(0).equals(new Tile(0, 0, 0)))
			return;
		fail("set get not working");
	}
	
	@Test
	public void getsetOtherValue() {
		Buffer b = new Buffer(null);

		b.set(0, new Tile(1, 0, 0));

		if (b.get(0).equals(new Tile(1, 0, 0)))
			return;
		fail("set get not working");
	}	
	
	@Test
	public void getsetOtherValueAtOtherIndex() {
		Buffer b = new Buffer(null);

		b.set(1, new Tile(1, 0, 0));

		if (b.get(1).equals(new Tile(1, 0, 0)))
			return;
		fail("set get not working");
	}		
	
	@Test
	public void getsetOtherValueAtOtherNegativeIndex() {
		Buffer b = new Buffer(null);

		b.set(-1, new Tile(1, 0, 0));

		if (!b.get(-1).equals(new Tile(1, 0, 0)))
		fail("set get not working");
		
		System.out.println("boundaries "+b.getBoundary(0)+" -- "+b.getBoundary(1));
	}
	
	@Test
	public void getsetOtherValueAtDifferentIndices() {
		Buffer b = new Buffer(null);

		b.set(-1, new Tile(1, 0, 0));
		b.set(+1, new Tile(1, 2, 0));

		if (!b.get(-1).equals(new Tile(1, 0, 0)))
			fail("set get not working");

		if (!b.get(+1).equals(new Tile(1, 2, 0)))
			fail("set get not working");		
		System.out.println("boundaries "+b.getBoundary(0)+" -- "+b.getBoundary(1));
	}		
}
