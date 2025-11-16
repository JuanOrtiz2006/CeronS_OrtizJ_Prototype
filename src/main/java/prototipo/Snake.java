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
        this.segmentos.add(this);
    }

    public int getTamanio() {
        return tamanio;
    }
    
    
    
    public void agregarSegmento(ElementosSnake nuevoSegmentoClonado) {
        this.segmentos.add(nuevoSegmentoClonado);
    }
    
    public void mover(char direccion) {
        switch (direccion) {
            case 'U': 
                moverArriba();
                break;
            case 'D': 
                moverAbajo();
                break;
            case 'L': 
                moverIzquierda();
                break;
            case 'R': 
                moverDerecha();
                break;
        }
    }
    
    public void moverArriba() {
        moverSegmentos(); 
        this.y -= tamanio; 
    }

    public void moverAbajo() {
        moverSegmentos(); 
        this.y += tamanio; 
    }

    public void moverIzquierda() {
        moverSegmentos(); 
        this.x -= tamanio; 
    }

    public void moverDerecha() {
        moverSegmentos(); 
        this.x += tamanio;
    }
    
    private void moverSegmentos() {
        
        for (int i = segmentos.size() - 1; i > 0; i--) {
            ElementosSnake segmentoActual = segmentos.get(i);
            ElementosSnake segmentoAnterior = segmentos.get(i - 1);
        
            segmentoActual.setX(segmentoAnterior.getX());
            segmentoActual.setY(segmentoAnterior.getY());
        }
    }

    @Override
    public void dibujar(Graphics g) {
        g.setColor(Color.DARK_GRAY); 
        for (int i = 1; i < segmentos.size(); i++) {
            ElementosSnake segmento = segmentos.get(i);
            g.fillRect(segmento.getX(), segmento.getY(), tamanio, tamanio);
        }
        
        g.setColor(Color.GREEN);
        g.fillRect(this.x, this.y, tamanio, tamanio);
        
    }
    
}
