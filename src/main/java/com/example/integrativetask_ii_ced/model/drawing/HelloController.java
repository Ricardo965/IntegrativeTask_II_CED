package com.example.integrativetask_ii_ced.model.drawing;

import com.example.integrativetask_ii_ced.HelloApplication;
import com.example.integrativetask_ii_ced.model.entities.Bullet;
import com.example.integrativetask_ii_ced.model.entities.Enemy;
import com.example.integrativetask_ii_ced.model.entities.Player;
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

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class HelloController implements Initializable {

    private final Image image = new Image("file:src/main/resources/images/background.jpg");

    @FXML
    private Canvas canvas;
    private GraphicsContext gc;
    private final Player character = new Player(50, 100, 70, 70,200);
    private final CopyOnWriteArrayList<Bullet> bullets = new CopyOnWriteArrayList<>();
    private final CopyOnWriteArrayList<Enemy> enemies = new CopyOnWriteArrayList<>();
    private final Cursor customCursor = new ImageCursor(new Image("file:src/main/resources/images/Cursor/nt_normal.png"));

    @Override
    public void initialize(java.net.URL location, java.util.ResourceBundle resources) {
        canvas.setCursor(customCursor);
        enemies.add(new Enemy(400, 200, 60, 60, 100));
        gc = canvas.getGraphicsContext2D();
        canvas.setFocusTraversable(true);
        canvas.setOnKeyPressed(character::pressKey);
        canvas.setOnKeyReleased(character::releasedKey);
        canvas.setOnMouseClicked(this::mouseClicked);
        canvas.setOnMouseMoved(this::onMouseMoved);
        new Thread(character).start();
        draw();
    }


    public void draw(){
        Thread h = new Thread(() -> {
            while(true){
                Platform.runLater(() -> {
                    gc.setFill(Color.WHITE);
                    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                    for (Bullet bullet : bullets){
                        bullet.draw(gc);
                    }
                    for (Enemy enemy : enemies){
                        enemy.draw(gc);
                    }
                    character.draw(gc);
                    gc.setFill(Color.BLACK);
                    gc.fillRect(100, 100, 80, 80);
                });
                character.movement();
                for (Bullet bullet : bullets) {
                    collision(bullet);
                    bulletOutside(bullet);
                }
                for (Enemy enemy : enemies){
                    enemy.draw(gc);
                }
                try {
                    Thread.sleep(16);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        h.start();
    }

    private void bulletOutside(Bullet bullet) {
        if(bullet.outside(canvas.getHeight(), canvas.getWidth())){
            bullets.remove(bullet);
            System.out.println("Bullet removed");
        }
    }

    private void mouseClicked(MouseEvent event) {
        character.shoot(event, bullets);
    }

    private void onMouseMoved(MouseEvent e) {
        double relativePosition = e.getX()-character.getPosition().getX();
        character.setFacingRight(
                relativePosition > 0
        );
    }

    private void collision(Bullet bullet){
        for(int i=0 ; i<enemies.size() ; i++){
            if ( bullet.getHitBox().comparePosition(enemies.get(i).getHitBox()) ){
                enemies.remove(i);
                bullets.remove(bullet);
                System.out.println("XD");
            }
        }
    }
}
