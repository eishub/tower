package edu.stanford.robotics.trTower.virtualWorld;

import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;

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

	private String tableId;

	public String getTableId() {
		return this.tableId;
	}

	void setTableId(final String s) {
		this.tableId = s;
	}

	private double xStep;

	double getXStep() {
		return this.xStep;
	}

	void setXStep(final double x) {
		this.xStep = x;
	}

	private double yStep;

	double getYStep() {
		return this.yStep;
	}

	void setYStep(final double y) {
		this.yStep = y;
	}

	private double xFloatErrorMargin;

	double getXFloatErrorMargin() {
		return this.xFloatErrorMargin;
	}

	void setXFloatErrorMargin(final double x) {
		this.xFloatErrorMargin = x;
	}

	private double yFloatErrorMargin;

	double getYFloatErrorMargin() {
		return this.yFloatErrorMargin;
	}

	void setYFloatErrorMargin(final double y) {
		this.yFloatErrorMargin = y;
	}

	private double blockWidth;

	double getBlockWidth() {
		return this.blockWidth;
	}

	void setBlockWidth(final double w) {
		this.blockWidth = w;
	}

	private double blockHeight;

	double getBlockHeight() {
		return this.blockHeight;
	}

	void setBlockHeight(final double h) {
		this.blockHeight = h;
	}

	private double columnWidth;

	double getColumnWidth() {
		return this.columnWidth;
	}

	void setColumnWidth(final double w) {
		this.columnWidth = w;
	}

	private double leftMargin;

	double getLeftMargin() {
		return this.leftMargin;
	}

	void setLeftMargin(final double l) {
		this.leftMargin = l;
	}

	private double rightMargin;

	double getRightMargin() {
		return this.rightMargin;
	}

	void setRightMargin(final double r) {
		this.rightMargin = r;
	}

	private double topMargin;

	double getTopMargin() {
		return this.topMargin;
	}

	void setTopMargin(final double t) {
		this.topMargin = t;
	}

	private double bottomMargin;

	double getBottomMargin() {
		return this.bottomMargin;
	}

	void setBottomMargin(final double b) {
		this.bottomMargin = b;
	}

	private Point2D armRestCoord;

	Point2D getArmRestCoord() {
		return this.armRestCoord;
	}

	void setArmRestCoord(final Point2D r) {
		this.armRestCoord = r;
	}

	void setArmRestCoord(final double x, final double y) {
		this.armRestCoord = new Point2D.Double(x, y);
	}

	private double armLengthClearence;

	double getArmLengthClearence() {
		return this.armLengthClearence;
	}

	void setArmLengthClearence(final double c) {
		this.armLengthClearence = c;
	}

	private double virtualWorldWidth;

	double getVirtualWorldWidth() {
		return this.virtualWorldWidth;
	}

	void setVirtualWorldWidth(final double w) {
		this.virtualWorldWidth = w;
	}

	private double virtualWorldHeight;

	double getVirtualWorldHeight() {
		return this.virtualWorldHeight;
	}

	void setVirtualWorldHeight(final double h) {
		this.virtualWorldHeight = h;
	}

	public double getFloorY() {
		return getVirtualWorldHeight() - getBottomMargin();
	}

	double getCeilingY() {
		return getTopMargin();
	}

	void render(final Graphics g) {
		// first render blocks that are not held
		final Iterator<VirtualObject> iBlock = getVirtualObjectList().iterator();
		while (iBlock.hasNext()) {
			final Object oBlock = iBlock.next();
			if (oBlock instanceof Block) {
				final Block block = (Block) oBlock;
				if (block.isCoordLegal() && !block.isBeingHeld()) {
					block.render(g);
				}
			}
		}

		// then render robot arm or block being held
		final Iterator<VirtualObject> iHold = getVirtualObjectList().iterator();
		while (iHold.hasNext()) {
			final Object oHold = iHold.next();
			if (oHold instanceof RobotArm) {
				final RobotArm robotArm = (RobotArm) oHold;
				if (robotArm.isCoordLegal()) {
					robotArm.render(g);
				}
			} else if (oHold instanceof Block) {
				final Block heldBlock = (Block) oHold;
				if (heldBlock.isCoordLegal() && heldBlock.isBeingHeld()) {
					heldBlock.render(g);
				}
			}
		}

		// then render objects with illegal coord (so they appear on top)
		final Iterator<VirtualObject> j = getVirtualObjectList().iterator();
		while (j.hasNext()) {
			final VirtualObject voIllegal = j.next();
			if (!voIllegal.isCoordLegal()) {
				voIllegal.render(g);
			}
		}
	}

	private List<VirtualObject> virtualObjectList;

	/**
	 * Returns list of all virtual objects.
	 */
	public List<VirtualObject> getVirtualObjectList() {
		if (this.virtualObjectList == null) {
			this.virtualObjectList = new LinkedList<>();
		}
		return this.virtualObjectList;
	}

	void reset() {
		getVirtualObjectList().clear();
		setChanged(true);
	}

	void addVirtualObject(final VirtualObject vo) {
		if (vo.getId() == null) {
			System.out.println("VirtualWorldModel.addVirtualObject> vo.getId() is null");
			System.exit(-1);
		}
		if (vo.getPaint() == null) {
			System.out.println("VirtualWorldModel.addVirtualObject> vo.getPaint() is null");
			System.exit(-1);
		}

		if (findVirtualObject(vo.getId()) != null) {
			System.out.println("VirtualWorldModel.addVirtualObject> vo.getId() already exists");
			System.exit(-1);
		}
		getVirtualObjectList().add(vo);
	}

	int getMaxNumOfColumns() {
		final double maxNumOfColumns = (getVirtualWorldWidth() - getLeftMargin() - getRightMargin()) / getColumnWidth();
		return (int) maxNumOfColumns;
	}

	int getMaxNumOfRows() {
		final double maxNumOfRows = (getVirtualWorldHeight() - getArmLengthClearence() - getTopMargin()
				- getBottomMargin()) / getBlockHeight();
		return (int) maxNumOfRows;
	}

	public VirtualObject findVirtualObject(final String id) {
		final Iterator<VirtualObject> i = getVirtualObjectList().iterator();
		VirtualObject vo;
		while (i.hasNext()) {
			vo = (i.next());
			if (vo.getId().equals(id)) {
				return vo;
			}
		}
		return null;
	}

	boolean isValidBlockId(final String id) {
		final Object vo = findVirtualObject(id);
		return vo instanceof Block ? true : false;
	}

	Block findBlockAt(final Point2D coord) {
		return findBlockAt(coord.getX(), coord.getY());
	}

	Block findBlockAt(final double x, final double y) {
		final Iterator<VirtualObject> i = getVirtualObjectList().iterator();
		VirtualObject vo;
		while (i.hasNext()) {
			vo = (i.next());
			if (vo instanceof Block && isXCloseEnough(vo.getCoord().getX(), x)
					&& isYCloseEnough(vo.getCoord().getY(), y)) {
				return (Block) vo;
			}
		}
		return null;
	}

	Block findLegalCoordBlockAt(final Point2D coord) {
		return findLegalCoordBlockAt(coord.getX(), coord.getY());
	}

	Block findLegalCoordBlockAt(final double x, final double y) {
		final Iterator<VirtualObject> i = getVirtualObjectList().iterator();
		VirtualObject vo;
		while (i.hasNext()) {
			vo = (i.next());
			if (vo.isCoordLegal() && vo instanceof Block && isXCloseEnough(vo.getCoord().getX(), x)
					&& isYCloseEnough(vo.getCoord().getY(), y)) {
				return (Block) vo;
			}
		}
		return null;
	}

	Block findLegalCoordUnheldBlockAt(final Point2D coord) {
		return findLegalCoordUnheldBlockAt(coord.getX(), coord.getY());
	}

	Block findLegalCoordUnheldBlockAt(final double x, final double y) {
		final Iterator<VirtualObject> i = getVirtualObjectList().iterator();
		VirtualObject vo;
		while (i.hasNext()) {
			vo = (i.next());
			if (vo.isCoordLegal() && vo instanceof Block && isXCloseEnough(vo.getCoord().getX(), x)
					&& isYCloseEnough(vo.getCoord().getY(), y)) {
				final Block block = (Block) vo;
				if (!block.isBeingHeld()) {
					return block;
				}
			}
		}
		return null;
	}

	Block findOtherLegalCoordUnheldBlockAt(final Point2D coord, final Block block) {
		return findOtherLegalCoordUnheldBlockAt(coord.getX(), coord.getY(), block);
	}

	Block findOtherLegalCoordUnheldBlockAt(final double x, final double y, final Block block) {
		final Iterator<VirtualObject> i = getVirtualObjectList().iterator();
		VirtualObject vo;
		while (i.hasNext()) {
			vo = (i.next());
			if (vo instanceof Block) {
				final Block candidateBlock = (Block) vo;
				if (candidateBlock != block && candidateBlock.isCoordLegal() && !candidateBlock.isBeingHeld()
						&& isXCloseEnough(candidateBlock.getCoord().getX(), x)
						&& isYCloseEnough(candidateBlock.getCoord().getY(), y)) {
					return candidateBlock;
				}
			}
		}
		return null;
	}

	List<String> getExistingBlockIds() {
		final Iterator<VirtualObject> i = getVirtualObjectList().iterator();
		Object o;
		final List<String> blockIdList = new ArrayList<>();
		while (i.hasNext()) {
			o = i.next();
			if (o instanceof Block) {
				final Block b = (Block) o;
				blockIdList.add(b.getId());
			}
		}
		return blockIdList;
	}

	public boolean isBlockClear(final Block b) {
		final Block block = findLegalCoordUnheldBlockAt(b.getCoord().getX(), b.getCoord().getY() - getBlockHeight());
		if (b.isBeingHeld()) {
			return false; // block is being held, not clear
		} else {
			return block == null ? true : false;
		}
	}

	Block getBlockOnTopOf(final String blockId) {
		final VirtualObject vo = findVirtualObject(blockId);
		if (!(vo instanceof Block)) {
			return null;
		} else {
			return getBlockOnTopOf((Block) vo);
		}
	}

	Block getBlockOnTopOf(final Block b) {
		return findLegalCoordUnheldBlockAt(b.getCoord().getX(), b.getCoord().getY() - getBlockHeight());
	}

	boolean isBlockOnTable(final String blockId) {
		final VirtualObject vo = findVirtualObject(blockId);
		if (!(vo instanceof Block)) {
			return false;
		} else {
			return isBlockOnTable((Block) vo);
		}
	}

	boolean isBlockOnTable(final Block b) {
		if (b.isBeingHeld()) {
			return false; // the object is held, so not on table yet
		} else if (isYCloseEnough(b.getCoord().getY(), getFloorY())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @modified: 15apr09: made public.
	 */
	public boolean isOn(final String objectIdX, final String objectIdY) {
		if (getTableId().equals(objectIdY)) {
			// is block on Table?
			return isBlockOnTable(objectIdX);
		} else {
			final Block b = getBlockOnTopOf(objectIdY);
			if (b == null) {
				return false;
			} else if (b.getId().equals(objectIdX)) {
				return true;
			} else {
				return false;
			}
		}
	}

	int getNumOfBlocks() {
		final Iterator<VirtualObject> i = getVirtualObjectList().iterator();
		VirtualObject vo;
		int sum = 0;
		while (i.hasNext()) {
			vo = (i.next());
			if (vo instanceof Block) {
				sum++;
			}
		}
		return sum;
	}

	boolean isColumnEmpty(final int columnNum) {
		final Block block = findLegalCoordUnheldBlockAt(columnToXCoord(columnNum), getFloorY());
		return block == null ? true : false;
	}

	boolean isColumnOccupied(final int columnNum) {
		final Block block = findLegalCoordBlockAt(columnToXCoord(columnNum), getFloorY());
		return block == null ? false : true;
	}

	int findFirstEmptyColumn() {
		for (int i = 0; i < getMaxNumOfColumns(); i++) {
			if (isColumnEmpty(i)) {
				return i;
			}
		}
		return -1;
	}

	int findFirstNonOccupiedColumn() {
		for (int i = 0; i < getMaxNumOfColumns(); i++) {
			if (!isColumnOccupied(i)) {
				return i;
			}
		}
		return -1;
	}

	public int findClosestEmptyColumnFromXCoord(final double xCoord) {
		return findClosestEmptyColumnFromColumn(xCoordToClosestColumn(xCoord));
	}

	int findClosestEmptyColumnFromColumn(final int currentColumn) {
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
				if (isColumnEmpty(left)) {
					return left;
				}
			} else {
				exhaustedLeft = true;
			}

			right = currentColumn + delta;
			exhaustedRight = right >= getMaxNumOfColumns() ? true : false;
			if (!exhaustedRight) {
				if (isColumnEmpty(right)) {
					return right;
				}
			} else {
				exhaustedRight = true;
			}
		}

		return -1;
	}

	boolean isMaxNumOfBlocksReached() {
		return getNumOfBlocks() >= getMaxNumOfBlocks() ? true : false;
	}

	int getMaxNumOfBlocks() {
		return Math.min(getMaxNumOfColumns(), getMaxNumOfRows());
	}

	void addBlockInFirstNonEmptyColumn(final Block b) {
		if (isMaxNumOfBlocksReached()) {
			System.out.println("A maximum of " + getMaxNumOfBlocks() + " blocks already exist.");
			return;
		}

		final int i = findFirstEmptyColumn();
		if (i < 0) {
			System.out.println(
					"VirtualWorldModel.addBlockInFristNonEmptyColumn> Should NOT happen! Cannot find empty column.");
			System.exit(-1);
			return;
		}
		// add block to empty column
		b.setCoord(columnToXCoord(i), getFloorY());
		addVirtualObject(b);
		setChanged(true);
	}

	void addBlockInFirstNonOccupiedColumn(final Block b) {
		final int maxNumOfBlocks = Math.min(getMaxNumOfColumns(), getMaxNumOfRows());
		if (getNumOfBlocks() >= maxNumOfBlocks) {
			System.out.println("A maximum of " + maxNumOfBlocks + " blocks already exist.");
			return;
		}
		final int i = findFirstNonOccupiedColumn();
		if (i < 0) {
			System.out.println(
					"VirtualWorldModel.addBlockInFristNonEmptyColumn> Should NOT happen! Cannot find empty column.");
			System.exit(-1);
			return;
		}
		// add block to empty column
		b.setCoord(columnToXCoord(i), getFloorY());
		addVirtualObject(b);
		setChanged(true);
	}

	// --- for drag and drop
	Block findBlockIntersectWith(final Point2D point) {
		// first try to match block that is being held
		final ListIterator<VirtualObject> liHeld = getVirtualObjectList().listIterator(getVirtualObjectList().size());
		Object oHeld;
		while (liHeld.hasPrevious()) {
			oHeld = liHeld.previous();
			if (oHeld instanceof Block) {
				final Block heldBlock = (Block) oHeld;
				final Rectangle2D heldRectangle = new Rectangle2D.Double(heldBlock.getCoord().getX(),
						heldBlock.getCoord().getY() - getBlockHeight(), getBlockWidth(), getBlockHeight());
				if (heldRectangle.contains(point)) {
					return heldBlock;
				}
			}
		}

		// find in the rest of the blocks
		final ListIterator<VirtualObject> li = getVirtualObjectList().listIterator(getVirtualObjectList().size());
		Object o;
		while (li.hasPrevious()) {
			o = li.previous();
			if (o instanceof Block) {
				final Block block = (Block) o;
				final Rectangle2D rectangle = new Rectangle2D.Double(block.getCoord().getX(),
						block.getCoord().getY() - getBlockHeight(), getBlockWidth(), getBlockHeight());
				if (rectangle.contains(point)) {
					return block;
				}
			}
		}
		return null;
	}

	void snapMoveBlock(final Block block, final Point2D targetCoord) {
		takeOutBlock(block);
		snapBlock(block, targetCoord);
		insertBlock(block);
	}

	void takeOutBlock(final Block block) {
		if (!block.isCoordLegal()) {
			// if block location not legal, block already taken out
			return;
		}

		// block on table or other block (could be null)
		final Block topBlock = getBlockOnTopOf(block);

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

	void moveDownBlock(final Block block) {
		if (block == null) {
			// base case
			return;
		}
		final Block topBlock = getBlockOnTopOf(block);
		// move down block
		final Point2D coord = block.getCoord();
		block.setCoord(coord.getX(), coord.getY() + getBlockHeight());
		// move down the block on top
		moveDownBlock(topBlock);
	}

	void snapBlock(final Block block, final Point2D targetCoord) {
		setChanged(true);
		// check if block snaps to one of the groud points
		for (int i = 0; i < getMaxNumOfColumns(); i++) {
			final Point2D groundPoint = new Point2D.Double(columnToXCoord(i), getFloorY());
			if (isPointWithinSnapZone(targetCoord, groundPoint)) {
				block.setCoord(groundPoint);
				block.setCoordLegal(true);
				return;
			}
		}

		// check if block snaps on top one of the blocks
		final Iterator<VirtualObject> i = getVirtualObjectList().iterator();
		Object o;
		while (i.hasNext()) {
			o = i.next();
			if (o instanceof Block) {
				final Block stepBlock = (Block) o;
				if (stepBlock.isCoordLegal() && stepBlock.isBeingHeld() == false) {
					// can snap to a block that is being transported.
					final Point2D stepBlockTop = new Point2D.Double(stepBlock.getCoord().getX(),
							stepBlock.getCoord().getY() - getBlockHeight());
					if (isPointWithinSnapZone(targetCoord, stepBlockTop)) {
						block.setCoord(stepBlockTop);
						block.setCoordLegal(true);
						return;
					}
				}
			}
		}

		// check if block snaps to robot arm (only if arm not holding anything)
		final Iterator<VirtualObject> j = getVirtualObjectList().iterator();
		Object p;
		while (j.hasNext()) {
			p = j.next();
			if (p instanceof RobotArm) {
				final RobotArm robotArm = (RobotArm) p;
				if (robotArm.getBlockHeld() == null) {
					// (only if arm not holding anything)
					final Point2D armPoint = new Point2D.Double(robotArm.getCoord().getX(),
							robotArm.getCoord().getY() + getBlockHeight());
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

	boolean isPointWithinSnapZone(final Point2D point, final Point2D centerPoint) {
		if (point.getX() >= centerPoint.getX() - snapX && point.getX() <= centerPoint.getX() + snapX
				&& point.getY() >= centerPoint.getY() - snapY && point.getY() <= centerPoint.getY() + snapY) {
			return true;
		} else {
			return false;
		}
	}

	void insertBlock(final Block block) {
		if (!block.isCoordLegal()) {
			// if block location not legal, leave it where it is
			return;
		}

		if (block.isBeingHeld()) {
			block.getHoldingRobotArm().setBlockHeld(block);
			block.getHoldingRobotArm().completelyCloseClaw();
		} else {
			// move up blocks
			final Block blockToBeMoved = findOtherLegalCoordUnheldBlockAt(block.getCoord(), block);
			moveUpBlock(blockToBeMoved);
		}
		// set block legal
		// block.setCoordLegal(true);
		setChanged(true);
	}

	void moveUpBlock(final Block block) {
		if (block == null) {
			// base case
			return;
		}
		// move up blocks on top of itself first
		moveUpBlock(getBlockOnTopOf(block));
		// move up itself
		final Point2D coord = block.getCoord();
		block.setCoord(coord.getX(), coord.getY() - getBlockHeight());
	}

	boolean isXCloseEnough(final double x1, final double x2) {
		return Math.abs(x1 - x2) <= getXFloatErrorMargin() ? true : false;
	}

	boolean isYCloseEnough(final double y1, final double y2) {
		return Math.abs(y1 - y2) <= getYFloatErrorMargin() ? true : false;
	}

	public double columnToXCoord(final int columnNum) {
		return getLeftMargin() + columnNum * getColumnWidth();
	}

	int xCoordToClosestColumn(final double x) {
		return (int) Math.round((x - getLeftMargin()) / getColumnWidth());
	}
}
