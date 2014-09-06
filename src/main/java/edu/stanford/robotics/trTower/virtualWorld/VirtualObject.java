package edu.stanford.robotics.trTower.virtualWorld;

import java.awt.*;
import java.awt.geom.*;

public abstract class VirtualObject {

    // properties
    private String id;
    public String getId() { return id; }
    void setId(String s) { id = s; }
    
    private Paint paint;
    Paint getPaint() { return paint; }
    void setPaint(Paint p) { paint = p; }

    private Point2D coord;
    public Point2D getCoord() { return coord; }
    void setCoord(Point2D p) { coord = p; }
//      double getXCoord() { return coord.getX(); }
//      double getYCoord() { return coord.getY(); }
    void setCoord(double x, double y) { coord = new Point2D.Double(x, y); }

    private boolean coordLegal = true;
    boolean isCoordLegal() { return coordLegal; }
    void setCoordLegal(boolean l) { coordLegal = l; }
    
    // public methods
    abstract void render(Graphics g);
}
