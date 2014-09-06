package edu.stanford.robotics.trTower.virtualWorld;

import java.util.List;

public class VirtualWorldSensor {

    // --- attributes
    private VirtualWorld virtualWorld;
    VirtualWorld getVirtualWorld() { return virtualWorld; }
    void setVirtualWorld(VirtualWorld w) { virtualWorld = w; }

    private VirtualWorldModel virtualWorldModel;
    VirtualWorldModel getVirtualWorldModel() { return virtualWorldModel; }
    void setVirtualWorldModel(VirtualWorldModel m) { virtualWorldModel = m;}

    
    // --- public method
    public boolean isAvailable() { 
	return getVirtualWorldModel().isAvailable(); 
    }

    public boolean isChanged() {
	return getVirtualWorldModel().isChanged();
    }
//      public void setChanged(boolean c) {
//  	getVirtualWorldModel().setChanged(c);
//      }

    public boolean isOn(String objectIdX, String objectIdY) {
	return getVirtualWorldModel().isOn(objectIdX, objectIdY);
    }
		
    public boolean isHolding(String blockId) {
	Block blockHeld = getVirtualWorld().getRobotArm().getBlockHeld();
	if (blockHeld == null) {
	    return false;
	} else {
	    if (blockHeld.getId().equals(blockId))
		return true;
	    else
		return false;
	}
    }

	public List<String> getExistingBlockIds() {
		return getVirtualWorldModel().getExistingBlockIds();
	}
    
    public String getTableId() {
	return getVirtualWorldModel().getTableId();
    }
}
