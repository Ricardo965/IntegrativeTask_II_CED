package com.example.integrativetask_ii_ced.model.entities;

import com.example.integrativetask_ii_ced.model.drawing.Vector;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Bullet extends Avatar {

    private Vector dir;
    public Bullet(double x, double y, double width, double height, double life, Vector dir) {
        super(x, y, width, height, life);
        this.dir = dir;
    }


    @Override
    public void draw(GraphicsContext gc) {
        gc.setFill(Color.BLUE);
        gc.fillOval(position.getX(), position.getY(), 10,10);

        position.setX( position.getX() + dir.getX() );
        position.setY( position.getY() + dir.getY() );
        hitBox.refreshHitBox(position.getX()-(width/2), position.getY()-(height/2), position.getX()+(width/2), position.getY()+(height/2));
    }

    public boolean outside(double height, double width) {
        return position.getX() > width || position.getX() < 0 || position.getY() > height || position.getY() < 0;
    }
}
