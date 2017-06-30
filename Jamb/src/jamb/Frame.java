package jamb;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Frame extends JFrame{
    private  static final int frameHeight = 800;
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
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setJMenuBar(initMenuBar());
        panel = new Panel();
        add(panel);
        pack();
    }
    
    public JMenuBar initMenuBar()
    {
        JMenuBar menuBar = new JMenuBar();
        
        JMenu Files = new JMenu("Files");
        
        JMenuItem newGame = new JMenuItem("New game");
        JMenuItem exit = new JMenuItem("Exit");
        
        newGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int again = javax.swing.JOptionPane.showConfirmDialog(null, 
                        "Are you sure you want to start new game?",
                        "Question?", javax.swing.JOptionPane.YES_NO_OPTION, 
                                            javax.swing.JOptionPane.WARNING_MESSAGE);
                if(again == javax.swing.JOptionPane.YES_OPTION) 
                    panel.newGame();
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int again;
                again = javax.swing.JOptionPane.showConfirmDialog(null, "Are you sure you want to exit?",
                                            "Question?", javax.swing.JOptionPane.YES_NO_OPTION, 
                                            javax.swing.JOptionPane.WARNING_MESSAGE);
                if(again == javax.swing.JOptionPane.YES_OPTION)
                    System.exit(0);
            }        
        });
        
        Files.add(newGame);
        Files.add(exit);
        menuBar.add(Files);
        
        return menuBar;
    
    }
    
}
