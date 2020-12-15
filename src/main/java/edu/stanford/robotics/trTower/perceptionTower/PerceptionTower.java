package edu.stanford.robotics.trTower.perceptionTower;

import java.util.Iterator;
import java.util.List;

import edu.stanford.robotics.trTower.StimulatorListener;
import edu.stanford.robotics.trTower.common.ListUtilities;
import edu.stanford.robotics.trTower.modelTower.ModelTower;
import edu.stanford.robotics.trTower.virtualWorld.VirtualWorldSensor;

/**
 * Wouter: this updates the percepts of the world. on(), clear() and ordered().
 *
 * These are updated by calls to getModelTower().setOn() setHolding() etc.
 */
public class PerceptionTower implements StimulatorListener {
	private VirtualWorldSensor virtualWorldSensor;

	public VirtualWorldSensor getVirtualWorldSensor() {
		return this.virtualWorldSensor;
	}

	public void setVirtualWorldSensor(final VirtualWorldSensor vws) {
		this.virtualWorldSensor = vws;
	}

	private ModelTower modelTower;

	public ModelTower getModelTower() {
		return this.modelTower;
	}

	public void setModelTower(final ModelTower mt) {
		this.modelTower = mt;
	}

	private boolean isPrimitivePredicateChanged;

	@Override
	public void stimuStep() {
		// if not sensor source not available just keep old data,
		// and set model tower to be unavailable
		if (!getVirtualWorldSensor().isAvailable()) {
			getModelTower().setAvailable(false);
			return;
		} else {
			getModelTower().setAvailable(true);
		}

		if (!getVirtualWorldSensor().isChanged()) {
			// not changed, nothing to do
			getModelTower().setChanged(false);
			return;
		}

		// === Rules based on TR paper "Draft of January 10, 2001"

		this.isPrimitivePredicateChanged = false;

		// --- R1, primitive recognizers

		// set existing block ids
		final List<String> virtualWorldSensorBlockList = getVirtualWorldSensor().getExistingBlockIds();
		if (!virtualWorldSensorBlockList.equals(getModelTower().getExistingBlockIds())) {
			// existing block list changed
			this.isPrimitivePredicateChanged = true;
			getModelTower().setChanged(true);
			getModelTower().clearModelTower();
			getModelTower().setExistingBlockIds(virtualWorldSensorBlockList);
		}

		// set primitive predicates

		// holding
		{
			final Iterator<String> i = getModelTower().getExistingBlockIds().iterator();
			while (i.hasNext()) {
				final String blockId = (i.next());
				final boolean isHolding = getVirtualWorldSensor().isHolding(blockId);
				if (getModelTower().isHoldingValid(blockId)) {
					// blockId exists
					if (isHolding != getModelTower().isHolding(blockId)) {
						this.isPrimitivePredicateChanged = true;
						getModelTower().setHolding(blockId, isHolding);
					}
				} else {
					// new blockId
					this.isPrimitivePredicateChanged = true;
					getModelTower().setHolding(blockId, isHolding);
				}
			}
		}
		// on
		{
			final Iterator<String> i = getModelTower().getExistingBlockIds().iterator();
			while (i.hasNext()) {
				final String blockIdX = (i.next());

				final Iterator<String> j = getModelTower().getExistingBlockIds().iterator();
				while (j.hasNext()) {
					final String blockIdY = (j.next());
					final boolean isOn = getVirtualWorldSensor().isOn(blockIdX, blockIdY);
					if (getModelTower().isOnValid(blockIdX, blockIdY)) {
						// blockIds exist
						if (isOn != getModelTower().isOn(blockIdX, blockIdY)) {
							this.isPrimitivePredicateChanged = true;
							getModelTower().setOn(blockIdX, blockIdY, isOn);
						}
					} else {
						// new blockId(s)
						this.isPrimitivePredicateChanged = true;
						getModelTower().setOn(blockIdX, blockIdY, isOn);
					}

				}
				// is X on table ?
				getModelTower().setOn(blockIdX, getTableId(), getVirtualWorldSensor().isOn(blockIdX, getTableId()));
			}
		}

		// After R1, if none of the primitive predicates changed,
		// nothing is changed.
		if (this.isPrimitivePredicateChanged == false) {
			getModelTower().setChanged(false);
			return;
		} else {
			getModelTower().setChanged(true);
			getModelTower().clearDerivedPredicates();
		}
		// --- R2
		// clear
		{
			final Iterator<String> y = getModelTower().getExistingBlockIds().iterator();
			while (y.hasNext()) {
				final String blockIdY = (y.next());
				boolean existXOnY = false;
				final Iterator<String> x = getModelTower().getExistingBlockIds().iterator();
				while (x.hasNext()) {
					final String blockIdX = (x.next());
					if (getModelTower().isOn(blockIdX, blockIdY)) {
						existXOnY = true;
					}
				}
				// after the while loop, if there doesn't exist an x that is On(x, y),
				// then existXOnY is false
				if (!existXOnY && !getModelTower().isHolding(blockIdY)) {
					getModelTower().setClear(blockIdY, true);
				} else {
					getModelTower().setClear(blockIdY, false);
				}
			}
		}
		// --- R3
		// ordered
		{
			final Iterator<String> x = getModelTower().getExistingBlockIds().iterator();
			while (x.hasNext()) {
				final String blockIdX = (x.next());
				if (getModelTower().isOn(blockIdX, getTableId())) {
					getModelTower().setOrdered(ListUtilities.newList(blockIdX), true);
				}
			}
		}
		// --- R4
		// ordered
		{
			boolean cont;
			int c = 1;
			do {
				cont = false;

				final List<List<String>> listC = ListUtilities.listLengthFilter(getModelTower().getOrderedListList(),
						c);
				final Iterator<List<String>> ic = listC.iterator();
				while (ic.hasNext()) {
					cont = true;
					final List<String> orderedList = ic.next();

					final Iterator<String> x = getModelTower().getExistingBlockIds().iterator();
					while (x.hasNext()) {
						final String blockIdX = (x.next());
						if (getModelTower().isOn(blockIdX, (ListUtilities.car(orderedList)))) {
							getModelTower().setOrdered(ListUtilities.cons(blockIdX, orderedList), true);
						}
					}
				}

				c++;
			} while (cont);

		}
		// --- R5
		{
			final Iterator<List<String>> s = getModelTower().getOrderedListList().iterator();
			while (s.hasNext()) {
				final List<String> listItem = s.next();
				if (getModelTower().isClear((ListUtilities.car(listItem)))) {
					getModelTower().setTower(listItem, true);
				}
			}
		}

	}

	protected String getTableId() {
		return getVirtualWorldSensor().getTableId();
	}
}
