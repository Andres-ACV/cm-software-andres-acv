package com.gildedrose;

/**
 * Strategy para Aged Brie que mejora en calidad con el tiempo.
 * Principio Single Responsibility: maneja únicamente la lógica del queso envejecido.
 */
public class AgedBrieStrategy implements ItemUpdateStrategy {
    
    private static final String AGED_BRIE_NAME = "Aged Brie";
    private static final int MAX_QUALITY = 50;
    private static final int QUALITY_INCREASE_RATE = 1;
    private static final int EXPIRED_QUALITY_INCREASE_RATE = 2;
    
    @Override
    public boolean canHandle(Item item) {
        return AGED_BRIE_NAME.equals(item.name);
    }
    
    @Override
    public void updateItem(Item item) {
        // Actualizar sellIn
        item.sellIn--;
        
        // Aged Brie mejora con el tiempo, más rápido después de expirar
        int qualityIncrease = item.sellIn < 0 ? 
            EXPIRED_QUALITY_INCREASE_RATE : QUALITY_INCREASE_RATE;
        
        // Aplicar mejora respetando límite máximo
        item.quality = Math.min(MAX_QUALITY, item.quality + qualityIncrease);
    }
} 