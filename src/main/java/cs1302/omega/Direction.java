package cs1302.omega;

/**
 *
 */
public enum Direction {

	// The Definitions for UP, DOWN, LEFT, and RIGHT
	UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0), STAY(0, 0);

	private final int x;
	private final int y;


	Direction(int x, int y)	{
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public String toString() {
		return name() + "(" + x + ", " + y + ")";
	}

} //Direction
