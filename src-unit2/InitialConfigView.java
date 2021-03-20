/* GameSettingsView.java
 */

/**
 * Class for game settings
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;
import java.text.*;

public class InitialConfigView implements ActionListener {
    private JFrame win;
	private JTextField numLanesF;
    private JTextField tieBreakerAllowedF;
    private JTextField maxPatronsF;
    private JTextField numFramesF;
	private JButton submitButton;

    public int numLanes;
    public int maxPatrons;
    public boolean tieBreakerAllowed;
    public int numFrames;
    private boolean formSubmitted;


    public InitialConfigView() {


        numLanes = 3;
        maxPatrons = 6 ;
        tieBreakerAllowed = false;
        numFrames = 10;
        formSubmitted = false;


        win = new JFrame("Set Initial Game Configurations");
		win.getContentPane().setLayout(new BorderLayout());
		((JPanel) win.getContentPane()).setOpaque(false);

        JPanel colPanel = new JPanel();
        colPanel.setLayout(new GridLayout(5, 5));

        JPanel numLanesPanel = new JPanel();
		numLanesPanel.setLayout(new FlowLayout());
		numLanesPanel.setBorder(new TitledBorder("Number of Lanes"));

		JPanel tiebreakerPanel = new JPanel();
		tiebreakerPanel.setLayout(new FlowLayout());
		tiebreakerPanel.setBorder(new TitledBorder("Is Tie Breaker Allowed? (1 if yes else 0)"));

		JPanel maxPatronsPanel = new JPanel();
		maxPatronsPanel.setLayout(new FlowLayout());
		maxPatronsPanel.setBorder(new TitledBorder("Max Patrons Per Party"));

		JPanel numFramesPanel = new JPanel();
		numFramesPanel.setLayout(new FlowLayout());
		numFramesPanel.setBorder(new TitledBorder("Number of Frames per Lane"));

        JPanel submitPanel = new JPanel();
		submitPanel.setLayout(new FlowLayout());

        numLanesF = new JTextField(20);
        numLanesPanel.add(numLanesF);

        tieBreakerAllowedF = new JTextField(20);
        tiebreakerPanel.add(tieBreakerAllowedF);

        maxPatronsF = new JTextField(20);
        maxPatronsPanel.add(maxPatronsF);

        numFramesF = new JTextField(20);
        numFramesPanel.add(numFramesF);

        submitButton = new JButton("Submit");
        submitButton.addActionListener(this);
        submitPanel.add(submitButton);

        colPanel.add(numLanesPanel);
		colPanel.add(tiebreakerPanel);
		colPanel.add(maxPatronsPanel);
		colPanel.add(numFramesPanel);
		// colPanel.add(extendedLastFramePanel);
		colPanel.add(submitPanel);
		// colPanel.add(rand4Panel);
		// colPanel.add(rand6Panel);
        // colPanel.add(rand5Panel);

        win.getContentPane().add("Center", colPanel);

		win.pack();
        Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();
    }

    public void actionPerformed(ActionEvent e) {

        if (e.getSource().equals(submitButton)) {
            try {
                String numLanesStr = numLanesF.getText();
                numLanes = Integer.parseInt(numLanesStr);
            } catch (NumberFormatException exception) {
                numLanes = 3;
            }

            try {
                String maxPatronsstr = maxPatronsF.getText();
                maxPatrons = Integer.parseInt(maxPatronsstr);
            } catch (NumberFormatException exception) {
                maxPatrons = 6;
            }

            try {
                String numFramesstr = numFramesF.getText();
                numFrames = Integer.parseInt(numFramesstr);
            } catch (NumberFormatException exception) {
                numFrames = 10;
            }

            try {
                String st = tieBreakerAllowedF.getText();
                if(st=="0") tieBreakerAllowed = false;
                else    tieBreakerAllowed = true;
            } catch (Exception exception) {
                tieBreakerAllowed = true;
            }

            formSubmitted = true;
        }
        win.hide();
    }

    public void getGameParameters() {

        while(!formSubmitted) {
            try {
                Thread.sleep(10);
            } catch (Exception e) {
                System.err.println(e);
            }
        }
    }

}
