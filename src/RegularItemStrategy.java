package com.gildedrose;

/**
 * Strategy para items regulares que degradan en calidad con el tiempo.
 * Implementa Single Responsibility Principle: solo maneja la lógica de items regulares.
 */
public class RegularItemStrategy implements ItemUpdateStrategy {
    
    private static final int MIN_QUALITY = 0;
    private static final int QUALITY_DECREASE_RATE = 1;
    private static final int EXPIRED_QUALITY_DECREASE_RATE = 2;
    
    @Override
    public boolean canHandle(Item item) {
        // Items regulares son todos excepto los especiales
        return !isSpecialItem(item.name);
    }
    
    @Override
    public void updateItem(Item item) {
        // Actualizar sellIn (días restantes para venta)
        item.sellIn--;
        
        // Calcular degradación de calidad
        int qualityDecrease = item.sellIn < 0 ? 
            EXPIRED_QUALITY_DECREASE_RATE : QUALITY_DECREASE_RATE;
        
        // Aplicar degradación respetando límites
        item.quality = Math.max(MIN_QUALITY, item.quality - qualityDecrease);
    }
    
    /**
     * Determina si un item es especial (tiene reglas particulares).
     * Principio DRY: lógica centralizada para identificar items especiales.
     */
    private boolean isSpecialItem(String itemName) {
        return itemName.equals("Aged Brie") ||
               itemName.equals("Backstage passes to a TAFKAL80ETC concert") ||
               itemName.equals("Sulfuras, Hand of Ragnaros") ||
               itemName.startsWith("Conjured");
    }
} 