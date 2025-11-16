package prototipo;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Clase principal que ensambla la arquitectura MVC e inicia la aplicación.
 */
public class Inicio {
    
    // El Registro de Prototipos se puede instanciar aquí
    private static final PrototypeRegistry PROTOTYPE_REGISTRY = new PrototypeRegistry();

    public static void main(String[] args) {
        
        // Configuración estándar de Swing para asegurar la apariencia
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
            
            // 1. Crear la Vista (la interfaz gráfica)
            Vista vista = new Vista();
            
            // 2. Crear el Controlador (el cerebro de la lógica)
            Controller controller = new Controller(vista,PROTOTYPE_REGISTRY, vista.getElementosDibujables());
            
            // 3. Inicializar el Controlador (enlaza botones y eventos de teclado)
            controller.iniciarContrlador();
            // 4. Mostrar la aplicación
            vista.setVisible(true);
        });
    }
}