package com.example.integrativetask_ii_ced.model.entities.objects.functional;

import com.example.integrativetask_ii_ced.model.entities.Avatar;
import javafx.scene.canvas.GraphicsContext;

public class Wall extends Avatar{

    public Wall(double x, double y, double width, double height, double life) {
        super(x, y, width, height, life);
    }

    @Override
    public void draw(GraphicsContext gc) {

    }
}
