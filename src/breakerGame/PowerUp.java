package breakerGame;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

// This entire file, along with BrickType.java, and Brick.java is part of my masterpiece
// Duy Trieu (dvt5)

/**
 * PowerUp Class: Manage the power up in the game
 *
 * @author duytrieu
 */
public class PowerUp {
    public static final double PWU_SPEED = 70;
    public static final int PWU_DIAMETER = 10;
    public static final String MULTI_ID = "extraballpower.gif";
    public static final String LONG_PADDLE_ID = "sizepower.gif";
    public static final String BIG_BALL_ID = "pointspower.gif";
    public String powerUpID;
    private boolean isActive;
    private ImageView myPowerUpView;
    private Point2D myPowerUpVelocity;

    /**
     * Constructor of the PowerUp class
     *
     * @param powerUpGIF
     */
    PowerUp (String powerUpGIF) {
        this.powerUpID = powerUpGIF;
        var pwuImage = new Image(this.getClass().getClassLoader().getResourceAsStream(powerUpGIF));
        myPowerUpView = new ImageView(pwuImage);
        isActive = false;
        myPowerUpView.setFitWidth(PWU_DIAMETER);
        myPowerUpView.setFitHeight(PWU_DIAMETER);
        myPowerUpVelocity = new Point2D(0, PWU_SPEED);
    }
    // Control power up falling for paddle to "catch"
    public void move (double elapsedTime) {
        myPowerUpView.setX(myPowerUpView.getX() + myPowerUpVelocity.getX() * elapsedTime);
        myPowerUpView.setY(myPowerUpView.getY() + myPowerUpVelocity.getY() * elapsedTime);
    }
    // Check if paddle "catches" the power up
    public boolean hitPaddle (Paddle paddle, Ball ball) {
        ImageView paddleView = paddle.getPaddleView();
        ImageView powerUpView = this.getPowerUpView();
        if(powerUpView.getBoundsInParent().intersects(paddleView.getBoundsInParent())) {
            if (this.getPowerUpID().equals(MULTI_ID)) {
                System.out.println("multiball received");
                paddle.multiBall();
            }
            if (this.getPowerUpID().equals(LONG_PADDLE_ID)) {
                System.out.println("long paddle received");
                paddle.extendPaddle();
            }
            if (this.getPowerUpID().equals(BIG_BALL_ID)) {
                System.out.println("big ball received");
                paddle.bigBall(ball);
            }
            return true;
        }
        return false;
    }
    // Layout the 3 types of power ups
    public static final PowerUp MULTI_BALL = new PowerUp(MULTI_ID);
    public static final PowerUp LONG_PADDLE = new PowerUp(LONG_PADDLE_ID);
    public static final PowerUp BIG_BALL = new PowerUp(BIG_BALL_ID);

    public ImageView getPowerUpView () {
        return myPowerUpView;
    }
    public String getPowerUpID () { return this.powerUpID; }
}
