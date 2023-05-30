package com.example.integrativetask_ii_ced.model.entities.objects.obstacle;

import com.example.integrativetask_ii_ced.model.entities.objects.Obstacle;
import javafx.scene.canvas.GraphicsContext;

public class Box extends Obstacle {

    public Box(double x, double y, double life){
        super(x, y, life);
    }

    @Override
    public void draw(GraphicsContext gc) {

    }
}
