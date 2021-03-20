import java.awt.*;
import javax.swing.*;
import java.awt.event.*;


public class ConsoleView implements ActionListener {

    // create a frame
    static JFrame win;
    private JButton play;

    static JProgressBar bar;
    private boolean chancePlayed;

    public ConsoleView() {

        chancePlayed = false;
        win = new JFrame("Play move: ");
        win.getContentPane().setLayout(new BorderLayout());
        ((JPanel) win.getContentPane()).setOpaque(false);

        bar = new JProgressBar();
        bar.setValue(50);
        bar.setStringPainted(true);

        play = new JButton("Play");
        play.addActionListener(this);

        JPanel panel = new JPanel();

//        panel.add(bar);
        panel.add(play);
        //
        win.getContentPane().add("Center", panel);

		win.pack();

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();
    }

    public double getUpdate() {

        int barValue = 0, adder = 1;

        while(!chancePlayed) {
            bar.setValue(barValue);

            try {
                Thread.sleep(10);
            } catch (Exception e) {
                System.err.println(e);
            }

            barValue += adder;

            if(barValue == 100) adder = -1;
            else if(barValue == 0)  adder = 1;
        }

        double skill = (Math.abs(barValue - 50)/(50.0));
        return (1.0 - skill);
    }

    public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(play)) {
            chancePlayed = true;
        }
	}

    public void hideWindow() {
		win.hide();
	}
}
