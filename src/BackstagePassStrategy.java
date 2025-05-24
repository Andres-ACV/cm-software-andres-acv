package com.gildedrose;

/**
 * Strategy para Backstage passes con lógica compleja de calidad basada en proximidad al concierto.
 * Demuestra cómo manejar reglas complejas de forma legible y mantenible.
 */
public class BackstagePassStrategy implements ItemUpdateStrategy {
    
    private static final String BACKSTAGE_PASS_NAME = "Backstage passes to a TAFKAL80ETC concert";
    private static final int MAX_QUALITY = 50;
    private static final int CONCERT_THRESHOLD_1 = 10;
    private static final int CONCERT_THRESHOLD_2 = 5;
    private static final int BASE_QUALITY_INCREASE = 1;
    private static final int MEDIUM_PROXIMITY_BONUS = 1;
    private static final int HIGH_PROXIMITY_BONUS = 2;
    
    @Override
    public boolean canHandle(Item item) {
        return BACKSTAGE_PASS_NAME.equals(item.name);
    }
    
    @Override
    public void updateItem(Item item) {
        // Actualizar sellIn
        item.sellIn--;
        
        // Lógica de calidad basada en proximidad al concierto
        if (item.sellIn < 0) {
            // Concierto pasado: calidad a cero
            item.quality = 0;
        } else {
            int qualityIncrease = calculateQualityIncrease(item.sellIn);
            item.quality = Math.min(MAX_QUALITY, item.quality + qualityIncrease);
        }
    }
    
    /**
     * Calcula el incremento de calidad basado en días restantes al concierto.
     * Principio KISS: lógica compleja dividida en métodos simples y comprensibles.
     */
    private int calculateQualityIncrease(int daysUntilConcert) {
        int increase = BASE_QUALITY_INCREASE;
        
        if (daysUntilConcert <= CONCERT_THRESHOLD_2) {
            // 5 días o menos: +3 total
            increase += HIGH_PROXIMITY_BONUS;
        } else if (daysUntilConcert <= CONCERT_THRESHOLD_1) {
            // 10 días o menos: +2 total
            increase += MEDIUM_PROXIMITY_BONUS;
        }
        // Más de 10 días: +1 (base)
        
        return increase;
    }
} 