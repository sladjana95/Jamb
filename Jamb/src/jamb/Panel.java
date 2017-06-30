package jamb;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Panel extends JPanel{
    private final int panelWidth = Frame.getFrameWidth() - 25;
    private final int panelHeight = Frame.getFrameHeight()-70;

    private JButton roll;
    private JButton[] dices;
    private JButton[][] scoreTracker; 
    private JTextField[][] areaSum;
    private JTextField finalSum;
    private JTextField[] columnNames;
    private JButton[] rowNames;
    
    private int numberOfColumns = 3;
    private int numberOfGames = 13;
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
    public void newGame()
    {
        for(int i = 0; i <  numberOfDices; i++){
            dices[i].setText("");
            dices[i].setEnabled(false);
        }
        for(int i = 0; i <  numberOfColumns; i++)
            for(int j = 0; j <  numberOfGames; j++){
                scoreTracker[i][j].setText("");
                scoreTracker[i][j].setEnabled(true);
            }
        for(int i = 0; i < numberOfGames ; i++)
        {
             scoreTracker[0][i].setEnabled(false);
             scoreTracker[1][i].setEnabled(false);
        }
        scoreTracker[0][0].setEnabled(true);
        scoreTracker[1][numberOfGames-1].setEnabled(true);
        finalSum.setText("0");
        for(int i = 0; i < numberOfColumns; i ++)
        {
            areaSum[0][i].setText("0");
            areaSum[1][i].setText("0");
            areaSum[2][i].setText("0");
        }
        reset();
    }
    private void reset()
    {
        dicesRolled = 0;
        for(int i = 0; i <  numberOfDices; i++){
            dices[i].setText("");
            dices[i].setBackground(new JButton().getBackground());
        }
        normalize();
    }
    
    private void normalize()
    {
        
        for(int i = numberOfGames - 1; i >= 1 ; i--)
            if(!scoreTracker[1][i].getText().isEmpty())
                if(scoreTracker[1][i-1].getText().isEmpty()){
                    scoreTracker[1][i].setEnabled(false);
                    scoreTracker[1][i-1].setEnabled(true);
                }
        for(int i = 0; i < numberOfGames -1 ; i++)
            if(!scoreTracker[0][i].getText().isEmpty())
                if(scoreTracker[0][i+1].getText().isEmpty())
                {    
                    scoreTracker[0][i+1].setEnabled(true);
                    scoreTracker[0][i].setEnabled(false);
                }
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
                if(dicesRolled < 4)
                {
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
            }
        });
        add(roll);
        
        
        dices = new JButton[numberOfDices];
        for(int i = 0; i <  numberOfDices; i++)
        {
            dices[i] = new JButton("");
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
        
        int rowCounter = 0;
        int rowSkip = 0;
        scoreTracker = new JButton[numberOfColumns][numberOfGames];
        columnNames = new JTextField[numberOfGames];
        for(int i = 0; i <  numberOfColumns; i++){
            rowCounter = rowSkip = 0;
            for(int j = 0; j <  numberOfGames; j++){
                
                scoreTracker[i][j] = new JButton();
                scoreTracker[i][j].setBounds(200 + i*50, (1 + j + rowSkip)*40, 50, 40);
                scoreTracker[i][j].setVisible(true);
                scoreTracker[i][j].setFont(new Font("Arial", Font.PLAIN, 8));
                scoreTracker[i][j].setName(i + "-" + j + "-0");
                scoreTracker[i][j].addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent me) {
                        if(dicesRolled > 0 ){
                            JButton button = ((JButton) me.getComponent());
                            if(button.isEnabled())
                            {
                                button.setText(CountSum(button.getName()));
                                if(button.getName().endsWith("0"))
                                    button.setName(button.getName().substring(0, button.getName().length() - 2) + "-1");
                                button.setEnabled(false);
                                EditSum();
                                reset();
                           }
                        }
                    }

                    @Override
                    public void mouseEntered(MouseEvent me) {
                        if (dicesRolled > 0) {
                            JButton button = ((JButton) me.getComponent());
                            if (button.isEnabled()) {
                                button.setText(CountSum(button.getName()));
                            }
                        }
                    }

                    @Override
                    public void mouseExited(MouseEvent me) {
                        JButton button = ((JButton) me.getComponent());
                        
                       if(button.getName().endsWith("0"))
                           button.setText("");
                    }
                });
                add(scoreTracker[i][j]);
                rowCounter++;
                if(rowCounter == 6 ||  rowCounter == 8 || rowCounter == 13)
                    rowSkip++;
            }
        }
        rowCounter = rowSkip = 0;
        for(int i = 0; i < numberOfGames; i++)
        {
            columnNames[i] = new JTextField();
            columnNames[i].setBounds(0, (1 + i + rowSkip)*40, 150, 40);
            columnNames[i].setVisible(true);
            columnNames[i].setEnabled(false);
            add(columnNames[i]);
            
            rowCounter++;
            if(rowCounter == 6 ||  rowCounter == 8 || rowCounter == 13)
                rowSkip++;
        }       
        setNames();
        areaSum = new JTextField[3][numberOfColumns];
            for(int i = 0; i <  numberOfColumns; i++){
                    areaSum[0][i] = new JTextField();
                    areaSum[0][i].setBounds(200 + i*50, (7)*40, 50, 40);
                    areaSum[0][i].setVisible(true);
                    areaSum[0][i].setEnabled(false);
                    areaSum[0][i].setText("");
       
                    areaSum[1][i] = new JTextField();
                    areaSum[1][i].setBounds(200 + i*50, (10)*40, 50, 40);
                    areaSum[1][i].setVisible(true);
                    areaSum[1][i].setEnabled(false);
                    areaSum[1][i].setText("");
                    
                    areaSum[2][i] = new JTextField();
                    areaSum[2][i].setBounds(200 + i*50, (16)*40, 50, 40);
                    areaSum[2][i].setVisible(true);
                    areaSum[2][i].setEnabled(false);
                    areaSum[2][i].setText("");
       
                    //areaSum[i][j].setFont(new Font("Arial", Font.PLAIN, 8));
                    add(areaSum[0][i]);
                    add(areaSum[1][i]);
                    add(areaSum[2][i]);
                }
                finalSum = new JTextField();
                finalSum.setBounds(panelWidth- 150, panelHeight-100, 150, 50);
                finalSum.setVisible(true);
                finalSum.setEnabled(false);
                finalSum.setText("");
                add(finalSum);
                
                rowNames = new JButton[numberOfColumns];
            for(int i = 0; i <  numberOfColumns; i++){
                rowNames[i] = new JButton();
                rowNames[i].setBounds(200 + i*50, 0, 50, 40);
                rowNames[i].setVisible(true);

                rowNames[i].setEnabled(false);
                rowNames[i].setText("na");
                //areaSum[i][j].setFont(new Font("Arial", Font.PLAIN, 8));
                add(rowNames[i]);
            }
            rowNames[0].setText("↓");
            rowNames[1].setText("↑");
            rowNames[2].setText("↓↑");
            
    }
    private String CountSum(String name)
    {   
        if(!(dices[0].getText().isEmpty()))
        {
            String[] place = name.split("-");
            int gameIndex = Integer.parseInt(place[1]);

            int[] currerentDices = new int[6];
            for(int i = 0; i < numberOfDices; i++)
                currerentDices[i] = 0;
            for(int i = 0; i < numberOfDices; i++)
                currerentDices[Integer.parseInt(dices[i].getText()) - 1]++;
            if(gameIndex < 6)
                return "" + (currerentDices[gameIndex]*(gameIndex+1));
            else if(gameIndex < 8)
            {
                int tempSum = 0;
                for(int i = 0; i < 6; i++)
                    tempSum += currerentDices[i]*(i+1);
                return "" + tempSum;
            }
            else
            {
                int sum = 0;
                
                switch(gameIndex){
                    case 8:
                        if(currerentDices[0] == 0 || currerentDices[5] == 0)
                            {
                        for(int i = 0; i < 6; i++)
                            if(currerentDices[i] > 1)
                                return "0";
                            else
                                sum += (i+1);
                        return "" + (sum + 55 - dicesRolled*10);
                        }
                        else
                        return "0";
                    case 9:
                        for(int i = 0; i < 6; i++)
                            if(currerentDices[i] >= 3)
                                sum = 3*(i+1);
                        if(sum > 0)
                            return "" + (sum + 20);
                        else
                            return "0";
                    case 10:
                        int sum2 = 0;
                        for(int i = 0; i < 6; i++)
                            if(currerentDices[i] == 2)
                                sum2 = 2*(i+1);
                            else if(currerentDices[i] == 3)
                                sum = 3*(i+1);
                    if(sum > 0 && sum2 > 0)                        
                        return "" + (sum + sum2 + 30);
                    else
                            return "0";
                    case 11:
                        for(int i = 0; i < 6; i++)
                            if(currerentDices[i] >= 4)
                                sum = 4*(i+1);
                        if(sum > 0)
                            return "" + (sum + 40);
                        else
                            return "0";
                    case 12:
                        for(int i = 0; i < 6; i++)
                            if(currerentDices[i] >= 5)
                                sum = 5*(i+1);
                        if(sum > 0)
                            return "" + (sum + 50);
                        else
                            return "0";
                }
            }
            return "0";
        }
        else
            return "0";
    }
    private void EditSum()
    {
        int sum = 0;
        for(int i = 0; i < numberOfColumns; i++)
        {
            sum = 0;
            for(int j = 0; j < 6; j++)
                sum += Integer.parseInt((scoreTracker[i][j].getText().isEmpty()) 
                        ? ("0") : (scoreTracker[i][j].getText()));
            areaSum[0][i].setText("" + sum);
        }
        for(int i = 0; i < numberOfColumns; i++)
        {
            sum = Integer.parseInt((scoreTracker[i][6].getText().isEmpty()) 
                        ? ("0") : (scoreTracker[i][6].getText()));
            int sum2 = Integer.parseInt((scoreTracker[i][7].getText().isEmpty()) 
                        ? ("0") : (scoreTracker[i][7].getText()));
            int finalSum = (sum - sum2)*(Integer.parseInt((scoreTracker[i][0].getText().isEmpty()) 
                        ? ("0") : (scoreTracker[i][0].getText())));
            areaSum[1][i].setText("" + finalSum);
        }
        for(int i = 0; i < numberOfColumns; i++)
        {
            sum = 0;
            for(int j = 8; j < 13; j++)
                sum += Integer.parseInt((scoreTracker[i][j].getText().isEmpty()) 
                        ? ("0") : (scoreTracker[i][j].getText()));
            areaSum[2][i].setText("" + sum);
        }
        sum = 0;
        for(int j = 0; j < 3; j++)
            for(int i = 0; i < numberOfColumns; i++)
                sum += Integer.parseInt(areaSum[j][i].getText());
        finalSum.setText("" + sum);
        normalize();
    }
    private void setNames()
    {
        columnNames[0].setText("1");
        columnNames[1].setText("2");
        columnNames[2].setText("3");
        columnNames[3].setText("4");
        columnNames[4].setText("5");
        columnNames[5].setText("6");
        columnNames[6].setText("MAX");
        columnNames[7].setText("MIN");
        columnNames[8].setText("Kenta");
        columnNames[9].setText("Trlling");
        columnNames[10].setText("Full");
        columnNames[11].setText("Poker");
        columnNames[12].setText("Jamb");
    }
    
}
