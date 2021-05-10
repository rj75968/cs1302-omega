package cs1302.omega;

import java.io.*;
import java.util.*;
import java.util.Random;

/**
 * GameBoard class creating objects to populate Pac-Man game.
 * Sets up moves for objects on the game's grid.
 *
 */
public class GameBoard {


    char[][] grid;
    //char array of game board
    boolean[][] visitCheck;
    //array to log checks for pacman visit
    boolean ateCherry = false;
    //check for cherry status
    int score;
    // stores score
    int cherryRounds;
    // stores how many turns cherry boost lasts for
    Character pacman;
    // Character object for user to play game with
    Character[] ghosts;
    // four colored enemies of pacman
    Character cherry;
    // cherry that gives pacman character ability to defeat ghosts


    public final int BOARD_SIZE;
    //grid size constant

    /**
     * Constructs a GameBoard object with randomized size.
     * Sets up placements of ghosts on the new {@code GameBoard}.
     * @param size
     */
    public GameBoard(int size) {


    	Random rand = new Random();

    	int x = rand.nextInt(size);
    	int y = rand.nextInt(size);
    	while ((x == size/2 && y == size/2) || (x == 0 && y == 0) || (x == size-1 && y == size-1) || (x == 0 && y == size-1) || (x == size-1 && y == 0)) {
    		x = rand.nextInt(size); //checks that cherry isn't on player or ghost
    		y = rand.nextInt(size); //board dimension
    	}

        BOARD_SIZE = size;
        score = 0;
        grid = new char[BOARD_SIZE][BOARD_SIZE];
        visitCheck = new boolean[BOARD_SIZE][BOARD_SIZE];

        pacman = new Character(BOARD_SIZE / 2, BOARD_SIZE / 2, 'P');
        ghosts = new Character[4];
        ghosts[0] = new Character(          0,           0, 'G');
        ghosts[1] = new Character(BOARD_SIZE-1,           0, 'G');
        ghosts[2] = new Character(          0, BOARD_SIZE-1, 'G');
        ghosts[3] = new Character(BOARD_SIZE-1, BOARD_SIZE-1, 'G');
        cherry = new Character(x, y, 'C');

        setVisitCheck(BOARD_SIZE / 2, BOARD_SIZE / 2);
        update(); //updates game progress every time new GameBoard created

    } //GameBoard(int size)

    /**
     * Constructs a {@code GameBoard} from user input.
     * Checks characters and places the {@link Character}
     * at that location.
     *
     * @param newBoard
     * @throws IOException
     */
    public GameBoard(String newBoard) throws IOException {

        Scanner input = new Scanner(new File(newBoard));

        BOARD_SIZE = input.nextInt();
        score = input.nextInt();

        grid = new char[BOARD_SIZE][BOARD_SIZE];
        visitCheck = new boolean[BOARD_SIZE][BOARD_SIZE];
        String chars = input.nextLine();

        char ch;
        ghosts = new Character[4];
        for (int rowIndex = 0; rowIndex < BOARD_SIZE; rowIndex++) {
            chars = input.nextLine();
            for (int columnIndex = 0; columnIndex < BOARD_SIZE; columnIndex++) {
                ch = chars.charAt(columnIndex);
                grid[rowIndex][columnIndex] = ch;

                switch (ch) {
                case 'P': //pacman character object
                    setVisitCheck(rowIndex, columnIndex);
                    pacman = new Character(rowIndex, columnIndex, 'P');
                    break;
                case 'G': //ghost object character
                    for (int i = 0; i < ghosts.length; i++) {
                        if (ghosts[i] == null) {
                            ghosts[i] = new Character(rowIndex, columnIndex, 'G');;
                            break;
                        }
                    }
                    break;
                case 'D': //game over character
                    pacman = new Character(rowIndex, columnIndex, 'P');
                    for (int i = 0; i < ghosts.length; i++) {
                        if (ghosts[i] == null) {
                            ghosts[i] = new Character(rowIndex, columnIndex, 'G');;
                            break;
                        }
                    }
                    break;
                case 'C': //cherry object character
                    cherry = new Character(rowIndex, columnIndex, 'C');
                    break;
                case ' ': //if empty, check for visit
                    setVisitCheck(rowIndex, columnIndex);
                    break;
                }
            }
        }

    } //GameBoard(String input)


    public int getScore() {
        return score;
    } //getScore


    public char[][] getGrid() {
        return grid;
    } //getGrid

    public void setVisitCheck(int x, int y) {
        if (x >= BOARD_SIZE || y > BOARD_SIZE || x < 0 || y < 0) {
            return;
        }
        visitCheck[x][y] = true;
    } //setVisitCheck

    public int getCherryTurns() {
    	return cherryRounds;
    }

    public Character[] getGhosts() {
    	return ghosts;
    }

    public Character getPacMan() {
    	return pacman;
    }

    /*
     * Updates score and overall game progress.
     */
    public void update() {
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (!visitCheck[i][j]) {
                    grid[i][j] = '*';
                } else {
                    grid[i][j] = ' ';
                }
            }
        }
        if (ateCherry == false) {
        	grid[cherry.getRow()][cherry.getColumn()] = cherry.getView();
        }
        grid[pacman.getRow()][pacman.getColumn()] = pacman.getView();

        for (Character ghost : ghosts) {
        	if (ghost != null) {
	            if (cherryRounds == 0) {
	            	if (pacman.getRow() == ghost.getRow() && pacman.getColumn() == ghost.getColumn()) {
	            		grid[ghost.getRow()][ghost.getColumn()] = 'D'; //game over, pacman dead :(
                    }   else grid[ghost.getRow()][ghost.getColumn()] = ghost.getView();
	            } else if (cherryRounds > 0) {
	            	if (pacman.getRow() == ghost.getRow() && pacman.getColumn() == ghost.getColumn()) {
	            		grid[ghost.getRow()][ghost.getColumn()] = pacman.getView();
	            		ghost = null; //set ghost to null when pacman on same spot and cherry rounds remaining
	            	} else {
	                	grid[ghost.getRow()][ghost.getColumn()] = ghost.getView();
	                }
	            }
        	}
        }
        if (cherryRounds > 0) {
        	cherryRounds--; //if cherryRounds still more than 0, decrement
        }
    } //update



    /**
     * Method to add points to score based on user's keyboard input.
     * @param direction
     */
    public void userMove(Direction direction) {
        int pacmanRow = pacman.getRow() + direction.getY();
        int pacmanCol = pacman.getColumn() + direction.getX();

        pacman.setPosition(pacmanRow, pacmanCol);

        if (!visitCheck[pacmanRow][pacmanCol]) {
            score += 10; //each dot eaten adds 10 points to score
            visitCheck[pacmanRow][pacmanCol] = true;
        }
        if (ateCherry == false && pacman.getColumn() == cherry.getColumn() && pacman.getRow() == cherry.getRow()) {
	        	score += 100; //cherries are 100 points
	        	cherryRounds = 4;
	        	ateCherry = true;
        }
        for (Character ghost : ghosts) {
            if (ghost != null) {
                ghostMove(ghost);
            }
        } //loop through each ghost and check for its move
        update(); //update game progress after character move
    } //move


    /**
     * Checks if the move can be executed depending on multiple grid and character
     * conditions.
     *
     * @param direction
     * @return
     */
    public boolean canMoveCheck(Direction direction) {

    	if (direction == null && isGameOver()) {
    		return false;
    	} //if direction null and game is over, cannot move

    	int pacmanRow = pacman.getRow() + direction.getY();
    	int pacmanCol = pacman.getColumn() + direction.getX();

    	return pacmanRow >= 0 && pacmanRow < BOARD_SIZE && pacmanCol >= 0 && pacmanCol < BOARD_SIZE;
    }

    /**
     * Boolean method checking if game is over.
     * Checks with zero cherry rounds and if ghosts remain.
     */
    public boolean isGameOver() {
        int pacmanRow = pacman.getRow();
        int pacmanCol = pacman.getColumn();

        for (Character ghost : ghosts) {
        	if (cherryRounds == 0 && ghost != null) {
        		if (ghost.getRow() == pacmanRow && ghost.getColumn() == pacmanCol) {
                    return true;
                }
        	}
        }
        return false;
    } //isGameOver

    /**
     * Method detailing the movement pattern of a {@link Character} ghost object.
     * @param ghost the ghost object to set a pattern for
     * @return Direction the Direction constant that the ghost is taking
     */
    public Direction ghostMove(Character ghost) {
        int pacmanRow = pacman.getRow();
        int pacmanCol = pacman.getColumn();

        int ghostRow = ghost.getRow();
        int ghostCol = ghost.getColumn();

        int rowDiff = Math.abs(ghostRow - pacmanRow);
        int colDiff = Math.abs(ghostCol - pacmanCol);

        if (cherryRounds == 0) {
	        if (rowDiff > 0 && colDiff == 0 ) {
	            if (ghostRow - pacmanRow > 0) {
	                ghost.setPosition(ghostRow - 1, ghostCol);
	                return Direction.UP;
	            } else {
	                ghost.setPosition(ghostRow + 1, ghostCol);
	                return Direction.DOWN;
	            }
	        } else if (rowDiff == 0 && colDiff > 0) {
	            if (ghostCol - pacmanCol > 0) {
	                ghost.setPosition(ghostRow, ghostCol - 1);
	                return Direction.LEFT;
	            } else {
	                ghost.setPosition(ghostRow, ghostCol + 1);
	                return Direction.RIGHT;
	            }
	        } else if (rowDiff == 0 && colDiff == 0) {
	            return Direction.STAY;
	        } else {
	            if (rowDiff < colDiff) {
	                if (ghostRow - pacmanRow > 0) {
	                    ghost.setPosition(ghostRow - 1, ghostCol);
	                    return Direction.UP;
	                } else {
	                    ghost.setPosition(ghostRow + 1, ghostCol);
	                    return Direction.DOWN;
	                }
	            } else {
	                if (ghostCol - pacmanCol > 0) {
	                    ghost.setPosition(ghostRow, ghostCol - 1);
	                    return Direction.LEFT;
	                } else {
	                    ghost.setPosition(ghostRow, ghostCol + 1);
	                    return Direction.RIGHT;
	                }
	            }
	        }
        } else {
        	if((ghostRow == BOARD_SIZE - 1 && ghostCol == BOARD_SIZE -1) ||
               (ghostRow == 0 && ghostCol == 0) ||
               (ghostRow == BOARD_SIZE - 1 && ghostCol == 0) ||
               (ghostRow == 0 && ghostCol == BOARD_SIZE - 1)) {
        		return Direction.STAY; //if its the corner of the board, don't move
        	} else if (rowDiff >= colDiff) {
        		if (ghostRow != BOARD_SIZE - 1 && ghostRow != 0) {
        		 	if (ghostRow - pacmanRow >0) {
        				return Direction.DOWN;
        			} else {
        				return Direction.UP;
        			}
        		} else if (ghostCol != 0 && ghostCol != BOARD_SIZE - 1) {
        			if (ghostCol - pacmanCol > 0) {
        				return Direction.RIGHT;
        			} else {
        				return Direction.LEFT;
        			}
        		}
        	} else if (rowDiff < colDiff) {
        		if (ghostCol != 0 && ghostCol != BOARD_SIZE - 1){
        			if (ghostCol - pacmanCol > 0) {
        				return Direction.RIGHT;
        			} else {
        				return Direction.LEFT;
        			}
        		} else if (ghostRow != BOARD_SIZE - 1 && ghostRow != 0) {
        			if (ghostRow - pacmanRow > 0) {
        				return Direction.DOWN;
        			} else {
        				return Direction.UP;
        			}
        		}
        	}
    		return Direction.STAY;
        }

    } //ghostMove


} //GameBoard
