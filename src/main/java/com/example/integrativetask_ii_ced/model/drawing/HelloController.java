package com.example.integrativetask_ii_ced.model.drawing;

import com.example.integrativetask_ii_ced.model.entities.*;
import com.example.integrativetask_ii_ced.model.entities.mob.Boss;
import com.example.integrativetask_ii_ced.model.entities.objects.functional.Bullet;
import com.example.integrativetask_ii_ced.model.entities.objects.Obstacle;
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
    public static final Player character = new Player(50, 100, 70, 70,200);
    public static Boss finalBoss;
    public static CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<>();
    private final Cursor customCursor = new ImageCursor(new Image("file:src/main/resources/images/Cursor/nt_normal.png"));

    public static CopyOnWriteArrayList<PressurePlate> pressurePlates = new CopyOnWriteArrayList<>();


    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        canvas.setCursor(customCursor);
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(character::pressKey);
        canvas.setOnKeyReleased(character::releasedKey);
        finalBoss = new Boss(canvas.getWidth()/2, canvas.getHeight()/2, 120, 170, 100);
        pressurePlates.add(new PressurePlate(100, 100));
        pressurePlates.add(new PressurePlate(200, 200));
        pressurePlates.add(new PressurePlate(300, 300));
        pressurePlates.add(new PressurePlate(400, 400));
        canvas.setOnMouseMoved(this::onMouseMoved);
        new Thread(character).start();
        new Thread(finalBoss).start();
        new Thread(this).start();
        draw();
    }

    @Override
    public void run() {
        while (true) {
            for (Bullet bullet : bullets){
                if ( bullet.outside(canvas.getHeight(), canvas.getWidth()) || bullet.giveDamage(character) ){
                    bullets.remove(bullet);
                }
            }
            for(PressurePlate pressurePlate : pressurePlates){
                pressurePlate.isPressed(character);
            }

        }
    }



    public void draw(){
        Thread h = new Thread(() -> {
            while(true){
                Platform.runLater(() -> {
                    gc.setFill(Color.WHITE);
                    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    finalBoss.draw(gc);
                    if ( character.getLife() > 0 ){
                        character.draw(gc);
                    }
                    gc.setFill(Color.BLACK);
                    gc.fillRect(100, 100, 80, 80);
                    for (Bullet bullet : bullets) {
                        bullet.draw(gc);
                    }
                    for(PressurePlate pressurePlate : pressurePlates){
                        pressurePlate.draw(gc);
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


}
