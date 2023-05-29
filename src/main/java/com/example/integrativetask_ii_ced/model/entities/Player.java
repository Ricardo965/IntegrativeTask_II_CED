package com.example.integrativetask_ii_ced.model.entities;

import com.example.integrativetask_ii_ced.HelloApplication;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;

import java.util.Objects;

public class Player extends Avatar implements Runnable {
    private boolean keyA;
    private boolean keyW;
    private boolean keyS;
    private boolean keyD;
    private Image[] idle;
    private Image[] run;

    private int frame = 0;

    private boolean isMoving;
    private boolean isFacingRight = true;

    public boolean isFacingRight() {
        return isFacingRight;
    }

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
            String uri = "file:src/main/resources/images/Character/pistol/player-run-shoott"+i+".png";
            run[i-1] = new Image(uri);
        }
    }

    @Override
    public void draw(GraphicsContext gc) {
        gc.drawImage(isMoving?run[frame]:idle[frame],
                isFacingRight?position.getX()-25:position.getX()+25,
                position.getY()-25,
                isFacingRight?50:-50,
                50);
    }

    @Override
    public void run() {
        while (true) {
            System.out.println(frame);
            frame = (frame + 1) % 6;
            System.out.println((frame + 1) % 6);
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
            case A -> keyA = true;
            case W -> keyW = true;
            case S -> keyS = true;
            case D -> keyD = true;
        }
    }

    public void releasedKey(KeyEvent event){
        switch (event.getCode()){
            case A:
                keyA = false;
                isMoving = false;
                break;
            case W:
                keyW = false;
                isMoving = false;
                break;
            case S:
                keyS = false;
                isMoving = false;
                break;
            case D:
                keyD = false;
                isMoving = false;
                break;
        }
        movement();
    }
}
