import java.awt.geom.Rectangle2D;

public class Head extends BodyPart {
  Direction direction;

  public Head(double x, double y){
    predecesor = null;
    rect = new Rectangle2D.Double(x, y, BLOCKSIZE, BLOCKSIZE);
    direction = Direction.RIGHT;
    setSuccesor();
  }
  
  
  public void setSuccesor(){
    switch (direction) {
      case UP -> {
        succesor = new Rectangle2D.Double(rect.getX(), rect.getY() + (BLOCKSIZE), BLOCKSIZE, BLOCKSIZE);
      }
      case DOWN -> {
        succesor = new Rectangle2D.Double(rect.getX(), rect.getY() - (BLOCKSIZE), BLOCKSIZE, BLOCKSIZE);
      }
      case LEFT -> {
        succesor = new Rectangle2D.Double(rect.getX() + (BLOCKSIZE), rect.getY(), BLOCKSIZE, BLOCKSIZE);
      }
      case RIGHT -> {
        succesor = new Rectangle2D.Double(rect.getX() - (BLOCKSIZE), rect.getY(), BLOCKSIZE, BLOCKSIZE);
      }
    }
  }

  public Rectangle2D whereWillHeadBe(){
     switch (direction) {
      case UP -> {
        return rectAbove(BLOCKSIZE);
      }
      case DOWN -> {
        return getRectBelow(BLOCKSIZE);
      }
      case LEFT -> {
        return getRectOnTheLeft(BLOCKSIZE);
      }
      case RIGHT -> {
        return getRectOnTheRight(BLOCKSIZE);     
      }
      default -> {
        return rect;
      }
    }
  }

    public Rectangle2D getRectOnTheLeft(double d){
      return new Rectangle2D.Double(rect.getX() - d, rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public Rectangle2D rectAbove(double d){
      return new Rectangle2D.Double(rect.getX(), rect.getY() - d, rect.getWidth(), rect.getHeight());
    }

    public Rectangle2D getRectOnTheRight(double d){
      return new Rectangle2D.Double(rect.getX() + d, rect.getY(), rect.getWidth(), rect.getHeight());
    }

    public Rectangle2D getRectBelow(double d){
      return new Rectangle2D.Double(rect.getX(), rect.getY() + d, rect.getWidth(), rect.getHeight());
    }


  @Override
  public void moveBodyPart(){
    this.rect.setRect(whereWillHeadBe());
  }

  public void turn(Direction newDir){
    switch (newDir) {
      case LEFT, RIGHT: 
        if(this.direction.equals(Direction.UP) | this.direction.equals(Direction.DOWN)){
          this.direction = newDir;
        }
        break;
      case UP, DOWN:
        if(this.direction.equals(Direction.LEFT) | this.direction.equals(Direction.RIGHT)){
          this.direction = newDir;
        }
        break;
    }
  }
}

enum Dir{
  UP, DOWN, LEFT, RIGHT;
}