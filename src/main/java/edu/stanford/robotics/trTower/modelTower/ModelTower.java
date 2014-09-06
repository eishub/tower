package edu.stanford.robotics.trTower.modelTower;

import java.util.*;
import edu.stanford.robotics.trTower.*;

public class ModelTower 
    //implements StimulatorListener 
{

    // separator of the Id's to form one string for hash map
    static final String sep = ".";
    protected String listToString(List l) {
	boolean firstField = true;
	StringBuffer tmp = new StringBuffer();
	Iterator i = l.iterator();
	while (i.hasNext()) {
	    if (firstField) {
		// don't need to put separator
		firstField = false;
	    } else {
		tmp.append(sep);
	    }
	    tmp.append((String)(i.next()));
	}
	return tmp.toString();
    }
    protected List stringToList(String s) {
	List l = new ArrayList();
	StringTokenizer st = new StringTokenizer(s, sep);
	while (st.hasMoreTokens()) {
	    l.add(st.nextToken());
	}
	return l;
    }

    // --- attributes
    private boolean toBeReset = false;
    public boolean isToBeReset() { return toBeReset; }
    public void setToBeReset(boolean r) { toBeReset = r; }
    
    private boolean available = true;
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean a) { available = a; }

    private boolean changed = false;
    public boolean isChanged() { return changed; }
    public void setChanged(boolean c) { changed = c; }

    // --- public methods
//      public void stimuStep() {
//  	if (isToBeReset()) {
//  	    clearModelTower();
//  	    setChanged(true);
//  	    setToBeReset(false);
//  	}
//      }

    public void clearModelTower() {
	setExistingBlockIds(new ArrayList());
	getOnMap().clear();
	getHoldingMap().clear();
	clearDerivedPredicates();
    }
    
    public void clearDerivedPredicates() {
	getClearMap().clear();
	clearOrdered();
	clearTower();
    }

    // --- primitive, P1
    public boolean isOnValid(String objectIdX, String objectIdY) {
	return getOnMap().get(objectIdX+sep+objectIdY) != null;
    }
    public boolean isOn(String objectIdX, String objectIdY) {
	Object isOn = getOnMap().get(objectIdX+sep+objectIdY);
	return ((Boolean)isOn).booleanValue();
    }

    public void setOn(String objectIdX, String objectIdY, boolean isOn) {
	getOnMap().put(objectIdX+sep+objectIdY, new Boolean(isOn));
    }
    private Map onMap;
    protected Map getOnMap() {
	if (onMap == null) {
	    onMap = new HashMap();
	}
	return onMap;
    }

    public boolean isHoldingValid(String blockId) {
	return getHoldingMap().get(blockId) != null;
    }
    public boolean isHolding(String blockId) {
	Object isHolding = getHoldingMap().get(blockId);
	return ((Boolean)isHolding).booleanValue();
    }
    public void setHolding(String blockId, boolean isHolding) {
	getHoldingMap().put(blockId, new Boolean(isHolding));
    }
    private Map holdingMap;
    protected Map getHoldingMap() {
	if (holdingMap == null) {
	    holdingMap = new HashMap();
	}
	return holdingMap;
    }

    // implicitly avail?
    private List blockIdList;
    public List getExistingBlockIds() { return blockIdList; }
    public void setExistingBlockIds(List l) { blockIdList = l; }

    private String tableId;
    public String getTableId() { return tableId; }
    public void setTableId(String t) { tableId = t; }

    // --- P2
    public boolean isClear(String blockId) {
	Object isClear = getClearMap().get(blockId);
	return ((Boolean)isClear).booleanValue();
    }
    public void setClear(String blockId, boolean isClear) {
	getClearMap().put(blockId, new Boolean(isClear));
    }
    private Map clearMap;
    protected Map getClearMap() {
	if (clearMap == null) {
	    clearMap = new HashMap();
	}
	return clearMap;
    }

    // --- P3
    public boolean isOrdered(List listOfBlocks) {
	return getOrderedSet().contains(listToString(listOfBlocks));
    }
    public void setOrdered(List listOfBlocks, boolean isOrdered) {
	if (isOrdered) 
	    getOrderedSet().add(listToString(listOfBlocks));
	else
	    getOrderedSet().remove(listToString(listOfBlocks));
    }
    public void clearOrdered() {
	getOrderedSet().clear();
    }
    
    private Set orderedSet;
    protected Set getOrderedSet() {
	if (orderedSet == null) {
	    orderedSet = new HashSet();
	}
	return orderedSet;
    }
    public List getOrderedListList() {
	List orderedListList = new ArrayList();
	Iterator i = getOrderedSet().iterator();
	while (i.hasNext()) {
	    String orderedListString = (String)(i.next());
	    orderedListList.add(stringToList(orderedListString));
	}
	return orderedListList;
    }
	
    // --- P4
    public List getTowerListList() {
	List towerListList = new ArrayList();
	Iterator i = getTowerSet().iterator();
	while (i.hasNext()) {
	    String towerListString = (String)(i.next());
	    towerListList.add(stringToList(towerListString));
	}
	return towerListList;
    }
    
    public boolean isTower(List listOfBlocks) {
	return getTowerSet().contains(listToString(listOfBlocks));
    }
    public void setTower(List listOfBlocks, boolean isTower) {
	if (isTower)
	    getTowerSet().add(listToString(listOfBlocks));
	else
	    getTowerSet().remove(listToString(listOfBlocks));
    }
    public void clearTower() {
	getTowerSet().clear();
    }
    private Set towerSet;
    protected Set getTowerSet() {
	if (towerSet == null) {
	    towerSet = new HashSet();
	}
	return towerSet;
    }

    // --- helpers
    // model shouldn't know if it's stable.
    //    boolean isStable() { return false; }

}
