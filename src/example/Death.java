package example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Death {
    private Runnable nextSceneHandler;
    public void setNextSceneHandler(Runnable handler) {
        nextSceneHandler = handler ;
    }
    public Scene init(double width, double height) {
        Group group = new Group();
        Scene scene = new Scene(group,width,height,Color.WHITE);

        Text welcomeText = new Text(width/9,height/2,"You Die");
        group.getChildren().add(welcomeText);

        return scene;
    }


}
