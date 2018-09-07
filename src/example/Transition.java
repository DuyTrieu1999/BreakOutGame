package example;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class Transition {
    private Runnable nextSceneHandler;
    public void setNextSceneHandler(Runnable handler) {
        nextSceneHandler = handler ;
    }
    public Scene init(double width, double height, String text) {
        Group group = new Group();
        Scene scene = new Scene(group,width,height,Color.WHITE);

        Text welcomeText = new Text(width/9,height/2,text);
        group.getChildren().add(welcomeText);

        scene.setOnKeyPressed(e -> {
                    if (nextSceneHandler != null) {
                        if (e.getCode() == KeyCode.SPACE) {
                            nextSceneHandler.run();
                        }
                    }
                }
        );
        return scene;
    }

}
