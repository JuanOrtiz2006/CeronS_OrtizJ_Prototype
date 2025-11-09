/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototipo;

import java.awt.Graphics;

/**
 *
 * @author juanp
 */
public abstract class ElementosSnake implements Cloneable {
    
    protected int x,y;
    protected int size=20;

    public ElementosSnake(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
    
    
    public abstract void dibujar(Graphics g);
    
    
    @Override
    public Object clone() throws CloneNotSupportedException{
        return  super.clone();
    }
    
    
}
