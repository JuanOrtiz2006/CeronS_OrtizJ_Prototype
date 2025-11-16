/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototipo;

import java.awt.Color;
import java.awt.Graphics;

/**
 *
 * @author juanp
 */
public class Comida extends ElementosSnake{

    private int id;
    
    public Comida(int id, int x, int y) {
        super(x, y);
        this.id=id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    
    @Override
    public void dibujar(Graphics g) {
        g.setColor(Color.YELLOW);
        
        g.fillOval(x, y, tamanio, tamanio);
        
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(id), x + tamanio/2 - 5, y + tamanio/2 + 5);
    }

    
    
    
}
