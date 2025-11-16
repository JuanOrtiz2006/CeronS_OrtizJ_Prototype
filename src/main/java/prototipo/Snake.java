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
            case 'U': // Up
                moverArriba();
                break;
            case 'D': // Down
                moverAbajo();
                break;
            case 'L': // Left
                moverIzquierda();
                break;
            case 'R': // Right
                moverDerecha();
                break;
        }
    }
    
    public void moverArriba() {
        moverSegmentos(); // 1. Mueve la cola
        this.y -= tamanio; // 2. Mueve la cabeza
    }

    public void moverAbajo() {
        moverSegmentos(); // 1. Mueve la cola
        this.y += tamanio; // 2. Mueve la cabeza
    }

    public void moverIzquierda() {
        moverSegmentos(); // 1. Mueve la cola
        this.x -= tamanio; // 2. Mueve la cabeza
    }

    public void moverDerecha() {
        moverSegmentos(); // 1. Mueve la cola
        this.x += tamanio; // 2. Mueve la cabeza
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
        // 1. Dibuja la cola (segmentos 1 en adelante)
        g.setColor(Color.DARK_GRAY); 
        for (int i = 1; i < segmentos.size(); i++) {
            ElementosSnake segmento = segmentos.get(i);
            g.fillRect(segmento.getX(), segmento.getY(), tamanio, tamanio);
        }
        
        // 2. Dibuja la cabeza (el elemento Snake en sí) AL FINAL.
        // Esto asegura que la cabeza siempre se superponga a la cola y otros elementos.
        g.setColor(Color.GREEN);
        g.fillRect(this.x, this.y, tamanio, tamanio);
        
    }
    
}
