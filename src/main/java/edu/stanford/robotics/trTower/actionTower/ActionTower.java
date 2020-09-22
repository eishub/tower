package edu.stanford.robotics.trTower.actionTower;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.stanford.robotics.trTower.StimulatorListener;
import edu.stanford.robotics.trTower.common.ListUtilities;
import edu.stanford.robotics.trTower.modelTower.ModelTower;
import edu.stanford.robotics.trTower.virtualWorld.VirtualWorld;

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
		final TRCallDescription d = new TRCallDescription();
		d.setTrFunction(TRCallDescription.NIL);
		setActiveTRCallDescription(d);
	}

	public ModelTower getModelTower() {
		return this.modelTower;
	}

	public void setModelTower(final ModelTower mt) {
		this.modelTower = mt;
	}

	public VirtualWorld getVirtualWorld() {
		return this.virtualWorld;
	}

	public void setVirtualWorld(final VirtualWorld vw) {
		this.virtualWorld = vw;
	}

	public TRCallDescription getActiveTRCallDescription() {
		return this.activeTRCallDescription;
	}

	public void setActiveTRCallDescription(final TRCallDescription d) {
		if (this.activeTRCallDescription != d) {
			this.activeTRCallDescription = d;
			this.activeTRCallDescriptionChanged = true;
		}
	}

	public List<TRCallDescription> getTRCallDescriptionList() {
		if (this.trCallDescriptionList == null) {
			this.trCallDescriptionList = new ArrayList<>();
		}
		return this.trCallDescriptionList;
	}

	protected void trMakeTower(final List<String> s) {
		final TRCallDescription tr = new TRCallDescription();
		tr.setTrFunction(TRCallDescription.MAKETOWER);
		tr.setParameterList(s);
		getTRCallDescriptionList().add(tr);
		makeTower(s);
	}

	protected void trMoveToTable(final String blockIdX) {
		final TRCallDescription tr = new TRCallDescription();
		tr.setTrFunction(TRCallDescription.MOVETOTABLE);
		tr.setParameterList(ListUtilities.newList(blockIdX));
		getTRCallDescriptionList().add(tr);
		moveToTable(blockIdX);
	}

	protected void trMove(final String blockIdX, final String blockIdY) {
		final TRCallDescription tr = new TRCallDescription();
		tr.setTrFunction(TRCallDescription.MOVE);
		tr.setParameterList(ListUtilities.newList(blockIdX, blockIdY));
		getTRCallDescriptionList().add(tr);
		move(blockIdX, blockIdY);
	}

	protected void trUnpile(final String blockIdX) {
		final TRCallDescription tr = new TRCallDescription();
		tr.setTrFunction(TRCallDescription.UNPILE);
		tr.setParameterList(ListUtilities.newList(blockIdX));
		getTRCallDescriptionList().add(tr);
		unpile(blockIdX);
	}

	protected void trPickup(final String blockIdX) {
		final TRCallDescription tr = new TRCallDescription();
		tr.setTrFunction(TRCallDescription.PICKUP);
		tr.setParameterList(ListUtilities.newList(blockIdX));
		getTRCallDescriptionList().add(tr);
		pickup(blockIdX);
	}

	protected void trPutdown(final String blockId, final String objectId) {
		final TRCallDescription tr = new TRCallDescription();
		tr.setTrFunction(TRCallDescription.PUTDOWN);
		tr.setParameterList(ListUtilities.newList(blockId, objectId));
		getTRCallDescriptionList().add(tr);
		putdown(blockId, objectId);
	}

	protected void trNil() {
		final TRCallDescription tr = new TRCallDescription();
		tr.setTrFunction(TRCallDescription.NIL);
		tr.setParameterList(new ArrayList<>(0));
		getTRCallDescriptionList().add(tr);
		nil();
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

	public boolean isValidBlockId(final String id) {
		return getVirtualWorld().isValidBlockId(id);
	}

	public boolean isValidTargetId(final String id) {
		return isValidBlockId(id) || id.equals(getVirtualWorld().getTableId());
	}

	@Override
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

		if (!this.activeTRCallDescriptionChanged) {
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

		final TRCallDescription atr = getActiveTRCallDescription();
		final List<String> p = atr.getParameterList();

		switch (atr.getTrFunction()) {
		case TRCallDescription.MAKETOWER:
			trMakeTower(p);
			break;
		case TRCallDescription.MOVETOTABLE:
			trMoveToTable((ListUtilities.car(p)));
			break;
		case TRCallDescription.MOVE:
			trMove((ListUtilities.car(p)), (ListUtilities.cadr(p)));
			break;
		case TRCallDescription.UNPILE:
			trUnpile((ListUtilities.car(p)));
			break;
		case TRCallDescription.PICKUP:
			trPickup((ListUtilities.car(p)));
			break;
		case TRCallDescription.PUTDOWN:
			trPutdown((ListUtilities.car(p)), (ListUtilities.cadr(p)));
			break;
		case TRCallDescription.NIL:
			trNil();
			break;
		}

	}

	// 2001-05-17. Based on the updated (from original paper)
	// ActionTower description.
	protected void makeTower(final List<String> s) {
		if (getModelTower().isTower(s)) {
			trNil();
		} else if (getModelTower().isOrdered(s)) {
			trUnpile((ListUtilities.car(s)));
		} else if (ListUtilities.cdr(s).size() == 0) {
			trMoveToTable((ListUtilities.car(s)));
		} else if (getModelTower().isTower(ListUtilities.cdr(s))) {
			trMove((ListUtilities.car(s)), (ListUtilities.cadr(s)));
		} else {
			trMakeTower(ListUtilities.cdr(s));
		}
	}

	protected void moveToTable(final String blockIdX) {
		// prepare variable(s) for testing conditions
		final String blockIdY = getBlockHeld();

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

	protected void move(final String blockIdX, final String blockIdY) {
		// prepare variable(s) for testing conditions
		final String blockIdZ = getBlockHeld();

		if (getModelTower().isOn(blockIdX, blockIdY)) {
			trNil();
		} else if (getModelTower().isHolding(blockIdX) && getModelTower().isClear(blockIdY)) {
			trPutdown(blockIdX, blockIdY);
		} else if (blockIdZ != null) {
			trPutdown(blockIdZ, getVirtualWorld().getTableId());
		} else if (getModelTower().isClear(blockIdX) && getModelTower().isClear(blockIdY)) {
			trPickup(blockIdX);
		} else if (getModelTower().isClear(blockIdY)) {
			trUnpile(blockIdX);
		} else {
			trUnpile(blockIdY);
		}
	}

	protected void unpile(final String blockIdX) {
		if (getModelTower().isClear(blockIdX)) {
			trNil();
		} else {
			final Iterator<String> i = getModelTower().getExistingBlockIds().iterator();
			while (i.hasNext()) {
				final String blockIdY = (i.next());
				if (getModelTower().isOn(blockIdY, blockIdX)) {
					trMoveToTable(blockIdY);
					return;
				}
			}
		}
	}

	protected void pickup(final String blockIdX) {
		getVirtualWorld().pickup(blockIdX);
	}

	protected void putdown(final String blockId, final String objectId) {
		getVirtualWorld().putdown(blockId, objectId);
	}

	protected void nil() {
		getVirtualWorld().nil();
	}

	protected String getBlockHeld() {
		final Iterator<String> i = getModelTower().getExistingBlockIds().iterator();
		while (i.hasNext()) {
			final String blockIdZ = (i.next());
			if (getModelTower().isHolding(blockIdZ)) {
				return blockIdZ;
			}
		}
		return null;
	}
}
