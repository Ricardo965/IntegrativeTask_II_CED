package com.example.integrativetask_ii_ced.model.drawing;

import com.example.integrativetask_ii_ced.model.entities.objects.Obstacle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MapNode extends Obstacle implements Drawable {

    boolean navigable;
    private Image[] design;


    public MapNode(double x, double y, boolean navigable) {
        super(x, y);
        this.navigable = navigable;
    }

    @Override
    public void draw(GraphicsContext gc) {
    }

    public boolean isNavigable() {
        return navigable;
    }

    public void setNavigable(boolean navigable) {
        this.navigable = navigable;
    }

    public Image[] getDesign() {
        return design;
    }

    public void setDesign(Image[] design) {
        this.design = design;
    }
}
