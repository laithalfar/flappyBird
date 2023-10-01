/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.flappybirds.flappybirds;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.Timer;
/**
 *
 * @author laith
 */


// announce flappybirds class as an actionlistener so it listens to any action happening in the class
public class FlappyBirds implements ActionListener{
    
    public static FlappyBirds flappyBirds; 

    public final int width = 800, height = 1200;
    
    public Renderer renderer;
    
    public FlappyBirds() {
    
    JFrame jframe = new JFrame();
    Timer timer = new Timer(20, this);
    renderer = new Renderer();
    jframe.add(renderer); // add renderer to the frame
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //close
    jframe.setSize(width,height); //size of frame
    jframe.setResizable(false); //none resizeable 
    jframe.setVisible(true); //make it visisble
    
    timer.start(); // start the timer
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        renderer.repaint();
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
    public static void main(String[] args) {
        
        flappyBirds = new FlappyBirds();
        //System.out.println("Hello World!");
    }

    void repaint(Graphics g) {
        System.out.println("hello");
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
