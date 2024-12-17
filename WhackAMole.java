package Whack.a.mole;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.Objects;
import java.util.Random;

public class WhackAMole {
    int boardWidth = 600;
    int boardHeight = 650;

    JFrame frame = new JFrame("Mario: Whac A Mole");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[] board = new JButton[9];
    ImageIcon moleIcon;
    ImageIcon plantIcon;

    JButton currMoleTile;
    JButton currPlantTile;

    Random random = new Random();
    Timer setMoleTimer;
    Timer setPlantTimer;
    int score=0;

    WhackAMole(){ //This is the constructor
        //frame.setVisible(true);
        frame.setSize(boardWidth,boardHeight);
        frame.setLocationRelativeTo(null);  //This will open the window on the center of the screen
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   //This is the X icon to exit the program
        frame.setLayout(new BorderLayout());

        textLabel.setFont(new Font("Arial", Font.PLAIN,50));
        textLabel.setHorizontalAlignment(JLabel.CENTER);  //This will place the text in the center with the horizontal alignment
        textLabel.setText("Score: 0");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel,BorderLayout.NORTH);


        boardPanel.setLayout(new GridLayout(3,3));
        //boardPanel.setBackground(Color.black);
        frame.add(boardPanel);

        //plantIcon = new ImageIcon(getClass().getResource("./piranha.png"));
        Image plantImg = new ImageIcon(getClass().getResource("./piranha.png")).getImage();
        plantIcon = new ImageIcon(plantImg.getScaledInstance(150,150, Image.SCALE_SMOOTH));

        Image moleImg = new ImageIcon(Objects.requireNonNull(getClass().getResource("./monty.png"))).getImage();
        moleIcon = new ImageIcon(moleImg.getScaledInstance(150,150, Image.SCALE_SMOOTH));

        score=0;

        for(int i=0;i<9;i++){   // This is to add the 9 tiles
            JButton tile= new JButton();
            board[i] = tile;
            boardPanel.add(tile);
            tile.setFocusable(false);
            //tile.setIcon(moleIcon);
            tile.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton tile = (JButton) e.getSource();
                    if (tile==currMoleTile) {
                        score += 10;
                        textLabel.setText("Score: " + Integer.toString(score));
                    }
                        else if(tile==currPlantTile){
                            textLabel.setText("Game Over: "+Integer.toString(score));
                            setMoleTimer.stop();
                            setPlantTimer.stop();
                            for (int i=0;i<9;i++){
                                board[i].setEnabled(false);
                            }
                        }

                }
            });

        }

        setMoleTimer = new Timer(600, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currMoleTile != null){
                    currMoleTile.setIcon(null);
                    currMoleTile=null;
                }

                //randomly select another tile
                int num = random.nextInt(9);  //0-8
                JButton tile = board[num];

                //if tile is occupied by plant,skip tile for this turn.
                if(currPlantTile==tile) return;

                //set tile to mole
                currMoleTile  =tile;
                currMoleTile.setIcon(moleIcon);

            }
        });
        setPlantTimer = new Timer(600, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currPlantTile!=null){
                    currPlantTile .setIcon(null);
                    currPlantTile = null;
                }

                int num = random.nextInt(9);
                JButton tile = board[num];

                if(currMoleTile==tile) return;

                currPlantTile=tile;
                currPlantTile.setIcon(plantIcon);
            }
        });




        setMoleTimer.start();
        setPlantTimer.start();
        frame.setVisible(true);


    }
}
