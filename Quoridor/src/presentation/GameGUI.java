package presentation;

import domain.*;


import javax.swing.border.Border;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class GameGUI extends JDialog{
	


	protected JPanel playerPanel;
    private JPanel board;
    private JTextField namePlayer;
    private JTextField colorPlayer;
    private JTextField remainingNormal;
    private JTextField remainingTemporary;
    private JTextField remainingLong;
    private JTextField remainingAlly;
    private JTextField visitedOrdinary;
    private JTextField visitedTeleporter;
    private JTextField visitedRevert; 
    private JTextField visitedDoubleTurn;
    private JTextField timeText;
    private ImageIcon player1Icon;
    private ImageIcon player2Icon;
 
    
    private JButton saveButton;
    private JButton openButton;
    private JButton finishButton;

    private JComboBox<String> wallsComboBox;
    
    
	
    /**
     * Constructor for the GameGUI class, which initializes the game interface.
     * 
     * @param 
     */
	protected GameGUI() {
	
        prepareElements();
        prepareActions();
    }
	
	
	/**
     * Prepares mainPanel in the center.
     * @param mainPanel
     * @return
     */
    private void addComponentsToFrame(JPanel mainPanel) {
        setLayout(new GridBagLayout());
        GridBagConstraints frameConstraints = new GridBagConstraints();
        frameConstraints.gridx = 0;
        frameConstraints.gridy = 0;
        frameConstraints.weightx = 1;
        frameConstraints.weighty = 1;
        frameConstraints.anchor = GridBagConstraints.CENTER;
        add(mainPanel, frameConstraints);
    }
    
    
    
	/**
     * Show the game interface
     * 
     * @param
     * @return
     */
	private void prepareElements()	{
		setTitle("Quoridor");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width+5;
        int screenHeight = screenSize.height-45;
        setSize(screenWidth, screenHeight);
        setLocationRelativeTo(null); 
        setContentPane(new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon imageIcon = new ImageIcon("resources/image.jpg");
                Image image = imageIcon.getImage();
                g.drawImage(image, 0, 0, getWidth(), getHeight(), this);
                
            }
        });       
		JPanel gamePanel = new JPanel(new BorderLayout());
        gamePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10));
        gamePanel.setPreferredSize(new Dimension(1350, 750));
        JPanel leftPanel = new JPanel(new BorderLayout());
        JPanel rightPanel = new JPanel();
        leftPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        int boardSize = gamePanel.getPreferredSize().width / 2;
        board = new JPanel();
        board.setPreferredSize(new Dimension(boardSize + 50, boardSize + 50));
        JLabel titleLabel = new JLabel("QUORIDOR", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        leftPanel.add(titleLabel, BorderLayout.NORTH);
        leftPanel.add(board, BorderLayout.CENTER);  
        gamePanel.add(leftPanel, BorderLayout.WEST);
        gamePanel.add(rightPanel, BorderLayout.EAST);
        addComponentsToFrame(gamePanel);
        prepareElementsBoard(leftPanel); 
        prepareElementsOptions(rightPanel);
	    Quoridor quoridor = Quoridor.getQuoridor();	
	    paintSquares(quoridor.getBoard());
        paintPlayers();  
        QuoridorGUI quoridorGUI = QuoridorGUI.getQuoridorGUI();
        String d = quoridorGUI.getDifficulty();
        if (d.equals("Timetrial")) prepareTime1();
        if (d.equals("Timed")) prepareTime2();
	}
	
	/**
	 * Prepares the time display component.
	 * 
	 * @param
	 * @return
	 */
	private void prepareTime1()	{
		JLabel timeLabel = new JLabel("Tiempo:");
        playerPanel.add(timeLabel);
        timeText = new JTextField(String.format("%30s", "0"), SwingConstants.CENTER);
        timeText.setEditable(false);
        timeText.setMaximumSize(new Dimension(500, 50));

        playerPanel.add(timeText);
        Thread updateTimeThread = new Thread(() -> {
            while (true) { 
                try {
                	Quoridor quoridor = Quoridor.getQuoridor();
                	if (quoridor != null){
                    String currentTime = quoridor.getTime(); 
                    timeText.setText(String.format("%30s", currentTime));
                    Thread.sleep(1000);	}
                } catch (InterruptedException e) {
                	Log.record(e);
                }
            }
        });
        updateTimeThread.start();
	}

	
	/**
	 * Prepares the time display component.
	 * 
	 * @param
	 * @return
	 */
	private void prepareTime2() {
	    JLabel timeLabel = new JLabel("Tiempo:");
	    playerPanel.add(timeLabel);
	    timeText = new JTextField(String.format("%30s", "0"), SwingConstants.CENTER);
	    timeText.setEditable(false);
	    timeText.setMaximumSize(new Dimension(500, 50));

	    playerPanel.add(timeText);
	    
	    Thread updateTimeThread = new Thread(() -> {
	        while (true) {
	            try {
	                Quoridor quoridor = Quoridor.getQuoridor();
	                if (quoridor != null){
	                String accumulatedTime = quoridor.getTimeInSeconds(); 
	                if (accumulatedTime.contains("-"))	{
	                timeText.setText(String.format("%30s", "0"));	}
	                else {timeText.setText(String.format("%30s", accumulatedTime));}
	                Thread.sleep(1000);	}
	            } catch (InterruptedException e) {
	            	Log.record(e);
	            }
	        }
	    });
	    updateTimeThread.start();
	}

	
	/**
     * Paint the squares on board.
     * 
     * @param board
     * @return
     */
    private void paintSquares(String[][] board) {
    	Quoridor quoridor = Quoridor.getQuoridor();
	    for (int row = 0; row < quoridor.getSize(); row++) {
	    	for (int col = 0; col < quoridor.getSize(); col++) {
	        	String squareType = board[row][col];
	        	Color color = getColorForSquare(squareType);
	        	paintCell(row, col, color);
	        }
	    }	       
    }
    
    
    /**
     * 
     * @param board
     * @return
     */
    private void paintPlayers() {
       Quoridor quoridor = Quoridor.getQuoridor();
	   int[] indixes = quoridor.findPlayerIndixes();
	   String[] names = quoridor.getPlayers();
	   String p1 = quoridor.getColor(names[0]);
	   String p2 = quoridor.getColor(names[1]);
	   if (p1.equals("Amarillo")) player1Icon = new ImageIcon("resources/Amarillo.gif");
	   if (p1.equals("Azul")) player1Icon = new ImageIcon("resources/Azul.gif");
	   if (p1.equals("Rojo")) player1Icon = new ImageIcon("resources/Rojo.gif");
	   if (p2.equals("Verde")) player2Icon = new ImageIcon("resources/Verde.gif");	        
	   if (p2.equals("Naranja")) player2Icon = new ImageIcon("resources/Naranja.gif");
	   if (p2.equals("Negro")) player2Icon = new ImageIcon("resources/Negro.gif");
	   JButton player1Button = (JButton) this.board.getComponent(indixes[0]);
	   JButton player2Button = (JButton) this.board.getComponent(indixes[1]);       
       player1Button.setIcon(player1Icon);
       player2Button.setIcon(player2Icon);
    }
    
    
    
    
    /**
     * According to the type, defines the color of the squares.
     * 
     * @param squareType
     * @return
     */
    private Color getColorForSquare(String squareType) {
    	String[] parts = squareType.split("\\|");       
        String className = parts[0];
        
    	int classNameIndex = 0;
        for (int i = 0; i < squareType.length(); i++) {
            if (!Character.isDigit(squareType.charAt(i))) {
                classNameIndex = i;
                break;
            }
        }
        String s = className.substring(classNameIndex);
        switch (s) {
            case "Ordinary":
                return Color.PINK;
            case "Teleporter":
                return Color.CYAN;
            case "Revert":
                return Color.RED;
            case "DoubleTurn":
                return Color.MAGENTA;
            default:
                return Color.DARK_GRAY; 
        }
    }
    
    
    /**
     * Paint the cell according to a given color.
     * 
     * @param panel
     * @param row
     * @param col
     * @param color
     * @return
     */
    private void paintCell(int row, int col, Color color) {
        try	{
        	Quoridor quoridor = Quoridor.getQuoridor(0,null,null,null,null,null,null,null,0);	
        	int n = row * quoridor.getSize() + col;
            Component component = board.getComponent(n);
            
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.setBackground(color);
            }
	    } catch (QuoridorException e)	{
	    	Log.record(e);
	    	JOptionPane.showMessageDialog(null, e.getMessage());
	    }
    }
    
    
	/*
     * Sets up the board elements.
     * @param
     * @return  
     */
    private void prepareElementsBoard(JPanel leftPanel) {
    	
        Quoridor quoridor = Quoridor.getQuoridor();
	    board.setLayout(new GridLayout(quoridor.getSize(), quoridor.getSize()));	    
	    for (int i = 0; i < quoridor.getSize()*quoridor.getSize(); i++) {
	          JButton button = new JButton(); 
	          button.setBackground(Color.WHITE);
	          button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	          board.add(button);
	   }
    }
    
    
    /*
     * Sets up the options elements.
     * @param
     * @return  
     */
    private void prepareElementsOptions(JPanel rightPanel)  {
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 40));        
        prepareElementsTurn(rightPanel);
        prepareElementsWalls(rightPanel);
        prepareElementsButtons(rightPanel);
        rightPanel.add(Box.createRigidArea(new Dimension(24, 24)));
    }
    
    
    /**
     * Prepares the turn panel containing player information, remaining walls, and visited squares.
     * 
     * @param rightPanel The panel to which the turn panel will be added.
     * @return
     */
    private void prepareElementsTurn(JPanel rightPanel) {
        JPanel turnPanel = new JPanel();
        turnPanel.setLayout(new BoxLayout(turnPanel, BoxLayout.Y_AXIS));
        int turnPanelWidth = 400; 
        int turnPanelHeight = 420;
        turnPanel.setPreferredSize(new Dimension(turnPanelWidth, turnPanelHeight));
        turnPanel.add(Box.createVerticalStrut(12)); 
        JLabel titleLabel = new JLabel("Turno", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
        turnPanel.add(titleLabel);
        
        JPanel playerPanel = preparePlayerPanel();
        
        JLabel wallsLabel = new JLabel(String.format("%40s", "Barreras restantes"), SwingConstants.LEFT);
        wallsLabel.setFont(new Font("Arial", Font.BOLD, 15));        
        JPanel wallsModifyPanel = prepareWallsPanel();        
        JLabel squaresLabel = new JLabel(String.format("%40s", "Casillas visitadas"), SwingConstants.LEFT);
        squaresLabel.setFont(new Font("Arial", Font.BOLD, 15));                
        JPanel squaresModifyPanel = prepareSquaresPanel();        
        turnPanel.add(playerPanel);
        turnPanel.add(wallsLabel);
        turnPanel.add(wallsModifyPanel);
        turnPanel.add(squaresLabel);
        turnPanel.add(squaresModifyPanel);        
        rightPanel.add(turnPanel);
    }
    
    
    /**
     * Prepares the walls panel for placing barriers.
     * 
     * @param rightPanel The panel to which the walls panel will be added.
     * @return
     */
    private void prepareElementsWalls(JPanel rightPanel) {
        JPanel wallsPanel = new JPanel();
        wallsPanel.setLayout(new BoxLayout(wallsPanel, BoxLayout.Y_AXIS));        
        JLabel titleLabel = new JLabel(String.format("%15sColocar barrera", ""), SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        wallsPanel.add(titleLabel);       
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        String[] walls = {"Normal", "Temporal", "Larga", "Aliada"};
        wallsPanel.add(Box.createVerticalStrut(12));
        wallsComboBox = new JComboBox<>(walls);
        wallsComboBox.setAlignmentX(Component.CENTER_ALIGNMENT);
        wallsPanel.add(wallsComboBox);
        wallsPanel.add(Box.createVerticalStrut(12)); 
        wallsPanel.add(Box.createVerticalStrut(10));        
        buttonPanel.add(Box.createHorizontalGlue()); 
        buttonPanel.add(Box.createHorizontalGlue());         
        wallsPanel.add(buttonPanel);
        wallsPanel.add(Box.createVerticalStrut(12));
        wallsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);        
        rightPanel.add(Box.createVerticalGlue()); 
        rightPanel.add(wallsPanel);
        rightPanel.add(Box.createVerticalGlue()); 
    }
    
    
    /**
     * Prepares the buttons panel with options.
     * 
     * @param rightPanel The panel to which the buttons panel will be added.
     */
    private void prepareElementsButtons(JPanel rightPanel) {
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS));        
        JLabel titleLabel = new JLabel("Opciones                          ", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        buttonsPanel.add(titleLabel);
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0)); 
        saveButton = new JButton("Guardar");
        openButton = new JButton("Abrir");
        finishButton = new JButton("Terminar");
        buttonsPanel.add(Box.createVerticalStrut(12));
        Dimension buttonSize = new Dimension(110, 35);
        saveButton.setPreferredSize(buttonSize);
        openButton.setPreferredSize(buttonSize);
        finishButton.setPreferredSize(buttonSize);
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); 
        buttonPanel.add(openButton);
        buttonPanel.add(Box.createHorizontalStrut(10)); 
        buttonPanel.add(finishButton);
        buttonsPanel.add(buttonPanel);
        buttonsPanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        rightPanel.add(Box.createVerticalGlue()); 
        rightPanel.add(buttonsPanel);
        rightPanel.add(Box.createVerticalGlue()); 
    }
    
    
    
    /**
     * Prepares the player panel with information about the current player.
     * 
     * @param
     * @return The player panel with player information.
     */
    private JPanel preparePlayerPanel() {
        playerPanel = new JPanel();
        playerPanel.setLayout(new GridLayout(3, 3, 10, 10)); 

        JLabel playerLabel = new JLabel("Jugador:");
        JLabel colorLabel = new JLabel("Color:");
        try	{
        Quoridor quoridor = Quoridor.getQuoridor(0,null,null,null,null,null,null,null,0);
        namePlayer = new JTextField(String.format("%7s", quoridor.getCurrentPlayerName()), SwingConstants.CENTER);
        namePlayer.setHorizontalAlignment(SwingConstants.CENTER);
        namePlayer.setEditable(false);
        namePlayer.setMaximumSize(new Dimension(500, 50));
        colorPlayer = new JTextField(String.format("%7s", quoridor.getColor(quoridor.getCurrentPlayerName())), SwingConstants.CENTER);
        colorPlayer.setHorizontalAlignment(SwingConstants.CENTER);
        colorPlayer.setEditable(false);
        colorPlayer.setMaximumSize(new Dimension(500, 50));
        playerPanel.add(playerLabel);
        playerPanel.add(namePlayer);
        playerPanel.add(colorLabel);
        playerPanel.add(colorPlayer);
        playerPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        } catch(QuoridorException e)	{
        	Log.record(e);
        	JOptionPane.showMessageDialog(null, e.getMessage());
        }
        return playerPanel;
    }

    
    
    /**
     * Prepares the walls panel with information about the remaining walls of each type.
     * 
     * @param
     * @return The walls panel with information about remaining walls.
     */
    private JPanel prepareWallsPanel() {
    	JPanel wallsModifyPanel = new JPanel(new GridLayout(4, 2, 5, 5));
    	
        Quoridor quoridor = Quoridor.getQuoridor();
    	
        JLabel normalWallsLabel = new JLabel("Normal:");
        remainingNormal = new JTextField(String.format("%30s", quoridor.getRemainingWallsByType("Normal")), SwingConstants.CENTER);
        remainingNormal.setEditable(false);
        JLabel temporalWallsLabel = new JLabel("Temporal:");
        remainingTemporary = new JTextField(String.format("%30s", quoridor.getRemainingWallsByType("Temporary")), SwingConstants.CENTER);
        remainingTemporary.setEditable(false);
        JLabel longWallsLabel = new JLabel("Larga:");
        remainingLong= new JTextField(String.format("%30s", quoridor.getRemainingWallsByType("Long")), SwingConstants.CENTER);
        remainingLong.setEditable(false);
        JLabel allyWallsLabel = new JLabel("Aliada:");
        remainingAlly = new JTextField(String.format("%30s", quoridor.getRemainingWallsByType("Ally")), SwingConstants.CENTER);
        remainingAlly.setEditable(false);        
        wallsModifyPanel.add(normalWallsLabel);
        wallsModifyPanel.add(remainingNormal);
        wallsModifyPanel.add(temporalWallsLabel);
        wallsModifyPanel.add(remainingTemporary);
        wallsModifyPanel.add(longWallsLabel);
        wallsModifyPanel.add(remainingLong);
        wallsModifyPanel.add(allyWallsLabel);
        wallsModifyPanel.add(remainingAlly);        
        
    	return wallsModifyPanel;
    }
    
    
    /**
     * Prepares the squares panel with information about visited squares and movements number.
     * 
     * @return The squares panel with information about visited squares and movements number.
     */
    private JPanel prepareSquaresPanel() {
    	JPanel squaresModifyPanel = new JPanel(new GridLayout(5, 2, 5, 5));
        JLabel normalSquaresLabel = new JLabel("Normal:");
        visitedOrdinary = new JTextField(String.format("%30s", "0"), SwingConstants.CENTER);
        visitedOrdinary.setEditable(false);
        JLabel teleporterSquaresLabel = new JLabel("Teletransportador:");
        visitedTeleporter = new JTextField(String.format("%30s", "0"), SwingConstants.CENTER);
        visitedTeleporter.setEditable(false);
        JLabel revertSquaresLabel = new JLabel("Regresar:");
        visitedRevert = new JTextField(String.format("%30s", "0"), SwingConstants.CENTER);
        visitedRevert.setEditable(false);
        JLabel doubleturnSquaresLabel = new JLabel("Turno Doble:");
        visitedDoubleTurn = new JTextField(String.format("%30s", "0"), SwingConstants.CENTER);
        visitedDoubleTurn.setEditable(false);
        squaresModifyPanel.add(normalSquaresLabel);
        squaresModifyPanel.add(visitedOrdinary);
        squaresModifyPanel.add(teleporterSquaresLabel);
        squaresModifyPanel.add(visitedTeleporter);
        squaresModifyPanel.add(revertSquaresLabel);
        squaresModifyPanel.add(visitedRevert);
        squaresModifyPanel.add(doubleturnSquaresLabel);
        squaresModifyPanel.add(visitedDoubleTurn); 
        return squaresModifyPanel;
    }
    
    
    /**
     * Prepares the actions for the game window.
     * 
     * @param
     * @return
     */
	private void prepareActions()	{
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		//prepareActionMovePlayer();
        //prepareActionPutWall();
		prepareActionsMoveOrPutWall();
		
        finishButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	finish();          
            }           
        });
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionSave();
            }
            }
        );
        
        openButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                optionOpen();
            }
			}
        );
        
		
	}

	
	/**
	 * 
	 * @param
	 * @return
	 */
	private void prepareActionsMoveOrPutWall()	{
		for (Component component : board.getComponents()) {
            if (component instanceof JButton) {
                JButton button = (JButton) component;
                button.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                    	prepareAction(button, e);
                    }
                });
            }
        }
	}
	
	/**
	 * 
	 * 
	 * @param button
	 * @param d
	 */
	private void actionBarrier(JButton button, char d)	{
		String wallType = (String) wallsComboBox.getSelectedItem();     
       	Quoridor quoridor = Quoridor.getQuoridor();
        	int n = board.getComponentZOrder(button);
           int[] l = new int[]{n/quoridor.getSize(), n % quoridor.getSize()};
           try	{                        		
           	quoridor.putWall(l, d, wallType);	                        	
               int n2 = quoridor.getPointFinalWall(n,d);
               JButton  button2 = (JButton) board.getComponent(n2); JButton  button3 = null;
               if (wallType.equals("Larga")) 	{
               	int n3 = quoridor.getPointFinalWall(n2,d);
	                button3 = (JButton) board.getComponent(n3);
               }addWallToButton(button,button2,button3, d, wallType);               
               act();    
           } catch (QuoridorException q)	{
           	Log.record(q);
               JOptionPane.showMessageDialog(null, q.getMessage());
           }
           refresh();
	}
	
	
	/**
	 * Move peon of current player.
	 * 
	 * @param button
	 */
	private void actionMovePeon(JButton button)	{
		Quoridor quoridor = Quoridor.getQuoridor();
    	int[] pos = quoridor.getLocationPeon();
    	JButton buttonInit = (JButton) board.getComponent(pos[0]*quoridor.getSize()+pos[1]);
    	Icon playerIcon = buttonInit.getIcon();
    	int n = board.getComponentZOrder(button);
	    	try	{
	    	String winner = quoridor.getCurrentPlayerName();
	    	boolean res = quoridor.getState();         	
	        if (!res){ JOptionPane.showMessageDialog(null,"El tiempo se ha terminado para ambos."); 
	        finish(); return;}
	    	quoridor.movePeon(new int[]	{n/quoridor.getSize(),n%quoridor.getSize()});  buttonInit.setIcon(null);	    		    	
	        button.setIcon(playerIcon); refresh(); 
			String[] players = quoridor.getPlayers();
			int[] p1 = quoridor.getLocationPeon(players[0]);
			int[] p2 = quoridor.getLocationPeon(players[1]);
			int n1 = p1[0] * quoridor.getSize() + p1[1];
			int n2 = p2[0] * quoridor.getSize() + p2[1];
			repaintElements(n1,n2);
    	boolean res2 = quoridor.getState();         	
        if (res2) 	{
        	refresh();
        } else {	
        	JOptionPane.showMessageDialog(null, "El ganador es: "+winner+"."); finish();
        }
    	} catch(QuoridorException ex) {   
    		refresh();
    		Log.record(ex);
    		JOptionPane.showMessageDialog(null, ex.getMessage());
    	}
	}
	
	/**
	 * 
	 * 
	 * @param
	 * @return
	 */
	private void prepareAction(JButton button, ActionEvent e)	{
		char d = determineClickOrientation(button, e);
	
        if (d != 'C') {
        	actionBarrier(button, d);
        } else 	{
        	actionMovePeon(button);
        }
		
	}
	
	
	/**
     * Option open.
     * 
     * @param
     * @return
     */
	private void optionOpen()	{
		JFileChooser filechooser = new JFileChooser();
        int option = filechooser.showOpenDialog(GameGUI.this);
        if (option == JFileChooser.APPROVE_OPTION){
            File file = filechooser.getSelectedFile();
            try {
            	Quoridor quoripoob = Quoridor.getQuoridor();
				quoripoob.open(file);
				refresh();
				String[] players = quoripoob.getPlayers();
				int[] p1 = quoripoob.getLocationPeon(players[0]);
				int[] p2 = quoripoob.getLocationPeon(players[1]);
				int n1 = p1[0] * quoripoob.getSize() + p1[1];
				int n2 = p2[0] * quoripoob.getSize() + p2[1];
				repaintElements(n1,n2);
			} catch (QuoridorException ex)	{
				Log.record(ex);
				JOptionPane.showMessageDialog(GameGUI.this, ex.getMessage());	
			}
        }    		
	}
	
	
	/**
     * Option save.
     * @param
     * @return
     */
	private void optionSave()	{
        JFileChooser filechooser = new JFileChooser();
        int option = filechooser.showSaveDialog(GameGUI.this);
        if (option == JFileChooser.APPROVE_OPTION){
            File file = filechooser.getSelectedFile();
            try {
				Quoridor quoripoob = Quoridor.getQuoridor();
				quoripoob.save(file);
			} catch (QuoridorException ex)	{
				Log.record(ex);
				JOptionPane.showMessageDialog(null, ex.getMessage());	
			}
        }
	}
	
	
	/**
	 * Finish the game and close the GUI.
	 * 
	 * @param
	 * @return
	 */
	private void finish()	{
		QuoridorGUI gui = QuoridorGUI.getQuoridorGUI();
		gui.finish();
	}
	
    
  
   
    
    /**
     * Paint the actions of machine.
     * 
     * @param
     * @return
     */
    private void act()	{
    	Quoridor quoridor = Quoridor.getQuoridor();
	    String[] players = quoridor.getPlayers();
		int[] p1 = quoridor.getLocationPeon(players[0]);
		int[] p2 = quoridor.getLocationPeon(players[1]);
		int n1 = p1[0] * quoridor.getSize() + p1[1];
		int n2 = p2[0] * quoridor.getSize() + p2[1];
		repaintElements(n1,n2);
    }
    
    /**
     * Adds a wall to the specified button with the given wall type and orientation.
     *
     * @param button      the JButton to which the wall is added
     * @param orientation the orientation of the wall ("L", "R", "T", or "B")
     * @param wallType    the type of wall to be added ("Normal", "Temporal", or "Larga")
     */
    private void addWallToButton(JButton button, JButton button2,JButton button3, char orientation, String wallType) {
        Border currentBorder = button.getBorder();
        Border newBorder = null; Color borderColor;        
        switch (wallType) {
            case "Normal":
                borderColor = Color.GRAY; break;
            case "Temporal":
                borderColor = Color.WHITE; break;                
            case "Larga":
                borderColor = Color.GREEN; break;                
            case "Aliada":
                borderColor = Color.BLACK; break;    
            default: return;            	
        }
        switch (orientation) {
            case 'W':
                newBorder = createMatteBorder(0, borderColor, currentBorder); break;                
            case 'E':
                newBorder = createMatteBorder(1, borderColor, currentBorder); break;                
            case 'N':
                newBorder = createMatteBorder(2, borderColor, currentBorder); break;                
            case 'S':
                newBorder = createMatteBorder(3, borderColor, currentBorder); break;
            default: return;                
        }
        if (button3 != null) button3.setBorder(newBorder);
        button.setBorder(newBorder); button2.setBorder(newBorder); refresh();        
    }
    
    
    
    /**
     * 
     * @param side
     * @param color
     * @param currentBorder
     * @return
     */
    private Border createMatteBorder(int side, Color color, Border currentBorder) {
        switch (side) {
            case 0:
                return BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 7, 0, 0, color), currentBorder);
            case 1:
                return BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 0, 7, color), currentBorder);
            case 2:
                return BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(7, 0, 0, 0, color), currentBorder);
            case 3:
                return BorderFactory.createCompoundBorder(BorderFactory.createMatteBorder(0, 0, 7, 0, color), currentBorder);
            default:
                return null;
        }
    }
    
    
    /**
     * Determines the orientation of the click relative to the JButton.
     *
     * @param button the JButton object on which the click event occurred
     * @param e the ActionEvent representing the click event
     * @return a String indicating the orientation of the click: "LEFT", "RIGHT", "TOP", "BOTTOM", or "CENTER"
     */
    private char determineClickOrientation(JButton button, ActionEvent e) {
        Point buttonLocation = button.getLocationOnScreen();
        Point clickLocationOnScreen = button.getRootPane().getParent().getMousePosition();
        if (clickLocationOnScreen == null) {
            return 'C';
        }
        int relativeX = clickLocationOnScreen.x - buttonLocation.x;
        int relativeY = clickLocationOnScreen.y - buttonLocation.y;
        int buttonWidth = button.getWidth();
        int buttonHeight = button.getHeight();
        if (relativeX <= 0.1 * buttonWidth) {
            return 'W';
        } else if (relativeX >= 0.9 * buttonWidth) {
            return 'E';
        } else if (relativeY <= 0.1 * buttonHeight) {
            return 'N';
        } else if (relativeY >= 0.9 * buttonHeight) {
            return 'S';
        } else {
            return 'C';
        }
    }
    
    
    
    
    /**
     * Refreshes the board by validating and repainting.
     * 
     * @param
     * @return
     */
    private void refresh() {   	
    	Quoridor quoripoob = Quoridor.getQuoridor();
        namePlayer.setText(String.format("%13s", quoripoob.getCurrentPlayerName()));
        colorPlayer.setText(String.format("%13s", quoripoob.getColor(quoripoob.getCurrentPlayerName())));
        remainingNormal.setText(String.format("%30s", quoripoob.getRemainingWallsByType("Normal")));
    	remainingTemporary.setText(String.format("%30s", quoripoob.getRemainingWallsByType("Temporary")));
        remainingLong.setText(String.format("%30s", quoripoob.getRemainingWallsByType("Long")));
        remainingAlly.setText(String.format("%30s", quoripoob.getRemainingWallsByType("Ally")));        
        visitedOrdinary.setText(String.format("%30s", quoripoob.getVisitedSquaresByType("Ordinary")));
        visitedTeleporter.setText(String.format("%30s", quoripoob.getVisitedSquaresByType("Teleporter")));
        visitedRevert.setText(String.format("%30s", quoripoob.getVisitedSquaresByType("Revert")));
        visitedDoubleTurn.setText(String.format("%30s", quoripoob.getVisitedSquaresByType("DoubleTurn")));       
        this.board.revalidate();
        this.board.repaint();    	
    }
    
    /**
     * Repaint the pawns and barriers in the board.
     * 
     * @param
     * @return
     */
    private void repaintElements(int n1, int n2)	{
    	
    	Quoridor quoridor = Quoridor.getQuoridor();    		
    	String[][] board = quoridor.getBoard();   	
    	paintSquares(board);   	
    	String[] players = quoridor.getPlayers();
    	int[] pos1 = quoridor.getLocationPeon(players[0]);
    	int[] pos2 = quoridor.getLocationPeon(players[1]);
    	iconsBoard();
    	JButton b1 = (JButton) this.board.getComponent(pos1[0]*quoridor.getSize()+pos1[1]);
		JButton b01 =(JButton) this.board.getComponent(n1); 
		b01.setIcon(null); 
		b1.setIcon(player1Icon); 
		JButton b02 =(JButton) this.board.getComponent(n2); 
		JButton b2 = (JButton) this.board.getComponent(pos2[0]*quoridor.getSize()+pos2[1]);
		b02.setIcon(null);
		b2.setIcon(player2Icon);
		paintBarriers(board);
    	
    }

    
    /**
     * 
     * @param
     * @return
     */
    private void iconsBoard()	{
    	for(Component component : board.getComponents())	{
	    	if (component instanceof JButton)	{
	    		JButton button = (JButton) component;
	    		button.setIcon(null);
	    	}
    	}
    	
    }

    /**
     * Paint the barriers on board.
     * 
     * @param board
     * @return
     */
    private void paintBarriers(String[][] board)	{
    	Quoridor quoridor = Quoridor.getQuoridor();
    	
    	HashMap<String, Player> players = quoridor.getPLayers();
    	borders();
    	for (Player p : players.values()){ 
    		HashMap<String, Wall> walls = p.getWalls();
    		for(Wall w : walls.values()) {
    			int[] l = w.getLocation();
    			int n = l[0]*board.length+l[1];
    			String type = w.getClass().getSimpleName();
    			char d = w.getOrientation() ;
    			addBarrierToBoard(type,d,n);
    		}
    	}
    	
    }
    
    /**
     * Visually eliminates existing walls.
     * 
     * @param
     * @return
     * 
     */
    private void borders()	{
    	for(Component component : board.getComponents())	{
	    	if (component instanceof JButton)	{
	    		JButton button = (JButton) component;
	    		button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    	}
    	}
    }
    
    /**
     * Add barrier on the board.
     * 
     * @param type
     * @param n
     */
    private void addBarrierToBoard(String type, char d, int n)	{
    	Quoridor quoridor = Quoridor.getQuoridor();
    	String wallType = getNameType(type);
    	JButton button = (JButton) this.board.getComponent(n);
    	
    	 int n2 = quoridor.getPointFinalWall(n,d);
         JButton  button2 = (JButton) board.getComponent(n2); JButton  button3 = null;
         if (type.equals("Long")) 	{
         	 int n3 = quoridor.getPointFinalWall(n2,d);
             button3 = (JButton) board.getComponent(n3);
         }addWallToButton(button,button2,button3, d, wallType);
    	
    }
    
 
    
    /**
     * Obtain name of the wall.
     * 
     * @param name
     * @return
     */
    private String getNameType(String wall) {
        switch (wall) {
            case "Normal":
                return "Normal";
            case "Temporary":
                return "Temporal";
            case "Ally":
                return "Aliada";  
            case "Long":
                return "Larga";
            default:
                return ""; 
        }
    }
    
}
