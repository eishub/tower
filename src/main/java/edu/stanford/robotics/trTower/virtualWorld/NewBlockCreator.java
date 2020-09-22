package edu.stanford.robotics.trTower.virtualWorld;

import java.awt.Color;
import java.awt.Paint;

class NewBlockCreator {
	NewBlockCreator() {
		reset();
	}

	void reset() {
		this.currentPaint = Color.blue;
		this.currentChar = 'Z';
	}

	Block createNewBlock() {
		final Block b = new Block();
		b.setId(String.valueOf(getNextChar()));
		b.setPaint(getNextPaint());
		b.setLabelPaint(getLabelPaint());
		b.setBlockWidth(getVirtualWorldModel().getBlockWidth());
		b.setBlockHeight(getVirtualWorldModel().getBlockHeight());
		return b;
	}

	private Paint labelPaint;

	Paint getLabelPaint() {
		return this.labelPaint;
	}

	void setLabelPaint(final Paint l) {
		this.labelPaint = l;
	}

	VirtualWorldModel virtualWorldModel;

	VirtualWorldModel getVirtualWorldModel() {
		return this.virtualWorldModel;
	}

	void setVirtualWorldModel(final VirtualWorldModel m) {
		this.virtualWorldModel = m;
	}

	private Paint currentPaint;
	private char currentChar;

	private char getNextChar() {
		if (this.currentChar == 'Z') {
			this.currentChar = 'A';
		} else if (this.currentChar == 'A') {
			this.currentChar = 'B';
		} else if (this.currentChar == 'B') {
			this.currentChar = 'C';
		} else if (this.currentChar == 'C') {
			this.currentChar = 'D';
		} else if (this.currentChar == 'D') {
			this.currentChar = 'E';
		} else if (this.currentChar == 'E') {
			this.currentChar = 'F';
		} else if (this.currentChar == 'F') {
			this.currentChar = 'G';
		} else if (this.currentChar == 'G') {
			this.currentChar = 'H';
		} else if (this.currentChar == 'H') {
			this.currentChar = 'I';
		} else if (this.currentChar == 'I') {
			this.currentChar = 'J';
		} else if (this.currentChar == 'J') {
			this.currentChar = 'K';
		} else if (this.currentChar == 'K') {
			this.currentChar = 'L';
		} else if (this.currentChar == 'L') {
			this.currentChar = 'M';
		} else if (this.currentChar == 'M') {
			this.currentChar = 'N';
		} else if (this.currentChar == 'N') {
			this.currentChar = 'O';
		} else if (this.currentChar == 'O') {
			this.currentChar = 'P';
		} else if (this.currentChar == 'P') {
			this.currentChar = 'Q';
		} else if (this.currentChar == 'Q') {
			this.currentChar = 'R';
		} else if (this.currentChar == 'R') {
			this.currentChar = 'S';
		} else if (this.currentChar == 'S') {
			this.currentChar = 'T';
		} else if (this.currentChar == 'T') {
			this.currentChar = 'U';
		} else if (this.currentChar == 'U') {
			this.currentChar = 'V';
		} else if (this.currentChar == 'V') {
			this.currentChar = 'W';
		} else if (this.currentChar == 'W') {
			this.currentChar = 'X';
		} else if (this.currentChar == 'X') {
			this.currentChar = 'Y';
		} else {
			this.currentChar = '*';
		}
		return this.currentChar;
	}

	Color c1 = new Color(255, 64, 64);

	private Paint getNextPaint() {
		if (this.currentPaint.equals(Color.blue)) {
			this.currentPaint = this.c1;
		} else if (this.currentPaint.equals(this.c1)) {
			this.currentPaint = Color.cyan;
		} else if (this.currentPaint.equals(Color.cyan)) {
			this.currentPaint = Color.green;
		} else if (this.currentPaint.equals(Color.green)) {
			this.currentPaint = Color.magenta;
		} else if (this.currentPaint.equals(Color.magenta)) {
			this.currentPaint = Color.orange;
		} else if (this.currentPaint.equals(Color.orange)) {
			this.currentPaint = Color.pink;
		} else if (this.currentPaint.equals(Color.pink)) {
			this.currentPaint = Color.yellow;
		} else {
			this.currentPaint = Color.blue;
		}
		return this.currentPaint;
	}
}
