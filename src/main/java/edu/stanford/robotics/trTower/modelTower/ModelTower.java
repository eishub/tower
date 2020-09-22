package edu.stanford.robotics.trTower.modelTower;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

public class ModelTower {
	private static final String sep = "."; // separator of the Id's to form one string for hash map

	protected String listToString(final List<String> l) {
		boolean firstField = true;
		final StringBuffer tmp = new StringBuffer();
		final Iterator<String> i = l.iterator();
		while (i.hasNext()) {
			if (firstField) {
				// don't need to put separator
				firstField = false;
			} else {
				tmp.append(sep);
			}
			tmp.append((i.next()));
		}
		return tmp.toString();
	}

	protected List<String> stringToList(final String s) {
		final List<String> l = new LinkedList<>();
		final StringTokenizer st = new StringTokenizer(s, sep);
		while (st.hasMoreTokens()) {
			l.add(st.nextToken());
		}
		return l;
	}

	private boolean toBeReset = false;

	public boolean isToBeReset() {
		return this.toBeReset;
	}

	public void setToBeReset(final boolean r) {
		this.toBeReset = r;
	}

	private boolean available = true;

	public boolean isAvailable() {
		return this.available;
	}

	public void setAvailable(final boolean a) {
		this.available = a;
	}

	private boolean changed = false;

	public boolean isChanged() {
		return this.changed;
	}

	public void setChanged(final boolean c) {
		this.changed = c;
	}

	public void clearModelTower() {
		setExistingBlockIds(new ArrayList<>(0));
		getOnMap().clear();
		getHoldingMap().clear();
		clearDerivedPredicates();
	}

	public void clearDerivedPredicates() {
		getClearMap().clear();
		clearOrdered();
		clearTower();
	}

	public boolean isOnValid(final String objectIdX, final String objectIdY) {
		return getOnMap().get(objectIdX + sep + objectIdY) != null;
	}

	public boolean isOn(final String objectIdX, final String objectIdY) {
		return getOnMap().get(objectIdX + sep + objectIdY);
	}

	public void setOn(final String objectIdX, final String objectIdY, final boolean isOn) {
		getOnMap().put(objectIdX + sep + objectIdY, isOn);
	}

	private Map<String, Boolean> onMap;

	protected Map<String, Boolean> getOnMap() {
		if (this.onMap == null) {
			this.onMap = new HashMap<>();
		}
		return this.onMap;
	}

	public boolean isHoldingValid(final String blockId) {
		return getHoldingMap().get(blockId) != null;
	}

	public boolean isHolding(final String blockId) {
		final Object isHolding = getHoldingMap().get(blockId);
		return ((Boolean) isHolding);
	}

	public void setHolding(final String blockId, final boolean isHolding) {
		getHoldingMap().put(blockId, isHolding);
	}

	private Map<String, Boolean> holdingMap;

	protected Map<String, Boolean> getHoldingMap() {
		if (this.holdingMap == null) {
			this.holdingMap = new HashMap<>();
		}
		return this.holdingMap;
	}

	private List<String> blockIdList;

	public List<String> getExistingBlockIds() {
		return this.blockIdList;
	}

	public void setExistingBlockIds(final List<String> l) {
		this.blockIdList = l;
	}

	private String tableId;

	public String getTableId() {
		return this.tableId;
	}

	public void setTableId(final String t) {
		this.tableId = t;
	}

	public boolean isClear(final String blockId) {
		final Object isClear = getClearMap().get(blockId);
		return ((Boolean) isClear);
	}

	public void setClear(final String blockId, final boolean isClear) {
		getClearMap().put(blockId, isClear);
	}

	private Map<String, Boolean> clearMap;

	protected Map<String, Boolean> getClearMap() {
		if (this.clearMap == null) {
			this.clearMap = new HashMap<>();
		}
		return this.clearMap;
	}

	// --- P3
	public boolean isOrdered(final List<String> listOfBlocks) {
		return getOrderedSet().contains(listToString(listOfBlocks));
	}

	public void setOrdered(final List<String> listOfBlocks, final boolean isOrdered) {
		if (isOrdered) {
			getOrderedSet().add(listToString(listOfBlocks));
		} else {
			getOrderedSet().remove(listToString(listOfBlocks));
		}
	}

	public void clearOrdered() {
		getOrderedSet().clear();
	}

	private Set<String> orderedSet;

	protected Set<String> getOrderedSet() {
		if (this.orderedSet == null) {
			this.orderedSet = new HashSet<>();
		}
		return this.orderedSet;
	}

	public List<List<String>> getOrderedListList() {
		final List<List<String>> orderedListList = new LinkedList<>();
		final Iterator<String> i = getOrderedSet().iterator();
		while (i.hasNext()) {
			final String orderedListString = (i.next());
			orderedListList.add(stringToList(orderedListString));
		}
		return orderedListList;
	}

	public List<List<String>> getTowerListList() {
		final List<List<String>> towerListList = new LinkedList<>();
		final Iterator<String> i = getTowerSet().iterator();
		while (i.hasNext()) {
			final String towerListString = (i.next());
			towerListList.add(stringToList(towerListString));
		}
		return towerListList;
	}

	public boolean isTower(final List<String> listOfBlocks) {
		return getTowerSet().contains(listToString(listOfBlocks));
	}

	public void setTower(final List<String> listOfBlocks, final boolean isTower) {
		if (isTower) {
			getTowerSet().add(listToString(listOfBlocks));
		} else {
			getTowerSet().remove(listToString(listOfBlocks));
		}
	}

	public void clearTower() {
		getTowerSet().clear();
	}

	private Set<String> towerSet;

	protected Set<String> getTowerSet() {
		if (this.towerSet == null) {
			this.towerSet = new HashSet<>();
		}
		return this.towerSet;
	}
}
