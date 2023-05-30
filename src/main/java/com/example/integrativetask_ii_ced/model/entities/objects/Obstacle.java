package com.example.integrativetask_ii_ced.model.entities.objects;

import com.example.integrativetask_ii_ced.model.entities.Avatar;
import javafx.scene.canvas.GraphicsContext;

public class Obstacle extends Avatar {

    public Obstacle(double x, double y, double life) {
        super(x, y, 80, 80, life);
    }
    @Override
    public void draw(GraphicsContext gc) {

    }
}
