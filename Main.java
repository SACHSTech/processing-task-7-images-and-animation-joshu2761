import processing.core.PApplet;

/**
 * 
 * Description: Space game where you move your mouse to dodge meteors and missiles
 * @author: Joshua Yin
 *
 */
class Main {
  public static void main(String[] args) {
    
    String[] processingArgs = {"MySketch"};
	  Sketch mySketch = new Sketch();
	  PApplet.runSketch(processingArgs, mySketch);
  }
  
}