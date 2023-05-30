package com.example.integrativetask_ii_ced.model.entities;

import com.example.integrativetask_ii_ced.model.drawing.HelloController;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;


public class Player extends Avatar implements Runnable {
    private boolean keyA;
    private boolean keyW;
    private boolean keyS;
    private boolean keyD;
    private boolean keyE;

    private Image[] idle;
    private Image[] run;
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
        run = new Image[6];
        for(int i=1 ; i<=6 ; i++) {
            String uri = "file:src/main/resources/images/Character/run/player-run"+i+".png";
            run[i-1] = new Image(uri);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.strokeRect(hitBox.getX0(), hitBox.getY0(), width, height);
        gc.drawImage((isMoving() ? run[frame] : idle[frame]), isFacingRight ? position.getX() - (width / 2) : position.getX() + (width / 2), position.getY() - (width / 2), isFacingRight ? width : -width, height);
    }

    @Override
    public void run() {
        while (true) {
            frame = (frame + 1) % 6;
            try {
                Thread.sleep(80);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void movement(){
        if ( keyA ){
            hitBox.refreshHitBox((position.getX()-3)-(width/2), position.getY()-(height/2), (position.getX()-3)+(width/2), position.getY()+(height/2));
            if ( colission() ) return;
            position.setX(position.getX()-3);
        }
        if ( keyW ){
            hitBox.refreshHitBox(position.getX()-(width/2), position.getY()-3-(height/2), position.getX()+(width/2), position.getY()-3+(height/2));
            if ( colission() ) return;
            position.setY(position.getY()-3);
        }
        if ( keyS ){
            hitBox.refreshHitBox(position.getX()-(width/2), position.getY()+3-(height/2), position.getX()+(width/2), position.getY()+3+(height/2));
            if ( colission() ) return;
            position.setY(position.getY()+3);
        }
        if ( keyD ){
            hitBox.refreshHitBox((position.getX()+3)-(width/2), position.getY()-(height/2), (position.getX()+3)+(width/2), position.getY()+(height/2));
            if ( colission() ) return;
            position.setX(position.getX()+3);
        }
        hitBox.refreshHitBox(position.getX(), position.getY(), position.getX(), position.getY());
    }

    private boolean colission() {
        if (hitBox.comparePosition(HelloController.finalBoss.getHitBox())) {
            hitBox.refreshHitBox(position.getX()-(width/2), position.getY()-(height/2), position.getX()+(width/2), position.getY()+(height/2));
            return true;
        }
        return false;
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
}
