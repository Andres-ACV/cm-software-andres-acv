package com.gildedrose;

import java.util.ArrayList;
import java.util.List;

/**
 * Manager que coordina las diferentes estrategias de actualización de items.
 * Implementa principios:
 * - Single Responsibility: solo maneja la selección y ejecución de estrategias
 * - Open/Closed: fácil agregar nuevas estrategias sin modificar este código
 * - Dependency Inversion: depende de abstracciones (ItemUpdateStrategy), no de implementaciones
 */
public class ItemUpdateStrategyManager {
    
    private final List<ItemUpdateStrategy> strategies;
    private final ItemUpdateStrategy defaultStrategy;
    
    /**
     * Constructor que inicializa todas las estrategias disponibles.
     * Principio de Dependency Injection: las dependencias se inyectan, no se crean internamente.
     */
    public ItemUpdateStrategyManager() {
        this.strategies = new ArrayList<>();
        this.defaultStrategy = new RegularItemStrategy();
        
        // Registrar estrategias específicas (orden importante: más específicas primero)
        strategies.add(new SulfurasStrategy());
        strategies.add(new AgedBrieStrategy());
        strategies.add(new BackstagePassStrategy());
        strategies.add(new ConjuredItemStrategy());
        // RegularItemStrategy se usa como default, no necesita registrarse
    }
    
    /**
     * Encuentra y ejecuta la estrategia apropiada para el item dado.
     * Principio Single Responsibility: solo coordina, no implementa lógica específica.
     */
    public void updateItem(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Item no puede ser null");
        }
        
        ItemUpdateStrategy strategy = findStrategy(item);
        strategy.updateItem(item);
    }
    
    /**
     * Encuentra la estrategia correcta para un item específico.
     * Principio KISS: lógica simple y directa de selección.
     */
    private ItemUpdateStrategy findStrategy(Item item) {
        for (ItemUpdateStrategy strategy : strategies) {
            if (strategy.canHandle(item)) {
                return strategy;
            }
        }
        
        // Si ninguna estrategia específica maneja el item, usar la default
        return defaultStrategy;
    }
    
    /**
     * Permite agregar nuevas estrategias dinámicamente.
     * Principio Open/Closed: extensible sin modificación.
     */
    public void addStrategy(ItemUpdateStrategy strategy) {
        if (strategy != null) {
            strategies.add(0, strategy); // Agregar al inicio para mayor prioridad
        }
    }
    
    /**
     * Obtiene el número de estrategias registradas (útil para testing).
     */
    public int getStrategyCount() {
        return strategies.size() + 1; // +1 por la default strategy
    }
} 