package com.gildedrose;

/**
 * Strategy interface para definir cómo cada tipo de item actualiza su calidad.
 * Implementa el principio de Open/Closed: abierto para extensión, cerrado para modificación.
 */
public interface ItemUpdateStrategy {
    
    /**
     * Actualiza la calidad y sellIn de un item según sus reglas específicas.
     * @param item El item a actualizar
     */
    void updateItem(Item item);
    
    /**
     * Determina si esta estrategia puede manejar el item dado.
     * @param item El item a evaluar
     * @return true si esta estrategia maneja este tipo de item
     */
    boolean canHandle(Item item);
} 