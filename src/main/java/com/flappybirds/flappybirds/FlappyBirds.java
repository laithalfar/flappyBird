/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.flappybirds.flappybirds;
import javax.swing.JFrame;
/**
 *
 * @author laith
 */


public class FlappyBirds {
    
    public static FlappyBirds flappyBirds; 

    public final int width = 800, height = 1200;
    
    public FlappyBirds(){
    
    JFrame jframe = new JFrame();
    
    jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    jframe.setSize(width,height);
    jframe.setResizable(false);
    jframe.setVisible(true);
    }
    
    public static void main(String[] args) {
        
        flappyBirds = new FlappyBirds();
        System.out.println("Hello World!");
    }
}
