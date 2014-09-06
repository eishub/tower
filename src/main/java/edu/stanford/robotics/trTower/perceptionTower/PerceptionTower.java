package edu.stanford.robotics.trTower.perceptionTower;

import java.util.*;

import edu.stanford.robotics.trTower.virtualWorld.*;
import edu.stanford.robotics.trTower.modelTower.*;
import edu.stanford.robotics.trTower.common.*;
import edu.stanford.robotics.trTower.*;


/** Wouter: this updates the percepts of the world. 
on(), clear() and ordered().

These are updated by calls to getModelTower().setOn()  setHolding() etc.

*/

public class PerceptionTower implements StimulatorListener {


    // --- attributes
    
    private VirtualWorldSensor virtualWorldSensor;
    public VirtualWorldSensor getVirtualWorldSensor() { return virtualWorldSensor; }
    public void setVirtualWorldSensor(VirtualWorldSensor vws) { virtualWorldSensor = vws; }

    private ModelTower modelTower;
    public ModelTower getModelTower() { return modelTower; }
    public void setModelTower(ModelTower mt) { modelTower = mt; }

    // --- public methods

    public void microStep() {
	// not supported for now
    }
    public void parallelStep() {
	// not supported for now
    }

    private boolean isPrimitivePredicateChanged;

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

	isPrimitivePredicateChanged = false;
	
	// --- R1, primative recognizers

	// set existing block ids
	List virtualWorldSensorBlockList =
	    getVirtualWorldSensor().getExistingBlockIds();
	if (!virtualWorldSensorBlockList.equals(getModelTower().getExistingBlockIds())) {
	    // existing block list changed
	    isPrimitivePredicateChanged = true;
	    getModelTower().setChanged(true);
	    getModelTower().clearModelTower();
	    getModelTower().setExistingBlockIds(virtualWorldSensorBlockList);
	}
	
	// set primitive predicates

	// holding
	{
	    Iterator i = getModelTower().getExistingBlockIds().iterator();
	    while (i.hasNext()) {
		String blockId = (String)(i.next());
		boolean isHolding = 
		    getVirtualWorldSensor().isHolding(blockId);
		if (getModelTower().isHoldingValid(blockId)) {
		    // blockId exists
		    if (isHolding != getModelTower().isHolding(blockId)) {
			isPrimitivePredicateChanged = true;
			getModelTower().setHolding(blockId, isHolding);
		    }
		} else {
		    // new blockId
		    isPrimitivePredicateChanged = true;
		    getModelTower().setHolding(blockId, isHolding);
		}
	    }
	}
	// on
	{
	    Iterator i = getModelTower().getExistingBlockIds().iterator();
	    while (i.hasNext()) {
		String blockIdX = (String)(i.next());

		Iterator j = getModelTower().getExistingBlockIds().iterator();
		while (j.hasNext()) {
		    String blockIdY = (String)(j.next());
		    boolean isOn = 
			getVirtualWorldSensor().isOn(blockIdX, blockIdY);
		    if (getModelTower().isOnValid(blockIdX, blockIdY)) {
			// blockIds exist
			if (isOn != getModelTower().isOn(blockIdX, blockIdY)) {
			    isPrimitivePredicateChanged = true;
			    getModelTower().setOn(blockIdX, blockIdY, isOn);
			}
		    } else {
			// new blockId(s)
			isPrimitivePredicateChanged = true;
			getModelTower().setOn(blockIdX, blockIdY, isOn);
		    }
		    
		}
		// is X on table ?
		getModelTower().setOn(blockIdX, getTableId(),
				      getVirtualWorldSensor().isOn(blockIdX, getTableId()));
	    }
	}

	// After R1, if none of the primitive predicates changed,
	// nothing is changed.
	if (isPrimitivePredicateChanged == false) {
	    getModelTower().setChanged(false);
	    return;
	} else {
	    getModelTower().setChanged(true);
	    getModelTower().clearDerivedPredicates();
	}

	// --- R2
	// clear
	{
	    Iterator y = getModelTower().getExistingBlockIds().iterator();
	    while (y.hasNext()) { 
		String blockIdY = (String)(y.next());
		boolean existXOnY = false;
		Iterator x = getModelTower().getExistingBlockIds().iterator();
		while (x.hasNext()) {
		    String blockIdX = (String)(x.next());
		    if (getModelTower().isOn(blockIdX, blockIdY))
			existXOnY = true;
		}
		// after the while loop, if there doesn't exist an x that is On(x, y), 
		// then existXOnY is false
		if (!existXOnY && !getModelTower().isHolding(blockIdY))
		    getModelTower().setClear(blockIdY, true);
		else
		    getModelTower().setClear(blockIdY, false);
	    }
	}

	// --- R3
	// ordered
	{
	    Iterator x = getModelTower().getExistingBlockIds().iterator();
	    while (x.hasNext()) {
		String blockIdX = (String)(x.next());
		if (getModelTower().isOn(blockIdX, getTableId()))
		    getModelTower().setOrdered(ListUtilities.newList(blockIdX), true);
	    }
	}

	// --- R4
	// ordered
	{
	    boolean cont;
	    int c = 1;
	    do {
		cont = false;

		List listC = ListUtilities.listLengthFilter(getModelTower().getOrderedListList(), c);
		Iterator ic = listC.iterator();
		while (ic.hasNext()) {
		    cont = true;
		    List orderedList = (List)(ic.next());

		    Iterator x = getModelTower().getExistingBlockIds().iterator();
		    while (x.hasNext()) {
			String blockIdX = (String)(x.next());
			if (getModelTower().isOn(blockIdX, (String)(ListUtilities.car(orderedList))))
			    getModelTower().setOrdered(ListUtilities.cons(blockIdX, orderedList), true);
		    }
		}

		c++;
	    } while (cont);

	}

	// --- R5
	{
	    Iterator s = getModelTower().getOrderedListList().iterator();
	    while (s.hasNext()) {
		List listItem = (List)(s.next());
		if (getModelTower().isClear((String)(ListUtilities.car(listItem))))
		    getModelTower().setTower(listItem, true);
	    }
	}

    }
    
    // --- helpers
    protected String getTableId() { return getVirtualWorldSensor().getTableId(); }
}
