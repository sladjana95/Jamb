package jamb;

import java.awt.Dimension;
import javax.swing.JFrame;

public class Frame extends JFrame{
    private  static final int frameHeight = 600;
    private static final int frameWidth = 600;

    private Panel panel;
    
    public static int getFrameHeight() {
        return frameHeight;
    }

    public static int getFrameWidth() {
        return frameWidth;
    }

    public Frame() {
        setPreferredSize(new Dimension(frameWidth, frameHeight));
        setVisible(true);
        setResizable(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        panel = new Panel();
        add(panel);
        pack();
    }
    
    
}
