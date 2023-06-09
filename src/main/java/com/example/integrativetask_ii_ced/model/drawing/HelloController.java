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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicBoolean;

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
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bienvenido al juego!");
        alert.setHeaderText("Elección de grafo");
        alert.setContentText("Escoge el tipo de grafo: \n RECUERDA QUE LAS PLACAS DEBES PRESIONAR LAS PLACAS DE PRESION PARA ELIMINAR AL BOSS MARLON");
        ButtonType okButton = new ButtonType("Lista de adyacencia");
        ButtonType cancelButton = new ButtonType("Matriz de adyacencia");

        alert.getButtonTypes().setAll(okButton, cancelButton);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == okButton) {
            setMatrixBased(false);
        } else {
            setMatrixBased(true);

        }
        gameMap.initialFillingOfMapWithNodesAndCoordinates();
        gameMap.creatingNotNavigableObstacles();

        if (isMatrixBased()) gameMap.establishMatrixGraphMapRepresentationForMinimumPaths();
        else gameMap.establishGraphMapRepresentationForMinimumPaths();


        for (int i = 0; i < gameMap.getMapGuide().get(0).size(); i++) {
            if (gameMap.getMapGuide().get(0).get(i).isNavigable()){
                character = new Player(gameMap.getMapGuide().get(0).get(i).getPosition().getX(),gameMap.getMapGuide().get(0).get(i).getPosition().getY(), 60,60,20000);
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

        pressurePlates = gameMap.creatingPressurePlates(pressurePlates);

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

            if ( allIsPressed() ){
                finalBoss.died();
            }

        }
    }

    public boolean allIsPressed(){
        if ( pressurePlates.size() == 0 ) return false;
        for(int i=0 ; i<pressurePlates.size() ; i++){
            if ( !pressurePlates.get(i).isPressed() ){
                return false;
            }
        }
        return true;
    }



    public void draw(){
        Thread h = new Thread(() -> {
            AtomicBoolean zzz = new AtomicBoolean(true);
            while(zzz.get()){
                    Platform.runLater(() -> {
                        gc.setFill(Color.WHITE);
                        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                        for (int i = 0; i < bullets.size(); i++){
                            bullets.get(i).draw(gc);
                        }
                        for (int i = 0; i < gameMap.getMapGuide().size(); i++){
                            for (int j = 0; j < gameMap.getMapGuide().get(i).size(); j++){
                                gameMap.getMapGuide().get(i).get(j).draw(gc);
                            }
                        }
                        for (int i = 0; i < pressurePlates.size(); i++){
                            gc.setFill(Color.VIOLET);
                            pressurePlates.get(i).draw(gc);
                        }
                        finalBoss.draw(gc);
                        for (int i = 0; i < mobilePumps.size(); i++){
                            mobilePumps.get(i).draw(gc);
                        }
                        if ( character.getLife() > 0 ){
                            character.draw(gc);
                        }
                        if ( finalBoss.getLife()<1 || character.getLife()<1 ){
                            zzz.set(false);
                        }

                    });
                    character.movement();

                    try {
                        Thread.sleep(16);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
            }
            if(finalBoss.getLife()<1 || character.getLife()<1){
                exit();
                try {
                    Thread.sleep(500000000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        h.start();

    }

    private void exit() {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);

            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            if (finalBoss.getLife() < 1) {
                alert.setTitle("YOU WINNN!");
                alert.setHeaderText("YOU ARE THE BEST!");
                ButtonType cancelButton = new ButtonType("EXIT");
                alert.getButtonTypes().setAll(cancelButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == cancelButton) {
                        System.exit(0);
                    }
                });
            } else {
                alert.setTitle("YOU LOOSEEEE :(((!");
                alert.setHeaderText("TRY AGAIN, WE HAVE TO DEFEAT MARLON!");
                ButtonType cancelButton = new ButtonType("EXIT");
                alert.getButtonTypes().setAll(cancelButton);

                alert.showAndWait().ifPresent(buttonType -> {
                    if (buttonType == cancelButton) {
                        System.exit(0);
                    }
                });
            }
        });
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
