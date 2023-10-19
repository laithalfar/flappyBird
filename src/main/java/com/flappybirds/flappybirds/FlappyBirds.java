/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.flappybirds.flappybirds;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;

/**
 *
 * @author laith
 */
// announce flappybirds class as an actionlistener so it listens to any action happening in the class
public class FlappyBirds implements ActionListener, MouseListener, KeyListener {

    public static FlappyBirds flappyBirds;

    public final int width = 900, height = 700;

    public boolean gameOver, started = false;

    public Renderer renderer;

    public Rectangle bird;

    public ArrayList<Rectangle> columns;
    public Random rand;

    public int ticks = 0, yMotion = 0, score = 0;

    public FlappyBirds() {

        JFrame jframe = new JFrame();
        Timer timer = new Timer(20, this); //to give it time to repaint the graphics

        renderer = new Renderer();
        rand = new Random();

        jframe.add(renderer); // add renderer to the frame
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close
        jframe.setSize(width, height); //size of frame
        jframe.addMouseListener(this);
        jframe.addKeyListener(this);
        jframe.setResizable(false); //none resizeable 
        jframe.setTitle("Flappy Bird"); // title displayed
        jframe.setVisible(true); //make it visisble

        bird = new Rectangle(width / 2 - 10, height / 2 - 10, 20, 20);
        columns = new ArrayList<Rectangle>();
        timer.start(); // start the timer

        addColumn(true);
        addColumn(true);
        addColumn(true);
        addColumn(true);

    }

    public void addColumn(boolean start) {
        int spaceCol = 225 + rand.nextInt(75);
        int widthCol = 114;
        int heightCol = 43 + rand.nextInt(258);

        if (start) {
            columns.add(new Rectangle(width + widthCol + columns.size() * 300, height - heightCol - 120, widthCol, heightCol));
            columns.add(new Rectangle(width + widthCol + (columns.size() - 1) * 300, 0, widthCol, height - heightCol - spaceCol));
        } else {
            columns.add(new Rectangle(columns.get(columns.size() - 1).x + 600, height - heightCol - 120, widthCol, heightCol));
            columns.add(new Rectangle(columns.get(columns.size() - 1).x, 0, widthCol, height - heightCol - spaceCol));
        }
    }

    public void paintColumn(Graphics g, Rectangle column) {
        g.setColor(Color.green.darker());
        g.fillRect(column.x, column.y, column.width, column.height);
    }

    public void jump() {
        
        //restart game when pressed
        if (gameOver) {

            bird = new Rectangle(width / 2 - 10, height / 2 - 10, 20, 20);
            columns.clear();
            yMotion = 0;
            score = 0;

            addColumn(true);
            addColumn(true);
            addColumn(true);
            addColumn(true);

            gameOver = false;
        }

        //start game when pressed
        if (!started) {
            started = true;
        } else if (!gameOver) { // else if game is not over
            if (yMotion > 0) { //either make bird jump
                yMotion = -6;
            } else { //or make it fall
                yMotion -= 9;
            }
        }
    }

    //apply the repaint function to the renderer
    @Override
    public void actionPerformed(ActionEvent e) {

        //speed of moving screen
        int speed = 11;

        ticks++;

        if (started) {

            //move x position of column by 10
            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);
                column.x -= speed;
            }

            //motion of the bird falling down where the Bird would automatically fall to the ground if no 
            //action is taken
            if (ticks % 2 == 0 && yMotion < 15) {
                yMotion += 2;
            }

            //remove column when out of screen and replace with new
            for (int i = 0; i < columns.size(); i++) {
                Rectangle column = columns.get(i);
                if (column.x + column.width < 0) {
                    columns.remove(column);

                    //add a lower column if the column generated is an upper one
                    // this is because in flappy bird there can be a lower column
                    //without an upper column but not vice versa
                    if (column.y == 0) {
                        addColumn(false);
                    }
                }
            }

            bird.y += yMotion;

            for (Rectangle column : columns) {

                //increase score
                if ((column.y == 0) && (bird.x + bird.width / 2 > column.x + (column.width / 2 - 6)) && (bird.x + bird.width / 2 < column.x + column.width / 2 + 6)) {
                    score++;
                }

                //if any column intersects with the bird game is over
                if (column.intersects(bird)) {

                    gameOver = true;
                    bird.x = column.x - bird.width;
                    if (bird.x <= column.x) {
                        bird.x = column.x - bird.width;
                    } else {
                        if (column.y != 0) {
                            bird.y = column.y - bird.height;
                        } else if (bird.y < column.height) {
                            bird.y = column.height;
                        }
                    }

                }
            }
            //if the bird goes below the ground or touches the  the roof game is over
            if (bird.y > (height - 120) || (bird.y < 0)) {

                gameOver = true;

            }

            if (bird.y + yMotion >= height) {
                bird.y = height - 120;
            }

        }
        renderer.repaint();

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

        //graphics for columns
        for (Rectangle column : columns) {
            paintColumn(g, column);
        }

        //font for text
        g.setColor(Color.white);
        g.setFont(new Font("Arial", 1, 100));

        //graphics if hasn't started
        if (!started) {
            g.drawString("Click to Start", 75, height / 2 - 50);
        }

        //graphics if gameover
        if (gameOver) {
            g.drawString("Game Over!", 75, height / 2 - 50);
        }

        if (!gameOver && started) {
            g.drawString(String.valueOf(score), width / 2 - 25, 100);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        jump();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            jump();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void keyPressed(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
