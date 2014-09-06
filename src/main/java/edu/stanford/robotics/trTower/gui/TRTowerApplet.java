package edu.stanford.robotics.trTower.gui;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import java.net.*;

import edu.stanford.robotics.trTower.*;

public class TRTowerApplet extends JApplet {
    protected static final int hs = 10;
    protected static final int vs = 10;
    
    public TRTowerApplet() {
	
	connect();

//  	getContentPane().setLayout(new BorderLayout());
//  	getContentPane().add(getTopPanel(), BorderLayout.NORTH);
//  	getContentPane().add(getBottomPanel(), BorderLayout.SOUTH);
//  	getContentPane().add(getTRAppBox(), BorderLayout.CENTER);
	setContentPane(getAppPanel());
    }

    // --- components
    private JPanel appPanel;
    protected JPanel getAppPanel() {
	if (appPanel == null) {
	    appPanel = new JPanel();
	    appPanel.setLayout(new GridBagLayout());
	    final int ipadx = 0;
	    final int ipady = 0;
	    final Insets insets = new Insets(0, 0, 0, 0);
	    GridBagConstraints top = new GridBagConstraints();
	    top.gridx = 0;
	    top.gridy = 0;
	    top.gridwidth = 3;
	    top.fill = GridBagConstraints.HORIZONTAL;
	    top.ipadx = ipadx;
	    top.ipady = ipady;
	    top.insets = insets;
	    appPanel.add(getTopPanel(), top);
	    GridBagConstraints actionTower = new GridBagConstraints();
	    actionTower.gridx = 0;
	    actionTower.gridy = 1;
	    actionTower.weightx = 0.34;
	    //	    actionTower.weighty = 0.5;
	    actionTower.fill = GridBagConstraints.BOTH;
	    actionTower.ipadx = ipadx;
	    actionTower.ipady = ipady;
	    actionTower.insets = insets;
	    appPanel.add(getActionTowerPanel(), actionTower);
	    GridBagConstraints virtualWorld = new GridBagConstraints();
	    virtualWorld.gridx = 1;
	    virtualWorld.gridy = 1;
	    virtualWorld.weightx = 0.33;
	    virtualWorld.fill = GridBagConstraints.BOTH;
	    virtualWorld.ipadx = ipadx;
	    virtualWorld.ipady = ipady;
	    virtualWorld.insets = insets;
	    appPanel.add(getVirtualWorldPanel(), virtualWorld);

	    GridBagConstraints durativeAction = new GridBagConstraints();
	    durativeAction.gridx = 2;
	    durativeAction.gridy = 1;
	    durativeAction.weightx = 0.33;
	    durativeAction.fill = GridBagConstraints.BOTH;
	    durativeAction.ipadx = ipadx;
	    durativeAction.ipady = ipady;
	    durativeAction.insets = insets;
	    appPanel.add(getDurativeActionPanel(), durativeAction);
//  	    GridBagConstraints perceptionTower = new GridBagConstraints();
//  	    perceptionTower.gridx = 0;
//  	    perceptionTower.gridy = 2;
//  	    perceptionTower.weighty = 0.5;
//  	    perceptionTower.fill = GridBagConstraints.BOTH;
//  	    perceptionTower.ipadx = ipadx;
//  	    perceptionTower.ipady = ipady;
//  	    perceptionTower.insets = insets;
//  	    appPanel.add(getPerceptionTowerPanel(), perceptionTower);
//  	    GridBagConstraints modelTower = new GridBagConstraints();
//  	    modelTower.gridx = 1;
//  	    modelTower.gridy = 2;
//  	    modelTower.gridwidth = 2;
//  	    modelTower.fill = GridBagConstraints.BOTH;
//  	    modelTower.ipadx = ipadx;
//  	    modelTower.ipady = ipady;
//  	    modelTower.insets = insets;
//  	    appPanel.add(getModelTowerPanel(), modelTower);

	    GridBagConstraints perceptionModel = new GridBagConstraints();
	    perceptionModel.gridx = 0;
	    perceptionModel.gridy = 2;
	    perceptionModel.gridwidth = 3;
	    perceptionModel.weighty = 1;
	    perceptionModel.fill = GridBagConstraints.BOTH;
	    perceptionModel.ipadx = ipadx;
	    perceptionModel.ipady = ipady;
	    perceptionModel.insets = insets;
	    appPanel.add(getPerceptionModelTowerPanel(), perceptionModel);
	    
	    GridBagConstraints bottom = new GridBagConstraints();
	    bottom.gridx = 0;
	    bottom.gridy = 3;
	    bottom.gridwidth = 3;
	    bottom.fill = GridBagConstraints.HORIZONTAL;
	    bottom.ipadx = ipadx;
	    bottom.ipady = ipady;
	    bottom.insets = insets;
	    appPanel.add(getBottomPanel(), bottom);
	}
	return appPanel;
    }
    private JPanel topPanel;
    // Wouter: quick hack, to get this panel
    public JPanel getTopPanel() {
	if (topPanel == null) {
	    topPanel = new JPanel();
	    topPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
	    topPanel.setLayout(new GridBagLayout());
	    GridBagConstraints trTower = new GridBagConstraints();
	    trTower.gridx = 0;
	    trTower.gridy = 0;
	    trTower.anchor = GridBagConstraints.WEST;
	    trTower.weightx = 1;
	    trTower.weighty = 1;
	    topPanel.add(getTRTowerActionLabel(), trTower);
	    GridBagConstraints about = new GridBagConstraints();
	    about.gridx = 1;
	    about.gridy = 0;
	    about.insets = new Insets(0, 0, 0, 10);
	    about.anchor = GridBagConstraints.EAST;
	    topPanel.add(getAboutActionLabel(), about);
	}
	return topPanel;
    }
    private ActionLabel trTowerActionLabel;
    protected ActionLabel getTRTowerActionLabel() {
	if (trTowerActionLabel == null) {
	    trTowerActionLabel = new ActionLabel();
	    trTowerActionLabel.setText("Teleo-Reactive Tower Builder");
	    trTowerActionLabel.setFont(trTowerActionLabel.getFont().deriveFont(Font.ITALIC, 18));

	    trTowerActionLabel.setAction(new AbstractAction("TRTower") {
		    public void actionPerformed(ActionEvent ae) {
			boolean useBuiltInHelpBrowser = 
			    (ae.getModifiers()&ActionEvent.SHIFT_MASK)==0? false : true;
			TRTower.getInstance().showHelp(useBuiltInHelpBrowser, 
						       "TR Tower", 
						       "help/TRTowerBuilder.html");
		    }
		});
	}
	return trTowerActionLabel;
    }

    private ActionLabel aboutActionLabel;
    protected ActionLabel getAboutActionLabel() {
	if (aboutActionLabel == null) {
	    aboutActionLabel = new ActionLabel();
	    aboutActionLabel.setText("About");
//  	    aboutActionLabel.setFont(aboutActionLabel.getFont().deriveFont(Font.PLAIN, 10));
	    aboutActionLabel.setFont(aboutActionLabel.getFont().deriveFont(Font.PLAIN));
	    aboutActionLabel.setAction(new AbstractAction("About") {
		    public void actionPerformed(ActionEvent ae) {
			TRTower.getInstance().showAbout();
		    }
		});
	}
	return aboutActionLabel;
    }

    private TRWorld trWorld;
    protected TRWorld getTRWorld() {
	if (trWorld == null) {
	    trWorld = new TRWorld();
	}
	return trWorld;
    }
    private ActionTowerPanel actionTowerPanel;
    protected ActionTowerPanel getActionTowerPanel() {
	if (actionTowerPanel == null) {
	    actionTowerPanel = new ActionTowerPanel();
	    actionTowerPanel.setActionTower(getTRWorld().getActionTower());
	//	getTRWorld().getStimulator().setActionTowerPanel(getActionTowerPanel());
	    getTRWorld().getStimulator().addStimulatorListener(actionTowerPanel);
	    
	}
	return actionTowerPanel;
    }
//      private VirtualWorldPresentationApplet virtualWorldPresentationApplet;
//      protected VirtualWorldPresentationApplet getVirtualWorldPresentationApplet() {
//  	if (virtualWorldPresentationApplet == null) {
//  	    virtualWorldPresentationApplet = new VirtualWorldPresentationApplet();
//  	}
//  	return virtualWorldPresentationApplet;
//      }
    private VirtualWorldPanel virtualWorldPanel;
    protected VirtualWorldPanel getVirtualWorldPanel() {
	if (virtualWorldPanel == null) {
	    virtualWorldPanel = new VirtualWorldPanel();
	    virtualWorldPanel.setVirtualWorld(getTRWorld().getVirtualWorld());
	    virtualWorldPanel.setModelTower(getTRWorld().getModelTower());
	    virtualWorldPanel.setStimulator(getTRWorld().getStimulator());
	    virtualWorldPanel.setAnimationTimer(getTRWorld().getAnimationTimer());
	}
	return virtualWorldPanel;
    }
    private TRCallPanel trCallPanel;
    protected TRCallPanel getTRCallPanel() {
	if (trCallPanel == null) {
	    trCallPanel = new TRCallPanel();
	    trCallPanel.setActionTower(getTRWorld().getActionTower());
	}
	return trCallPanel;
    }
    private StimulatorPanel stimulatorPanel;
    protected StimulatorPanel getStimulatorPanel() {
	if (stimulatorPanel == null) {
	    stimulatorPanel = new StimulatorPanel();
	    stimulatorPanel.setStimulator(getTRWorld().getStimulator());
	    
	}
	return stimulatorPanel;
    }

    // --- GUI components
    private JPanel bottomPanel;
    protected JPanel getBottomPanel() {
	if (bottomPanel == null) {
	    bottomPanel = new JPanel();
	    bottomPanel.setLayout(new GridLayout(1, 2, 5, 5));
//  	    GridBagConstraints messageLabelConstraints = 
//  		new GridBagConstraints();
//  	    messageLabelConstraints.gridx = 0;
//  	    messageLabelConstraints.gridy = 0;
//  	    messageLabelConstraints.fill = GridBagConstraints.HORIZONTAL;
//  	    messageLabelConstraints.weightx = 0.7;
//  	    messageLabelConstraints.weighty = 0;
	    bottomPanel.add(getMessageLabel());
//  	    GridBagConstraints durativeActionLabelConstraints =
//  		new GridBagConstraints();
//  	    durativeActionLabelConstraints.gridx = 1;
//  	    durativeActionLabelConstraints.gridy = 0;
//  	    durativeActionLabelConstraints.fill = 
//  		GridBagConstraints.HORIZONTAL;
//  	    durativeActionLabelConstraints.weightx = 0.3;
//  	    durativeActionLabelConstraints.weighty = 0;
	    bottomPanel.add(getDurativeArmActionStatusMessageLabel());
	}
	return bottomPanel;
    }
    private MessageLabel messageLabel;
    protected MessageLabel getMessageLabel() {
	if (messageLabel == null) {
	    messageLabel = new MessageLabel();
	}
	return messageLabel;
    }
//      private JLabel durativeActionLabel;
//      protected JLabel getDurativeActionLabel() {
//  	if (durativeActionLabel == null) {
//  	    durativeActionLabel = new JLabel();
//  	    //	    durativeActionLabel.setMinimumSize(new Dimension(16, 16));
//  	    durativeActionLabel.setFont(durativeActionLabel.getFont().deriveFont(Font.PLAIN));
//  	    durativeActionLabel.setText(" Test Message");
//  	    durativeActionLabel.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));

//  	}
//  	return durativeActionLabel;
//      }
    private DurativeArmActionStatusMessageLabel durativeArmActionStatusMessageLabel;
    protected DurativeArmActionStatusMessageLabel getDurativeArmActionStatusMessageLabel() {
	if (durativeArmActionStatusMessageLabel == null) {
	    durativeArmActionStatusMessageLabel = new DurativeArmActionStatusMessageLabel();
	    durativeArmActionStatusMessageLabel.setVirtualWorld(getTRWorld().getVirtualWorld());
	    getTRWorld().getStimulator().addStimulatorListener(durativeArmActionStatusMessageLabel);
	}
	return durativeArmActionStatusMessageLabel;
    }

//      private Box trAppBox;
//      protected Box getTRAppBox() {
//  	if (trAppBox == null) {
//  	    trAppBox = Box.createVerticalBox();
//  	    trAppBox.add(getTRWorldPanel());
//  	    trAppBox.add(getModelTowerBox());
//  	}
//  	return trAppBox;
//      }
//      private Box modelTowerBox;
//      protected Box getModelTowerBox() {
//  	if (modelTowerBox == null) {
//  	    modelTowerBox = Box.createHorizontalBox();
//  	    modelTowerBox.add(getPerceptionTowerPanel());
//  	    modelTowerBox.add(getModelTowerPanel());
//  	}
//  	return modelTowerBox;
//      }
//      private ModelPanel modelPanel;
//      protected ModelPanel getModelPanel() {
//  	if (modelPanel == null) {
//  	    modelPanel = new ModelPanel();
//  	}
//  	return modelPanel;
//      }
//      private ModelTowerPanel modelTowerPanel;
//      protected ModelTowerPanel getModelTowerPanel() {
//  	if (modelTowerPanel == null) {
//  	    modelTowerPanel = new ModelTowerPanel();
//  	    modelTowerPanel.setModelTower(getTRWorld().getModelTower());
//  	    getTRWorld().getStimulator().addStimulatorListener(modelTowerPanel);
//  	}
//  	return modelTowerPanel;
//      }
//      private BasicModelPanel basicModelPanel;
//      protected BasicModelPanel getBasicModelPanel() {
//  	if (basicModelPanel == null) {
//  	    basicModelPanel = new BasicModelPanel();
//  	    basicModelPanel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2));
//  	}
//  	return basicModelPanel;
//      }
//      private PerceptionTowerPanel perceptionTowerPanel;
//      protected PerceptionTowerPanel getPerceptionTowerPanel() {
//  	if (perceptionTowerPanel == null) {
//  	    perceptionTowerPanel = new PerceptionTowerPanel();
//  	    perceptionTowerPanel.setModelTower(getTRWorld().getModelTower());
//  	    getTRWorld().getStimulator().addStimulatorListener(perceptionTowerPanel);
//  	}
//  	return perceptionTowerPanel;
//      }
    private PerceptionModelTowerPanel perceptionModelTowerPanel;
    protected PerceptionModelTowerPanel getPerceptionModelTowerPanel() {
	if (perceptionModelTowerPanel == null) {
	    perceptionModelTowerPanel = new PerceptionModelTowerPanel();
	    perceptionModelTowerPanel.setModelTower(getTRWorld().getModelTower());
	    getTRWorld().getStimulator().addStimulatorListener(perceptionModelTowerPanel);
	}
	return perceptionModelTowerPanel;
    }
    //    private Box virtualWorldBox;
//      protected Box getVirtualWorldBox() {
//  	if (virtualWorldBox == null) {
//  	    virtualWorldBox = Box.createVerticalBox();
//  	    virtualWorldBox.add(Box.createVerticalStrut(vs));
//  	    virtualWorldBox.add(getVirtualWorldPresentationApplet());
//  	    virtualWorldBox.add(Box.createVerticalStrut(vs));
//  	    virtualWorldBox.add(getAnimationControlPanel());
//  	    virtualWorldBox.add(Box.createVerticalStrut(vs));
//  	    virtualWorldBox.add(getControlPanel());
//  	}
//  	return virtualWorldBox;
//      }
//      private JPanel virtualWorldPanel;
//      protected JPanel getVirtualWorldPanel() {
//  	if (virtualWorldPanel == null) {
//  	    virtualWorldPanel = new JPanel();
//  	    Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
//  	    Border bevel = new SoftBevelBorder(SoftBevelBorder.LOWERED);
//  	    virtualWorldPanel.setBorder(BorderFactory.createCompoundBorder(bevel, empty));
//  	    virtualWorldPanel.setLayout(new GridBagLayout());
//  	    GridBagConstraints title = new GridBagConstraints();
//  	    title.gridx = 0;
//  	    title.gridy = 0;
//  	    //	    title.fill = GridBagConstraints.BOTH;
//  	    title.weighty = 0.5;
//  	    title.anchor = GridBagConstraints.NORTHWEST;
//  	    virtualWorldPanel.add(getVirtualWorldActionLabel(), title);
//  	    GridBagConstraints applet = new GridBagConstraints();
//  	    applet.gridx = 0;
//  	    applet.gridy = 1;
//  //  	    applet.fill = GridBagConstraints.BOTH;
//  //  	    applet.weightx = 1;
//  //  	    applet.weighty = 1;
//  	    virtualWorldPanel.add(getVirtualWorldPresentationApplet(), applet);
//  	    GridBagConstraints anim = new GridBagConstraints();
//  	    anim.gridx = 0;
//  	    anim.gridy = 2;
//  	    anim.anchor = GridBagConstraints.SOUTH;
//  	    anim.fill = GridBagConstraints.HORIZONTAL;
//  	    anim.weightx = 1;
//  	    anim.weighty = 0.5;
//  	    virtualWorldPanel.add(getAnimationControlPanel(), anim);
//  //  	    GridBagConstraints control = new GridBagConstraints();
//  //  	    control.gridx = 0;
//  //  	    control.gridy = 3;
//  //  	    control.fill = GridBagConstraints.HORIZONTAL;
//  //  	    virtualWorldPanel.add(getControlPanel(), control);
//  	}
//  	return virtualWorldPanel;
//      }
//      private Box actionBox;
//      protected Box getActionBox() {
//  	if (actionBox == null) {
//  	    actionBox = Box.createVerticalBox();
//  //  	    actionBox.add(getStimulatorPanel());
//  //  	    actionBox.add(Box.createVerticalStrut(vs));
//  	    actionBox.add(getTRCallPanel());
//    	    actionBox.add(Box.createVerticalStrut(vs));
//  	    actionBox.add(getControlPanel());
//  	}
//  	return actionBox;
//      }
    private JPanel durativeActionPanel;
    protected JPanel getDurativeActionPanel() {
	if (durativeActionPanel == null) {
	    durativeActionPanel = new JPanel();
	    Border empty = BorderFactory.createEmptyBorder(5, 5, 5, 5);
	    Border bevel = new SoftBevelBorder(SoftBevelBorder.LOWERED);
	    durativeActionPanel.setBorder(BorderFactory.createCompoundBorder(bevel, empty));
	    durativeActionPanel.setLayout(new GridBagLayout());
	    GridBagConstraints trCall = new GridBagConstraints();
	    trCall.gridx = 0;
	    trCall.gridy = 0;
	    trCall.fill = GridBagConstraints.BOTH;
	    trCall.weightx = 1;
	    trCall.weighty = 1;
	    durativeActionPanel.add(getTRCallPanel(), trCall);
	    GridBagConstraints control  = new GridBagConstraints();
	    control.gridx = 0;
	    control.gridy = 1;
	    control.insets = new Insets(10, 0, 0, 0);
	    control.fill = GridBagConstraints.HORIZONTAL;
	    durativeActionPanel.add(getControlPanel(), control);
	}
	return durativeActionPanel;
    }
		

//      private Box trWorldBox;
//      protected Box getTRWorldBox() {
//  	if (trWorldBox == null) {
//  	    trWorldBox = Box.createHorizontalBox();
//  	    trWorldBox.add(getActionTowerPanel());
//  	    trWorldBox.add(Box.createRigidArea(new Dimension(hs, 0)));
//  	    trWorldBox.add(getVirtualWorldPanel());
//  	    trWorldBox.add(Box.createRigidArea(new Dimension(hs, 0)));
//  	    trWorldBox.add(getDurativeActionPanel());
//  	}
//  	return trWorldBox;
//      }
//      private JPanel trWorldPanel;
//      protected JPanel getTRWorldPanel() {
//  	if (trWorldPanel == null) {
//  	    trWorldPanel = new JPanel();
//  	    trWorldPanel.setLayout(new GridBagLayout());
//  	    GridBagConstraints actionTower = new GridBagConstraints();
//  	    actionTower.gridx = 0;
//  	    actionTower.gridy = 0;
//  	    actionTower.fill = GridBagConstraints.BOTH;
//  	    actionTower.weightx = 0.33;
//  	    actionTower.weighty = 1;
//  	    trWorldPanel.add(getActionTowerPanel(), actionTower);
//  	    GridBagConstraints virtualWorld = new GridBagConstraints();
//  	    virtualWorld.gridx = 1;
//  	    virtualWorld.gridy = 0;
//  	    virtualWorld.fill = GridBagConstraints.BOTH;
//  	    virtualWorld.weightx = 0.34;
//  	    trWorldPanel.add(getVirtualWorldPanel(), virtualWorld);
//  	    GridBagConstraints durativeAction = new GridBagConstraints();
//  	    durativeAction.gridx = 2;
//  	    durativeAction.gridy = 0;
//  	    durativeAction.fill = GridBagConstraints.BOTH;
//  	    durativeAction.weightx = 0.33;
//  	    trWorldPanel.add(getDurativeActionPanel(), durativeAction);
//  	}
//  	return trWorldPanel;
//      }

    private ControlPanel controlPanel;
    protected ControlPanel getControlPanel() {
	if (controlPanel == null) {
	    controlPanel = new ControlPanel();
	    controlPanel.setVirtualWorld(getTRWorld().getVirtualWorld());
	    controlPanel.setModelTower(getTRWorld().getModelTower());
	    controlPanel.setPerceptionTower(getTRWorld().getPerceptionTower());
	    controlPanel.setActionTower(getTRWorld().getActionTower());
	}
	return controlPanel;
    }

    // --- private methods
    private void connect() {
	getControlPanel().setMessageLabel(getMessageLabel());
	getTRCallPanel().setMessageLabel(getMessageLabel());

	// [fix this later]
	//	getAnimationControlPanel().setAnimatorApplet(getVirtualWorldPresentationApplet());
    }
    
    // --- public methods
    public void start() {
//  	getVirtualWorldPresentationApplet().start();
	getTRWorld().getAnimationTimer().startTimer();
    }
    public void stop() {
//  	getVirtualWorldPresentationApplet().stop();
	getTRWorld().getAnimationTimer().stopTimer();
    }
    public void init() {
//  	getVirtualWorldPresentationApplet().init();
	getTRWorld().getAnimationTimer().startTimer();
    }
    public void destroy() {
//  	getVirtualWorldPresentationApplet().destroy();
    }
}
