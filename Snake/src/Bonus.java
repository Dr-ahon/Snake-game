import java.awt.geom.Rectangle2D;


import java.awt.Color;
import java.awt.Graphics2D;

public class Bonus implements Block{
  protected Rectangle2D rect;
  protected BonusType type;
  
  public Bonus(double x, double y, BonusType _type){
    rect = new Rectangle2D.Double(x, y, BLOCKSIZE, BLOCKSIZE);
    type = _type;
  }

  public Bonus(Rectangle2D _rect, BonusType _type){
    rect = _rect;
    type = _type;
  }

  @Override
  public String toString(){
    int x = (int)this.rect.getX();
    int y = (int)this.rect.getY();
    return this.type + " " + x + " : " + y;
  }   

  public void paint(Graphics2D g2){
    switch(type){
      case FOOD:
        g2.setColor(Color.green);
        break;
      case BOOSTER:
        g2.setColor(Color.orange);
        break;
      }
    g2.fill(rect);
  }
}

enum BonusType{
  FOOD, BOOSTER;
}