package com.example.integrativetask_ii_ced.model.entities;

import com.example.integrativetask_ii_ced.HelloApplication;
import com.example.integrativetask_ii_ced.model.drawing.Vector;
import javafx.event.Event;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;

public class Player extends Avatar implements Runnable {
    private boolean keyA;
    private boolean keyW;
    private boolean keyS;
    private boolean keyD;
    private boolean keyE;

    private Image[] idle;
    private Image[] run;

    private Image[] runShoot;

    private Image[] shoot;

    private int frame = 0;

    private boolean isFacingRight = true;

    private boolean isShooting = false;
    public void setFacingRight(boolean facingRight) {
        isFacingRight = facingRight;
    }

    public Player(double x, double y, double width, double height, double life) {
        super(x, y, width, height, life);
        idle = new Image[6];
        for(int i=1 ; i<=6 ; i++) {
            String uri = "file:src/main/resources/images/Character/idle/player-idle"+i+".png";
            idle[i-1] = new Image(uri);
        }
        runShoot = new Image[6];
        for(int i=1 ; i<=6 ; i++) {
            String uri = "file:src/main/resources/images/Character/pistol/player-run-shoott"+i+".png";
            runShoot[i-1] = new Image(uri);
        }
        run = new Image[6];
        for(int i=1 ; i<=6 ; i++) {
            String uri = "file:src/main/resources/images/Character/run/player-run"+i+".png";
            run[i-1] = new Image(uri);
        }
        shoot = new Image[3];
        for(int i=1 ; i<=3 ; i++) {
            String uri = "file:src/main/resources/images/Character/shoot/player-shoot"+i+".png";
            shoot[i-1] = new Image(uri);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        if (isShooting){
            gc.drawImage((isMoving() ? runShoot[frame] : shoot[frame]), isFacingRight ? position.getX() - (width / 2) : position.getX() + (width / 2), position.getY() - (width / 2), isFacingRight ? width : -width, height);
        } else {
            gc.drawImage((isMoving() ? run[frame] : idle[frame]), isFacingRight ? position.getX() - (width / 2) : position.getX() + (width / 2), position.getY() - (width / 2), isFacingRight ? width : -width, height);
        }
    }

    @Override
    public void run() {
        while (true) {
            if ( isShooting && !isMoving()){
                frame = 0;
                while(isShooting && !isMoving()){
                    System.out.println(isShooting);
                    if ( frame != 2 ){
                        frame++;
                        try {
                            Thread.sleep(66);
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            } else {
                frame = (frame + 1) % 6;
            }
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void movement(){
        if ( keyA ){
            position.setX(position.getX()-3);
        }
        if ( keyW ){
            position.setY(position.getY()-3);
        }
        if ( keyS ){
            position.setY(position.getY()+3);
        }
        if ( keyD ){
            position.setX(position.getX()+3);
        }

    }
    public void pressKey(KeyEvent event){
        switch (event.getCode()) {
            case A -> {
                keyA = true;
            }
            case W -> {
                keyW = true;
            }
            case S -> {
                keyS = true;
            }
            case D -> {
                keyD = true;
            }
            case E -> {
                isShooting = !isShooting;
                frame = 0;
            }
        }
    }

    public void releasedKey(KeyEvent event){
        switch (event.getCode()) {
            case A -> {
                keyA = false;
            }
            case W -> {
                keyW = false;
            }
            case S -> {
                keyS = false;
            }
            case D -> {
                keyD = false;
            }
        }
        movement();
    }

    public boolean isMoving() {
        return keyA || keyW || keyS || keyD;
    }

    public CopyOnWriteArrayList<Bullet> shoot(MouseEvent event, CopyOnWriteArrayList<Bullet> bullets){
        if (isShooting ){
            double diffX = event.getX() + 10 - this.getPosition().getX();
            double diffY = event.getY() + 10 - this.getPosition().getY();
            Vector diff = new Vector(diffX, diffY);
            diff.normalize();
            diff.setMag(4);


            bullets.add(
                    new Bullet(
                            this.getPosition().getX(), this.getPosition().getY(), 5, 5, 20, diff
                    )
            );
        }
        return bullets;
    }
}
