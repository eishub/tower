package edu.stanford.robotics.trTower.virtualWorld;

import java.util.List;

public class VirtualWorldSensor {
	private VirtualWorld virtualWorld;

	VirtualWorld getVirtualWorld() {
		return this.virtualWorld;
	}

	void setVirtualWorld(final VirtualWorld w) {
		this.virtualWorld = w;
	}

	private VirtualWorldModel virtualWorldModel;

	VirtualWorldModel getVirtualWorldModel() {
		return this.virtualWorldModel;
	}

	void setVirtualWorldModel(final VirtualWorldModel m) {
		this.virtualWorldModel = m;
	}

	public boolean isAvailable() {
		return getVirtualWorldModel().isAvailable();
	}

	public boolean isChanged() {
		return getVirtualWorldModel().isChanged();
	}

	public boolean isOn(final String objectIdX, final String objectIdY) {
		return getVirtualWorldModel().isOn(objectIdX, objectIdY);
	}

	public boolean isHolding(final String blockId) {
		final Block blockHeld = getVirtualWorld().getRobotArm().getBlockHeld();
		if (blockHeld == null) {
			return false;
		} else {
			if (blockHeld.getId().equals(blockId)) {
				return true;
			} else {
				return false;
			}
		}
	}

	public List<String> getExistingBlockIds() {
		return getVirtualWorldModel().getExistingBlockIds();
	}

	public String getTableId() {
		return getVirtualWorldModel().getTableId();
	}
}
