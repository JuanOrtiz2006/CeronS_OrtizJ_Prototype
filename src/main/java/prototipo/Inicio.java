package prototipo;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Inicio {
    
    // Declaramos el Registry aquí para asegurar que sea una instancia única
    private static final PrototypeRegistry PROTOTYPE_REGISTRY = new PrototypeRegistry();

    // =========================================================================
    // BLOQUE DE COMENTARIO OBLIGATORIO DE LA GUÍA DE PRÁCTICA
    // (Cláusula 3)
    /*
    Creando el prototipo de la comida:
    Clase: Controller
    Método: iniciarObjetos()
    
    Creando el prototipo de la cabeza de la serpiente:
    Clase: Controller
    Método: iniciarObjetos()
    
    Clonado la comida:
    Clase: Controller
    Método: clonarComida()
    
    Clonado la cabeza para obtener el cuerpo de la serpiente (segmento):
    Clase: Controller
    Método: chequearColisionYCrecimiento() 
    */
    // =========================================================================

    public static void main(String[] args) {
        
        // Manejo de la apariencia (Look and Feel)
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(Inicio.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Ensamblaje de Componentes MVC en el hilo de eventos de Swing
        java.awt.EventQueue.invokeLater(() -> {
            
            // 1. Crear la Vista (Instancia el JFrame)
            Vista vista = new Vista();
            
            // 2. Crear el Controlador (inyecta la Vista, el Registry y el Modelo [elementosDibujables])
            Controller controller = new Controller(
                vista, 
                PROTOTYPE_REGISTRY, 
                vista.getElementosDibujables()
            );
            
            // 3. Inicializar el Controlador (enlaza todos los botones y el teclado)
            controller.iniciarContrlador();
            
            // 4. Mostrar la aplicación
            vista.setVisible(true);
        });
    }
}