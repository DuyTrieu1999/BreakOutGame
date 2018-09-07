package example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.util.Duration;
import javafx.scene.text.Text;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class MainGame {
    public static final int SIZE = 400;
    public static final int SIZE_Y = 450;
    public static final int FRAMES_PER_SECOND = 60;
    public static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    public static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
    public static final Paint BACKGROUND = Color.AZURE;
    public static final String BOUNCER_IMAGE = "ball.gif";
    public static final String PADDLE_IMAGE = "paddle.gif";
    public static final int MOVER_SPEED = 15;

    // some things we need to remember during our game
    private Timeline animation;
    private Scene myScene;
    private Group myGroup;
    private Ball myBouncer;
    private ArrayList<Brick> myBricks = new ArrayList<>();
    private ArrayList<Brick> bricksToBeRemoved = new ArrayList<>();
    private ArrayList<PowerUp> myPowerUp = new ArrayList<>();
    private ArrayList<PowerUp> powerUpToBeRemoved = new ArrayList<>();
    private Paddle myMover;
    private HashMap<Brick, BrickType> brickTypeMap = new HashMap<>(); //map of brick to its original type
    private Text scoreText = new Text("Score: 0");
    private Text livesText = new Text();
    private HBox hbox = new HBox(3,scoreText,livesText);
    private String currentMap;
    private Runnable nextSceneHandler ;

    public void setNextSceneHandler (Runnable handler) {
        nextSceneHandler = handler;
    }

    public MainGame (String mapName) {
        currentMap = mapName;
        myGroup = new Group();
        myScene = new Scene(myGroup, SIZE, SIZE_Y, BACKGROUND);

        var paddleImage = new Image(this.getClass().getClassLoader().getResourceAsStream(PADDLE_IMAGE));
        myMover = new Paddle(paddleImage, SIZE, SIZE);
        var ballImage = new Image(this.getClass().getClassLoader().getResourceAsStream(BOUNCER_IMAGE));
        myBouncer = new Ball(ballImage, myMover.getPaddleView().getX()+myMover.getPaddleView().getFitWidth()/2,
                myMover.getPaddleView().getY()-Ball.BALL_DIAMETER);

        hbox.setLayoutX(0);
        hbox.setLayoutY(SIZE);
        livesText.setText("Life Left: "+ Integer.toString(myMover.getLives()));
        myGroup.getChildren().add(myMover.getPaddleView());
        myGroup.getChildren().add(myBouncer.getView());
        myGroup.getChildren().add(hbox);
        myGroup.getChildren().add(new Line(0,400,400,400));
        // Set brick map
        createMap(currentMap);
        for (Brick br: myBricks) {
            if (!myGroup.getChildren().contains(br.getBrickView())) {
                myGroup.getChildren().add(br.getBrickView());
            }
        }
    }

    public void run () {
        var frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY), e -> step(SECOND_DELAY));
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step (double elapsedTime) {
        // update attributes
        myBouncer.move(elapsedTime);
        myBouncer.bounce(myScene.getWidth(), myScene.getHeight());
        if (myBouncer.getView().getY() > SIZE - myBouncer.getView().getBoundsInLocal().getHeight()) {
            resetBall();
            myMover.setLives(myMover.getLives() - 1);
            livesText.setText("Life Left: "+ Integer.toString(myMover.getLives()));
        }
        //myMover.hitBall(myBouncers);
        myBouncer.bouncePaddle(myMover);
        for (Brick br : myBricks) {
            if (br.hitBrick(myBouncer)) {
                spawnPowerUp(br);
                myMover.setScore(myMover.getScore() + br.getBrickType().getPoint());
                br.brickDown();
                if (br.isDestroyed()) {
                    bricksToBeRemoved.add(br);
                }
            }
        }
        for (PowerUp pwu: myPowerUp) {
            //pwu.getPowerUpView().setVisible(true);
            pwu.move(elapsedTime);
            if (pwu.hitPaddle(myMover, myBouncer) ||
                    (pwu.getPowerUpView().getY() > SIZE - pwu.getPowerUpView().getBoundsInLocal().getHeight())) {
                pwu.getPowerUpView().setVisible(false);
                powerUpToBeRemoved.add(pwu);
            }
        }
        //System.out.println(powerUpToBeRemoved.size());
        scoreText.setText("Score: " + myMover.getScore());
        myBricks.removeAll(bricksToBeRemoved);
        myPowerUp.removeAll(powerUpToBeRemoved);
        for (Brick brick: bricksToBeRemoved){
            myGroup.getChildren().remove(brick.getBrickView());
        }
        for (PowerUp pwu: powerUpToBeRemoved){
            myGroup.getChildren().remove(pwu.getPowerUpView());
        }
        bricksToBeRemoved.clear();
        powerUpToBeRemoved.clear();
        myScene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));

        if (myBricks.size() == 0){
            nextSceneHandler.run();
            animation.stop();
        }
    }

    public void spawnPowerUp (Brick br) {
        if (br.getBrickType().pwuExist()) {
            PowerUp pwu = new PowerUp(br.getBrickType().getPwuID());
            pwu.getPowerUpView().setX(br.getBrickView().getX());
            pwu.getPowerUpView().setY(br.getBrickView().getY());
            myPowerUp.add(pwu);
            myGroup.getChildren().add(pwu.getPowerUpView());
        }
    }

    public void createMap (String myMap) {
        String line = null;
        try {
            // FileReader reads text files in the default encoding.
            FileReader fileReader = new FileReader(myMap);
            BufferedReader bufferedReader =
                    new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(myMap)));
            while ((line = bufferedReader.readLine()) != null) {
                String[] splt = line.split(",");
                double xPos = Double.parseDouble(splt[0]);
                double yPos = Double.parseDouble(splt[1]);
                BrickType type = BrickType.stringToBrickType(splt[2]);
                Brick brick = new Brick(xPos, yPos, type);
                myBricks.add(brick);
                brickTypeMap.put(brick, type);
            }
            bufferedReader.close();
        }
        catch (FileNotFoundException ex) {
            System.out.println("Unable to open file '" + myMap + "'");
        }
        catch (IOException ex) {
            System.out.println("Error reading file '" + myMap + "'");
        }
    }

    private void handleKeyInput (KeyCode code) {
        if (code == KeyCode.RIGHT) {
            myMover.getPaddleView().setX(myMover.getPaddleView().getX() + MOVER_SPEED);
        }
        else if (code == KeyCode.LEFT) {
            myMover.getPaddleView().setX(myMover.getPaddleView().getX() - MOVER_SPEED);
        }
        if (myMover.getPaddleView().getX() >= (myScene.getWidth()-myMover.getPaddleView().getBoundsInLocal().getWidth())) {
            myMover.getPaddleView().setX(myScene.getWidth()-myMover.getPaddleView().getBoundsInLocal().getWidth());
        }
        if (myMover.getPaddleView().getX() <= 0) {
            myMover.getPaddleView().setX(0);
        }
        //cheat code
        if (code == KeyCode.DIGIT1) {
            myMover.multiBall();
        }
        if (code == KeyCode.DIGIT2) {
            myMover.extendPaddle();
        }
        if (code == KeyCode.DIGIT3) {
            myMover.bigBall(myBouncer);
        }
    }

    private void resetBall () {
        if (isDead()) {
            System.out.println("yOu LOsE");
        }
        else{
            myBouncer.getView().setX(myMover.getPaddleView().getX()+myMover.getPaddleView().getFitWidth()/2);
            myBouncer.getView().setY(myMover.getPaddleView().getY()-Ball.BALL_DIAMETER);
            myBouncer.setDY(myBouncer.getVelocity().getY() *-1);
        }
    }

    private boolean isDead () {
        return (myMover.getLives() <= 1);
    }

    public Scene getGameScene() {return myScene;}
    public Group getGroup() {return myGroup;}

}
