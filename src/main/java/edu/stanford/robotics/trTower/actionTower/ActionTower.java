package edu.stanford.robotics.trTower.actionTower;

import java.util.*;

import edu.stanford.robotics.trTower.virtualWorld.*;
import edu.stanford.robotics.trTower.modelTower.*;
import edu.stanford.robotics.trTower.common.*;
import edu.stanford.robotics.trTower.*;

/**
 * There is an internal trCallDescriptionList containing a list of
 * TRCallDescription objects.
 */
public class ActionTower implements StimulatorListener {
	
	private ModelTower modelTower;
	private VirtualWorld virtualWorld;
	private boolean activeTRCallDescriptionChanged = false;
	private TRCallDescription activeTRCallDescription;
	private List<TRCallDescription> trCallDescriptionList;

	public ActionTower() {
		reset();
	}

	public void reset() {
		TRCallDescription d = new TRCallDescription();
		d.setTrFunction(TRCallDescription.NIL);
		setActiveTRCallDescription(d);
	}

	public ModelTower getModelTower() {
		return modelTower;
	}

	public void setModelTower(ModelTower mt) {
		modelTower = mt;
	}

	public VirtualWorld getVirtualWorld() {
		return virtualWorld;
	}

	public void setVirtualWorld(VirtualWorld vw) {
		virtualWorld = vw;
	}

	public TRCallDescription getActiveTRCallDescription() {
		return activeTRCallDescription;
	}

	public void setActiveTRCallDescription(TRCallDescription d) {
		if (activeTRCallDescription != d) {
			activeTRCallDescription = d;
			activeTRCallDescriptionChanged = true;
		}
	}

	// --- public components

	public List<TRCallDescription> getTRCallDescriptionList() {
		if (trCallDescriptionList == null) {
			trCallDescriptionList = new ArrayList<TRCallDescription>();
		}
		return trCallDescriptionList;
	}

	// --- tr wrappers
	
	protected void trMakeTower(List s) {
		TRCallDescription tr = new TRCallDescription();
		tr.setTrFunction(TRCallDescription.MAKETOWER);
		tr.setParameterList(s);
		getTRCallDescriptionList().add(tr);
		makeTower(s);
	}

	protected void trMoveToTable(String blockIdX) {
		TRCallDescription tr = new TRCallDescription();
		tr.setTrFunction(TRCallDescription.MOVETOTABLE);
		tr.setParameterList(ListUtilities.newList(blockIdX));
		getTRCallDescriptionList().add(tr);
		moveToTable(blockIdX);
	}

	protected void trMove(String blockIdX, String blockIdY) {
		TRCallDescription tr = new TRCallDescription();
		tr.setTrFunction(TRCallDescription.MOVE);
		tr.setParameterList(ListUtilities.newList(blockIdX, blockIdY));
		getTRCallDescriptionList().add(tr);
		move(blockIdX, blockIdY);
	}

	protected void trUnpile(String blockIdX) {
		TRCallDescription tr = new TRCallDescription();
		tr.setTrFunction(TRCallDescription.UNPILE);
		tr.setParameterList(ListUtilities.newList(blockIdX));
		getTRCallDescriptionList().add(tr);
		unpile(blockIdX);
	}

	protected void trPickup(String blockIdX) {
		TRCallDescription tr = new TRCallDescription();
		tr.setTrFunction(TRCallDescription.PICKUP);
		tr.setParameterList(ListUtilities.newList(blockIdX));
		getTRCallDescriptionList().add(tr);
		pickup(blockIdX);
	}

	protected void trPutdown(String blockId, String objectId) {
		TRCallDescription tr = new TRCallDescription();
		tr.setTrFunction(TRCallDescription.PUTDOWN);
		tr.setParameterList(ListUtilities.newList(blockId, objectId));
		getTRCallDescriptionList().add(tr);
		putdown(blockId, objectId);
	}

	protected void trNil() {
		TRCallDescription tr = new TRCallDescription();
		tr.setTrFunction(TRCallDescription.NIL);
		tr.setParameterList(new ArrayList());
		getTRCallDescriptionList().add(tr);
		nil();
	}

	// --- attributes
	private boolean toBeReset = false;

	public boolean isToBeReset() {
		return toBeReset;
	}

	public void setToBeReset(boolean r) {
		toBeReset = r;
	}

	private boolean available = true;

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean a) {
		available = a;
	}

	private boolean changed = false;

	public boolean isChanged() {
		return changed;
	}

	public void setChanged(boolean c) {
		changed = c;
	}

	// --- public methods

	public boolean isValidBlockId(String id) {
		return getVirtualWorld().isValidBlockId(id);
	}

	public boolean isValidTargetId(String id) {
		return isValidBlockId(id) || id.equals(getVirtualWorld().getTableId());
	}

	public void stimuStep() {

		if (isToBeReset()) {
			reset();
			setToBeReset(false);
		}

		if (!getModelTower().isAvailable()) {
			setAvailable(false);
			return;
		} else {
			setAvailable(true);
		}

		if (!activeTRCallDescriptionChanged) {
			// no new durative action call set
			if (!getModelTower().isChanged()) {
				// model tower not changed
				setChanged(false);
				return;
			}
		}

		setChanged(true);

		// --- reset
		getTRCallDescriptionList().clear();

		TRCallDescription atr = getActiveTRCallDescription();
		List p = atr.getParameterList();

		switch (atr.getTrFunction()) {
		case TRCallDescription.MAKETOWER:
			trMakeTower(p);
			break;
		case TRCallDescription.MOVETOTABLE:
			trMoveToTable((String) (ListUtilities.car(p)));
			break;
		case TRCallDescription.MOVE:
			trMove((String) (ListUtilities.car(p)), (String) (ListUtilities
					.cadr(p)));
			break;
		case TRCallDescription.UNPILE:
			trUnpile((String) (ListUtilities.car(p)));
			break;
		case TRCallDescription.PICKUP:
			trPickup((String) (ListUtilities.car(p)));
			break;
		case TRCallDescription.PUTDOWN:
			trPutdown((String) (ListUtilities.car(p)), (String) (ListUtilities
					.cadr(p)));
			break;
		case TRCallDescription.NIL:
			trNil();
			break;
		}

	}

	// --- TR sequences

	// 2001-05-17. Based on the updated (from original paper)
	// ActionTower description.

	protected void makeTower(List s) {

		if (getModelTower().isTower(s)) {
			trNil();
		} else if (getModelTower().isOrdered(s)) {
			trUnpile((String) (ListUtilities.car(s)));
		} else if (ListUtilities.cdr(s).size() == 0) {
			trMoveToTable((String) (ListUtilities.car(s)));
		} else if (getModelTower().isTower(ListUtilities.cdr(s))) {
			trMove((String) (ListUtilities.car(s)), (String) (ListUtilities
					.cadr(s)));
		} else {
			trMakeTower(ListUtilities.cdr(s));
		}
	}

	protected void moveToTable(String blockIdX) {

		// prepare variable(s) for testing conditions
		String blockIdY = getBlockHeld();

		if (getModelTower().isOn(blockIdX, getVirtualWorld().getTableId())) {
			trNil();
		} else if (blockIdY != null) {
			trPutdown(blockIdY, getVirtualWorld().getTableId());
		} else if (getModelTower().isClear(blockIdX)) {
			trPickup(blockIdX);
		} else {
			trUnpile(blockIdX);
		}
	}

	protected void move(String blockIdX, String blockIdY) {

		// prepare variable(s) for testing conditions
		String blockIdZ = getBlockHeld();

		if (getModelTower().isOn(blockIdX, blockIdY)) {
			trNil();
		} else if (getModelTower().isHolding(blockIdX)
				&& getModelTower().isClear(blockIdY)) {
			trPutdown(blockIdX, blockIdY);
		} else if (blockIdZ != null) {
			trPutdown(blockIdZ, getVirtualWorld().getTableId());
		} else if (getModelTower().isClear(blockIdX)
				&& getModelTower().isClear(blockIdY)) {
			trPickup(blockIdX);
		} else if (getModelTower().isClear(blockIdY)) {
			trUnpile(blockIdX);
		} else {
			trUnpile(blockIdY);
		}
	}

	protected void unpile(String blockIdX) {

		if (getModelTower().isClear(blockIdX)) {
			trNil();
		} else {
			Iterator i = getModelTower().getExistingBlockIds().iterator();
			while (i.hasNext()) {
				String blockIdY = (String) (i.next());
				if (getModelTower().isOn(blockIdY, blockIdX)) {
					trMoveToTable(blockIdY);
					return;
				}
			}
		}
	}

	protected void pickup(String blockIdX) {
		getVirtualWorld().pickup(blockIdX);
	}

	protected void putdown(String blockId, String objectId) {
		getVirtualWorld().putdown(blockId, objectId);
	}

	protected void nil() {
		getVirtualWorld().nil();
	}

	// --- helpers
	protected String getBlockHeld() {
		Iterator i = getModelTower().getExistingBlockIds().iterator();
		while (i.hasNext()) {
			String blockIdZ = (String) (i.next());
			if (getModelTower().isHolding(blockIdZ))
				return blockIdZ;
		}
		return null;
	}
}
