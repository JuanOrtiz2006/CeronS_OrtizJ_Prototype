/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototipo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

/**
 *
 * @author juanp
 */
public class Controller {
    private Vista vista;
    private Snake snake;
    private PrototypeRegistry prot;
    private char direccionActual='R';
    private final List<ElementosSnake> elementosDibujables; 

    public Controller(Vista vista, PrototypeRegistry proto, List<ElementosSnake> elementosDibujables) {
        this.vista= vista;
        this.elementosDibujables = elementosDibujables;
        this.prot=proto;
    }
    
    
    public void iniciarContrlador(){
       
        vista.getBtnObjects().addActionListener(e -> iniciarObjetos());
        vista.getBtnClone().addActionListener(e -> clonarComida());
        vista.getBtnActualizar().addActionListener(e -> actualizarComida()); 
        vista.getBtnIniciar().addActionListener(e -> iniciarJuego());
    }
    
    private int calcularSiguienteComidaId() {
    int maxId = 0;
    
    for (ElementosSnake elemento : elementosDibujables) {
        if (elemento instanceof Comida comida) {
            if (comida.getId() > maxId) {
                maxId = comida.getId();
            }
        }
    }
    // El siguiente ID es el ID más alto encontrado + 1. 
    // Si la lista está vacía (maxId = 0), el siguiente será 1.
    return maxId + 1;
}
    
    
    public void iniciarObjetos(){
        Comida comidaBase= new Comida(0,5,5);
        this.snake = new Snake(200,200);
        
        prot.addPrototype("comidaBase", comidaBase);
        prot.addPrototype("SegmentoBase", this.snake);
        
        elementosDibujables.add(comidaBase);
        elementosDibujables.add(this.snake);
        
        vista.getPanelSnake().repaint();
    }
    
    public void clonarComida(){
        ElementosSnake prototipoComida = prot.getPrototype("comidaBase");
        
        try {
            Comida nuevaComida = (Comida) prototipoComida.clone();
            int nuevoId = calcularSiguienteComidaId();
            // Asignar datos únicos al clon
            nuevaComida.setId(nuevoId);
            nuevaComida.setY(5 + (nuevoId * 25));
            
            elementosDibujables.add(nuevaComida);
            vista.getPanelSnake().repaint();
            
        } catch (Exception e) {
            System.err.println("Error clonando comida: " + e.getMessage());
        }
    }
    
    public void actualizarComida() {
        try {
            int inputID = Integer.parseInt(vista.getTxtID().getText());
            int inputX = Integer.parseInt(vista.getTxtXPosition().getText());
            int inputY = Integer.parseInt(vista.getTxtYPosition().getText());

            if (inputID == 0) {
                System.out.println("No se puede actualizar el prototipo base (ID 0).");
                return;
            }

            // Buscar en la lista de elementos dibujables (Modelo)
            for (ElementosSnake elemento : elementosDibujables) {
                if (elemento instanceof Comida comidaActual) {
                    if (comidaActual.getId() == inputID) {
                        comidaActual.setX(inputX);
                        comidaActual.setY(inputY);
                        vista.getPanelSnake().repaint();
                        return;
                    }
                }
            }
            System.out.println("Comida con ID " + inputID + " no encontrada.");

        } catch (NumberFormatException e) {
            System.err.println("Error de formato: Ingrese números válidos.");
        }
    }
    
    
    public void iniciarJuego() { // Nombre corregido
        if (snake == null) {
            System.out.println("Debe iniciar los objetos primero.");
            return;
        }

        vista.getPanelSnake().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int keyCode = e.getKeyCode();
                char nuevaDireccion = direccionActual;
                // Lógica para cambiar la dirección (mover en una dirección cada vez)
                if (keyCode == KeyEvent.VK_UP && direccionActual != 'D') {
                    nuevaDireccion = 'U';
                } else if (keyCode == KeyEvent.VK_DOWN && direccionActual != 'U') {
                    nuevaDireccion = 'D';
                } else if (keyCode == KeyEvent.VK_LEFT && direccionActual != 'R') {
                    nuevaDireccion = 'L';
                } else if (keyCode == KeyEvent.VK_RIGHT && direccionActual != 'L') {
                    nuevaDireccion = 'R';
                }
                
                snake.mover(nuevaDireccion);
    
                direccionActual = nuevaDireccion;

                chequearColisionYCrecimiento();
                chequearLimites()   ;
                vista.getPanelSnake().repaint();
            }
        });
        
        vista.getPanelSnake().setFocusable(true);
        vista.getPanelSnake().requestFocusInWindow();
    }
    
    // Dentro de la clase Controller

    private void chequearColisionYCrecimiento() {
        if (this.snake == null) return;

        // Obtener la posición de la cabeza (asumo que snake.tamanio es el tamaño del bloque)
        int headX = this.snake.getX();
        int headY = this.snake.getY();
    
        // Suponemos que el tamaño del bloque (tamanio) es accesible o fijo (ej: 20)
        // Ya que no lo veo en el Controller, asumiré que es 20 (de la clase Snake).
        int tamanio = snake.getTamanio(); 

        ElementosSnake comidaColisionada = null;
    
        // 1. ITERAR: Usamos una copia de la lista para buscar y luego eliminar de forma segura
        for (ElementosSnake elemento : new java.util.ArrayList<>(elementosDibujables)) {
        
            // Solo verificamos si es una Comida Y si NO es el prototipo (ID 0)
            if (elemento instanceof Comida comida && comida.getId() != 0) { 
            
                // Lógica de colisión (simple superposición de rectángulos)
                if (headX < comida.getX() + tamanio && headX + tamanio > comida.getX() &&
                    headY < comida.getY() + tamanio && headY + tamanio > comida.getY()) {
                
                    comidaColisionada = comida;
                    break;
                }
            }
        }
    
        // 2. CRECER si hubo colisión
        if (comidaColisionada != null) {
        
            // a) La comida desaparece del panel (Modelo)
            elementosDibujables.remove(comidaColisionada);
        
            // b) Obtener el prototipo del segmento de cola
            ElementosSnake prototipoSegmento = prot.getPrototype("SegmentoBase");
        
            if (prototipoSegmento != null) {
                 try {
                    // c) CLONAR: Se crea el nuevo segmento usando el patrón Prototype
                    ElementosSnake nuevoSegmento = (ElementosSnake) prototipoSegmento.clone();
                
                    // d) Agregar al cuerpo de la serpiente
                    this.snake.agregarSegmento(nuevoSegmento); 
                    
                } catch (Exception ex) {
                    System.err.println("Error al clonar el segmento para el crecimiento: " + ex.getMessage());
                }
            } else {
                System.err.println("Error: No se encontró el prototipo 'SegmentoBase' en el Registry.");
            }
        }
    }
    
    private void chequearLimites() {
        if (this.snake == null) return;
    
        int headX = this.snake.getX();
        int headY = this.snake.getY();
        int tamanio = this.snake.getTamanio();
    
        // Obtener los límites del panel de juego (Vista)
        // Asumo que tienes un getter para el panel, por ejemplo: vista.getPanelSnake()
        int panelWidth = vista.getPanelSnake().getWidth();
        int panelHeight = vista.getPanelSnake().getHeight();
    
        boolean chocoLimite = false;
    
        if (headX < 0 || headX + tamanio > panelWidth ||
            headY < 0 || headY + tamanio > panelHeight) {
        
            chocoLimite = true;
        }
    
        if (chocoLimite) {
            // Lógica de fin del juego
            System.out.println("💥 ¡GAME OVER! La serpiente chocó contra el límite del panel.");
        
            resetJuego();
        }
    }
    
    // Dentro de la clase Controller

private void resetJuego() {
    // 1. Eliminar todos los elementos del panel (MODELO)
    elementosDibujables.clear(); 
    
    // 2. Resetear variables de juego
    this.snake = null; // La serpiente deja de existir
    this.direccionActual = 'R'; // Volver a la dirección inicial
        

    // 4. Quitar el KeyListener del panel para que no intente mover una serpiente nula
    if (vista.getPanelSnake().getKeyListeners().length > 0) {
        // Asumo que solo tienes un KeyListener
        vista.getPanelSnake().removeKeyListener(vista.getPanelSnake().getKeyListeners()[0]); 
    }
    
    vista.getPanelSnake().repaint();
}
}
