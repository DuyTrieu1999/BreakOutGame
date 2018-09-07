package example;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class BrickType {
    private int life;
    private int point;
    private ImageView image;
    private PowerUp pwu;

    public static final String HIGH_BRICK = "brick4.gif";
    public static final String MEDIUM_BRICK = "brick1.gif";
    public static final String LOW_BRICK = "brick2.gif";

    public static final String MULTI_BALL_BRICK = "brick10.gif";
    public static final String LARGE_PADDLE_BRICK = "brick5.gif";
    public static final String BIG_BALL_BRICK = "brick2.gif";

    BrickType (int brickLife, int point, String imgGif, PowerUp pwu) {
        this.life = brickLife;
        this.point = point;
        var brickImage = new Image(this.getClass().getClassLoader().getResourceAsStream(imgGif));
        this.image = new ImageView(brickImage);
        if (pwu != null) {
            this.pwu = pwu;
        }
    }
    BrickType (int brickLife, int point, String imgGif) {
        this(brickLife, point, imgGif, null);
    }

    public int getLife(){return this.life;}
    public int getPoint(){return this.point;}
    public ImageView getView(){return this.image;}

    public void setLife(int life){this.life = life;}

    public static final BrickType HIGH = new BrickType(3,30,HIGH_BRICK);
    public static final BrickType MEDIUM = new BrickType(2,20,MEDIUM_BRICK);
    public static final BrickType LOW = new BrickType(1,10,LOW_BRICK);

    public static final BrickType MULTI_BRICK = new BrickType(1, 10, MULTI_BALL_BRICK, PowerUp.MULTI_BALL);
    public static final BrickType LARGE_BRICK = new BrickType(1, 10, LARGE_PADDLE_BRICK, PowerUp.LONG_PADDLE);
    public static final BrickType BIG_B_BRICK = new BrickType(1,10,BIG_BALL_BRICK, PowerUp.BIG_BALL);

    public static BrickType stringToBrickType(String s){
        if (s.equals("HIGH")){
            return HIGH;
        }
        else if (s.equals("MEDIUM")){
            return MEDIUM;
        }
        else if (s.equals("MULTI_BRICK")){
            return MULTI_BRICK;
        }
        else if (s.equals("LARGE_BRICK")){
            return LARGE_BRICK;
        }
        else if (s.equals("BIG_B_BRICK")){
            return BIG_B_BRICK;
        }
        else {
            return LOW;
        }
    }
    public Image getImg () {
        return this.image.getImage();
    }
    public String getPwuID () {
        return this.pwu.getPowerUpID();
    }
    public PowerUp getPwu () {
        return this.pwu;
    }
    public boolean pwuExist () {
        if (this.pwu!= null)
            return true;
        return false;
    }
}
