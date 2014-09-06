package edu.stanford.robotics.trTower.virtualWorld;

import java.awt.*;
import java.awt.geom.*;
import java.util.*;

import java.util.List;

/**
 * Holds the blocks and renders and manipulates them. if user is manipulating,
 * isAvailable returns false.
 */
public class VirtualWorldModel {

	private boolean available = true;

	/**
	 * @returns false if user is manipulating the world (drags objects with the
	 *          mouse)
	 */
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

	// properties
	private String tableId;

	public String getTableId() {
		return tableId;
	}

	void setTableId(String s) {
		tableId = s;
	}

	private double xStep;

	double getXStep() {
		return xStep;
	}

	void setXStep(double x) {
		xStep = x;
	}

	private double yStep;

	double getYStep() {
		return yStep;
	}

	void setYStep(double y) {
		yStep = y;
	}

	private double xFloatErrorMargin;

	double getXFloatErrorMargin() {
		return xFloatErrorMargin;
	}

	void setXFloatErrorMargin(double x) {
		xFloatErrorMargin = x;
	}

	private double yFloatErrorMargin;

	double getYFloatErrorMargin() {
		return yFloatErrorMargin;
	}

	void setYFloatErrorMargin(double y) {
		yFloatErrorMargin = y;
	}

	private double blockWidth;

	double getBlockWidth() {
		return blockWidth;
	}

	void setBlockWidth(double w) {
		blockWidth = w;
	}

	private double blockHeight;

	double getBlockHeight() {
		return blockHeight;
	}

	void setBlockHeight(double h) {
		blockHeight = h;
	}

	private double columnWidth;

	double getColumnWidth() {
		return columnWidth;
	}

	void setColumnWidth(double w) {
		columnWidth = w;
	}

	private double leftMargin;

	double getLeftMargin() {
		return leftMargin;
	}

	void setLeftMargin(double l) {
		leftMargin = l;
	}

	private double rightMargin;

	double getRightMargin() {
		return rightMargin;
	}

	void setRightMargin(double r) {
		rightMargin = r;
	}

	private double topMargin;

	double getTopMargin() {
		return topMargin;
	}

	void setTopMargin(double t) {
		topMargin = t;
	}

	private double bottomMargin;

	double getBottomMargin() {
		return bottomMargin;
	}

	void setBottomMargin(double b) {
		bottomMargin = b;
	}

	private Point2D armRestCoord;

	Point2D getArmRestCoord() {
		return armRestCoord;
	}

	void setArmRestCoord(Point2D r) {
		armRestCoord = r;
	}

	void setArmRestCoord(double x, double y) {
		armRestCoord = new Point2D.Double(x, y);
	}

	private double armLengthClearence;

	double getArmLengthClearence() {
		return armLengthClearence;
	}

	void setArmLengthClearence(double c) {
		armLengthClearence = c;
	}

	private double virtualWorldWidth;

	double getVirtualWorldWidth() {
		return virtualWorldWidth;
	}

	void setVirtualWorldWidth(double w) {
		virtualWorldWidth = w;
	}

	private double virtualWorldHeight;

	double getVirtualWorldHeight() {
		return virtualWorldHeight;
	}

	void setVirtualWorldHeight(double h) {
		virtualWorldHeight = h;
	}

	// derived
	public double getFloorY() {
		return getVirtualWorldHeight() - getBottomMargin();
	}

	double getCeilingY() {
		return getTopMargin();
	}

	void render(Graphics g) {
		// first render blocks that are not held
		Iterator iBlock = getVirtualObjectList().iterator();
		while (iBlock.hasNext()) {
			Object oBlock = iBlock.next();
			if (oBlock instanceof Block) {
				Block block = (Block) oBlock;
				if (block.isCoordLegal() && !block.isBeingHeld())
					block.render(g);
			}
		}

		// then render robot arm or block being held
		Iterator iHold = getVirtualObjectList().iterator();
		while (iHold.hasNext()) {
			Object oHold = iHold.next();
			if (oHold instanceof RobotArm) {
				RobotArm robotArm = (RobotArm) oHold;
				if (robotArm.isCoordLegal())
					robotArm.render(g);
			} else if (oHold instanceof Block) {
				Block heldBlock = (Block) oHold;
				if (heldBlock.isCoordLegal() && heldBlock.isBeingHeld())
					heldBlock.render(g);
			}
		}

		// then render objects with illegal coord (so they appear on top)
		Iterator j = getVirtualObjectList().iterator();
		while (j.hasNext()) {
			VirtualObject voIllegal = (VirtualObject) j.next();
			if (!voIllegal.isCoordLegal())
				voIllegal.render(g);
		}
	}

	private List<VirtualObject> virtualObjectList;

	/**
	 * Returns list of all virtual objects.
	 */
	public List<VirtualObject> getVirtualObjectList() {
		if (virtualObjectList == null) {
			virtualObjectList = new ArrayList<VirtualObject>();
		}
		return virtualObjectList;
	}

	// private String statusMessage;
	// public String getStatusMessage() { return statusMessage; }
	// public void setStatusMessage(String s) { statusMessage = s; }

	// methods
	void reset() {
		getVirtualObjectList().clear();
		setChanged(true);
	}

	void addVirtualObject(VirtualObject vo) {

		if (vo.getId() == null) {
			System.out
					.println("VirtualWorldModel.addVirtualObject> vo.getId() is null");
			System.exit(-1);
		}
		if (vo.getPaint() == null) {
			System.out
					.println("VirtualWorldModel.addVirtualObject> vo.getPaint() is null");
			System.exit(-1);
		}

		if (findVirtualObject(vo.getId()) != null) {
			System.out
					.println("VirtualWorldModel.addVirtualObject> vo.getId() already exists");
			System.exit(-1);
		}
		getVirtualObjectList().add(vo);
	}

	// --- geometry of VirtualWorld
	int getMaxNumOfColumns() {

		double maxNumOfColumns = (getVirtualWorldWidth() - getLeftMargin() - getRightMargin())
				/ getColumnWidth();
		// System.out.println("getMaxNumOfColumns()= " + maxNumOfColumns);
		// System.out.println("(int)getMaxNumOfColumns()= " +
		// (int)maxNumOfColumns);
		return (int) maxNumOfColumns;
	}

	int getMaxNumOfRows() {
		double maxNumOfRows = (getVirtualWorldHeight()
				- getArmLengthClearence() - getTopMargin() - getBottomMargin())
				/ getBlockHeight();
		// System.out.println("getMaxNumberOfRows()= " + maxNumOfRows);
		// System.out.println("(int)getMaxNumberOfRows()= " +
		// (int)maxNumOfRows);
		return (int) maxNumOfRows;
	}

	// --- lower level query
	// returns null if not found
	public VirtualObject findVirtualObject(String id) {
		Iterator i = getVirtualObjectList().iterator();
		VirtualObject vo;
		while (i.hasNext()) {
			vo = (VirtualObject) (i.next());
			if (vo.getId().equals(id))
				return vo;
		}
		return null;
	}

	boolean isValidBlockId(String id) {
		Object vo = findVirtualObject(id);
		return vo instanceof Block ? true : false;
	}

	// returns null if not found
	// VirtualObject findVirtualObjectAt(Point2D coord) {
	// return findVirtualObjectAt(coord.getX(), coord.getY());
	// }
	// VirtualObject findVirtualObjectAt(double x, double y) {
	// Iterator i = getVirtualObjectList().iterator();
	// VirtualObject vo;
	// while (i.hasNext()) {
	// vo = (VirtualObject)(i.next());
	// if (isXCloseEnough(vo.getCoord().getX(), x) &&
	// isYCloseEnough(vo.getCoord().getY(), y))
	// return vo;
	// }
	// return null;
	// }
	Block findBlockAt(Point2D coord) {
		return findBlockAt(coord.getX(), coord.getY());
	}

	Block findBlockAt(double x, double y) {
		Iterator i = getVirtualObjectList().iterator();
		VirtualObject vo;
		while (i.hasNext()) {
			vo = (VirtualObject) (i.next());
			if (vo instanceof Block && isXCloseEnough(vo.getCoord().getX(), x)
					&& isYCloseEnough(vo.getCoord().getY(), y))
				return (Block) vo;
		}
		return null;
	}

	// VirtualObject findLegalCoordVirtualObjectAt(Point2D coord) {
	// return findLegalCoordVirtualObjectAt(coord.getX(), coord.getY());
	// }
	// VirtualObject findLegalCoordVirtualObjectAt(double x, double y) {
	// Iterator i = getVirtualObjectList().iterator();
	// VirtualObject vo;
	// while (i.hasNext()) {
	// vo = (VirtualObject)(i.next());
	// if (vo.isCoordLegal() &&
	// isXCloseEnough(vo.getCoord().getX(), x) &&
	// isYCloseEnough(vo.getCoord().getY(), y))
	// return vo;
	// }
	// return null;
	// }
	Block findLegalCoordBlockAt(Point2D coord) {
		return findLegalCoordBlockAt(coord.getX(), coord.getY());
	}

	Block findLegalCoordBlockAt(double x, double y) {
		Iterator i = getVirtualObjectList().iterator();
		VirtualObject vo;
		while (i.hasNext()) {
			vo = (VirtualObject) (i.next());
			if (vo.isCoordLegal() && vo instanceof Block
					&& isXCloseEnough(vo.getCoord().getX(), x)
					&& isYCloseEnough(vo.getCoord().getY(), y))
				return (Block) vo;
		}
		return null;
	}

	Block findLegalCoordUnheldBlockAt(Point2D coord) {
		return findLegalCoordUnheldBlockAt(coord.getX(), coord.getY());
	}

	Block findLegalCoordUnheldBlockAt(double x, double y) {
		Iterator i = getVirtualObjectList().iterator();
		VirtualObject vo;
		while (i.hasNext()) {
			vo = (VirtualObject) (i.next());
			if (vo.isCoordLegal() && vo instanceof Block
					&& isXCloseEnough(vo.getCoord().getX(), x)
					&& isYCloseEnough(vo.getCoord().getY(), y)) {
				Block block = (Block) vo;
				if (!block.isBeingHeld())
					return block;
			}
		}
		return null;
	}

	Block findOtherLegalCoordUnheldBlockAt(Point2D coord, Block block) {
		return findOtherLegalCoordUnheldBlockAt(coord.getX(), coord.getY(),
				block);
	}

	Block findOtherLegalCoordUnheldBlockAt(double x, double y, Block block) {
		Iterator i = getVirtualObjectList().iterator();
		VirtualObject vo;
		while (i.hasNext()) {
			vo = (VirtualObject) (i.next());
			if (vo instanceof Block) {

				Block candidateBlock = (Block) vo;

				if (candidateBlock != block && candidateBlock.isCoordLegal()
						&& !candidateBlock.isBeingHeld()
						&& isXCloseEnough(candidateBlock.getCoord().getX(), x)
						&& isYCloseEnough(candidateBlock.getCoord().getY(), y))
					return candidateBlock;
			}
		}
		return null;
	}

	List<String> getExistingBlockIds() {
		Iterator<VirtualObject> i = getVirtualObjectList().iterator();
		Object o;
		List<String> blockIdList = new ArrayList<String>();
		while (i.hasNext()) {
			o = i.next();
			if (o instanceof Block) {
				Block b = (Block) o;
				blockIdList.add(b.getId());
			}
		}
		return blockIdList;
	}

	// --- easy high level model query
	// being held is NOT clear
	public boolean isBlockClear(Block b) {
		Block block = findLegalCoordUnheldBlockAt(b.getCoord().getX(), b
				.getCoord().getY()
				- getBlockHeight());
		if (b.isBeingHeld()) {
			// block is being held, not clear
			return false;
		} else {
			return block == null ? true : false;
		}
	}

	// Block getBlockUnder(String blockId) {
	// VirtualObject vo = findVirtualObject(blockId);
	// if (!(vo instanceof Block))
	// return null;
	// else
	// return getBlockUnder((Block)vo);
	// }

	// Block getBlockUnder(Block b) {
	// VirtualObject vo =
	// findVirtualObjectAt(b.getCoord().getX(),
	// b.getCoord().getY()+getBlockHeight());
	// if (!(vo instanceof Block))
	// return null;
	// else
	// return (Block)vo;

	// }

	Block getBlockOnTopOf(String blockId) {
		VirtualObject vo = findVirtualObject(blockId);
		if (!(vo instanceof Block))
			return null;
		else
			return getBlockOnTopOf((Block) vo);
	}

	Block getBlockOnTopOf(Block b) {
		return findLegalCoordUnheldBlockAt(b.getCoord().getX(), b.getCoord()
				.getY()
				- getBlockHeight());
	}

	boolean isBlockOnTable(String blockId) {
		VirtualObject vo = findVirtualObject(blockId);
		if (!(vo instanceof Block))
			return false;
		else
			return isBlockOnTable((Block) vo);
	}

	boolean isBlockOnTable(Block b) {
		if (b.isBeingHeld())
			// the object is not setted, so not on table yet
			return false;
		else if (isYCloseEnough(b.getCoord().getY(), getFloorY()))
			return true;
		else
			return false;
	}

	/**
	 * @modified: 15apr09: made public.
	 */
	public boolean isOn(String objectIdX, String objectIdY) {

		if (getTableId().equals(objectIdY)) {
			// is block on Table?
			return isBlockOnTable(objectIdX);
		} else {
			Block b = getBlockOnTopOf(objectIdY);
			if (b == null)
				return false;
			else if (b.getId().equals(objectIdX))
				return true;
			else
				return false;
		}
	}

	int getNumOfBlocks() {
		Iterator i = getVirtualObjectList().iterator();
		VirtualObject vo;
		int sum = 0;
		while (i.hasNext()) {
			vo = (VirtualObject) (i.next());
			if (vo instanceof Block)
				sum++;
		}
		return sum;
	}

	boolean isColumnEmpty(int columnNum) {
		Block block = findLegalCoordUnheldBlockAt(columnToXCoord(columnNum),
				getFloorY());
		return block == null ? true : false;
	}

	boolean isColumnOccupied(int columnNum) {
		Block block = findLegalCoordBlockAt(columnToXCoord(columnNum),
				getFloorY());
		return block == null ? false : true;
	}

	// returns -1 if all full
	int findFirstEmptyColumn() {
		for (int i = 0; i < getMaxNumOfColumns(); i++)
			if (isColumnEmpty(i))
				return i;
		return -1;
	}

	int findFirstNonOccupiedColumn() {
		for (int i = 0; i < getMaxNumOfColumns(); i++)
			if (!isColumnOccupied(i))
				return i;
		return -1;
	}

	public int findClosestEmptyColumnFromXCoord(double xCoord) {
		return findClosestEmptyColumnFromColumn(xCoordToClosestColumn(xCoord));
	}

	int findClosestEmptyColumnFromColumn(int currentColumn) {

		if (isColumnEmpty(currentColumn)) {
			return currentColumn;
		}

		boolean exhaustedLeft = false;
		boolean exhaustedRight = false;
		int left;
		int right;

		for (int delta = 1; !(exhaustedRight && exhaustedLeft); delta++) {

			left = currentColumn - delta;
			exhaustedLeft = left < 0 ? true : false;
			if (!exhaustedLeft) {
				if (isColumnEmpty(left))
					return left;
			} else {
				exhaustedLeft = true;
			}

			right = currentColumn + delta;
			exhaustedRight = right >= getMaxNumOfColumns() ? true : false;
			if (!exhaustedRight) {
				if (isColumnEmpty(right))
					return right;
			} else {
				exhaustedRight = true;
			}

		}
		return -1;
	}

	// --- helper
	boolean isMaxNumOfBlocksReached() {
		return getNumOfBlocks() >= getMaxNumOfBlocks() ? true : false;
	}

	int getMaxNumOfBlocks() {
		return Math.min(getMaxNumOfColumns(), getMaxNumOfRows());
	}

	void addBlockInFirstNonEmptyColumn(Block b) {

		// setStatusMessage("");
		if (isMaxNumOfBlocksReached()) {
			System.out.println("A maximum of " + getMaxNumOfBlocks()
					+ " blocks already exist.");
			// setStatusMessage("A maximum of " + maxNumOfBlocks +
			// " blocks already exist.");
			return;
		}

		int i = findFirstEmptyColumn();
		if (i < 0) {
			System.out
					.println("VirtualWorldModel.addBlockInFristNonEmptyColumn> Should NOT happen! Cannot find empty column.");
			System.exit(-1);
			return;
		}
		// add block to empty column
		b.setCoord(columnToXCoord(i), getFloorY());
		addVirtualObject(b);
		setChanged(true);
	}

	void addBlockInFirstNonOccupiedColumn(Block b) {

		// setStatusMessage("");
		int maxNumOfBlocks = Math.min(getMaxNumOfColumns(), getMaxNumOfRows());
		if (getNumOfBlocks() >= maxNumOfBlocks) {
			System.out.println("A maximum of " + maxNumOfBlocks
					+ " blocks already exist.");
			// setStatusMessage("A maximum of " + maxNumOfBlocks +
			// " blocks already exist.");
			return;
		}
		int i = findFirstNonOccupiedColumn();
		if (i < 0) {
			System.out
					.println("VirtualWorldModel.addBlockInFristNonEmptyColumn> Should NOT happen! Cannot find empty column.");
			System.exit(-1);
			return;
		}
		// add block to empty column
		b.setCoord(columnToXCoord(i), getFloorY());
		addVirtualObject(b);
		setChanged(true);
	}

	// --- for drag and drop
	Block findBlockIntersectWith(Point2D point) {

		// first try to match block that is being held
		ListIterator liHeld = getVirtualObjectList().listIterator(
				getVirtualObjectList().size());
		Object oHeld;
		while (liHeld.hasPrevious()) {
			oHeld = liHeld.previous();
			if (oHeld instanceof Block) {
				Block heldBlock = (Block) oHeld;
				Rectangle2D heldRectangle = new Rectangle2D.Double(heldBlock
						.getCoord().getX(), heldBlock.getCoord().getY()
						- getBlockHeight(), getBlockWidth(), getBlockHeight());
				if (heldRectangle.contains(point)) {
					return heldBlock;
				}
			}
		}

		// find in the rest of the blocks
		ListIterator li = getVirtualObjectList().listIterator(
				getVirtualObjectList().size());
		Object o;
		while (li.hasPrevious()) {
			o = li.previous();
			if (o instanceof Block) {
				Block block = (Block) o;
				Rectangle2D rectangle = new Rectangle2D.Double(block.getCoord()
						.getX(), block.getCoord().getY() - getBlockHeight(),
						getBlockWidth(), getBlockHeight());
				if (rectangle.contains(point)) {
					return block;
				}
			}
		}
		return null;
	}

	void snapMoveBlock(Block block, Point2D targetCoord) {
		takeOutBlock(block);
		snapBlock(block, targetCoord);
		insertBlock(block);
	}

	void takeOutBlock(Block block) {

		if (!block.isCoordLegal()) {
			// if block location not legal, block already taken out
			return;
		}

		// block on table or other block (could be null)
		Block topBlock = getBlockOnTopOf(block);

		// make block coord illegal
		block.setCoordLegal(false);

		if (block.isBeingHeld()) {
			// block held by arm
			block.getHoldingRobotArm().setBlockHeld(null);
			// block.getHoldingRobotArm().completelyOpenClaw();
			// block.setHoldingRobotArm(null);
			block.setBeingHeld(false);
		} else {
			// move down blocks on top
			moveDownBlock(topBlock);
		}

		// update arm status
		if (block.getHoldingRobotArm().getBlockHeld() == null) {
			block.getHoldingRobotArm().completelyOpenClaw();
		}
		setChanged(true);
	}

	void moveDownBlock(Block block) {
		if (block == null) {
			// base case
			return;
		}
		Block topBlock = getBlockOnTopOf(block);
		// move down block
		Point2D coord = block.getCoord();
		block.setCoord(coord.getX(), coord.getY() + getBlockHeight());
		// move down the block on top
		moveDownBlock(topBlock);
	}

	void snapBlock(Block block, Point2D targetCoord) {

		setChanged(true);
		// check if block snaps to one of the groud points
		for (int i = 0; i < getMaxNumOfColumns(); i++) {
			Point2D groundPoint = new Point2D.Double(columnToXCoord(i),
					getFloorY());
			if (isPointWithinSnapZone(targetCoord, groundPoint)) {
				block.setCoord(groundPoint);
				block.setCoordLegal(true);
				return;
			}
		}

		// check if block snaps on top one of the blocks
		Iterator i = getVirtualObjectList().iterator();
		Object o;
		while (i.hasNext()) {
			o = i.next();
			if (o instanceof Block) {
				Block stepBlock = (Block) o;
				if (stepBlock.isCoordLegal()
						&& stepBlock.isBeingHeld() == false) {
					// can snap to a block that is being transported.
					Point2D stepBlockTop = new Point2D.Double(stepBlock
							.getCoord().getX(), stepBlock.getCoord().getY()
							- getBlockHeight());
					if (isPointWithinSnapZone(targetCoord, stepBlockTop)) {
						block.setCoord(stepBlockTop);
						block.setCoordLegal(true);
						return;
					}
				}
			}
		}

		// check if block snaps to robot arm (only if arm not holding anything)
		Iterator j = getVirtualObjectList().iterator();
		Object p;
		while (j.hasNext()) {
			p = j.next();
			if (p instanceof RobotArm) {
				RobotArm robotArm = (RobotArm) p;
				if (robotArm.getBlockHeld() == null) {
					// (only if arm not holding anything)
					Point2D armPoint = new Point2D.Double(robotArm.getCoord()
							.getX(), robotArm.getCoord().getY()
							+ getBlockHeight());
					if (isPointWithinSnapZone(targetCoord, armPoint)) {
						block.setCoord(armPoint);
						block.setCoordLegal(true);
						block.setBeingHeld(true);
						// block.setHoldingRobotArm(robotArm);
						return;
					}
				}
			}
		}

		if (block.getCoord().equals(targetCoord)) {
			// block not moved, nothing changed
			setChanged(false);
			return;
		} else {
			// block coord not legal
			block.setCoord(targetCoord);
		}
	}

	private static final double snapX = 10;
	private static final double snapY = 10;

	boolean isPointWithinSnapZone(Point2D point, Point2D centerPoint) {
		if (point.getX() >= centerPoint.getX() - snapX
				&& point.getX() <= centerPoint.getX() + snapX
				&& point.getY() >= centerPoint.getY() - snapY
				&& point.getY() <= centerPoint.getY() + snapY)
			return true;
		else
			return false;
	}

	void insertBlock(Block block) {

		if (!block.isCoordLegal()) {
			// if block location not legal, leave it where it is
			return;
		}

		if (block.isBeingHeld()) {
			block.getHoldingRobotArm().setBlockHeld(block);
			block.getHoldingRobotArm().completelyCloseClaw();
		} else {
			// move up blocks
			Block blockToBeMoved = findOtherLegalCoordUnheldBlockAt(block
					.getCoord(), block);
			moveUpBlock(blockToBeMoved);
		}
		// set block legal
		// block.setCoordLegal(true);
		setChanged(true);
	}

	void moveUpBlock(Block block) {
		if (block == null) {
			// base case
			return;
		}
		// move up blocks on top of itself first
		moveUpBlock(getBlockOnTopOf(block));
		// move up itself
		Point2D coord = block.getCoord();
		block.setCoord(coord.getX(), coord.getY() - getBlockHeight());
	}

	// --- helper
	boolean isXCloseEnough(double x1, double x2) {
		return Math.abs(x1 - x2) <= getXFloatErrorMargin() ? true : false;
	}

	boolean isYCloseEnough(double y1, double y2) {
		return Math.abs(y1 - y2) <= getYFloatErrorMargin() ? true : false;
	}

	public double columnToXCoord(int columnNum) {
		return getLeftMargin() + columnNum * getColumnWidth();
	}

	int xCoordToClosestColumn(double x) {
		return (int) Math.round((x - getLeftMargin()) / getColumnWidth());
	}
}
