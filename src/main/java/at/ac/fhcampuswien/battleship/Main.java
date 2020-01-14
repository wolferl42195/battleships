package at.ac.fhcampuswien.battleship;


import at.ac.fhcampuswien.battleship.ship.Direction;
import at.ac.fhcampuswien.battleship.ship.ImageShip;
import at.ac.fhcampuswien.battleship.ship.Ship;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.File;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import static at.ac.fhcampuswien.battleship.BattleShipConstants.*;


public class Main extends Application {

    static Logger logger = LogManager.getLogger("Main");

    private Player player1 = new Player(true);
    private Player player2 = new Player(true);
    private double pressedX, pressedY;
    private int gameRound = 1;
    private boolean shipsComplete = false;

    private Button buttonSaveShipsLeft = new Button(TEXT_SAVE_SHIPS);
    private Button buttonSaveShipsRight = new Button(TEXT_SAVE_SHIPS);
    private Button newGame = new Button(TEXT_NEW_GAME);
    private Button exit = new Button(TEXT_EXIT_GAME);
    private Button reset = new Button(TEXT_RESTART);
    private Button seeShips1 = new Button(TEXT_SHOW_SHIPS);
    private Button seeShips2 = new Button(TEXT_SHOW_SHIPS);
    private Button cont = new Button(TEXT_PROCESS);

    private ImageView startMenu = new ImageView(FILE_START);
    private ImageView wonLeft = new ImageView(FILE_PLAYER1_WON);
    private ImageView wonRight = new ImageView(FILE_PLAYER2_WON);
    private ImageView maskLeftField = new ImageView(FILE_ISLAND1);
    private ImageView maskRightField = new ImageView(FILE_ISLAND2);

    private Rectangle indicate1 = new Rectangle(439, 481, 442, 7);
    private Rectangle indicate2 = new Rectangle(919, 481, 442, 7);


    private Media bomb = new Media(new File(SOUND_BOMB).toURI().toString());
    private MediaPlayer bombPlayer = new MediaPlayer(bomb);
    private Media miss = new Media(new File(SOUND_MISS).toURI().toString());


    private MediaPlayer missPlayer = new MediaPlayer(miss);
    private Media music = new Media(new File(SOUND_BACKGROUND_MUSIC).toURI().toString());
    private MediaPlayer musicPlayer = new MediaPlayer(music);
    private Media winner = new Media(new File(SOUND_WINNER).toURI().toString());
    private MediaPlayer winnerPlayer = new MediaPlayer(winner);

    private Image[] bships = {
            new Image(FILE_SHIP_SIZE2),
            new Image(FILE_SHIP_SIZE3),
            new Image(FILE_SHIP_SIZE4),
            new Image(FILE_SHIP_SIZE5)
    };

    private ImageShip[] imageShip1 = {
            new ImageShip(1520, 640, SHIP_LENGTH_2, bships[0]),
            new ImageShip(1520, 640, SHIP_LENGTH_2, bships[0]),
            new ImageShip(1520, 640, SHIP_LENGTH_2, bships[0]),
            new ImageShip(1520, 640, SHIP_LENGTH_2, bships[0]),
            new ImageShip(1520, 720, SHIP_LENGTH_3, bships[1]),
            new ImageShip(1520, 720, SHIP_LENGTH_3, bships[1]),
            new ImageShip(1520, 720, SHIP_LENGTH_3, bships[1]),
            new ImageShip(1520, 800, SHIP_LENGTH_4, bships[2]),
            new ImageShip(1520, 800, SHIP_LENGTH_4, bships[2]),
            new ImageShip(1520, 880, SHIP_LENGTH_5, bships[3])
    };

    private ImageShip[] imageShip0 = {
            new ImageShip(1800 - 1520 - 3 * 40, 640, SHIP_LENGTH_2, bships[0]),
            new ImageShip(1800 - 1520 - 3 * 40, 640, SHIP_LENGTH_2, bships[0]),
            new ImageShip(1800 - 1520 - 3 * 40, 640, SHIP_LENGTH_2, bships[0]),
            new ImageShip(1800 - 1520 - 3 * 40, 640, SHIP_LENGTH_2, bships[0]),
            new ImageShip(1800 - 1520 - 3 * 40, 720, SHIP_LENGTH_3, bships[1]),
            new ImageShip(1800 - 1520 - 3 * 40, 720, SHIP_LENGTH_3, bships[1]),
            new ImageShip(1800 - 1520 - 3 * 40, 720, SHIP_LENGTH_3, bships[1]),
            new ImageShip(1800 - 1520 - 3 * 40, 800, SHIP_LENGTH_4, bships[2]),
            new ImageShip(1800 - 1520 - 3 * 40, 800, SHIP_LENGTH_4, bships[2]),
            new ImageShip(1800 - 1520 - 3 * 40, 880, SHIP_LENGTH_5, bships[3])
    };


    private Pane battleShipContainer = new Pane();

    private void drawGUI() {
        musicPlayer.setCycleCount(500);
        musicPlayer.play();

        for (int i = 0; i < imageShip0.length; i++) {
            battleShipContainer.getChildren().add(imageShip0[i].getImageView());
            battleShipContainer.getChildren().add(imageShip1[i].getImageView());
        }

        battleShipContainer.addEventHandler(MouseEvent.ANY, new EventHandler<>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getEventType() == MouseEvent.MOUSE_PRESSED) {
                    pressedX = event.getSceneX();
                    pressedY = event.getSceneY();
                    attacks((int) Math.round(pressedX), (int) Math.round(pressedY));
                }
            }
        });


        buttonSaveShipsLeft.setLayoutX(1800 - 1520 - 3 * 40);
        buttonSaveShipsLeft.setLayoutY(500);
        buttonSaveShipsLeft.setPrefSize(120, 10);

        buttonSaveShipsLeft.setOnAction(new EventHandler<ActionEvent>() {
                                            @Override
                                            public void handle(ActionEvent event) {
                                                saveShips(imageShip0, player1, 440 + 40, 40 + 440 + 40 + 40, 440 + 440, 40 + 920);
                                                shipsComplete();
                                            }
                                        }
        );


        buttonSaveShipsRight.setLayoutX(1520);
        buttonSaveShipsRight.setLayoutY(500);
        buttonSaveShipsRight.setPrefSize(120, 10);
        buttonSaveShipsRight.setOnAction(new EventHandler<ActionEvent>() {
                                             @Override
                                             public void handle(ActionEvent event) {
                                                 saveShips(imageShip1, player2, 2 * 440 + 40 + 40, 40 + 440 + 40 + 40, 440 + 440 + 40 + 440, 920 + 40);
                                                 shipsComplete();
                                             }
                                         }
        );


        startMenu.setVisible(true);
        seeShips1.setLayoutX(1520);
        seeShips1.setLayoutY(550);
        seeShips1.setPrefSize(120, 10);
        seeShips1.setOnAction(new EventHandler<ActionEvent>() {
                                  @Override
                                  public void handle(ActionEvent event) {
                                      changeMask();
                                  }
                              }
        );

        seeShips2.setLayoutX(160);
        seeShips2.setLayoutY(550);
        seeShips2.setPrefSize(120, 10);
        seeShips2.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        changeMask();
                    }
                }
        );

        indicate1.setFill(Color.RED);
        indicate2.setFill(Color.RED);

        battleShipContainer.getChildren().add(seeShips1);
        battleShipContainer.getChildren().add(seeShips2);
        battleShipContainer.getChildren().addAll(buttonSaveShipsLeft, buttonSaveShipsRight, maskLeftField, maskRightField,
                startMenu, indicate1, indicate2);

        reset.setVisible(false);
        maskLeftField.setVisible(false);
        maskRightField.setVisible(false);
        seeShips1.setVisible(false);
        seeShips2.setVisible(false);
        indicate1.setVisible(false);
        indicate2.setVisible(false);
        changeMask();
    }

    private void activateMask() {
        maskLeftField.setVisible(true);
        maskRightField.setVisible(true);
    }

    private void deactivateMask() {
        maskLeftField.setVisible(false);
        maskRightField.setVisible(false);
    }

    private void changeMask() {
        if (gameRound % 2 == 1) {
            maskLeftField.setVisible(false);
            maskRightField.setVisible(true);
        } else if (gameRound % 2 == 0) {
            maskLeftField.setVisible(true);
            maskRightField.setVisible(false);
        }
    }


    @Override
    public void start(Stage primaryStage) {
        BackgroundImage background = new BackgroundImage(new Image(FILE_BACKGROUND, 1800, 1000,
                true, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        maskLeftField.setX(439);
        maskLeftField.setY(39 + 440 + 40);
        maskRightField.setX(439 + 440 + 40);
        maskRightField.setY(39 + 440 + 40);

        battleShipContainer.setBackground(new Background(background));
        drawGUI();

        reset.setLayoutX(440);
        reset.setLayoutY(10);
        reset.setPrefHeight(10);

        reset.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                reset();
                Scene scenel = new Scene(battleShipContainer, 1800, 1000);
                primaryStage.setScene(scenel);
                primaryStage.show();
            }
        });
        battleShipContainer.getChildren().add(reset);
        newGame.setLayoutX(700);
        newGame.setLayoutY(300);
        newGame.setMinSize(400, 150);
        Font font = new Font(30);
        newGame.setFont(font);
        newGame.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent event) {
                                    reset();
                                    Scene scenel = new Scene(battleShipContainer, 1800, 1000);
                                    primaryStage.setScene(scenel);
                                    primaryStage.show();

                                }
                            }
        );

        battleShipContainer.getChildren().add(newGame);

        exit.setLayoutX(700);
        exit.setLayoutY(500);
        exit.setMinSize(400, 150);
        exit.setFont(font);
        exit.setOnAction(new EventHandler<ActionEvent>() {
                             @Override
                             public void handle(ActionEvent event) {
                                 System.exit(0);
                             }
                         }
        );


        battleShipContainer.getChildren().add(exit);
        cont.setOnAction(
                new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        reset();
                        reset.setVisible(false);
                        battleShipContainer.getChildren().add(newGame);
                        battleShipContainer.getChildren().add(exit);
                        startMenu.setVisible(true);
                        newGame.setVisible(true);
                        exit.setVisible(true);
                        Scene scenel = new Scene(battleShipContainer, 1800, 1000);
                        primaryStage.setScene(scenel);
                        primaryStage.show();
                    }
                }
        );

        Scene scene = new Scene(battleShipContainer, 1800, 1000);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    /*Wir berechnen x und y relativ zum jeweiligen spielfeld und kriegen eine zahl zwischen 0 und 9 raus.*/
    private int[] calculateXY(int imageshipx, int imageshipy, int p1x, int p1y, int p2x, int p2y) {
        int result[] = new int[2];

        //Checkt ob die Koordinaten vom Schiff im richtigen Feld liegen
        if (imageshipx >= p1x && imageshipx <= p2x && imageshipy >= p1y && imageshipy <= p2y) {
            int vectorx, vectory;
            //berechnet Relation zum Spielfeld
            vectorx = imageshipx - p1x;
            vectory = imageshipy - p1y;
            //Damit es eine Zahl zwischen 0 und 9 ist (denke ich!!)
            result[0] = vectorx / 40;
            result[1] = vectory / 40;
            return result;
        }
        return null;
    }


    private void saveShips(ImageShip imageShip[], Player player, int p1x, int p1y, int p2x, int p2y) {
        for (ImageShip imageship : imageShip) {
            if (!imageship.isDisable()) {
                int a[] = calculateXY(imageship.getX(), imageship.getY(), p1x, p1y, p2x, p2y);

                if (a != null) {
                    if (player.setShip(new Position(a[0], a[1]), imageship.getLength(), imageship.getDirection(), imageship.getDiffVectorX(), imageship.getDiffVectorY())) {
                        imageship.lock();

                    } else {
                        imageship.changePosition(0, 0);
                        imageship.rotateTo(Direction.RIGHT);
                    }
                } else {
                    imageship.changePosition(0, 0);
                    imageship.rotateTo(Direction.RIGHT);
                }
            }
        }
        if (player.isFleetComplete()) {
            gameRound++;
            if (player == player1) {
                changeMask();
                buttonSaveShipsLeft.setVisible(false);
            } else {
                buttonSaveShipsRight.setVisible(false);
                changeMask();
                seeShips1.setVisible(true);
                seeShips2.setVisible(true);
                indicate1.setVisible(true);
            }
            if (player1.isFleetComplete() && player2.isFleetComplete()) {
                activateMask();
            }
        }
    }

    private void attacks(int x, int y) {
        int a[];
        if (!(player1.checkGameOver() || player2.checkGameOver())) {
            if (shipsComplete) {
                if (gameRound % 2 == 1) {
                    a = calculateXY(x, y, 440 + 40, 40 + 40, 440 + 440, 440 + 40);
                    if (a != null) {
                        if (player1.attackPossible(new Position(a[0], a[1]))) {
                            if (player2.attack(new Position(a[0], a[1]))) {
                                drawAttack(a[0], a[1], x, y, player2);
                                player1.saveAttack(a[0], a[1]);
                                activateMask();
                                bombPlayer.stop();
                                bombPlayer.play();
                            } else {
                                drawMiss(x, y);
                                player1.saveAttack(a[0], a[1]);
                                activateMask();
                                indicate1.setVisible(false);
                                indicate2.setVisible(true);
                                missPlayer.stop();
                                missPlayer.play();
                            }
                        }
                    }
                    if (player2.checkGameOver()) {
                        //System.out.println("Spieler 1 hat gewonnen");
                        deactivateMask();
                        seeShips1.setVisible(false);
                        seeShips2.setVisible(false);
                        reset.setVisible(false);
                        battleShipContainer.getChildren().add(wonLeft);
                        wonLeft.setX(50);
                        wonLeft.setY(520);
                        winnerPlayer.stop();
                        winnerPlayer.play();
                        battleShipContainer.getChildren().add(cont);
                        cont.setLayoutX(160);
                        cont.setLayoutY(850);
                        cont.setVisible(true);
                    }

                } else {
                    a = calculateXY(x, y, 440 + 40 + 10 * 40 + 2 * 40, 40 + 40, 440 + 440 + 440 + 40, 440 + 40);
                    if (a != null) {
                        if (player2.attackPossible(new Position(a[0], a[1]))) {
                            if (player1.attack(new Position(a[0], a[1]))) {
                                drawAttack(a[0], a[1], x, y, player1);
                                player2.saveAttack(a[0], a[1]);
                                activateMask();
                                bombPlayer.stop();
                                bombPlayer.play();
                            } else {
                                drawMiss(x, y);
                                player2.saveAttack(a[0], a[1]);
                                activateMask();
                                indicate1.setVisible(true);
                                indicate2.setVisible(false);
                                missPlayer.stop();
                                missPlayer.play();
                            }
                        }
                    }
                    if (player1.checkGameOver()) {
                        //System.out.println("Spieler 2 hat gewonnen");
                        deactivateMask();
                        seeShips1.setVisible(false);
                        seeShips2.setVisible(false);
                        reset.setVisible(false);
                        battleShipContainer.getChildren().add(wonRight);
                        wonRight.setX(1450);
                        wonRight.setY(520);
                        winnerPlayer.stop();
                        winnerPlayer.play();
                        battleShipContainer.getChildren().add(cont);
                        cont.setLayoutX(1520);
                        cont.setLayoutY(850);
                        cont.setVisible(true);
                    }
                }
            }
        }
    }

    /*Wasserzeichen, gerundet auf die richtige Stelle setzen*/
    private void drawMiss(double x, double y) {
        int diffx = (int) x % 40;
        x -= diffx;
        int diffy = (int) y % 40;
        y -= diffy;
        ImageView miss = new ImageView(FILE_WATERHITMARKER);
        miss.setX(x);
        miss.setY(y);
        battleShipContainer.getChildren().add(miss);
        gameRound++;
    }

    /*Feuerzeichen, gerundet auf die richtige Stelle. Wenn Schiff zerstört, richtiges destroyed Schiff setzen*/
    private void drawAttack(int xx, int yy, double xreal, double yreal, Player player) {
        ImageShip imageShipl;

        int diffx = (int) xreal % 40;
        xreal -= diffx;

        int diffy = (int) yreal % 40;
        yreal -= diffy;

        ImageView hit = new ImageView(FILE_HIT);
        hit.setX(xreal);
        hit.setY(yreal);
        battleShipContainer.getChildren().addAll(hit);


        Image image = new Image(FILE_SHIP_SIZE2_DESTROYED);
        /*Objekt ship wird entweder null oder ein Schiff zugewiesen (Siehe Klasse Ship, Methode isDestroyed). Wenn
        das Schiff zerstört ist, wird im switch case gefragt welche Länge und dementsprechen setzen wir das Schiff*/
        Ship ship = player.isDestroyed(new Position(xx, yy));

        if (ship != null) {
            switch (ship.getLength()) {
                case 0:
                    break;
                case SHIP_LENGTH_2:
                    image = new Image(FILE_SHIP_SIZE2_DESTROYED);
                    break;
                case SHIP_LENGTH_3:
                    image = new Image(FILE_SHIP_SIZE3_DESTROYED);
                    break;
                case SHIP_LENGTH_4:
                    image = new Image(FILE_SHIP_SIZE4_DESTROYED);
                    break;
                case SHIP_LENGTH_5:
                    image = new Image(FILE_SHIP_SIZE5_DESTROYED);
                    break;
            }

            int x, y;
            //*40 um auf unsere Spielfeldkoordinaten zu kommen
            Position position = ship.getPosition();
            x = position.getX() * 40;
            y = position.getY() * 40;
            //Wird immer in das gegenüberliegende Feld gesetzt, deshalb stehen hier die Koordinaten vom Spieler 2
            if (player == player1) {
                x += 2 * 440 + 40 + 40;
                y += 2 * 40;

            } else {
                x += (440 + 40);
                y += (2 * 40);
            }

            /*Schiff kreiert und zum Battleshipcontainer dazugehaut und lock==true, um es nicht bewegbar zu machen*/
            imageShipl = new ImageShip(x - ship.getDiffVectorX(), y - ship.getDiffVectorY(), ship.getLength(), image);
            battleShipContainer.getChildren().add(imageShipl.getImageView());
            imageShipl.rotateTo(ship.getDirection());
            imageShipl.lock();
        }
    }

    private void shipsComplete() {
        if (player1.isFleetComplete() && player2.isFleetComplete()) {
            this.shipsComplete = true;
        }
    }

    private void reset() {
        for (int i = 0; i < imageShip0.length; i++) {
            imageShip1[i].rotateTo(Direction.RIGHT);
            imageShip0[i].rotateTo(Direction.RIGHT);
            imageShip0[i].reset();
            imageShip1[i].reset();
        }
        player1.removeAll();
        player2.removeAll();
        player1.reset();
        player2.reset();
        gameRound = 1;
        shipsComplete = false;
        buttonSaveShipsRight.setVisible(true);
        buttonSaveShipsLeft.setVisible(true);
        battleShipContainer = new Pane();
        BackgroundImage background = new BackgroundImage(new Image(FILE_BACKGROUND, 1800, 1000,
                true, true), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        battleShipContainer.setBackground(new Background(background));
        drawGUI();
        battleShipContainer.getChildren().add(reset);
        reset.setVisible(true);
        startMenu.setVisible(false);
    }


    public static void main(String[] args) {
        logger.info("Start");
        launch(args);
    }
}