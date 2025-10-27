import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Snake {
  Head head;
  BodyPart tail;
  BodyPart growableSpace;
  ArrayList<BodyPart> corpus;
  Queue<Direction> turnQueue;

  public Snake(double x, double y){
    head = new Head(x, y);
    tail = new BodyPart(head);
    corpus = new ArrayList<>();
    corpus.add(tail);
    turnQueue = new LinkedList<>();
  }

  public void moveSnake(){
    for(int i = corpus.size() -1; i >= 0; i--){
      corpus.get(i).moveBodyPart();
    }
    head.moveBodyPart();
  }

  public void turn(Direction newDir){
    this.head.turn(newDir);
  }

  public void grow(){
    BodyPart newTail = new BodyPart(this.tail);
    newTail.succesor = tail.rect;
    this.corpus.add(newTail);
    tail = newTail;
  }

  public void paint(Graphics2D g2){
    corpus.forEach(n -> n.paint(g2));
    head.paint(g2);
  }
}


enum Direction{
  UP, DOWN, LEFT, RIGHT;
}
