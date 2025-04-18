package presentation;

import domain.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class QuoridorGUI extends JFrame{
    
	
    private ConfigGUI configGUI;
    
    private GameGUI gameGUI;
  
    private JButton startButton;
    

    
    private static QuoridorGUI quoridorGUI;
    

    /**
     * Factory method to get the quoridorGUI  singleton object.
     */
    public static QuoridorGUI getQuoridorGUI(){
        if(quoridorGUI == null) {
        	quoridorGUI = new QuoridorGUI();
        }
        return quoridorGUI;
    }
    
    
    /**
     * Constructs a new instance of SquareGUI.
     * 
     * This constructor initializes a new instance of the SquareGUI class. It prepares the elements and actions
     * required for the graphical user interface of Square.
     * 
     * @param
     */
    private QuoridorGUI() {
    	prepareElements();  
    	prepareActions();
    }
    
    
    /**
     * Show the game interface
     * 
     * @param
     * @return
     */
    private void prepareElements() {
        setTitle("Quoridor");
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int screenWidth = screenSize.width + 5; int screenHeight = screenSize.height - 45;        
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
        JPanel centralPanel = new JPanel(new GridBagLayout());
        centralPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10)); 
        centralPanel.setBackground(Color.WHITE);
        centralPanel.setPreferredSize(new Dimension(585, 200)); 
        JLabel titleLabel = new JLabel("QUORIDOR");
        titleLabel.setFont(new Font("Serif", Font.BOLD, 37));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        startButton = new JButton("Empezar"); startButton.setPreferredSize(new Dimension(150, 40));        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;gbc.gridy = 0;  gbc.insets = new Insets(10, 10, 10, 10);            
        centralPanel.add(titleLabel, gbc); gbc.gridy = 1;               
        centralPanel.add(startButton, gbc); getContentPane().setLayout(null);        
        int x = (screenWidth - centralPanel.getPreferredSize().width) / 2;
        int y = (screenHeight - centralPanel.getPreferredSize().height) / 2;
        centralPanel.setBounds(x, y, centralPanel.getPreferredSize().width, centralPanel.getPreferredSize().height);
        getContentPane().add(centralPanel);
    }

        
	
	
	/**
	 * Prepare the actions for close the window and start the game
	 * 
	 * @param
	 * @return
	 */
	private void prepareActions() {
		addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                close();
            }
        });  
		startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            		configurationGame();
            }
        });
	}
	
	/**
     * Closes the application window with a confirmation dialog.
     * 
     * @param
     * @return
     */
    private void close()    {
    	System.exit(0);  
    }
    
	/**
	 * Creates the game configuration window 
	 * 
	 * @param
	 * @return
	 */
	private void configurationGame()	{
		if (configGUI == null) {
	        configGUI = new ConfigGUI();
	    }
	    configGUI.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
	    configGUI.setLocationRelativeTo(this);
	    configGUI.setVisible(true);
	
	}
    
   
    
    /**
     * Show the game window with board and players.
     * 
     * @param
     * @return
     * 
     */
    protected void showGameInterface() 	{ 	
    	gameGUI = new GameGUI();
    	configGUI.dispose(); 
    	gameGUI.setVisible(true);
    }
    
    
    /**
     * Finish the game.
     * 
     * @param
     * @return
     */
    protected void finish()	{
    	gameGUI.dispose();
    	configGUI = new ConfigGUI();
    	configGUI.setVisible(true);
    }
    
    /**
     * Obtain difficulty of game.
     * 
     * @param
     * @return
     */
    public String getDifficulty() {	
    	return configGUI.getDifficulty();
    }
    
    
    /**
     * Main method to start the application.
     * @param args Command line arguments (not used in this case).
     */
    public static void main(String[] args) {
    	
    	QuoridorGUI quoridorGUI = QuoridorGUI.getQuoridorGUI();
        //Comentario
        quoridorGUI.setVisible(true);
    }
    
    
}
