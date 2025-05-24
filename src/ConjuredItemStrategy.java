package com.gildedrose;

/**
 * Strategy para items conjurados que se degradan el doble de rápido que items regulares.
 * Demuestra extensibilidad: fácil agregar nuevos tipos sin modificar código existente.
 */
public class ConjuredItemStrategy implements ItemUpdateStrategy {
    
    private static final String CONJURED_PREFIX = "Conjured";
    private static final int MIN_QUALITY = 0;
    private static final int QUALITY_DECREASE_MULTIPLIER = 2;
    private static final int BASE_QUALITY_DECREASE = 1;
    
    @Override
    public boolean canHandle(Item item) {
        return item.name.startsWith(CONJURED_PREFIX);
    }
    
    @Override
    public void updateItem(Item item) {
        // Actualizar sellIn
        item.sellIn--;
        
        // Items conjurados se degradan el doble de rápido
        int qualityDecrease = BASE_QUALITY_DECREASE * QUALITY_DECREASE_MULTIPLIER;
        
        // Si está expirado, degradación adicional
        if (item.sellIn < 0) {
            qualityDecrease *= QUALITY_DECREASE_MULTIPLIER;
        }
        
        // Aplicar degradación respetando límites
        item.quality = Math.max(MIN_QUALITY, item.quality - qualityDecrease);
    }
} 