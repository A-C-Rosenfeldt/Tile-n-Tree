package vector2;

// Todo: Template / generic to remove int  (not boxed right now)

public class Tupel {
	public int[] s = new int[2]; // Scalar, (or ordinate? or component? direction?)
	
	// shorten notation
	public Tupel(int l0, int l1){
		this.s[0]=l0;
		this.s[1]=l1;
	}
	
	public void AddAt(int x, int i)
	{
		this.s[i]+=x;
	}
}
