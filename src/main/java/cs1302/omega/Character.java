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
     * @param look
     */
    public Character(int row, int column, char view) {
        this.row = row;
        this.column = column;
        this.view = view;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public char getView() {
        return view;
    }

    public void setView(char view) {
        this.view = view;
    }

    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

} //Character
