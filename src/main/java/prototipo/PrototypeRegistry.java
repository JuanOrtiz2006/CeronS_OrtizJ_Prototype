/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prototipo;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author juanp
 */
public class PrototypeRegistry {
    
    private static Map<String, ElementosSnake> prototipos = new HashMap<>();
    
    
    public static void addPrototype(String key, ElementosSnake prototype) {
        prototipos.put(key, prototype);
    }
    
    public static ElementosSnake getPrototype(String key) {
        return prototipos.get(key);
    }
    
}
