package towerenv;

/**
 * @modified Koen V. Hindriks
 * @modified W.Pasman 6dec09 EIS0.3 porting #1390
 * @modified W.Pasman 30oct14 EIS0.4
 */

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import edu.stanford.robotics.trTower.virtualWorld.Block;
import edu.stanford.robotics.trTower.virtualWorld.VirtualWorld;
import edu.stanford.robotics.trTower.virtualWorld.VirtualWorldSensor;
import eis.EIDefaultImpl;
import eis.PerceptUpdate;
import eis.exceptions.ActException;
import eis.exceptions.EntityException;
import eis.exceptions.ManagementException;
import eis.exceptions.NoEnvironmentException;
import eis.exceptions.PerceiveException;
import eis.iilang.Action;
import eis.iilang.EnvironmentState;
import eis.iilang.Identifier;
import eis.iilang.Parameter;
import eis.iilang.Percept;

/**
 * Provides an interface to agent platforms.
 *
 * @author Koen V. Hindriks
 */
@SuppressWarnings("deprecation")
public class TowerInterface extends EIDefaultImpl implements Observer {
	private static final long serialVersionUID = -8315701188485371282L;

	private TowerEnvironment towerEnvironment = null;

	/**
	 * Single CONTROLLABLE entity in the Tower environment.
	 */
	private static final String ENTITY = "gripper";

	/**
	 * Starts stand-alone tower environment application.
	 *
	 * @param args
	 */
	public static void main(final String[] args) {
		new TowerInterface();
	}

	/**
	 * Does nothing. See see {@link #init(Map)}
	 *
	 */
	public TowerInterface() {
	}

	/***********************************************************/
	/************** support functions **************************/
	/***********************************************************/

	/**
	 * Action pickup.
	 */
	public void actionpickup(final String block) {
		this.towerEnvironment.getWorld().pickup(block.toUpperCase());
	}

	/**
	 * Action putdown.
	 *
	 * @param block  is the block that is being put down
	 * @param target is what the block will be placed on
	 */
	public void actionputdown(final String block, final String target) {
		final String blockString = block.toUpperCase();
		String targetString = target.toUpperCase();
		if (targetString.equals("TABLE")) {
			targetString = "TA";
		}
		this.towerEnvironment.getWorld().putdown(blockString, targetString);
	}

	public void actionnil() {
		this.towerEnvironment.getWorld().nil();
	}

	/***********************************************************/
	/**************** implements Observer **********************/
	/***********************************************************/
	/**
	 * Here we catch events from the observed virtual world. Right now this is
	 * concerns only the run state. We pass the value through to EIS.
	 *
	 * @author W.Pasman 3mar10
	 */
	@Override
	public void update(final Observable observable, final Object info) {
		if (!(observable instanceof VirtualWorld)) {
			System.out.println("Internal error TowerWorld: unexpected  observable " + observable);
			return;
		}
		try {
			switch ((VirtualWorld.RunState) info) {
			case PAUSED:
				if (getState() != EnvironmentState.PAUSED) {
					setState(EnvironmentState.PAUSED);
				}
				break;
			case RUNNING:
				if (getState() != EnvironmentState.RUNNING) {
					setState(EnvironmentState.RUNNING);
				}
				break;
			default:
				System.out.println("Internal error Towerworld: unexpected event " + info);
			}
		} catch (final ManagementException e) {
			System.out.println("BUG: problem in environment while handling update of state:" + e);
		}
	}

	/***********************************************************/
	/***** implements EnvironmentInterfaceStandard *************/
	/***********************************************************/

	private final Map<String, Identifier> holding = new HashMap<>();
	private final Map<String, List<Percept>> previousPercepts = new HashMap<>();

	@Override
	protected PerceptUpdate getPerceptsForEntity(final String entity) throws PerceiveException, NoEnvironmentException {
		final List<Percept> addList = new LinkedList<>();
		final List<Percept> delList = new LinkedList<>();
		try {
			final VirtualWorld virtualWorld = this.towerEnvironment.getWorld();
			final VirtualWorldSensor vws = virtualWorld.getVirtualWorldSensor();

			// Construct percept if gripper is holding a block.
			final Block gripped = virtualWorld.getRobotArm().getBlockHeld();
			final Identifier previouslyGripped = this.holding.get(entity);
			if (gripped == null && previouslyGripped != null) {
				delList.add(new Percept("holding", previouslyGripped));
				this.previousPercepts.remove(entity);
			}
			if (gripped != null) {
				final Identifier holdingNow = new Identifier(gripped.getId().toLowerCase());
				if (previouslyGripped == null) {
					addList.add(new Percept("holding", holdingNow));
					this.holding.put(entity, holdingNow);
				} else if (!holdingNow.equals(previouslyGripped)) {
					delList.add(new Percept("holding", previouslyGripped));
					addList.add(new Percept("holding", holdingNow));
					this.holding.put(entity, holdingNow);
				}
			}

			final List<Percept> percepts = new LinkedList<>();
			List<Percept> previous = this.previousPercepts.get(entity);
			if (previous == null) {
				previous = new ArrayList<>(0);
			}
			// Adds percept 'block(X)' for each block X.
			// Adds percepts for each block that returns its 'on' status.
			for (final String blockId : vws.getExistingBlockIds()) {
				percepts.add(new Percept("block", new Identifier(blockId.toLowerCase())));

				// can't be on something if gripped.
				if (gripped != null && blockId.equals(gripped.getId())) {
					continue;
				}

				// on table if we can't find another block the currently
				// considered block is on.
				String on = "table";
				for (final String blockId2 : vws.getExistingBlockIds()) {
					if (vws.isOn(blockId, blockId2)) {
						on = blockId2;
						break;
					}
				}
				percepts.add(
						new Percept("on", new Identifier(blockId.toLowerCase()), new Identifier(on.toLowerCase())));
			}
			addList.addAll(percepts);
			addList.removeAll(previous);
			delList.addAll(previous);
			delList.removeAll(percepts);
		} catch (final RuntimeException e) {
			throw new PerceiveException("internal error while getting percepts for entity " + entity, e);
		}

		return new PerceptUpdate(addList, delList);
	}

	/**
	 * Provides name of the environment.
	 *
	 * @return name of the environment.
	 */
	@Override
	public String toString() {
		return "Tower Environment";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isSupportedByEntity(final Action action, final String entity) {
		if (action.getName().equals("pickup")) {
			return action.getParameters().size() == 1;
		} else if (action.getName().equals("putdown")) {
			return action.getParameters().size() == 2;
		} else if (action.getName().equals("nil")) {
			return action.getParameters().size() == 0;
		} else {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isSupportedByEnvironment(final Action arg0) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected boolean isSupportedByType(final Action arg0, final String arg1) {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected void performEntityAction(final Action action, final String entity) throws ActException {
		Parameter param0, param1;
		try {
			if (action.getName().equals("pickup")) {
				param0 = action.getParameters().get(0);
				if (!(param0 instanceof Identifier)) {
					throw new ActException(ActException.FAILURE,
							"action " + action.getName() + " needs Identifier as parameter but got " + param0);
				}
				actionpickup(((Identifier) param0).getValue());
			} else if (action.getName().equals("putdown")) {
				param0 = action.getParameters().get(0);
				param1 = action.getParameters().get(1);
				if (!(param0 instanceof Identifier)) {
					throw new ActException(ActException.FAILURE,
							"action " + action.getName() + " needs Identifier as first parameter but got " + param0);
				}
				if (!(param1 instanceof Identifier)) {
					throw new ActException(ActException.FAILURE,
							"action " + action.getName() + " needs Identifier as second parameter but got " + param1);
				}
				actionputdown(((Identifier) param0).getValue(), ((Identifier) param1).getValue());
			} else if (action.getName().equals("nil")) {
				actionnil();
			} else {
				throw new ActException(ActException.FAILURE, "unknown action " + action);
			}
		} catch (final RuntimeException e) {
			throw new ActException(ActException.FAILURE, "internal error while executing action " + action.toProlog(),
					e);
		}

	}

	/**
	 * Creates new tower environment interface and launches user interface.
	 */
	@Override
	public void init(final Map<String, Parameter> parameters) throws ManagementException {
		this.towerEnvironment = new TowerEnvironment(this);
		this.towerEnvironment.virtualWorld.addObserver(this);

		/*
		 * Define behavior when user closes window. Code is here because
		 * TowerEnvironment is not observable, and therefore we have no update event
		 * related to KILL.
		 */
		this.towerEnvironment.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent arg0) {
				try {
					kill();
				} catch (final ManagementException e) {
					e.printStackTrace();
				}
			}
		});

		pause(); // try to get in PAUSEd mode.

		try {
			addEntity(ENTITY);
		} catch (final EntityException e) {
			throw new ManagementException("could not add entity " + ENTITY + " to environment", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void kill() throws ManagementException {
		if (this.towerEnvironment != null) {
			this.towerEnvironment.close();
			this.towerEnvironment = null;
			setState(EnvironmentState.KILLED);
		} else {
			throw new ManagementException("The environment is not available and cannot be killed.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void pause() throws ManagementException {
		this.towerEnvironment.pause(); // triggers callback to update().
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void start() throws ManagementException {
		// EIS will only call this when we are in PAUSED mode.
		this.towerEnvironment.start(); // this triggers callback to update()
	}
}
