import java.awt.*;
import javax.swing.JFrame;

public class EmojiRections extends Canvas{

    private String currentImage;

    public EmojiRections(int val) {
        if(val == 10)   currentImage = "code/happy.gif";
        else if(val == 0)   currentImage = "code/sad.gif";
    }

    public void paint(Graphics g) {
        Toolkit t=Toolkit.getDefaultToolkit();
        Image i=t.getImage(currentImage);
        g.drawImage(i, 0, 0, getWidth(), getHeight(), this);
    }

    public void show() {
        // MyCanvas m=new MyCanvas();
        JFrame frame=new JFrame();
        frame.add(this);
        frame.setSize(400, 400);
        frame.setVisible(true);

        try {
            Thread.sleep(3000);
        } catch ( InterruptedException e ) {
            System.err.println( "Interrupted" );
        }

        // this.hide();
        frame.hide();
    }

}
