package edu.stanford.robotics.trTower.virtualWorld;

import java.awt.*;

class NewBlockCreator {
    
    NewBlockCreator() {
	reset();
    }
    
    void reset() {
	currentPaint = Color.blue;
	currentChar = 'Z';
    }

    Block createNewBlock() {
	Block b = new Block();
	b.setId(String.valueOf(getNextChar()));
	b.setPaint(getNextPaint());
	b.setLabelPaint(getLabelPaint());
	b.setBlockWidth(getVirtualWorldModel().getBlockWidth());
	b.setBlockHeight(getVirtualWorldModel().getBlockHeight());
	return b;
    }
    
    // --- properties
    private Paint labelPaint;
    Paint getLabelPaint() { return labelPaint; }
    void setLabelPaint(Paint l) { labelPaint = l; }


    // --- components
    VirtualWorldModel virtualWorldModel;
    VirtualWorldModel getVirtualWorldModel() { return virtualWorldModel; }
    void setVirtualWorldModel(VirtualWorldModel m) { virtualWorldModel = m; }

    private Paint currentPaint;
    private char currentChar;
    
    private char getNextChar() {
	if (currentChar == 'Z')
	    currentChar = 'A';
	else if (currentChar == 'A')
	    currentChar = 'B';
	else if (currentChar == 'B')
	    currentChar = 'C';
	else if (currentChar == 'C')
	    currentChar = 'D';
	else if (currentChar == 'D')
	    currentChar = 'E';
	else if (currentChar == 'E')
	    currentChar = 'F';
	else if (currentChar == 'F')
	    currentChar = 'G';
	else if (currentChar == 'G')
	    currentChar = 'H';
	else if (currentChar == 'H')
	    currentChar = 'I';
	else if (currentChar == 'I')
	    currentChar = 'J';
	else if (currentChar == 'J')
	    currentChar = 'K';
	else if (currentChar == 'K')
	    currentChar = 'L';
	else if (currentChar == 'L')
	    currentChar = 'M';
	else if (currentChar == 'M')
	    currentChar = 'N';
	else if (currentChar == 'N')
	    currentChar = 'O';
	else if (currentChar == 'O')
	    currentChar = 'P';
	else if (currentChar == 'P')
	    currentChar = 'Q';
	else if (currentChar == 'Q')
	    currentChar = 'R';
	else if (currentChar == 'R')
	    currentChar = 'S';
	else if (currentChar == 'S')
	    currentChar = 'T';
	else if (currentChar == 'T')
	    currentChar = 'U';
	else if (currentChar == 'U')
	    currentChar = 'V';
	else if (currentChar == 'V')
	    currentChar = 'W';
	else if (currentChar == 'W')
	    currentChar = 'X';
	else if (currentChar == 'X')
	    currentChar = 'Y';
	else
	    currentChar = '*';
	return currentChar;
    }
		

    Color c1 = new Color(255, 64, 64);

    private Paint getNextPaint() {
	if (currentPaint.equals(Color.blue)) 
	    currentPaint = c1;
	else if (currentPaint.equals(c1))
	    currentPaint = Color.cyan;
	else if (currentPaint.equals(Color.cyan))
	    currentPaint = Color.green;
	else if (currentPaint.equals(Color.green))
	    currentPaint = Color.magenta;
	else if (currentPaint.equals(Color.magenta))
	    currentPaint = Color.orange;
	else if (currentPaint.equals(Color.orange))
	    currentPaint = Color.pink;
	else if (currentPaint.equals(Color.pink))
	    currentPaint = Color.yellow;
	else
	    currentPaint = Color.blue;
	return currentPaint;
    }
}

