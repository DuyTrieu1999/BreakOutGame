package breakerGame;

import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

// This entire file, along with BrickType.java, and PowerUp.java is part of my masterpiece
// Duy Trieu (dvt5)

/**
 * PowerUp Class: Manage the brick in the game
 *
 * @author duytrieu
 */
public class Brick {
    public static final int BRICK_SIZE_X = 40;
    public static final int BRICK_SIZE_Y = 20;
    private BrickType brickType;
    private ImageView brickView;

    /**
     * Constructor of the Brick class
     *
     * @param xPos
     * @param yPos
     * @param brickType
     */
    public Brick (double xPos, double yPos, BrickType brickType) {
        brickView = new ImageView(brickType.getImg());
        brickView.setFitHeight(BRICK_SIZE_Y);
        brickView.setFitWidth(BRICK_SIZE_X);
        this.brickType = brickType;
        this.getBrickView().setX(xPos);
        this.getBrickView().setY(yPos);
    }
    public void setBrickType(BrickType type){this.brickType = type;}
    public BrickType getBrickType() {return this.brickType;}
    // check collision between brick and ball
    public boolean hitBrick (Ball ball) {
        ImageView ballView = ball.getView();
        ImageView brickView = this.getBrickView();
        Point2D ballVel = ball.getVelocity();
        double ballCenterX = ballView.getX() + ballView.getFitWidth() /2;
        double ballCenterY = ballView.getY() + ballView.getFitHeight() /2;
        if (brickView.getBoundsInParent().intersects(ballView.getBoundsInParent())) {
            if ((ballCenterX >= brickView.getX() - ballView.getBoundsInLocal().getWidth()/2) &&
                    (ballCenterX <= brickView.getX() + brickView.getFitWidth() + ballView.getBoundsInLocal().getWidth()/2)) {
                ball.setDY(-ballVel.getY());
            }

            else if ((ballCenterY >= brickView.getY() - ballView.getBoundsInLocal().getWidth()/2) &&
                    (ballCenterY <= brickView.getY() + brickView.getFitHeight() + ballView.getBoundsInLocal().getWidth()/2)) {
                ball.setDX(-ballVel.getX());
            }
            return true;
        }
        else {
            return false;
        }
    }
    // manages how bricks would react when hit
    public void brickDown () {
        if (this.brickType == BrickType.HIGH) {
            this.setBrickType(BrickType.MEDIUM);
        }
        else if (this.brickType == BrickType.MEDIUM){
            this.setBrickType(BrickType.LOW);
        }
        else if (this.brickType == BrickType.LOW){
            brickView.setVisible(false);
            brickType.setLife(0);
        }
        else if (this.brickType == BrickType.MULTI_BRICK) {
            brickView.setVisible(false);
            brickType.setLife(0);
        }
        else if (this.brickType == BrickType.LARGE_BRICK) {
            brickView.setVisible(false);
            brickType.setLife(0);
        }
        else if (this.brickType == BrickType.BIG_B_BRICK) {
            brickView.setVisible(false);
            brickType.setLife(0);
        }
        brickView.setImage(brickType.getView().getImage());
    }
    public ImageView getBrickView () {
        return brickView;
    }
    public boolean isDestroyed () {
        return (this.brickType.getLife() == 0);
    }
}
