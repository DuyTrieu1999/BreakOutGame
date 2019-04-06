package breakerGame;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * Simple bouncer based on an image that moves and bounces.
 *
 * @author Robert C. Duvall
 */
public class Ball {
    public static final int BOUNCER_SPEED = 150;
    public static final int BALL_DIAMETER = 15;

    private ImageView myView;
    private Point2D myVelocity;


    /**
     * Create a bouncer from a given image with random attributes.
     */
    public Ball (Image image, double dx, double dy) {
        myView = new ImageView(image);
        // make sure it stays a circle
        myView.setFitWidth(BALL_DIAMETER);
        myView.setFitHeight(BALL_DIAMETER);
        // make sure it stays within the bounds
        myView.setX(dx);
        myView.setY(dy);
        // turn speed into velocity that can be updated on bounces
        myVelocity = new Point2D(100, 100);
    }

    /**
     * Move by taking one step based on its velocity.
     *
     * Note, elapsedTime is used to ensure consistent speed across different machines.
     */
    public void move (double elapsedTime) {
        myView.setX(myView.getX() + myVelocity.getX() * elapsedTime);
        myView.setY(myView.getY() + myVelocity.getY() * elapsedTime);
    }

    /**
     * Bounce off the walls represented by the edges of the screen.
     */
    public void bounce (double screenWidth, double screenHeight) {
        // collide all bouncers against the walls
        if (myView.getX() < 0 || myView.getX() > screenWidth - myView.getBoundsInLocal().getWidth()) {
            myVelocity = new Point2D(-myVelocity.getX(), myVelocity.getY());
        }
        if (myView.getY() < 0) {
            myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
        }
    }

    /**
     * Bounce off the paddle that is controlled by the player.
     */
    public void bouncePaddle (Paddle paddle) {
        if (paddle.hitMover(this)) {
            myVelocity = new Point2D(myVelocity.getX(), -myVelocity.getY());
        }
    }

    /**
     * Returns internal view of bouncer to interact with other JavaFX methods.
     */
    public ImageView getView () {
        return myView;
    }

    public Point2D getVelocity () {
        return myVelocity;
    }

    public void setDX (double dX) {
        myVelocity = new Point2D(dX, myVelocity.getY());
    }

    public void setDY (double dY) {
        myVelocity = new Point2D(myVelocity.getX(), dY);
    }
}
