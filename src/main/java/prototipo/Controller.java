/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototipo;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;
import javax.swing.JOptionPane;

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
        vista.getBtnIniciar().setEnabled(false);
        vista.getBtnActualizar().setEnabled(false);
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
        
        vista.getBtnIniciar().setEnabled(true);
        vista.getBtnActualizar().setEnabled(true);

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
        
        vista.getTxtID().setText("");
        vista.getTxtXPosition().setText("");
        vista.getTxtYPosition().setText("");

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

        int headX = this.snake.getX();
        int headY = this.snake.getY();
    
        
        int tamanio = snake.getTamanio(); 

        ElementosSnake comidaColisionada = null;
    
        for (ElementosSnake elemento : new java.util.ArrayList<>(elementosDibujables)) {
        
            if (elemento instanceof Comida comida && comida.getId() != 0) { 
            
                if (headX < comida.getX() + tamanio && headX + tamanio > comida.getX() &&
                    headY < comida.getY() + tamanio && headY + tamanio > comida.getY()) {
                
                    comidaColisionada = comida;
                    break;
                }
            }
        }
    
        if (comidaColisionada != null) {
        
            elementosDibujables.remove(comidaColisionada);
        
            ElementosSnake prototipoSegmento = prot.getPrototype("SegmentoBase");
        
            if (prototipoSegmento != null) {
                 try {
                    ElementosSnake nuevoSegmento = (ElementosSnake) prototipoSegmento.clone();
                
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
    
        int panelWidth = vista.getPanelSnake().getWidth();
        int panelHeight = vista.getPanelSnake().getHeight();
    
        boolean chocoLimite = false;
    
        if (headX < 0 || headX + tamanio > panelWidth ||
            headY < 0 || headY + tamanio > panelHeight) {
        
            chocoLimite = true;
        }
    
        if (chocoLimite) {
            JOptionPane.showMessageDialog(
            vista, // Componente padre: la ventana principal
            "¡Perdiste! La serpiente chocó contra el límite del panel.", 
            "GAME OVER", 
            JOptionPane.INFORMATION_MESSAGE
        );
        
            resetJuego();
        }
    }
    

private void resetJuego() {
    elementosDibujables.clear(); 
    
    this.snake = null; 
    this.direccionActual = 'R'; 
        

    if (vista.getPanelSnake().getKeyListeners().length > 0) {
        vista.getPanelSnake().removeKeyListener(vista.getPanelSnake().getKeyListeners()[0]); 
    }
    
    vista.getPanelSnake().repaint();
}
}
