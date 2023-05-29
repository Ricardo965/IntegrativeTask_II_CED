package com.example.integrativetask_ii_ced.model.entities;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Enemy extends Avatar {
    public Enemy(double x, double y, double width, double height, double life){
        super(x, y, width, height, life);
        String uri = "file:src/main/resources/images/avatar.png";
        idle = new Image(uri);

    }

    @Override
    public void draw(GraphicsContext gc) {
        hitBox.refreshHitBox(position.getX()-(width/2), position.getY()-(height/2), position.getX()+(width/2), position.getY()+(height/2));
        gc.drawImage(idle, hitBox.getX0(), hitBox.getY0(), width, height);
    }
}

