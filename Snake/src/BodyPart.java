import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class BodyPart implements Block{
  protected BodyPart predecesor;
  protected Rectangle2D succesor;
  protected Rectangle2D rect;

  public BodyPart(BodyPart _predecesor){
    predecesor = _predecesor;
    succesor = null;
    rect = new Rectangle2D.Double(_predecesor.succesor.getX(), _predecesor.succesor.getY(), BLOCKSIZE, BLOCKSIZE);
  }

  public BodyPart(){}
  

  public void moveBodyPart(){
      this.succesor = (Rectangle2D) this.rect.clone();
      rect.setRect(predecesor.rect.getX(), predecesor.rect.getY(), rect.getWidth(), rect.getHeight());
  }

  public void paint(Graphics2D g2){
    Color c = Color.red;
    g2.setColor(c);
    g2.fill(rect);
  }
}



