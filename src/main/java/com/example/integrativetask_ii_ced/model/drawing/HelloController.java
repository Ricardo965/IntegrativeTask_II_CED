package com.example.integrativetask_ii_ced.model.drawing;

import com.example.integrativetask_ii_ced.model.entities.*;
import com.example.integrativetask_ii_ced.model.entities.mob.Boss;
import com.example.integrativetask_ii_ced.model.entities.mob.MobilePump;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.Bullet;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.PressurePlate;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.concurrent.CopyOnWriteArrayList;

public class HelloController implements Initializable, Runnable{

    private final Image image = new Image("file:src/main/resources/images/background.jpg");

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    public static Player character;

    private static boolean matrixBased;

    public static GameMap gameMap = new GameMap(1200,720, 80,3);
    public static Boss finalBoss;
    public static CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<>();
    private final Cursor customCursor = new ImageCursor(new Image("file:src/main/resources/images/Cursor/nt_normal.png"));
    public static CopyOnWriteArrayList<PressurePlate> pressurePlates = new CopyOnWriteArrayList<>();

    public static CopyOnWriteArrayList<MobilePump> mobilePumps = new CopyOnWriteArrayList<>();


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {

        setMatrixBased(false);
        gameMap.initialFillingOfMapWithNodesAndCoordinates();
        gameMap.creatingNotNavigableObstacles();
        gameMap.establishGraphMapRepresentationForMinimumPaths();


        for (int i = 0; i < gameMap.getMapGuide().get(0).size(); i++) {
            if (gameMap.getMapGuide().get(0).get(i).isNavigable()){
                character = new Player(gameMap.getMapGuide().get(0).get(i).getPosition().getX(),gameMap.getMapGuide().get(0).get(i).getPosition().getY(), 60,60,10000);
                break;
            }
        }

        canvas.setCursor(customCursor);
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(character::pressKey);
        canvas.setOnKeyReleased(character::releasedKey);
        finalBoss = new Boss(canvas.getWidth()/2, canvas.getHeight()/2, 240, 240, 100);
        canvas.setOnMouseMoved(this::onMouseMoved);



        new Thread(character).start();
        new Thread(finalBoss).start();
        new Thread(this).start();


        draw();
    }

    @Override
    public void run() {
        while (true) {
            for (int i=0 ; i<bullets.size() ; i++){
                if ( bullets.get(i).outside(canvas.getHeight(), canvas.getWidth()) || bullets.get(i).giveDamage(character) ){
                    bullets.remove(bullets.get(i));
                }
            }

            for (int i = 0; i < mobilePumps.size(); i++) {
                if ( mobilePumps.get(i).outside(canvas.getHeight(), canvas.getWidth()) || mobilePumps.get(i).giveDamage(character) ) {
                    mobilePumps.remove(mobilePumps.get(i));
                }
            }

            for(int i=0 ; i<pressurePlates.size() ; i++){
                pressurePlates.get(i).isPressed(character);
            }

        }
    }



    public void draw(){
        Thread h = new Thread(() -> {
            while(true){
                Platform.runLater(() -> {
                    gc.setFill(Color.WHITE);
                    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());



                    for (int i=0 ; i<bullets.size() ; i++) {
                        bullets.get(i).draw(gc);
                    }
                    for(int i=0 ; i<pressurePlates.size() ; i++){
                        pressurePlates.get(i).draw(gc);
                    }


                    for (int i = 0; i < gameMap.getMapGuide().size() ; i++) {
                        for (int j = 0; j < gameMap.getMapGuide().get(i).size(); j++) {
                            gameMap.getMapGuide().get(i).get(j).draw(gc);
                        }
                    }


                    finalBoss.draw(gc);
                    for (int i=0; i< mobilePumps.size() ; i++) {
                        mobilePumps.get(i).draw(gc);
                    }
                    if ( character.getLife() > 0 ){
                        character.draw(gc);
                    }
                });

                character.movement();

                try {
                    Thread.sleep(16);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        h.start();
    }




    private void onMouseMoved(MouseEvent e) {
        double relativePosition = e.getX()-character.getPosition().getX();
        character.setFacingRight(
                relativePosition > 0
        );
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
    }

    public Image getImage() {
        return image;
    }

    public GraphicsContext getGc() {
        return gc;
    }

    public void setGc(GraphicsContext gc) {
        this.gc = gc;
    }

    public static GameMap getGameMap() {
        return gameMap;
    }

    public static void setGameMap(GameMap gameMap) {
        HelloController.gameMap = gameMap;
    }

    public static Boss getFinalBoss() {
        return finalBoss;
    }

    public static void setFinalBoss(Boss finalBoss) {
        HelloController.finalBoss = finalBoss;
    }

    public static CopyOnWriteArrayList<Bullet> getBullets() {
        return bullets;
    }

    public static void setBullets(CopyOnWriteArrayList<Bullet> bullets) {
        HelloController.bullets = bullets;
    }

    public Cursor getCustomCursor() {
        return customCursor;
    }

    public static CopyOnWriteArrayList<PressurePlate> getPressurePlates() {
        return pressurePlates;
    }

    public static void setPressurePlates(CopyOnWriteArrayList<PressurePlate> pressurePlates) {
        HelloController.pressurePlates = pressurePlates;
    }

    public static CopyOnWriteArrayList<MobilePump> getMobilePumps() {
        return mobilePumps;
    }

    public static void setMobilePumps(CopyOnWriteArrayList<MobilePump> mobilePumps) {
        HelloController.mobilePumps = mobilePumps;
    }

    public static Player getCharacter() {
        return character;
    }

    public static void setCharacter(Player character) {
        HelloController.character = character;
    }

    public static boolean isMatrixBased() {
        return matrixBased;
    }

    public static void setMatrixBased(boolean matrixBased) {
        HelloController.matrixBased = matrixBased;
    }
}
