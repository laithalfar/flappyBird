/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.flappybirds.flappybirds;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;
/**
 *
 * @author laith
 */


// announce flappybirds class as an actionlistener so it listens to any action happening in the class
public class FlappyBirds implements ActionListener{
    
    public static FlappyBirds flappyBirds; 

    public final int width = 900, height = 700;
    
    public Renderer renderer;
    
    public Rectangle bird;
    
    public ArrayList<Rectangle> columns;
    public Random rand;
    
    
    public int ticks=0, yMotion=0;
    public FlappyBirds() {
    
    JFrame jframe = new JFrame();
    Timer timer = new Timer(20, this); //to give it time to repaint the graphics
    
    renderer = new Renderer();
    rand = new Random();
    
    jframe.add(renderer); // add renderer to the frame
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close
    jframe.setSize(width,height); //size of frame
    jframe.setResizable(false); //none resizeable 
    jframe.setTitle("Flappy Bird"); // title displayed
    jframe.setVisible(true); //make it visisble
    
    bird = new Rectangle(width/2-10, height/2-10, 20, 20);
    columns = new ArrayList<Rectangle>(); 
    timer.start(); // start the timer
    
    addColumn(true);
    addColumn(true);
    addColumn(true);
    addColumn(true);
    
    }

    public void addColumn(boolean start){
        int spaceCol = 500;
        int widthCol = 114;
        int heightCol = 43 + rand.nextInt(258);
        
        if(start){
        columns.add(new Rectangle(width + widthCol + columns.size()*300, height - heightCol - 120, widthCol, heightCol));
        columns.add(new Rectangle(width + widthCol + (columns.size()-1)*300,0, widthCol, height - heightCol - spaceCol));
        } else{
        columns.add(new Rectangle(columns.get(columns.size()-1).x + 600, height - heightCol - 120, widthCol, heightCol));
        columns.add(new Rectangle(columns.get(columns.size()-1).x,0, widthCol, height - heightCol - spaceCol));
    }      
    }
    
    
    public void paintColumn(Graphics g, Rectangle column){
        g.setColor(Color.green.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }
    
    //apply the repaint function to the renderer
    @Override
    public void actionPerformed(ActionEvent e) {
        
        int speed = 10;
        
        ticks++;
        
        //move x position of column by 10
        for(int i = 0; i < columns.size(); i++){
           Rectangle column = columns.get(i);
           column.x -= speed;
       }
        
        //motion of the bird falling down where the Bird would automatically fall to the ground if no 
        //action is taken
        if(ticks % 2 == 0 && yMotion < 15){
            yMotion += 2;
        }
        
       //remove column when out of screen and replace with new
       for(int i = 0; i < columns.size(); i++){
           Rectangle column = columns.get(i);
           if(column.x + column.width<0){
               columns.remove(column);
               
               //add a lower column if the column generated is an upper one
               // this is because in flappy bird there can be a lower column
               //without an upper column but not vice versa
               if(column.y == 0){
               addColumn(false);
               }
           }
        }
        renderer.repaint();
        bird.y += yMotion;
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
   //main 
    public static void main(String[] args) {
        
        flappyBirds = new FlappyBirds();
        //System.out.println("Hello World!");
    }

    //graphic qualities added
    void repaint(Graphics g) {
       
        //graphics for sky rectangle (renderer)
        g.setColor(Color.cyan);
        g.fillRect(0, 0, width, height);
        
        //graphics for middle rectangle
        g.setColor(Color.red);
        g.fillRect(bird.x, bird.y, bird.width, bird.height);
        
        //graphics for base rectangle
        g.setColor(Color.orange);
        g.fillRect(0, height - 120, width, 120);
        
        //graphics for a grass rectangle
        g.setColor(Color.green.darker());
        g.fillRect(0, height - 120, width, 20);
        
        for(Rectangle column: columns){
            paintColumn(g, column);
        }
    }
}
