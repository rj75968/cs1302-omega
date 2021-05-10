
package cs1302.omega;

import javafx.scene.effect.DropShadow;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.io.IOException;
import javafx.application.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.scene.*;
import javafx.geometry.Pos;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.Event;
import javafx.geometry.Bounds;
import javafx.geometry.BoundingBox;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

/**
 * Represents a Pac-Man GUI application.
 */
public class OmegaApp extends Application {

    GameBoard board; // PacMan board
    private String outputBoard;

	private final Timeline loop = new Timeline();    // timeline for main game loop
	private static final Color COLOR_GAME_OVER = Color.web("#0000FF", 0.63);

	private static Tile[][] tiles;
	private int rowcount; //rows in the grid
	private int colcount; //columns in the grid
    char tileView;

	Text score = new Text(); //text to be updated with score
	Text cherryTurnsLeft = new Text(); // text to be updated with number of turns left

	GridPane gridPane = new GridPane();; //stores tiles array
	GridPane end = new GridPane(); //gameOver screen

	StackPane stackPane = new StackPane(); //StackPane to display end game screen
	Rectangle screen = new Rectangle();
    // Stage mainStage = new Stage();

/**
 * Constructs an {@code OmegaApp} object. This default (i.e., no argument)
 * constructor is executed in Step 2 of the JavaFX Application Life-Cycle.
 */
	public OmegaApp() {}

	/**
	 * Main entry point for OmegaApp application.
	 *
	 * {@inheritdoc}
	 *
	 * @param stage the stage for the OmegaApp
	 */
	@Override
	public void start(Stage mainStage) {

		addGridFormat();

        stackPane.getChildren().add(gridPane);
        Scene scene = new Scene(stackPane,1280,720);
		Helper handler = new Helper();
		scene.setOnKeyPressed(handler); //Handles key inputs
		mainStage.setMaxWidth(50 * (board.BOARD_SIZE));
		mainStage.setMaxHeight(50 * (board.BOARD_SIZE + 1));
		mainStage.setTitle("Pac-Man ");
		mainStage.setScene(scene);
		mainStage.setResizable(true);
		mainStage.show(); //Displays GUI
    } //start

	/**
	 * Sets up {@code gridPane} formatting.
	 */
	public void addGridFormat() {

        commandLine(getParameters().getRaw().toArray(new String[0]));
		tiles = new Tile[board.BOARD_SIZE][board.BOARD_SIZE];

		gridPane.setStyle("-fx-background-color: black;");
		gridPane.setHgap(10); //Sets horizontal gap between tiles to 10
		gridPane.setVgap(10);//Sets Vertical gap between tiles to 10

		Text title = new Text();
		title.setText("Pac-Man"); //Sets the text for the title
		title.setFont(Font.font("Helvetica", FontWeight.BOLD, 27));
		title.setFill(Color.BLUE);
		GridPane.setMargin(title, new Insets(0, 0, 0, 0));
		gridPane.add(title, 3, 0, 4, 1); //Sets title at coordinates (1,0) with 4 horizontal slots

		score.setText("Score: " + board.getScore());
		score.setFont(Font.font("Helvetica", FontWeight.BOLD, 16));
		score.setFill(Color.WHITE);
		GridPane.setMargin(score, new Insets(0, 0, 0, 0));
		gridPane.add(score, 0, 0, 2, 1);//Sets title at coordinates (5,0) with 2 horizontal slots

		cherryTurnsLeft.setText("Cherry Turns Left: " + board.getCherryTurns());
		cherryTurnsLeft.setFont(Font.font("Helvetica", FontWeight.BOLD, 12));
		cherryTurnsLeft.setFill(Color.RED);
		GridPane.setMargin(cherryTurnsLeft, new Insets(0, 0, 0, 0));
		gridPane.add(cherryTurnsLeft,7,0,4,1);

		for (int i = 0;i < board.BOARD_SIZE; i++) {
			colcount = 0;							// each character
			for (int j = 0; j < board.BOARD_SIZE; j++) {
				tiles[i][j] = new Tile(board.getGrid()[i][j]);
				gridPane.add(tiles[i][j].getImage(), j, i + 1);
				colcount++;
			}
			rowcount++;
		}

	} //addGridFormat

	/**
	 * Used in {@link playAgain}. Adds a new scene.
	 */
	public void play() {
        if (board.isGameOver()) {

            Stage another = new Stage();
            Scene scene = new Scene(stackPane, 1280, 720);
            Helper handler = new Helper();
            scene.setOnKeyPressed(handler); //Handles key inputs
            GameBoard newBoard = new GameBoard(board.BOARD_SIZE);
            another.setScene(scene);
        }

    } //play


	/**
	 * Inner class helping with taking in {@link userMove} and comparing
	 * it to keyboard input.
	 *
	 */
	private class Helper implements EventHandler<KeyEvent> {

		public void handle (KeyEvent e) {
			if (e.getCode().equals(KeyCode.RIGHT) && board.canMoveCheck(Direction.RIGHT)) {
				board.userMove(Direction.RIGHT);//Moves pacman right if valid userMove
				System.out.println("Moved Right");
			} else if (e.getCode().equals(KeyCode.LEFT) && board.canMoveCheck(Direction.LEFT)) {
				board.userMove(Direction.LEFT);//Moves pacman left if valid userMove
				System.out.println("Moved Left");
			} else if (e.getCode() == KeyCode.UP && board.canMoveCheck(Direction.UP)) {
				board.userMove(Direction.UP);//Moves pacman up if valid userMove
				System.out.println("Moved Up");
			} else if (e.getCode().equals(KeyCode.DOWN) && board.canMoveCheck(Direction.DOWN)) {
				board.userMove(Direction.DOWN);//Moves pacman down if valid userMove
				System.out.println("Moved Down");
			}
			rowcount = 0;
			for (int i = 0;i < board.BOARD_SIZE; i++) {
				colcount = 0;
				for (int j = 0; j < board.BOARD_SIZE; j++) {
					tiles[i][j].updateImageView(board.getGrid()[i][j]);
					colcount++;
				}
				rowcount++;
			}

			cherryTurnsLeft.setText("Cherry Turns Left: " + board.getCherryTurns());
            score.setText("Score: " + board.getScore());
            //updates score and cherry count
			gameOver();

		} //Helper


        /**
         * Sets up {@link gameOver} screen shown when user loses.
         */
		public void gameOver() {
			if (board.isGameOver()) {
                playAgain();
                Text endtext = new Text();
				endtext.setText("GAME OVER");
                DropShadow ds = new DropShadow();
                ds.setOffsetY(3.0f);
                ds.setColor(Color.color(0.4f, 0.4f, 0.4f));
                endtext.setEffect(ds);
                endtext.setCache(true);
                endtext.setX(10.0f);
                endtext.setY(270.0f);

				endtext.setFont(Font.font("Boulder", FontWeight.BOLD, 45));
                endtext.setFill(Color.RED);

                GridPane.setMargin(endtext, new Insets(250, 100, 220, 100));
				end.add(endtext, 1280, 720);
				screen.setWidth(700);
				screen.setHeight(700);
				screen.setOpacity(.72);
				screen.setFill(COLOR_GAME_OVER);
				stackPane.getChildren().addAll(screen,end);
                play();
            }
		}
	} //gameOver

	/**
	 * Method with modal application pop up to deal with new game or exit decision
	 * by user. Clears screen of {@link gameOver} message.
	 */
	public void playAgain() {
		Stage stage = new Stage();
		FlowPane playPane = new FlowPane();
		Text message = new Text("Game Over! To play again, select 'New Game' and close window.");
        playPane.getChildren().add(message);

        Button buttonYes = new Button("NewGame");
        buttonYes.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent arg0) {
                    stackPane.getChildren().remove(screen);
                    stackPane.getChildren().remove(end);
                }
            });

        Button buttonNo = new Button("Exit Game");
		buttonNo.setOnAction(e -> System.exit(0));

        playPane.setHgap(10);
        playPane.getChildren().addAll(buttonYes, buttonNo);

		Scene playScene = new Scene(playPane);
		stage.setScene(playScene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.setTitle("Continue Game? ");
		stage.sizeToScene();
		stage.show();

	} //playAgain




/**
 * Inner class containing the images to be used for {@link Character}
 * objects.
 *
 */
	private class Tile {

		private ImageView image;

		/**
		 * Creates the tile with its associated image depending on which char the
		 * {@code tileView} is.
		 *
		 * @param tileView
		 */
		public Tile(char tileView) {
         		if (tileView == '*') {
				image = new ImageView((new Image("file:resources/bigDot.png", 40,40,true,true)));
			} else if (tileView == ' ') {
				image = new ImageView((new Image("file:resources/smallDot.png", 40,40,true,true)));
			} else if (tileView == 'P') {
                image = new ImageView((new Image("file:resources/pacman.png", 40,40,true,true)));
			} else if (tileView == 'G' && board.getCherryTurns() == 0) {
				if (board.getGhosts()[0] != null && board.getGhosts()[0].getRow() == rowcount &&
                board.getGhosts()[0].getColumn() == colcount) {
                    image = new ImageView((new Image("file:resources/blinkyLeft.png", 30,30,true,true)));
				} else if (board.getGhosts()[1] != null && board.getGhosts()[1].getRow() == rowcount &&
                board.getGhosts()[1].getColumn() == colcount) {
				 	image = new ImageView((new Image("file:resources/inkyUp.png", 30,30,true,true)));
				} else if (board.getGhosts()[2] != null && board.getGhosts()[2].getRow() == rowcount &&
                board.getGhosts()[2].getColumn() == colcount) {
					image = new ImageView((new Image("file:resources/clydeUp.png", 30,30,true,true)));
				} else if (board.getGhosts()[3] != null && board.getGhosts()[3].getRow() == rowcount &&
                board.getGhosts()[3].getColumn() == colcount) {
					image = new ImageView((new Image("file:resources/pinkyLeft.png", 30,30,true,true)));
				}

                } else if (tileView == 'G' && board.getCherryTurns() > 0) {
                    image = new ImageView((new Image("file:resources/blueGhost.png", 40,40,true,true)));
                } else if (tileView == 'C') {
                    image = new ImageView((new Image("file:resources/cherry.png", 40,40,true,true)));
                } else if (tileView == 'D') {
                    image = new ImageView((new Image("file:resources/pacmanOver.png", 40,40,true,true)));
                }
        }

        /**
         * Getter method to get the {@code image} of the tile.
         * @return image the image for the tile
         */
        public ImageView getImage() {
            return image;
        }

        /**
         * Updates the tile image.
         * @param tileView
         */
        public void updateImageView(char tileView) {
            if (tileView == '*') {
                image.setImage(new Image("file:resources/bigDot.png", 40,40,true,true));
                    } else if (tileView == ' ') {
                image.setImage(new Image("file:resources/smallDot.png", 40,40,true,true));
            } else if (tileView == 'P') {
                image.setImage(new Image("file:resources/pacman.png", 40,40,true,true));
            } else if (tileView == 'G' && board.getCherryTurns() == 0) {
                if (board.getGhosts()[0] != null && board.getGhosts()[0].getRow() == rowcount &&
                board.getGhosts()[0].getColumn() == colcount) {
                    image.setImage(new Image("file:resources/blinkyLeft.png", 40,40,true,true));
                } else if (board.getGhosts()[1] != null && board.getGhosts()[1].getRow() == rowcount &&
                board.getGhosts()[1].getColumn() == colcount) {
                    image.setImage(new Image("file:resources/inkyUp.png", 40,40,true,true));
                } else if (board.getGhosts()[2] != null && board.getGhosts()[2].getRow() == rowcount &&
                board.getGhosts()[2].getColumn() == colcount) {
					image.setImage(new Image("file:resources/clydeUp.png", 40,40,true,true));
				} else if (board.getGhosts()[3] != null && board.getGhosts()[3].getRow() == rowcount &&
                board.getGhosts()[3].getColumn() == colcount) {
					image.setImage(new Image("file:resources/pinkyLeft.png", 40,40,true,true));
				}

			} else if (tileView == 'G' && board.getCherryTurns() > 0) {
				image.setImage(new Image("file:resources/blueGhost.png", 40,40,true,true));
			} else if (tileView == 'C') {
                image.setImage(new Image("file:resources/cherry.png", 40,40,true,true));
			} else if (tileView == 'D') {
                image.setImage(new Image("file:resources/pacmanOver.png", 40,40,true,true));
            }
            if (board.isGameOver() && tileView == ' ') {
                image.setImage(new Image("file:resources/bigDot.png", 40,40,true,true));
            }
            if (board.isGameOver() && tileView == 'D') {
                board.pacman.setView('P');
            }

        } //updateImageView

    }  // Tile

// The method used to process the command line arguments
	private void commandLine(String[] args) {
		String inputBoard = null;   // The filename for where to load the Board
		int boardSize = 0;          // The Size of the Board

		// Arguments must come in pairs
		if ((args.length % 2) != 0) {
			printUsage();
			System.exit(-1);
		}

		// Process all the arguments
		for (int i = 0; i < args.length; i += 2) {
			if (args[i].equals("-i")) {
				inputBoard = args[i + 1];
			} else if (args[i].equals("-o")) {
				outputBoard = args[i + 1];
			} else if (args[i].equals("-s")) {
				boardSize = Integer.parseInt(args[i + 1]);
			} else {   // Incorrect Argument
				printUsage();
				System.exit(-1);
			}
		}

		if (outputBoard == null) {
			outputBoard = "PacMan.outputBoard";
        }
		if(boardSize < 3)
			boardSize = 10;

		try {
			if(inputBoard != null) {
				board = new GameBoard(inputBoard);
            } else {
				board = new GameBoard(boardSize);
            }
        } catch (Exception e) {
            System.exit(1);
		}
	}


    private static void printUsage() {
        System.out.println("Check your input command.");
    }


} //OmegaApp
