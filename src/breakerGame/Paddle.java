package breakerGame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Paddle {
    public static final int MOVER_SIZE_X = 50;
    public static final int MOVER_SIZE_Y = 5;

    private ImageView paddleView;

    private int lives = 3;
    private int scores;
    private boolean longPaddle = false;
    private boolean extraBall = false;
    private boolean bigBall = false;


    public Paddle (Image img, int screenWidth, int screenHeight) {
        paddleView = new ImageView(img);
        paddleView.setFitHeight(MOVER_SIZE_Y);
        paddleView.setFitWidth(MOVER_SIZE_X);
        paddleView.setX(screenWidth/2);
        paddleView.setY(screenHeight/2 + 180);
    }

    public ImageView getPaddleView () {
        return paddleView;
    }

    // Handling collision with ball
    public boolean hitMover (Ball ball) {
        if (ball.getView().getX() <= paddleView.getX() + MOVER_SIZE_X) {
            if (ball.getView().getX() >= paddleView.getX()) {
                if ((ball.getView().getY() + ball.getView().getBoundsInLocal().getWidth()/2 + MOVER_SIZE_Y) >= (paddleView.getY())) {
                    if ((ball.getView().getY() + ball.getView().getBoundsInLocal().getWidth()/2) <= (paddleView.getY() + MOVER_SIZE_Y)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getScore () {
        return scores;
    }

    public int getLives () {
        return lives;
    }

    public void setScore(int score){
        this.scores = (score>=0)? score:0;
    }

    public void setLives(int life){
        this.lives = (life >=0)? life:0;
    }

    //adding powerups
    public void multiBall () {
        extraBall = true;

    }
    public void extendPaddle () {
        longPaddle = true;
        this.getPaddleView().setFitWidth(MOVER_SIZE_X * 2);
    }
    public void bigBall (Ball ball) {
        bigBall = true;
        ImageView ballView = ball.getView();
        ballView.setFitWidth(ballView.getFitWidth() * 1.5);
        ballView.setFitHeight(ballView.getFitHeight() * 1.5);
    }
}
