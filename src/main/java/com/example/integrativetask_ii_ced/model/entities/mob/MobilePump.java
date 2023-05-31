package com.example.integrativetask_ii_ced.model.entities.mob;

import com.example.integrativetask_ii_ced.model.entities.Avatar;
import javafx.scene.canvas.GraphicsContext;

public class MobilePump extends Avatar implements Runnable {



    public MobilePump(double x, double y, double width, double height, double life) {
        super(x, y, width, height, life);
    }

    @Override
    public void run() {

    }

    @Override
    public void draw(GraphicsContext gc) {

    }
}
