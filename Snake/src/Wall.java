import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;

public class Wall implements Block {
  Rectangle2D rect;

  public Wall(double x, double y){
    rect = new Rectangle2D.Double(x, y, BLOCKSIZE, BLOCKSIZE);
  }


  public void paint(Graphics2D g2){
    g2.setColor(Color.black);
    g2.fill(rect);
  }
}
