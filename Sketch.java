import processing.core.PApplet;
import processing.core.PFont;
import processing.core.PImage; //import the PImage library
import java.util.Random;


public class Sketch extends PApplet {

  // Declares a meteor Random variable
  Random projectileRandom = new Random();

  // Declares the image variables
  PImage imgBackground;
  PImage imgMeteor; 
  PImage imgRocket;
  PImage imgMisile;
  PImage imgExplosion;

  // Declares the other variables
  float fltBackgroundX1;
  float fltBackgroundX2;
  float fltMeteorX;
  float fltMeteorY;
  float fltMeteorRadius;
  float fltRocketX;
  float fltRocketY;
  float fltMissileX;
  float fltMissileYSpawn;
  float fltMissileY;
  float fltSpeed;
  int intTimeSpent;
  int intScore;

  public void settings() {
    size(500, 500);
  
  }

  public void setup() {

    // Loads the images
    imgMeteor = loadImage("spaceMeteors_003.png");
    imgRocket = loadImage("spaceRocketsRotated.png");
    imgMisile = loadImage("spaceMissilesRotated.png");
    imgBackground = loadImage("SpaceBackground.png");
    imgExplosion = loadImage("explosion.png");
    
    // Resizes the images
    imgRocket.resize(imgRocket.width / 2, imgRocket.height / 2);
    imgMeteor.resize(imgMeteor.width / 2, imgMeteor.height / 2);   
    imgBackground.resize(imgBackground.width, height);
    imgExplosion.resize(imgExplosion.width / 4, imgExplosion.height / 4);

    // Sets values to the variables
    fltBackgroundX1 = 0;
    fltBackgroundX2 = 0;
    fltMeteorX = width + imgMeteor.width;
    fltMeteorY = projectileRandom.nextInt(height);
    fltMeteorRadius = imgMeteor.width / 2;
    fltMissileX = 500;
    fltMissileYSpawn = projectileRandom.nextInt(height);
    intTimeSpent = 0;
    fltSpeed = 1;
}

  public void draw() {

    //Draws the background
    image(imgBackground, fltBackgroundX1, 0);
    image(imgBackground, fltBackgroundX2, 0);
    // Draws the rocket, meteor, and missle
    image(imgRocket, fltRocketX, fltRocketY);
    image(imgMeteor, fltMeteorX, fltMeteorY);
    image(imgMisile, fltMissileX, fltMissileY);
    
    // Draws the score on the screen
    PFont scoreFont = createFont("Roboto", 20);
    textFont(scoreFont, 20);
    text("Score: " + intTimeSpent, (float)(width * 0.75), (float)(height * 0.05));


    // Calculates if the Rocket hits a meteor
    if (dist(fltRocketX, fltRocketY - imgRocket.height / 2, fltMeteorX, fltMeteorY) < fltMeteorRadius ||
        dist(fltRocketX + imgRocket.width / 2, fltRocketY - imgRocket.height / 2, fltMeteorX, fltMeteorY) < fltMeteorRadius ||
        dist(fltRocketX, fltRocketY - imgRocket.height / 2, fltMeteorX, fltMeteorY) < fltMeteorRadius) {

      // Draws Explosion
      image(imgExplosion, fltRocketX - imgExplosion.width / 4, fltRocketY - imgExplosion.height / 3);
      textFont(scoreFont, 40);
      // End Screen
      text("You lost", (float)(width * 0.3), (float)(height * 0.4));
      text("Your Final Score: " + intTimeSpent, (float)(width * 0.1), (float)(height * 0.5));
    }

    
    // Calculates if the Rocket hits a missile
    else if (fltRocketX + imgRocket.width > fltMissileX && fltRocketX < fltMissileX + imgMisile.width && 
             fltRocketY + imgRocket.height > fltMissileY && fltRocketY < fltMissileY + imgMisile.height) {

      // Draws Explosion
      image(imgExplosion, fltRocketX - imgExplosion.width / 4, fltRocketY - imgExplosion.height / 3);
      textFont(scoreFont, 40);
      // End Screen
      text("You lost", (float)(width * 0.3), (float)(height * 0.4));
      text("Your Final Score: " + intTimeSpent, (float)(width * 0.1), (float)(height * 0.5));
    }


    else {
      
      // Loops through the background:
      fltBackgroundX1 -= fltSpeed / 4;
      fltBackgroundX2 = fltBackgroundX1 + imgBackground.width;
      // If the background reaches the end, a new one is drawn
      if(fltBackgroundX1 < - imgBackground.width) {
        fltBackgroundX1 = 0;
      }

      // Gets the rocket location based on the mouse location
      fltRocketX = mouseX - imgRocket.width / 2;
      fltRocketY = mouseY - imgRocket.height / 2;
      
      // Calculates the Meteor location
      fltMeteorX -= fltSpeed;
      
      // Random missle Spawn location
      if (fltMeteorX <= -imgMeteor.width) {
        fltMeteorY = projectileRandom.nextInt(height);
        fltMeteorX = width + projectileRandom.nextInt(width);
      }

      // Calculates the Missile location
      fltMissileX -= fltSpeed * 1.5;
      // The missile travels in a sinusoidal function 
      fltMissileY = (float)((double)(height / 10) * (double) Math.sin((double)(2 * Math.PI) / (width / 3) * (fltMissileX)) + fltMissileYSpawn);

      // Random missle spawn location
      if (fltMeteorX <= -imgMisile.width) {
        fltMissileYSpawn = projectileRandom.nextInt(height);
        fltMissileX = width + projectileRandom.nextInt(width * 3);
      }

      intTimeSpent += 1;
      // Every 5 seconds the speed gets 1.1x faster
      if (intTimeSpent % 500 == 0) {
        fltSpeed = (float) Math.ceil(fltSpeed * 1.1);
      }
    }
  }
}