package edu.stanford.robotics.trTower.actionTower;

import java.util.List;

public class TRCallDescription {
	public static final int MAKETOWER = 1;
	public static final int MOVETOTABLE = 2;
	public static final int MOVE = 3;
	public static final int UNPILE = 4;
	public static final int PICKUP = 5;
	public static final int PUTDOWN = 6;
	public static final int NIL = 7;

	private int trFunction;

	public int getTrFunction() {
		return this.trFunction;
	}

	public void setTrFunction(final int n) {
		this.trFunction = n;
	}

	private List<String> parameterList;

	public List<String> getParameterList() {
		return this.parameterList;
	}

	public void setParameterList(final List<String> l) {
		this.parameterList = l;
	}
}
