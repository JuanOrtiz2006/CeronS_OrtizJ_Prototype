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
    protected int tamanio=20;

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

    public int getSize() {
        return tamanio;
    }
    
    

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setSize(int tamanio) {
        this.tamanio = tamanio;
    }
    
    
    
    public abstract void dibujar(Graphics g);
    
    
    @Override
    public ElementosSnake clone() {
        try {
            return (ElementosSnake) super.clone();
        } catch (CloneNotSupportedException e) {
            // En Java, esto no debería pasar si implementa Cloneable.
            throw new RuntimeException("Error al clonar ElementosSnake", e); 
        }
    }
    
    
}
