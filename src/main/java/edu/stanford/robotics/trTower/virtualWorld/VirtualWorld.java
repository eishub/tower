package edu.stanford.robotics.trTower.virtualWorld;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.util.Observable;

import edu.stanford.robotics.trTower.StimulatorListener;
import edu.stanford.robotics.trTower.gui.NilAction;
import edu.stanford.robotics.trTower.gui.PickupAction;
import edu.stanford.robotics.trTower.gui.PutdownAction;

/**
 * The VirtualWorld class represents a world consists of two objects: Blocks and
 * RobotArm(s). VirtualWorld is also the "scheduler" that synchronizes the GUI
 * actions, the rendering of the world, and the execution of durative actions,
 * etc.
 *
 * @modified 3mar10 W.Pasman now VirtualWorld is Observable to fix trac 966
 *           observers now receive "RUNNING" and "PAUSED" events.
 */
@SuppressWarnings("deprecation")
public class VirtualWorld extends Observable implements StimulatorListener {
	public enum RunState {
		RUNNING, PAUSED
	}

	static final Paint stringPaint = Color.black;
	static final Paint armPaint = Color.gray;

	static final String tableId = "TA";
	static final double xStep = 4; // fixed, #1640
	static final double blockWidth = 30;

	static final double yStep = xStep;
	static final double blockHeight = blockWidth;
	static final double leftMargin = blockWidth / 2;
	static final double rightMargin = leftMargin;
	static final double topMargin = 0;
	static final double bottomMargin = 0;

	static final double columnWidth = blockWidth * 4 / 3;
	static final double xFloatErrorMargin = 0.5;
	static final double yFloatErrorMargin = 0.5;

	static final double armLengthClearence = 1.5 * blockHeight;

	static final double armThickness = VirtualWorld.blockWidth / 8;
	static final double innerArmWidth = VirtualWorld.blockWidth;
	static final double innerArmDrop = VirtualWorld.blockHeight / 2;

	static final double armCeilingY = topMargin;
	static final Point2D armRestCoord = new Point2D.Double(leftMargin, blockHeight);

	private VirtualWorldModel virtualWorldModel;

	/**
	 * @modified: made public April 15th, 2009.
	 */
	public VirtualWorldModel getVirtualWorldModel() {
		if (this.virtualWorldModel == null) {
			this.virtualWorldModel = new VirtualWorldModel();
			this.virtualWorldModel.setTableId(tableId);
			this.virtualWorldModel.setXStep(xStep);
			this.virtualWorldModel.setYStep(yStep);
			this.virtualWorldModel.setXFloatErrorMargin(xFloatErrorMargin);
			this.virtualWorldModel.setYFloatErrorMargin(yFloatErrorMargin);
			this.virtualWorldModel.setBlockWidth(blockWidth);
			this.virtualWorldModel.setBlockHeight(blockHeight);
			this.virtualWorldModel.setColumnWidth(columnWidth);
			this.virtualWorldModel.setLeftMargin(leftMargin);
			this.virtualWorldModel.setRightMargin(rightMargin);
			this.virtualWorldModel.setTopMargin(topMargin);
			this.virtualWorldModel.setBottomMargin(bottomMargin);
			this.virtualWorldModel.setArmRestCoord(armRestCoord);
			this.virtualWorldModel.setArmLengthClearence(armLengthClearence);
			initVirtualWorldChildren();
		}
		return this.virtualWorldModel;
	}

	protected void initVirtualWorldChildren() {
		resetRobotArm();
		getVirtualWorldModel().addVirtualObject(getRobotArm());
		setDurativeArmAction(getNilAction());
		getVirtualWorldModel().setChanged(true);
	}

	// caller of the action responsible for instantiating the action objects
	void initDurativeArmAction(final DurativeArmAction a) {
		a.setHostRobotArm(getRobotArm());
		a.setVirtualWorldModel(getVirtualWorldModel());
	}

	private DemoAction demoAction;

	protected DemoAction getDemoAction() {
		if (this.demoAction == null) {
			this.demoAction = new DemoAction();
			initDurativeArmAction(this.demoAction);
		}
		return this.demoAction;
	}

	private NilAction nilAction;

	protected NilAction getNilAction() {
		if (this.nilAction == null) {
			this.nilAction = new NilAction();
			initDurativeArmAction(this.nilAction);
		}
		return this.nilAction;
	}

	private PickupAction pickupAction;

	protected PickupAction getPickupAction() {
		if (this.pickupAction == null) {
			this.pickupAction = new PickupAction();
			initDurativeArmAction(this.pickupAction);
		}
		return this.pickupAction;
	}

	private PutdownAction putdownAction;

	protected PutdownAction getPutdownAction() {
		if (this.putdownAction == null) {
			this.putdownAction = new PutdownAction();
			initDurativeArmAction(this.putdownAction);
		}
		return this.putdownAction;
	}

	public void setDurativeArmAction(final DurativeArmAction a) {
		getRobotArm().setDurativeArmAction(a);
	}

	public void nil() {
		setDurativeArmAction(getNilAction());
	}

	public void pickup(final String blockId) {
		getPickupAction().setTargetBlockId(blockId);
		setDurativeArmAction(getPickupAction());
	}

	public void putdown(final String blockId, final String objectId) {
		getPutdownAction().setSubjectBlockId(blockId);
		getPutdownAction().setTargetBlockId(objectId);
		setDurativeArmAction(getPutdownAction());
	}

	RobotArm robotArm;

	/**
	 * @modified made visible 15apr08.
	 */
	public RobotArm getRobotArm() {
		if (this.robotArm == null) {
			this.robotArm = new RobotArm();
			this.robotArm.setId("RobotArm");
			this.robotArm.setPaint(armPaint);
			this.robotArm.setArmThickness(armThickness);
			this.robotArm.setInnerArmWidth(innerArmWidth);
			this.robotArm.setInnerArmDrop(innerArmDrop);

			this.robotArm.setArmCeilingY(armCeilingY);
			this.robotArm.setArmRestCoord(armRestCoord);

			resetRobotArm();
		}
		return this.robotArm;
	}

	private void resetRobotArm() {
		getRobotArm().setCoord(getVirtualWorldModel().getArmRestCoord());
		getRobotArm().setBlockHeld(null);
	}

	private NewBlockCreator newBlockCreator;

	protected NewBlockCreator getNewBlockCreator() {
		if (this.newBlockCreator == null) {
			this.newBlockCreator = new NewBlockCreator();
			this.newBlockCreator.setVirtualWorldModel(getVirtualWorldModel());
			this.newBlockCreator.setLabelPaint(stringPaint);
		}
		return this.newBlockCreator;
	}

	VirtualWorldSensor virtualWorldSensor;

	public VirtualWorldSensor getVirtualWorldSensor() {
		if (this.virtualWorldSensor == null) {
			this.virtualWorldSensor = new VirtualWorldSensor();
			this.virtualWorldSensor.setVirtualWorld(this);
			this.virtualWorldSensor.setVirtualWorldModel(getVirtualWorldModel());
		}
		return this.virtualWorldSensor;
	}

	private boolean toBeReset = false;

	public boolean isToBeReset() {
		return this.toBeReset;
	}

	public void setToBeReset(final boolean r) {
		this.toBeReset = r;
	}

	private boolean toAddNewBlock = false;

	public boolean isToAddNewBlock() {
		return this.toAddNewBlock;
	}

	public void setToAddNewBlock(final boolean n) {
		this.toAddNewBlock = n;
	}

	private boolean durativeActionRunning = true;

	public boolean isDurativeActionRunning() {
		return this.durativeActionRunning;
	}

	public void setDurativeActionRunning(final boolean r) {
		this.durativeActionRunning = r;
		setChanged();
		notifyObservers(r ? VirtualWorld.RunState.RUNNING : VirtualWorld.RunState.PAUSED); // inform listeners.
	}

	public String getDurativeArmActionStatusMessage() {
		return getRobotArm().getDurativeArmAction().getStatusMessage();
	}

	public String getTableId() {
		return tableId;
	}

	public boolean isValidBlockId(final String id) {
		return getVirtualWorldModel().isValidBlockId(id);
	}

	public double getVirtualWorldWidth() {
		return getVirtualWorldModel().getVirtualWorldWidth();
	}

	public void setVirtualWorldWidth(final double w) {
		getVirtualWorldModel().setVirtualWorldWidth(w);
	}

	public double getVirtualWorldHeight() {
		return getVirtualWorldModel().getVirtualWorldHeight();
	}

	public void setVirtualWorldHeight(final double h) {
		getVirtualWorldModel().setVirtualWorldHeight(h);
	}

	private boolean externallyPerturbed = false;
	private Point2D selectPoint;
	private Block selectBlock;
	private Point2D selectBlockOrigin;
	private boolean selectBlockHeld;

	public boolean isPointOverBlock(final Point2D point) {
		return getVirtualWorldModel().findBlockIntersectWith(point) != null;
	}

	public Point2D getSelectPoint() {
		return this.selectPoint;
	}

	public void setSelectPoint(final Point2D sp) {
		if (sp == null) {
			// release select point
			if (this.selectBlock != null) {
				// only need to release select point if it is previous set
				if (!this.selectBlock.isCoordLegal()) {
					// return to original location
					this.selectBlock.setCoord(this.selectBlockOrigin);
					if (this.selectBlockHeld) {
						this.selectBlock.setBeingHeld(true);
					}
					this.selectBlock.setCoordLegal(true);
				}
				getVirtualWorldModel().insertBlock(this.selectBlock);
				this.selectBlockOrigin = null;
				this.selectBlock = null;
				setAvailable(true);
				this.externallyPerturbed = true;
			}
		} else {
			// set select point
			this.selectBlock = getVirtualWorldModel().findBlockIntersectWith(sp);
			if (this.selectBlock != null) {
				this.selectBlockOrigin = this.selectBlock.getCoord();
				this.selectBlockHeld = this.selectBlock.isBeingHeld();
				setAvailable(false);
			}
		}
		this.selectPoint = sp;
	}

	private Point2D targetPoint;

	public Point2D getTargetPoint() {
		return this.targetPoint;
	}

	public void setTargetPoint(final Point2D tp) {
		if (this.selectBlock == null) {
			// no block selected
			return;
		}

		// move selected block to target point
		final double dx = tp.getX() - this.selectPoint.getX();
		final double dy = tp.getY() - this.selectPoint.getY();
		final Point2D translatedOrigin = new Point2D.Double(this.selectBlockOrigin.getX() + dx,
				this.selectBlockOrigin.getY() + dy);

		// move block and snap block to closest grid
		getVirtualWorldModel().snapMoveBlock(this.selectBlock, translatedOrigin);
		this.externallyPerturbed = true;
	}

	public boolean isVirtualWorldAvailable() {
		return getVirtualWorldModel().isAvailable();
	}

	/**
	 * @modified sends event to EIS to indicate environment is (temporarily) paused
	 *           or not.
	 */
	public void setAvailable(final boolean a) {
		getVirtualWorldModel().setAvailable(a);
	}

	public void render(final Graphics g) {
		getVirtualWorldModel().render(g);
	}

	public boolean isChanged() {
		return getVirtualWorldModel().isChanged();
	}

	@Override
	public void stimuStep() {
		if (!this.externallyPerturbed) {
			// only clears if not under external perturbation
			getVirtualWorldModel().setChanged(false);
		}
		this.externallyPerturbed = false;

		if (isToBeReset()) {
			reset();
			setToBeReset(false);
		}
		if (isToAddNewBlock()) {
			addNewBlock();
			setToAddNewBlock(false);
		}
		if (isDurativeActionRunning()) {
			getRobotArm().getDurativeArmAction().actionStep();
		}
	}

	public void addNewBlock() {
		final Block b = getNewBlockCreator().createNewBlock();
		b.setHoldingRobotArm(getRobotArm());
		getVirtualWorldModel().addBlockInFirstNonOccupiedColumn(b);
	}

	public boolean isMaxNumOfBlocksReached() {
		return getVirtualWorldModel().isMaxNumOfBlocksReached();
	}

	public void reset() {
		setToAddNewBlock(false);
		getVirtualWorldModel().reset();
		getNewBlockCreator().reset();
		initVirtualWorldChildren();
	}
}
