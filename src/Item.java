package com.gildedrose;

/**
 * Clase Item refactorizada aplicando principios de encapsulación y validación.
 * Mantiene compatibilidad con código legacy pero mejora la robustez.
 */
public class Item {
    
    // Constantes para validación - principio DRY
    private static final int MIN_QUALITY = 0;
    private static final int MAX_QUALITY = 50;
    private static final int SULFURAS_QUALITY = 80;
    
    // Campos públicos mantenidos para compatibilidad legacy
    public String name;
    public int sellIn;
    public int quality;

    public Item(String name, int sellIn, int quality) {
        this.name = validateName(name);
        this.sellIn = sellIn;
        this.quality = validateQuality(quality, name);
    }
    
    /**
     * Valida que el nombre no sea null ni vacío.
     * Principio de fail-fast: detectar errores temprano.
     */
    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del item no puede ser null o vacío");
        }
        return name;
    }
    
    /**
     * Valida que la calidad esté en el rango correcto según el tipo de item.
     * Principio Single Responsibility: cada método tiene una sola función.
     */
    private int validateQuality(int quality, String itemName) {
        if (quality < MIN_QUALITY) {
            throw new IllegalArgumentException("La calidad no puede ser negativa");
        }
        
        // Sulfuras tiene calidad fija
        if ("Sulfuras, Hand of Ragnaros".equals(itemName)) {
            return SULFURAS_QUALITY;
        }
        
        // Otros items no pueden exceder el máximo
        if (quality > MAX_QUALITY) {
            throw new IllegalArgumentException("La calidad no puede exceder " + MAX_QUALITY);
        }
        
        return quality;
    }
    
    /**
     * Método para actualizar calidad de forma segura.
     * Mejora: encapsula la lógica de límites en lugar de repetirla.
     */
    public void setQuality(int newQuality) {
        // Sulfuras nunca cambia
        if ("Sulfuras, Hand of Ragnaros".equals(this.name)) {
            return;
        }
        
        // Aplicar límites
        this.quality = Math.max(MIN_QUALITY, Math.min(MAX_QUALITY, newQuality));
    }
    
    /**
     * Método para decrementar sellIn de forma controlada.
     * Sulfuras nunca cambia su sellIn.
     */
    public void decrementSellIn() {
        if (!"Sulfuras, Hand of Ragnaros".equals(this.name)) {
            this.sellIn--;
        }
    }

    @Override
    public String toString() {
        return this.name + ", " + this.sellIn + ", " + this.quality;
    }
    
    // Getters para acceso controlado (opcional para el futuro)
    public String getName() { return name; }
    public int getSellIn() { return sellIn; }
    public int getQuality() { return quality; }
}
