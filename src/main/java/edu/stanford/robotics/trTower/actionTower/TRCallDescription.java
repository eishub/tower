package edu.stanford.robotics.trTower.actionTower;

import java.util.*;

public class TRCallDescription {

    // --- TR call function name
    public static final int MAKETOWER = 1;
    public static final int MOVETOTABLE = 2;
    public static final int MOVE = 3;
    public static final int UNPILE = 4;
    public static final int PICKUP = 5;
    public static final int PUTDOWN = 6;
    public static final int NIL = 7;
    
    // --- attributes
    private int trFunction;
    public int getTrFunction() { return trFunction; }
    public void setTrFunction(int n) { trFunction = n; }

    private List parameterList;
    public List getParameterList() { return parameterList; }
    public void setParameterList(List l) { parameterList = l; }
}

