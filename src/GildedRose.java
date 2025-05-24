package com.gildedrose;

import java.util.Arrays;
import java.util.List;

/**
 * Clase GildedRose refactorizada aplicando principios SOLID y patrón Strategy.
 * 
 * Mejoras implementadas:
 * - Single Responsibility: solo maneja la coordinación de updates, delegando lógica específica
 * - Open/Closed: abierta para extensión (nuevos tipos de items), cerrada para modificación
 * - Liskov Substitution: todas las estrategias son intercambiables
 * - Interface Segregation: interfaces específicas y enfocadas
 * - Dependency Inversion: depende de abstracciones, no de implementaciones concretas
 */
public class GildedRose {
    
    private final List<Item> items;
    private final ItemUpdateStrategyManager strategyManager;
    
    /**
     * Constructor que acepta array para mantener compatibilidad con código legacy.
     * Internamente usa List para mejor manejo de colecciones.
     */
    public GildedRose(Item[] items) {
        if (items == null) {
            throw new IllegalArgumentException("Items array no puede ser null");
        }
        
        this.items = Arrays.asList(items);
        this.strategyManager = new ItemUpdateStrategyManager();
    }
    
    /**
     * Constructor alternativo que acepta List (más moderno).
     * Principio de flexibilidad: múltiples formas de crear la instancia.
     */
    public GildedRose(List<Item> items) {
        if (items == null) {
            throw new IllegalArgumentException("Items list no puede ser null");
        }
        
        this.items = items;
        this.strategyManager = new ItemUpdateStrategyManager();
    }
    
    /**
     * Constructor con inyección de dependencias para testing.
     * Principio Dependency Inversion: permite inyectar mocks para testing.
     */
    public GildedRose(Item[] items, ItemUpdateStrategyManager strategyManager) {
        if (items == null || strategyManager == null) {
            throw new IllegalArgumentException("Items y strategyManager no pueden ser null");
        }
        
        this.items = Arrays.asList(items);
        this.strategyManager = strategyManager;
    }
    
    /**
     * Método principal refactorizado que aplica el patrón Strategy.
     * 
     * Antes: 60+ líneas de lógica compleja con múltiples responsabilidades
     * Después: 10 líneas simples con una sola responsabilidad
     * 
     * Principios aplicados:
     * - DRY: no hay código duplicado
     * - KISS: lógica simple y directa
     * - Single Responsibility: solo coordina, no implementa lógica específica
     */
    public void updateQuality() {
        for (Item item : items) {
            if (item != null) {
                strategyManager.updateItem(item);
            }
        }
    }
    
    /**
     * Obtiene todos los items (útil para testing y debugging).
     * Principio de transparencia: permite inspeccionar el estado interno.
     */
    public List<Item> getItems() {
        return items;
    }
    
    /**
     * Obtiene un item específico por índice.
     * Mantiene compatibilidad con código que espera acceso por índice.
     */
    public Item getItem(int index) {
        if (index < 0 || index >= items.size()) {
            throw new IndexOutOfBoundsException("Índice fuera de rango: " + index);
        }
        return items.get(index);
    }
    
    /**
     * Obtiene el número total de items.
     */
    public int getItemCount() {
        return items.size();
    }
    
    /**
     * Permite agregar nuevas estrategias dinámicamente.
     * Útil para extensibilidad y testing.
     */
    public void addCustomStrategy(ItemUpdateStrategy strategy) {
        strategyManager.addStrategy(strategy);
    }
}
