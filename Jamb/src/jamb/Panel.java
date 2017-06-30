package jamb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Panel extends JPanel{
    private final int panelWidth = Frame.getFrameWidth() - 25;
    private final int panelHeight = Frame.getFrameHeight()-50;

    private JButton roll;
    private JButton[] dices;
    
    private final int numberOfDices = 5;
    private int dicesRolled = 0;
    public int getPanelWidth() {
        return panelWidth;
    }

    public int getPanelHeight() {
        return panelHeight;
    }

    public Panel() {
        setPreferredSize(new Dimension(panelWidth, panelHeight));
        setLayout(null);
        setBackground(Color.green);
        setFocusable(true);
        setVisible(true);
        
        initComponents();
        newGame();
        
    }
    private void newGame()
    {
        for(int i = 0; i <  numberOfDices; i++){
            dices[i].setText(" ");
            dices[i].setEnabled(false);
        }
        
        dicesRolled = 0;
    }
    private void initComponents()
    {
        roll = new JButton("Roll dices");
        roll.setBounds(panelWidth-150, panelHeight-50, 150, 50);
        roll.setVisible(true);
        roll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dicesRolled++;
                Random random = new Random();
                int[] nonChosenIndexes = new int[numberOfDices];
                for(int i = 0; i <  numberOfDices; i++)
                {
                    if(dicesRolled == 1)
                        dices[i].setEnabled(true);
                    if(dices[i].getBackground() == Color.YELLOW)
                        nonChosenIndexes[i] = i;
                    else
                        nonChosenIndexes[i] = -1;
                }
                for(int i = 0; i <  numberOfDices; i++)
                    if(nonChosenIndexes[i] < 0)
                        dices[i].setText("" + (random.nextInt(6) + 1));
            }
        });
        add(roll);
        
        
        dices = new JButton[numberOfDices];
        for(int i = 0; i <  numberOfDices; i++)
        {
            dices[i] = new JButton(" 4");
            dices[i].setBounds(40 + i*60, panelHeight-50, 50, 50);
            dices[i].setVisible(true);
            dices[i].setName("" + i);
            dices[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    JButton pressedButton = ((JButton) ae.getSource());
                    if(pressedButton.getBackground() == Color.YELLOW)
                    {
                        pressedButton.setBackground(new JButton().getBackground());
                    }
                    else{
                        
                        pressedButton.setBackground(Color.YELLOW);
                    
                    }
                }   
            });
            add(dices[i]);
        }
    }
    
}
