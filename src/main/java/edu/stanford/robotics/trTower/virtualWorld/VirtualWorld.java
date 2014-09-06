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
public class VirtualWorld extends Observable implements StimulatorListener {

	public enum RunState {
		RUNNING, PAUSED
	};

	// some colors
	static final Paint stringPaint = Color.black;
	static final Paint armPaint = Color.gray;

	// some parameters defining the VirtualWorld
	static final String tableId = "TA";
	static final double xStep = 4; // fixed, #1640
	static final double blockWidth = 30;
	// semi-derived parameters
	static final double yStep = xStep;
	static final double blockHeight = blockWidth;
	static final double leftMargin = blockWidth / 2;
	static final double rightMargin = leftMargin;
	static final double topMargin = 0;
	static final double bottomMargin = 0;
	// derived parameters
	static final double columnWidth = blockWidth * 4 / 3;
	// static final double xFloatErrorMargin = 0.5 * xStep;
	// static final double yFloatErrorMargin = 0.5 * yStep;
	static final double xFloatErrorMargin = 0.5;
	static final double yFloatErrorMargin = 0.5;

	static final double armLengthClearence = 1.5 * blockHeight;

	static final double armThickness = VirtualWorld.blockWidth / 8;
	static final double innerArmWidth = VirtualWorld.blockWidth;
	static final double innerArmDrop = VirtualWorld.blockHeight / 2;

	static final double armCeilingY = topMargin;
	static final Point2D armRestCoord = new Point2D.Double(leftMargin,
			blockHeight);

	// --- components
	private VirtualWorldModel virtualWorldModel;

	/**
	 * @modified: made public April 15th, 2009.
	 */
	public VirtualWorldModel getVirtualWorldModel() {
		if (virtualWorldModel == null) {
			virtualWorldModel = new VirtualWorldModel();
			// --- set self parameters
			virtualWorldModel.setTableId(tableId);
			virtualWorldModel.setXStep(xStep);
			virtualWorldModel.setYStep(yStep);
			virtualWorldModel.setXFloatErrorMargin(xFloatErrorMargin);
			virtualWorldModel.setYFloatErrorMargin(yFloatErrorMargin);
			virtualWorldModel.setBlockWidth(blockWidth);
			virtualWorldModel.setBlockHeight(blockHeight);
			virtualWorldModel.setColumnWidth(columnWidth);
			virtualWorldModel.setLeftMargin(leftMargin);
			virtualWorldModel.setRightMargin(rightMargin);
			virtualWorldModel.setTopMargin(topMargin);
			virtualWorldModel.setBottomMargin(bottomMargin);
			virtualWorldModel.setArmRestCoord(armRestCoord);
			virtualWorldModel.setArmLengthClearence(armLengthClearence);

			// --- init children
			initVirtualWorldChildren();
		}
		return virtualWorldModel;
	}

	protected void initVirtualWorldChildren() {
		resetRobotArm();
		getVirtualWorldModel().addVirtualObject(getRobotArm());
		setDurativeArmAction(getNilAction());
		getVirtualWorldModel().setChanged(true);
	}

	// caller of the action responsible for instantiating the action objects
	void initDurativeArmAction(DurativeArmAction a) {
		a.setHostRobotArm(getRobotArm());
		a.setVirtualWorldModel(getVirtualWorldModel());
	}

	// --- components
	private DemoAction demoAction;

	protected DemoAction getDemoAction() {
		if (demoAction == null) {
			demoAction = new DemoAction();
			initDurativeArmAction(demoAction);
		}
		return demoAction;
	}

	private NilAction nilAction;

	protected NilAction getNilAction() {
		if (nilAction == null) {
			nilAction = new NilAction();
			initDurativeArmAction(nilAction);
		}
		return nilAction;
	}

	private PickupAction pickupAction;

	protected PickupAction getPickupAction() {
		if (pickupAction == null) {
			pickupAction = new PickupAction();
			initDurativeArmAction(pickupAction);
		}
		return pickupAction;
	}

	private PutdownAction putdownAction;

	protected PutdownAction getPutdownAction() {
		if (putdownAction == null) {
			putdownAction = new PutdownAction();
			initDurativeArmAction(putdownAction);
		}
		return putdownAction;
	}

	public void setDurativeArmAction(DurativeArmAction a) {
		getRobotArm().setDurativeArmAction(a);
	}

	// --- public methods to set DurativeArmAction
	public void nil() {
		setDurativeArmAction(getNilAction());
	}

	public void pickup(String blockId) {
		getPickupAction().setTargetBlockId(blockId);
		setDurativeArmAction(getPickupAction());
	}

	public void putdown(String blockId, String objectId) {
		getPutdownAction().setSubjectBlockId(blockId);
		getPutdownAction().setTargetBlockId(objectId);
		setDurativeArmAction(getPutdownAction());
	}

	// --- component
	RobotArm robotArm;

	/**
	 * @modified made visible 15apr08.
	 */
	public RobotArm getRobotArm() {
		if (robotArm == null) {
			robotArm = new RobotArm();
			robotArm.setId("RobotArm");
			robotArm.setPaint(armPaint);
			robotArm.setArmThickness(armThickness);
			robotArm.setInnerArmWidth(innerArmWidth);
			robotArm.setInnerArmDrop(innerArmDrop);

			robotArm.setArmCeilingY(armCeilingY);
			robotArm.setArmRestCoord(armRestCoord);

			resetRobotArm();
		}
		return robotArm;
	}

	private void resetRobotArm() {
		getRobotArm().setCoord(getVirtualWorldModel().getArmRestCoord());
		getRobotArm().setBlockHeld(null);
	}

	private NewBlockCreator newBlockCreator;

	protected NewBlockCreator getNewBlockCreator() {
		if (newBlockCreator == null) {
			newBlockCreator = new NewBlockCreator();
			newBlockCreator.setVirtualWorldModel(getVirtualWorldModel());
			newBlockCreator.setLabelPaint(stringPaint);
		}
		return newBlockCreator;
	}

	// --- public component
	VirtualWorldSensor virtualWorldSensor;

	public VirtualWorldSensor getVirtualWorldSensor() {
		if (virtualWorldSensor == null) {
			virtualWorldSensor = new VirtualWorldSensor();
			virtualWorldSensor.setVirtualWorld(this);
			virtualWorldSensor.setVirtualWorldModel(getVirtualWorldModel());
		}
		return virtualWorldSensor;
	}

	// --- attributes
	private boolean toBeReset = false;

	public boolean isToBeReset() {
		return toBeReset;
	}

	public void setToBeReset(boolean r) {
		toBeReset = r;
	}

	private boolean toAddNewBlock = false;

	public boolean isToAddNewBlock() {
		return toAddNewBlock;
	}

	public void setToAddNewBlock(boolean n) {
		toAddNewBlock = n;
	}

	private boolean durativeActionRunning = true;

	public boolean isDurativeActionRunning() {
		return durativeActionRunning;
	}

	public void setDurativeActionRunning(boolean r) {
		durativeActionRunning = r;
		setChanged();
		notifyObservers(r ? VirtualWorld.RunState.RUNNING
				: VirtualWorld.RunState.PAUSED); // inform listeners.
	}

	// --- public methods
	public String getDurativeArmActionStatusMessage() {
		return getRobotArm().getDurativeArmAction().getStatusMessage();
	}

	public String getTableId() {
		return tableId;
	}

	public boolean isValidBlockId(String id) {
		return getVirtualWorldModel().isValidBlockId(id);
	}

	public double getVirtualWorldWidth() {
		return getVirtualWorldModel().getVirtualWorldWidth();
	}

	public void setVirtualWorldWidth(double w) {
		getVirtualWorldModel().setVirtualWorldWidth(w);
	}

	public double getVirtualWorldHeight() {
		return getVirtualWorldModel().getVirtualWorldHeight();
	}

	public void setVirtualWorldHeight(double h) {
		getVirtualWorldModel().setVirtualWorldHeight(h);
	}

	// --- handles Drag and Drop
	private boolean externallyPerturbed = false;
	private Point2D selectPoint;
	private Block selectBlock;
	private Point2D selectBlockOrigin;
	private boolean selectBlockHeld;

	// public boolean isBlockSelected() {
	// return selectBlock != null;
	// }

	public boolean isPointOverBlock(Point2D point) {
		return getVirtualWorldModel().findBlockIntersectWith(point) != null;
	}

	public Point2D getSelectPoint() {
		return selectPoint;
	}

	public void setSelectPoint(Point2D sp) {
		if (sp == null) {
			// release select point
			if (selectBlock != null) {
				// only need to release select point if it is previous set
				if (!selectBlock.isCoordLegal()) {
					// return to original location
					selectBlock.setCoord(selectBlockOrigin);
					if (selectBlockHeld) {
						selectBlock.setBeingHeld(true);
						// selectBlock.setHoldingRobotArm(getRobotArm());
					}
					selectBlock.setCoordLegal(true);
				}
				getVirtualWorldModel().insertBlock(selectBlock);
				selectBlockOrigin = null;
				selectBlock = null;
				setAvailable(true);
				externallyPerturbed = true;
			}
		} else {
			// set select point
			selectBlock = getVirtualWorldModel().findBlockIntersectWith(sp);
			if (selectBlock != null) {
				selectBlockOrigin = selectBlock.getCoord();
				selectBlockHeld = selectBlock.isBeingHeld();
				setAvailable(false);
			}
		}
		selectPoint = sp;
	}

	private Point2D targetPoint;

	public Point2D getTargetPoint() {
		return targetPoint;
	}

	public void setTargetPoint(Point2D tp) {

		if (selectBlock == null) {
			// no block selected
			return;
		}

		// move selected block to target point
		double dx = tp.getX() - selectPoint.getX();
		double dy = tp.getY() - selectPoint.getY();
		Point2D translatedOrigin = new Point2D.Double(selectBlockOrigin.getX()
				+ dx, selectBlockOrigin.getY() + dy);
		// selectBlock.setCoord(translatedOrigin);

		// getVirtualWorldModel().snapBlock(selectBlock);

		// move block and snap block to closest grid
		getVirtualWorldModel().snapMoveBlock(selectBlock, translatedOrigin);
		externallyPerturbed = true;
	}

	public boolean isVirtualWorldAvailable() {
		return getVirtualWorldModel().isAvailable();
	}

	/**
	 * @modified sends event to EIS to indicate environment is (temporarily)
	 *           paused or not.
	 */
	public void setAvailable(boolean a) {
		getVirtualWorldModel().setAvailable(a);

		// TODO:
		// if (a == false) {
		// notifyEvent(TowerInterface.PAUSED_EVT);
		// } else {
		// notifyEvent(TowerInterface.STARTED_EVT);
		// }
	}

	public void render(Graphics g) {
		getVirtualWorldModel().render(g);
	}

	public boolean isChanged() {
		return getVirtualWorldModel().isChanged();
	}

	public void stimuStep() {

		if (!externallyPerturbed) {
			// only clears if not under external perturbation
			getVirtualWorldModel().setChanged(false);
		}
		externallyPerturbed = false;

		if (isToBeReset()) {
			reset();
			setToBeReset(false);
		}
		if (isToAddNewBlock()) {
			addNewBlock();
			setToAddNewBlock(false);
		}
		if (isDurativeActionRunning())
			getRobotArm().getDurativeArmAction().actionStep();
	}

	public void addNewBlock() {
		Block b = getNewBlockCreator().createNewBlock();
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
