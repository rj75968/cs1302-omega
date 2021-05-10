package cs1302.omega;

/**
 * Class for Pac-Man character. Sets up getter and setter methods.
 *
 */
public class Character {

    int row;
    int column;
    char view;

    /**
     * Constructor for Character object. Takes in location and view values.
     * @param row
     * @param column
     * @param view
     */
    public Character(int row, int column, char view) {
        this.row = row;
        this.column = column;
        this.view = view;
    }

    /**
     * Getter method for {@code row}.
     * @return row
     */
    public int getRow() {
        return row;
    }

    /**
     * Setter method for {@code row}.
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Getter method for {@code column}.
     * @return column
     */
    public int getColumn() {
        return column;
    }

    /**
     * Setter method for {@code column}.
     * @param column
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * Getter method for {@code view}.
     * @return view
     */
    public char getView() {
        return view;
    }

    /**
     * Setter method for {@code view}.
     * @param view
     */
    public void setView(char view) {
        this.view = view;
    }

    /**
     * Setter method for {@code row} and {@code column}.
     * @param row
     * @param column
     */
    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

} //Character
