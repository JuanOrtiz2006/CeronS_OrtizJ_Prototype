/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototipo;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

/**
 *
 * @author juanp
 */
public class Snake extends ElementosSnake{
    
    private List<ElementosSnake> segmentos ;

    public Snake(int x, int y) {
        super(x, y);
        
        this.segmentos= new ArrayList<>();
    }
    
    public void moverArriba() { /* Lógica de movimiento */ }
    public void moverAbajo() { /* Lógica de movimiento */ }
    public void moverIzquierda() { /* Lógica de movimiento */ }
    public void moverDerecha() { /* Lógica de movimiento */ }
    
    
    public void agregarSegmento(ElementosSnake nuevoSegmentoClonado) {
        this.segmentos.add(nuevoSegmentoClonado);
    }
    

    @Override
    public void dibujar(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, size, size);
        for (int i = 1; i < segmentos.size(); i++) {
            segmentos.get(i).dibujar(g); 
        }
    }
    
}
