package cs1302.omega;

/**
 * Enumerated class for direction constants.
 */
public enum Direction {

    UP(0, -1), DOWN(0, 1), LEFT(-1, 0), RIGHT(1, 0), STAY(0, 0);
    //the movements for each direction

    private final int x;
    private final int y;

    /**
     * Constructor for grid positions.
     * @param x
     * @param y
     */
    private Direction(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Getter method for {@code x}.
     * @return x
     */
    public int getX() {
        return x;
    } //getX

    /**
     * Getter method for {@code y}.
     * @return y
     */
    public int getY() {
        return y;
    } //getY

    /**
     * Returns user friendly string of enum constant.
     * @return name() + "(" + x + ", " + y + ")"
     */
    public String toString() {
        return name() + "(" + x + ", " + y + ")";
    } //toString

} //Direction
