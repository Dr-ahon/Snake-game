import javax.swing.*;
import java.awt.*;

public class App {
    JLabel gameName;
    JFrame window;
    JLabel speedLabel;
    JLabel recordLabel;
    JButton pauseButton;
    JButton newGameButton;
    JButton startButton;
    JLabel startLabel;
    JLabel pausedLabel;
    JLabel gameOverLabel;


    public App(){
        Color myGreen = Color.decode("#306B34");
        Color myMustard = Color.decode("#EDCB07");
        Color myrichBrown = Color.decode("#64403E");

        window = new JFrame();

        window.setLayout(new BorderLayout());
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Game gameSpace = new Game(this);

        gameName = new JLabel("SNAKE",  SwingConstants.CENTER);
        gameName.setBackground(myGreen);
        gameName.setOpaque(true);
        gameName.setFont(new Font("Bauhaus 93", Font.BOLD, 50));
        gameName.setForeground(myMustard);

        JPanel rightMenu = new JPanel();
        rightMenu.setLayout(new GridLayout(4,1));

        recordLabel = new JLabel("RECORD: 1");
        recordLabel.setFont(new Font("Bauhaus 93", Font.PLAIN, 25));
        recordLabel.setForeground(myMustard);
        JPanel panelRecord = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelRecord.setBackground(myGreen);
        panelRecord.add(recordLabel);

        speedLabel = new JLabel("SPEED: 1");
        speedLabel.setFont(new Font("Bauhaus 93", Font.PLAIN, 25));
        speedLabel.setForeground(myMustard);
        JPanel panelSpeed = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelSpeed.setBackground(myGreen);
        panelSpeed.add(speedLabel);

        pauseButton = new JButton("PAUSE");
        pauseButton.setFont(new Font("Bauhaus 93", Font.PLAIN, 25));
        //pauseButton.setHorizontalAlignment(SwingConstants.CENTER);
        //pauseButton.setVerticalAlignment(SwingConstants.CENTER);
        pauseButton.setBackground(myrichBrown);
        pauseButton.setBorderPainted(false); 
        pauseButton.addActionListener(gameSpace);  
        pauseButton.setFocusable(false);
        JPanel panelPauseButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelPauseButton.setBackground(myGreen);
        panelPauseButton.add(pauseButton);
   

        newGameButton = new JButton("NEWGAME");
        newGameButton.setFont(new Font("Bauhaus 93", Font.PLAIN, 25)); 
        //newGameButton.setHorizontalAlignment(SwingConstants.CENTER);
        //newGameButton.setVerticalAlignment(SwingConstants.CENTER);
        newGameButton.setBackground(myrichBrown);
        newGameButton.setBorderPainted(false); 
        newGameButton.addActionListener(gameSpace);
        newGameButton.setFocusable(false);
        //newGameButton.setEnabled(false);
        int height = 100;
        //newGameButton.setBounds(gameName.getX(), gameSpace.getWidth() / 2 - (height / 2), gameSpace.getWidth(), height);

        JPanel panelNewGameButton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelNewGameButton.setBackground(myGreen);
        panelNewGameButton.add(newGameButton);

        pausedLabel = new JLabel("GAME PAUSED");
        pausedLabel.setVerticalAlignment(JLabel.CENTER);
        pausedLabel.setHorizontalAlignment(JLabel.CENTER);
        pausedLabel.setVisible(false);
        pausedLabel.setOpaque(true);
        pausedLabel.setBounds(gameName.getX(), gameSpace.getWidth() / 2 - (height / 2), gameSpace.getWidth(), height);
        pausedLabel.setBackground(Color.gray);
        pausedLabel.setFont(new Font("Bauhaus 93", Font.PLAIN, 25)); 

        gameOverLabel = new JLabel("GAME OVER");
        gameOverLabel.setVerticalAlignment(JLabel.CENTER);
        gameOverLabel.setHorizontalAlignment(JLabel.CENTER);
        gameOverLabel.setVisible(false);
        gameOverLabel.setOpaque(true);
        gameOverLabel.setBounds(gameName.getX(), gameSpace.getWidth() / 2 - (height / 2), gameSpace.getWidth(), height);
        gameOverLabel.setBackground(Color.gray);
        gameOverLabel.setFont(new Font("Bauhaus 93", Font.PLAIN, 25)); 

       

        startButton = new JButton("START");
        startButton.setFont(new Font("Bauhaus 93", Font.PLAIN, 25)); 
        startButton.setBackground(myrichBrown);
        startButton.setBorderPainted(false);
        startButton.setVerticalAlignment(JButton.CENTER);
        startButton.setHorizontalAlignment(JButton.CENTER);
        startButton.addActionListener(gameSpace);
        //startButton.setVisible(true);
        startButton.setOpaque(true);
        startButton.setBounds(gameName.getX(), gameSpace.getWidth() / 2 - (height / 2), gameSpace.getWidth(), height);
        
        // startLabel = new JLabel();
        // startLabel.setLayout(new BorderLayout());
        // startLabel.setVerticalAlignment(JLabel.CENTER);
        // startLabel.setHorizontalAlignment(JLabel.CENTER);
        // startLabel.setFocusable(true);
        // startLabel.setVisible(true);
        // startLabel.setOpaque(true);
        // int border = 100;
        // startLabel.setBounds(gameName.getX() + border, gameSpace.getWidth() / 2 - (height / 2), gameSpace.getWidth() - 2*border, height);
        // startLabel.setBackground(Color.gray);
        // startLabel.setFont(new Font("Bauhaus 93", Font.PLAIN, 25)); 
        // startLabel.add(startButton);
       

        gameSpace.add(pausedLabel);
        gameSpace.add(gameOverLabel);
        gameSpace.add(startButton);
        //gameSpace.add(startLabel);

        // String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
  
        // for (int i = 0; i < fonts.length; i++) {
        //     System.out.println(fonts[i]);
        // }

        rightMenu.add(panelRecord);
        rightMenu.add(panelSpeed);
        rightMenu.add(panelPauseButton);
        rightMenu.add(panelNewGameButton);

        JPanel fillerPanelLeft = new JPanel();
        fillerPanelLeft.setBackground(myGreen);

        JPanel fillerPanelBottom = new JPanel();
        fillerPanelBottom.setBackground(myGreen);
        
        
        window.add(gameName, BorderLayout.NORTH);
        window.add(gameSpace, BorderLayout.CENTER);
        window.add(rightMenu, BorderLayout.LINE_END);
        window.add(fillerPanelLeft, BorderLayout.LINE_START);
        window.add(fillerPanelBottom, BorderLayout.SOUTH);
        window.pack();

        Point center = GraphicsEnvironment.getLocalGraphicsEnvironment().getCenterPoint();
        window.setBounds(center.x - window.getWidth() / 2, center.y - window.getHeight() / 2, window.getWidth(), window.getHeight());
        window.setResizable(false);
        window.setVisible(true);
        gameSpace.setVisible(true);

        gameSpace.setFocusable(true);
        gameSpace.addKeyListener(gameSpace);

    }

    public void setSpeedlLabel(int speed){
        speedLabel.setText("SPEED: " + speed);
    }
    public void setSpeedLabelBoosted(){
        speedLabel.setText("BOOSTED");
    }


    public void setRecordLabel(int speed){
        recordLabel.setText("RECORD: " + speed);
    }
    public static void main(String[] args) throws Exception {

        new App();
    }
}
//TODO: bonuses
//TODO: options
//TODO: snake design
//TODO: refactor game and app
//TODO: 





