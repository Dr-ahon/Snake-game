import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;


public class Game extends JComponent implements KeyListener, ActionListener {
  App app;
  Timer gameTimer;
  Snake snake;
  ArrayList<Bonus> bonuses;
  ArrayList<Wall> walls;
  Graphics g;
  Boolean isGameOver;
  Boolean isBoosted;
  Boolean isPaused;
  int speed;
  int record;
  int baseDelay;
  int applesEaten;
  int boosterTime;
  private final Random rand;
  ArrayList<Block> blocksToRemove;
  
  public Game(App _app){
    super();
    this.app = _app;
    setSize(new Dimension(420,420));
    rand = new Random();
    setPreferredSize(this.getSize());
    nullGame();
  }

  public void nullGame(){
    speed = 1;
    record = 1;
    applesEaten = 0;
    boosterTime = 0;
    baseDelay = 400;
    createWalls();
    isGameOver = false;
    isBoosted = false;
    isPaused = true;
    blocksToRemove = new ArrayList<>();
    gameTimer = new Timer(300, this);
    repaint();

  } 

  public void setLabels(int speed){
    if(record < speed){
      app.setRecordLabel(speed);
    }
    app.setSpeedlLabel(speed);
  }

  public void setGameComponents(){
    snake = new Snake(2*Block.BLOCKSIZE,Block.BLOCKSIZE);
    bonuses = new ArrayList<>();
    setLabels(speed);
    placeFood();
    placeFood();
    placeFood();
    //addMouseListener(this);
  }

  public void placeFood(){
    ArrayList<Rectangle2D> freeSpaces = getFreeRects();
    int randInt = rand.nextInt(freeSpaces.size());
    Bonus food = new Bonus(freeSpaces.get(randInt), BonusType.FOOD);
    bonuses.add(food);
    //System.out.println(food.toString());
  }

  public void placeBoosterRandomly(){
    int randInt = rand.nextInt(30);
    if(randInt == 1){
      ArrayList<Rectangle2D> freeSpaces = getFreeRects();
      int randIndex = rand.nextInt(freeSpaces.size());
      Bonus booster = new Bonus(freeSpaces.get(randIndex), BonusType.BOOSTER);
      bonuses.add(booster);
      //System.out.println(booster.toString());
    }
  }

  public ArrayList<Rectangle2D> getFreeRects(){
    ArrayList<Rectangle2D> list = new ArrayList<>();
    //System.out.println();
    int width = this.getWidth();
    int height = this.getHeight();

    //System.out.println("width: " + width + " height: " + height);

    for(int i = 0; i <= width - Wall.BLOCKSIZE; i += Wall.BLOCKSIZE){
      for(int j = 0; j <= height - Wall.BLOCKSIZE; j += Wall.BLOCKSIZE){
        list.add(new Rectangle2D.Double(i, j, Block.BLOCKSIZE, Block.BLOCKSIZE));
      }
    }
    return list.stream()
              .filter(e -> snake.corpus.stream()
                                      .noneMatch(body -> body.rect.equals(e)))
              .filter(e -> !snake.head.rect.equals(e))
              .filter(e -> walls.stream()
                                .noneMatch(wall -> wall.rect.equals(e)))
              .filter(e -> bonuses.stream()
                                .noneMatch(bonus -> bonus.rect.equals(e)))
              .collect(Collectors.toCollection(ArrayList::new));
  }

  public void createWalls(){
    walls = new ArrayList<>();
    int width = this.getWidth();
    int height = this.getHeight();
    //System.out.println("w: " + width + " h: " + height);
    for(int i = 0; i <= width; i += (width / Wall.BLOCKSIZE - 1) * Wall.BLOCKSIZE){
      for(int j = 0; j <= height - Wall.BLOCKSIZE; j += Wall.BLOCKSIZE){
        walls.add(new Wall(i, j));
      }
    }
    for(int i = 0; i < width - Wall.BLOCKSIZE; i += Wall.BLOCKSIZE){
      for(int j = 0; j <= height- Wall.BLOCKSIZE; j += (height / Wall.BLOCKSIZE - 1) * Wall.BLOCKSIZE){
        walls.add(new Wall(i, j));
      }
    }
  }
  
  public void startGame(){
    isPaused = false;
    app.startButton.setEnabled(false);
    app.startButton.setVisible(false);
    setGameComponents();
    gameTimer.start();

  }
  
  @Override
  public void paintComponent(Graphics g){
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D)g;
    walls.forEach(w -> w.paint(g2));

    try{
      snake.paint(g2);
      bonuses.forEach(b -> b.paint(g2));
    }catch(Exception e){
      System.out.println("Snake is not initialized");}
    

    // if(paused){
    //   snake.paint(g2);
    // }

    // g2.setColor(Color.yellow);
    // for (int i = 0; i <= 30; i++) {
    //       int y = i * Block.BLOCKSIZE;
    //       g.drawLine(0, y, this.getWidth(), y);
    //   }
    //   for (int i = 0; i <= 20; i++) {
    //       int x = i * Block.BLOCKSIZE;
    //       g.drawLine(x, 0, x, this.getHeight());
    //   }
  }

  public void collisionDetection(){
    ArrayList<Bonus> bonusCollisisons = bonuses.stream()
      .filter(b -> b.rect.equals(snake.head.rect))
      .collect(Collectors.toCollection(ArrayList::new));
    
    bonusCollisisons.forEach(c -> resolveCollision(c)); 

    Boolean collidedWalls = walls.stream()
                                .anyMatch(b -> b.rect.equals(snake.head.rect));
                                
    if(collidedWalls){
      gameOver();
    }

    Boolean tailBited = snake.corpus.stream()
                              .anyMatch(b -> b.rect.equals(snake.head.rect));

    if(collidedWalls || tailBited){
      gameOver();
    }
  }

  public void clearBoardfromBonuses(){
    for(Block b: blocksToRemove){
      if(b.getClass().getName().equals("Bonus")){
        bonuses.remove(b);
      }
    }
  }

  public void updateGameTimer(int multiplicity){
    gameTimer.setDelay((int)(baseDelay - speed*((((100.0-speed*8))/100)*40)*multiplicity));
    System.out.println(gameTimer.getDelay());
  }

  public void resolveCollision(Bonus bonus){
    switch(bonus.type){
      case FOOD -> {
        blocksToRemove.add(bonus);
        snake.grow();
        if(!isBoosted){
          applesEaten ++;
          if(Math.floorMod(applesEaten, 5) == 0) {
            updateGameTimer(1);
            speed++;
            setLabels(speed);
          }   
        }
        placeFood();
      }
      case BOOSTER -> {
        blocksToRemove.add(bonus);
        boost();
      }
    }
  }

  public void boost(){
    isBoosted = true;
    boosterTime = 0;
    app.setSpeedLabelBoosted();
    updateGameTimer(2);
  }

  public void gameOver(){
    gameTimer.stop();
    isGameOver = true;
    app.gameOverLabel.setVisible(true);
  }

  public void newGame(){
    nullGame();
    snake = null;
    bonuses.clear();
    app.gameOverLabel.setVisible(false);
    app.startButton.setEnabled(true);
    app.startButton.setVisible(true);
    speed = 1;
    setLabels(speed);
  }

  @Override
  public void actionPerformed(ActionEvent e) {
    Object source = e.getSource();
    if(source == gameTimer){


      if(!snake.turnQueue.isEmpty()){snake.turn(snake.turnQueue.poll());}
      snake.moveSnake();
      collisionDetection();

      if(isBoosted){
        boosterTime ++;
        System.out.println(boosterTime);
        if(boosterTime == 20){
          isBoosted = false;
          app.setSpeedlLabel(speed);
          updateGameTimer(1);
        }
      }else{
        placeBoosterRandomly();
      }
      if(isGameOver){
        gameOver();
      }else{
        repaint();
      }
      
      clearBoardfromBonuses();
    }else if(source == app.pauseButton){
        this.pauseGame();
   
    }else if(source == app.startButton){
      startGame();
    }else if(source == app.newGameButton){
      newGame();

    }

  }



  public void printTail(){
    
    int headX = (int)(snake.head.rect.getX());
    int headY = (int)(snake.head.rect.getY());
    //System.out.println(headX + " : " + headY);
    //System.out.println(snake.head.rect.getX(), snake.head.rect.getY());
    int tailX = (int)(snake.tail.rect.getX());
    int tailY = (int)(snake.tail.rect.getY());
    //System.out.println(tailX + " : " + tailY);

    //System.out.println();
  }

  public void pauseGame(){
    if(!isPaused){
      isPaused = true;
      gameTimer.stop();
      app.pausedLabel.setVisible(true);

      // Rectangle2D pausedRect = new Rectangle2D.Double(this.getX(), this.getY(), this.getWidth(), this.getHeight());
      // Color col = new Color(255, 0, 0, 100);
    }else{
      gameTimer.start();
      isPaused = false;
      app.pausedLabel.setVisible(false);

    };
    
  }

  
  @Override
  public void keyPressed(KeyEvent keyEvent) {
    int keyCode = keyEvent.getKeyCode();
    switch(keyCode){
      case KeyEvent.VK_UP -> {
        snake.turnQueue.add(Direction.UP);
        //System.out.println(keyCode);
        break;
      }
      case KeyEvent.VK_DOWN -> {
        snake.turnQueue.add(Direction.DOWN);
        //System.out.println(keyCode);
        break;
      }
      case KeyEvent.VK_LEFT -> {
        snake.turnQueue.add(Direction.LEFT);
        //System.out.println(keyCode);
        break;
      }
      case KeyEvent.VK_RIGHT -> {
        snake.turnQueue.add(Direction.RIGHT);
        //System.out.println(keyCode);
        break;
      }
      case KeyEvent.VK_ENTER -> {
        startGame();
        break;
      }
      case KeyEvent.VK_SPACE -> {
        pauseGame();
        break;
      }
      
    }
  
  }


  @Override
  public void keyTyped(KeyEvent e) {
    // TODO Auto-generated method stub
    //throw new UnsupportedOperationException("Unimplemented method 'keyTyped'");
  }


  @Override
  public void keyReleased(KeyEvent e) {
    // TODO Auto-generated method stub
    //throw new UnsupportedOperationException("Unimplemented method 'keyReleased'");
  }


}
