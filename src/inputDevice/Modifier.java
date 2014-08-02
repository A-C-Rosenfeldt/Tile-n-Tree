package inputDevice;

public enum Modifier {
	shift(1 << 0), alternative(1 << 1), control(1 << 2);

	private int i;

	Modifier(int i) {
		this.i = i;
	}

	public int getNumVal() {
		return i;
	}

}
