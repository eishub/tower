package edu.stanford.robotics.trTower.perceptionTower;

import edu.stanford.robotics.trTower.modelTower.ModelTower;

public interface Rule {

    void execute();

    // --- would the following be useful?
    boolean isConditionTrue();
    boolean isTargetChanged();

    String getId();
    String toString();

    boolean evalCondition();
    boolean updateTarget();
    Object getTarget();
    void setTarget(Object t);
    
    // --- attributes
    // code broken... 
    // private modelTower;
    ModelTower getModelTower();
    void setModelTower(ModelTower mt);

    // code broken...
    // private ruleScheduler;
    //RuleIterator getRuleScheduler() { return ruleScheduler; }
    void setRuleScheduler(RuleScheduler rs);
}
