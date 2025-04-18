package presentation;

import domain.Quoridor;

import domain.QuoridorException;

import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import domain.Log;

public class ConfigGUI extends JDialog{

	private JButton continueButton;	
	private JComboBox<String> modeComboBox;

	private  JTextField boardSizeTextField;
    private JComboBox<String> difficultyComboBox;
    private Map<String, JComboBox<String>> cellComboBoxes;
    private Map<String, JComboBox<String>> wallComboBoxes;
	private JButton plusButton;
    private JButton minusButton;
	
    
    
    /**
     * Constructor for Configuration.
     * 
     * @param
     */
	protected ConfigGUI() {	
		Quoridor.setNull();
        prepareElements();
        prepareActions();
    }
    
    
    
    
    /**
     * Sets up the GUI elements.
     * 
     * @param 
     * @return
     */
    private void prepareElements() {
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
        prepareElementsModes();
    }
    
    
    
    /**
     * Prepares the actions for the main window.
     * 
     * @param
     * @return
     */
    private void prepareActions() {
                 
        prepareActionsGameMode();
        
    }
    
    /**
     * Returns the selected item from the difficulty combo box.
     * 
     * @return The selected item as a String.
     */
    public String getSelectedDifficulty() {
        return (String) difficultyComboBox.getSelectedItem();
    }
    
    /**
     * Prepares the action listeners for game mode.
     * @param 
     * @return
     */
    private void prepareActionsGameMode() {
    	plusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String currentSize = boardSizeTextField.getText();
                int size = Integer.parseInt(currentSize);
                size += 1;
                boardSizeTextField.setText(String.valueOf(size));
            }
        });

        minusButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String currentSize = boardSizeTextField.getText();
                int size = Integer.parseInt(currentSize);
                size -= 1;
                boardSizeTextField.setText(String.valueOf(size));
            }
        });
        continueButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	showGameInterface();           
            }           
        });
    }
    
    
    
    /**
     * Return difficulty.
     * 
     * @param
     * @return
     */
    public String getDifficulty()	{
    	String d = (String) difficultyComboBox.getSelectedItem();
        String difficulty = convertClassDifficulty(d);
        return difficulty;
    }
    
    /**
     * Show the game interface.
     * 
     * @param
     * @return
     */
    private void showGameInterface()	{
    	try {
            String selectedMode=(String)modeComboBox.getSelectedItem(); int size=getSelectedBoardSize();            
            String d = (String) difficultyComboBox.getSelectedItem();
            String difficulty = convertClassDifficulty(d); int time = 0;                       
            HashMap<String, Integer>walls=getSelectedWallValues();HashMap<String, Integer>squares=getSelectedCellValues();          
            try	{ if (difficulty.equals("Timetrial"))	{
            	time =  Integer.parseInt(JOptionPane.showInputDialog("Tiempo por turno en segundos:"));           	
            } else if (difficulty.equals("Timed"))	{
            	time =  Integer.parseInt(JOptionPane.showInputDialog("Tiempo total por jugador en minutos:"));           	
            }	
            } catch (Exception e)	{ throw new QuoridorException(QuoridorException.INVALID_TIME); }            	
            if (selectedMode.equals("Jugador vs Jugador")) {
                String namePlayer1 = JOptionPane.showInputDialog("Ingrese nombre del jugador 1:");
                String color1 = (String) JOptionPane.showInputDialog(null, "Seleccione color del peón",
                        "COLORES",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Amarillo","Azul","Rojo"},"Amarillo");                       
                String namePlayer2 = JOptionPane.showInputDialog("Ingrese nombre del jugador 2:");
                String color2 = (String) JOptionPane.showInputDialog(null, "Seleccione color del peón",
                        "COLORES",JOptionPane.QUESTION_MESSAGE,null,new Object[]{"Verde","Naranja","Negro"},"Verde");                                   
                Quoridor.getQuoridor(size, namePlayer1, color1, color2,namePlayer2, difficulty, walls, squares,time);
            } else if (selectedMode.equals("Jugador vs Máquina")) {
                String namePlayer1 = JOptionPane.showInputDialog("Ingrese el nombre del jugador:");
                String color1 = (String) JOptionPane.showInputDialog(null, "Seleccione color del peón",
                        "COLORES",JOptionPane.QUESTION_MESSAGE, null, new Object[]{"Amarillo","Azul","Rojo"},"Amarillo");                                       
                String typeMachine = (String) JOptionPane.showInputDialog(null, "Seleccione tipo de máquina:",
                        "MÁQUINA",JOptionPane.QUESTION_MESSAGE, null,
                        new Object[]{"Principiante","Intermedia","Avanzada"}, "Principiante");  
                String profile = getNameClassMachine(typeMachine);
                Quoridor.getQuoridor(size, namePlayer1, color1, profile, difficulty, walls, squares,time);               
            }
            QuoridorGUI gui = QuoridorGUI.getQuoridorGUI();  gui.showGameInterface();                              
    	} catch (QuoridorException q) { Log.record(q); JOptionPane.showMessageDialog(null, q.getMessage()); }         		
    }
    
    
    
    
    /**
     * Obtain name of class to machine.
     * 
     * @param typeMachine
     * @return simple name of class
     */
    private String getNameClassMachine(String typeMachine)	{
    	switch (typeMachine) {
        case "Principiante":
            return "Begginer";
        case "Intermedia":
            return "Intermediate";
        case "Avanzada":
            return "Advanced";    
        default:
            return ""; 
    }
    }
    
    
    /**
     * Returns a HashMap with the values selected by the user for the wall ComboBox.
     * 
     * @param
     * @return HashMap with the wall type name as key and the selected number as value.
     */
    private HashMap<String, Integer> getSelectedWallValues() {
        HashMap<String, Integer> wallValues = new HashMap<>();
        for (Map.Entry<String, JComboBox<String>> entry : wallComboBoxes.entrySet() ) {
            String key = entry.getKey();
            JComboBox<String> comboBox = entry.getValue();
            String selectedItem = (String) comboBox.getSelectedItem();
            Integer value = Integer.parseInt(selectedItem);
            wallValues.put(key, value);
         }
        return wallValues;
    }
    
    
    
    /**
     * Returns a HashMap with the values selected by the user for the cell ComboBox.
     * @return HashMap with the cell type name as key and the selected number as value.
     */
    private HashMap<String, Integer> getSelectedCellValues() {
        HashMap<String, Integer> cellValues = new HashMap<>();
        
        for (Map.Entry<String, JComboBox<String>> entry : cellComboBoxes.entrySet() ) {
           String key = entry.getKey();
           JComboBox<String> comboBox = entry.getValue();
           String selectedItem = (String) comboBox.getSelectedItem();
           Integer value = Integer.parseInt(selectedItem);
           cellValues.put(key, value);
        }
      
        
        return cellValues;
    }
    
    
    
    /**
     * Returns the size of the board based on the selected item in the board size combo box.
     * 
     * @param
     * @return the size of the board as a String
     */
    private int getSelectedBoardSize() {
        String selectedSize = boardSizeTextField.getText();
        int size = Integer.parseInt(selectedSize);
        return size;
    }
    
    
    /**
     * 
     * @param difficulty
     * @return
     */
    private String convertClassDifficulty(String difficulty) {
        switch (difficulty) {
            case "Normal":
                return "Untimed";
            case "Contrarreloj":
                return "Timetrial";
            case "Cronometrado":
                return "Timed";    
            default:
                return ""; 
        }
    }
    
    
    /**
     * Prepares the elements for game mode including difficulty level.
     * @param
     * @return
     */
    private void prepareElementsModes() {
        JPanel mainPanel = new JPanel(new BorderLayout()); 
        JPanel modePanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 10)); 
        mainPanel.setPreferredSize(new Dimension(930, 530));
        modePanel.setAlignmentX(Component.CENTER_ALIGNMENT); 
        
        prepareModePanel(modePanel); 
        JPanel panelSquares = new JPanel();
        JPanel panelWalls = new JPanel();
        prepareCellsComboBox(panelSquares); 
        prepareWallsComboBox(panelWalls);
        JPanel otherPanel1 = new JPanel(); 
        otherPanel1.setAlignmentX(Component.CENTER_ALIGNMENT);
        prepareGameTitleLabel(otherPanel1); 
        
        JPanel otherPanel2 = new JPanel();
        prepareContinueButton(otherPanel2);
        mainPanel.add(otherPanel1, BorderLayout.NORTH);
        mainPanel.add(modePanel, BorderLayout.CENTER);
        mainPanel.add(otherPanel2, BorderLayout.SOUTH); 
        mainPanel.add(panelSquares, BorderLayout.WEST);
        mainPanel.add(panelWalls, BorderLayout.EAST);
        addComponentsToFrame(mainPanel);
        
    }
    
    
    
    /**
     * Prepares the mode panel with mode selection and difficulty level.
     * @param modePanel the panel for mode selection
     * @return
     */
    private void prepareModePanel(JPanel modePanel) {
        JLabel titleLabel = new JLabel("Escoger Modo de Juego");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 17));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        String[] options = {"Jugador vs Jugador", "Jugador vs Máquina"};
        modeComboBox = new JComboBox<>(options);
        modeComboBox.setMaximumSize(new Dimension(150, modeComboBox.getPreferredSize().height));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10); 
        modePanel.add(titleLabel, gbc); gbc.gridy++;        
        modePanel.add(modeComboBox, gbc); gbc.gridy++;        
        JLabel difficultyLabel = new JLabel("Nivel de Dificultad");
        difficultyLabel.setFont(new Font("Arial", Font.BOLD, 16));
        difficultyLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 2, 0));
        difficultyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);        
        modePanel.add(difficultyLabel, gbc); gbc.gridy++;
        prepareDifficultyComboBox();
        modePanel.add(difficultyComboBox, gbc); gbc.gridy++;       
        JLabel boardSizeLabel = new JLabel("Tamaño del Tablero");
        boardSizeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        boardSizeLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        modePanel.add(boardSizeLabel, gbc); gbc.gridy++;
        JPanel panel = prepareBoardSizeComboBox();
        modePanel.add(panel, gbc);     
        modePanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1), 
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
    }
    
    
    /**
     * Prepares the combo box for selecting the number of cells for each type.
     * @param modePanel the mode panel
     * @return
     */
    private void prepareCellsComboBox(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10); 
        JLabel cellsLabel = new JLabel("Número de casillas por tipo");
        cellsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        cellsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
        panel.add(cellsLabel, gbc);
        String[] cellOptions = 	{"Teletransportador", "Regresar", "Turno Doble"};
        gbc.gridy++; cellComboBoxes = new HashMap<>();
        for (int i = 0; i < cellOptions.length; i++) {
            JLabel label = new JLabel(cellOptions[i]);
            gbc.gridx = 0;
            panel.add(label, gbc);
            JComboBox<String> comboBox = new JComboBox<>(new String[]{ "1", "2", "3", "4", "5"});
            gbc.gridx = 1;
            panel.add(comboBox, gbc);
            gbc.gridy++;
            cellComboBoxes.put(cellOptions[i], comboBox);
        }
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1), 
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
    }
    
    
    
    /**
     * Prepares the difficulty level combo box.
     * @param
     * @return the JComboBox for selecting the difficulty level
     */
    private void prepareDifficultyComboBox() {
        String[] difficultyOptions = {"Normal", "Contrarreloj", "Cronometrado"};
        difficultyComboBox = new JComboBox<>(difficultyOptions);
        difficultyComboBox.setMaximumSize(new Dimension(150, difficultyComboBox.getPreferredSize().height));
    
    }
    
    
    /**
     * Prepares the board size combo box.
     * @param
     * @return
     */
    private JPanel prepareBoardSizeComboBox() {
    	JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        boardSizeTextField = new JTextField("9"); 
        boardSizeTextField.setHorizontalAlignment(JTextField.CENTER);
        boardSizeTextField.setEditable(false); 

        plusButton = new JButton("+");
        minusButton = new JButton("-");
        panel.add(minusButton, BorderLayout.WEST);
        panel.add(boardSizeTextField, BorderLayout.CENTER);
        panel.add(plusButton, BorderLayout.EAST);
        return panel;
    }
    
    /**
     * Prepares the combo box for selecting the number of walls for each type.
     * @param modePanel the mode panel
     * @return
     */
    private void prepareWallsComboBox(JPanel panel) {
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.gridy = 0;        
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 10, 5, 10);         
        JLabel wallsLabel = new JLabel("Número de barreras por tipo");
        wallsLabel.setFont(new Font("Arial", Font.BOLD, 14));
        wallsLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
        panel.add(wallsLabel, gbc);      
        String[] wallOptions = {"Normal","Temporal", "Larga", "Aliada"};
        gbc.gridy++; wallComboBoxes = new HashMap<>();
        for (int i = 0; i < wallOptions.length; i++) {
            JLabel label = new JLabel(wallOptions[i]);
            gbc.gridx = 0;
            panel.add(label, gbc);            
            JComboBox<String> comboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
            gbc.gridx = 1;
            panel.add(comboBox, gbc);        
            gbc.gridy++;
            wallComboBoxes.put(wallOptions[i], comboBox);
        }
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK, 1), 
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
    }
    
    
    
    /**
     * Prepares the title label for the game.
     * @param panel the panel to which the title label will be added
     * @return
     */
    private void prepareGameTitleLabel(JPanel panel) {
        JLabel gameTitleLabel = new JLabel("QUORIDOR");
        gameTitleLabel.setFont(new Font("Arial", Font.BOLD, 34));
        gameTitleLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 2, 0));
        gameTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(gameTitleLabel);
    }
    
    
    
    /**
     * Prepares the continue button.
     * @param panel the panel to which the continue button will be added
     * @return
     */
    private void prepareContinueButton(JPanel panel) {
        continueButton = new JButton("Continuar");
        panel.add(continueButton); 
        continueButton.setPreferredSize(new Dimension(170, 40));
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
    
    
    
    
   
    

}
