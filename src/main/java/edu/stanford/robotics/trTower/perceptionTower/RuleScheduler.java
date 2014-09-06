package edu.stanford.robotics.trTower.perceptionTower;

import edu.stanford.robotics.trTower.modelTower.ModelTower;

public interface RuleScheduler {

    // just use the Iterator interface?

    // --- or
    boolean isStable();
    Rule getNextRule();

    // --- attributes
    // code broken...
    // private modelTower;
    ModelTower getModelTower();
    void setModelTower(ModelTower m);
}
