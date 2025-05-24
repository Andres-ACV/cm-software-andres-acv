package com.gildedrose;

/**
 * Strategy para Sulfuras, el item legendario que nunca cambia.
 * Ejemplo perfecto del principio Single Responsibility: una sola responsabilidad muy específica.
 */
public class SulfurasStrategy implements ItemUpdateStrategy {
    
    private static final String SULFURAS_NAME = "Sulfuras, Hand of Ragnaros";
    
    @Override
    public boolean canHandle(Item item) {
        return SULFURAS_NAME.equals(item.name);
    }
    
    @Override
    public void updateItem(Item item) {
        // Sulfuras nunca cambia: ni calidad ni sellIn
        // Este método intencionalmente no hace nada
        // Principio KISS: la solución más simple para el caso más simple
    }
} 