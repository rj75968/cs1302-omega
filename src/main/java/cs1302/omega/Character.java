package cs1302.omega;

/**
 * Class for Pac-Man character. Sets up getter and setter methods.
 *
 */
public class Character {

    int row;
    //row position of character
    int column;
    //column position of character
    char view;
    //assigns a char to represent each object

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
    } //Character constructor

    /**
     * Getter method for {@code row}.
     * @return row
     */
    public int getRow() {
        return row;
    } //getRow

    /**
     * Setter method for {@code row}.
     * @param row
     */
    public void setRow(int row) {
        this.row = row;
    } //setRow

    /**
     * Getter method for {@code column}.
     * @return column
     */
    public int getColumn() {
        return column;
    } //getColumn

    /**
     * Setter method for {@code column}.
     * @param column
     */
    public void setColumn(int column) {
        this.column = column;
    } //setColumn

    /**
     * Getter method for {@code view}.
     * @return view
     */
    public char getView() {
        return view;
    } //getView

    /**
     * Setter method for {@code view}.
     * @param view
     */
    public void setView(char view) {
        this.view = view;
    } //setView

    /**
     * Setter method for {@code row} and {@code column}.
     * @param row
     * @param column
     */
    public void setPosition(int row, int column) {
        this.row = row;
        this.column = column;
    } //setPosition

} //Character
