package example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * A basic example JavaFX program for the first lab.
 *
 * @author Robert C. Duvall
 */
public class LevelControl extends Application {
    /**
     * Initialize what will be displayed and how it will be updated.
     */
    @Override
    public void start (Stage stage) {
        // attach scene to the stage and display it
        Transition opening = new Transition();
        Transition transitionPage = new Transition();
        Transition winnerPage = new Transition();
        MainGame level1 = new MainGame("map1.txt");
        MainGame level2 = new MainGame("map2.txt");
        Scene scene0 = opening.init(400, 400, "Welcome to BreakOut Game, Press SPACE to start");
        Scene sceneBtwLevel = transitionPage.init(400, 400, "You passed level 1!!! Press SPACE to continue");
        Scene scene1 = level1.getGameScene();
        Scene scene2 = level2.getGameScene();
        Scene winnerScene = winnerPage.init(400, 400, "You won!!");
        opening.setNextSceneHandler(()->{stage.setScene(scene1);level1.run();});
        level1.setNextSceneHandler(() -> {stage.setScene(sceneBtwLevel);});
        transitionPage.setNextSceneHandler(()->{stage.setScene(scene2);level2.run();});
        level2.setNextSceneHandler(() -> {stage.setScene(winnerScene);});

        stage.setTitle("Breakout Game");
        stage.setScene(scene0);
        stage.show();
    }


    /**
     * Start the program.
     */
    public static void main (String[] args) {
        launch(args);
    }
}
