package com.gildedrose;

/**
 * Demo actualizado que muestra las mejoras del refactoring aplicando patrón Strategy.
 * Demuestra cómo el código refactorizado mantiene la misma funcionalidad pero con mejor estructura.
 */
public class GildedRoseDemo {
    public static void main(String[] args) {
        System.out.println("=== GILDED ROSE INN - SISTEMA REFACTORIZADO ===");
        System.out.println("Aplicando principios SOLID y patrón Strategy");
        System.out.println("===============================================\n");
        
        // Crear items de prueba con diferentes tipos
        Item[] items = createTestItems();
        
        // Crear instancia de GildedRose con el código refactorizado
        GildedRose app = new GildedRose(items);
        
        // Simular varios días de operación
        int days = 4;
        for (int day = 0; day <= days; day++) {
            printDayHeader(day);
            printAllItems(app);
            
            if (day < days) {
                System.out.println("Actualizando calidad...\n");
                app.updateQuality();
            }
        }
        
        printRefactoringBenefits();
    }
    
    /**
     * Crea items de prueba que demuestran todos los tipos soportados.
     * Principio DRY: lógica de creación centralizada.
     */
    private static Item[] createTestItems() {
        return new Item[] {
            // Items regulares
            new Item("+5 Dexterity Vest", 10, 20),
            new Item("Elixir of the Mongoose", 5, 7),
            
            // Items especiales
            new Item("Aged Brie", 2, 0),
            new Item("Sulfuras, Hand of Ragnaros", 0, 80),
            new Item("Sulfuras, Hand of Ragnaros", -1, 80),
            
            // Backstage passes con diferentes proximidades
            new Item("Backstage passes to a TAFKAL80ETC concert", 15, 20),
            new Item("Backstage passes to a TAFKAL80ETC concert", 10, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 5, 49),
            new Item("Backstage passes to a TAFKAL80ETC concert", 1, 30),
            
            // Items conjurados (nueva funcionalidad)
            new Item("Conjured Mana Cake", 3, 6),
            new Item("Conjured Health Potion", 1, 10)
        };
    }
    
    /**
     * Imprime el encabezado del día con formato claro.
     * Principio KISS: presentación simple y clara.
     */
    private static void printDayHeader(int day) {
        System.out.println("┌─────────────────────────────────────────────────────────────┐");
        System.out.printf( "│                         DÍA %-2d                            │%n", day);
        System.out.println("├─────────────────────────────────────────────────────────────┤");
        System.out.println("│ NOMBRE                                   │ VENTA │ CALIDAD │");
        System.out.println("├─────────────────────────────────────────────────────────────┤");
    }
    
    /**
     * Imprime todos los items con formato tabular mejorado.
     * Mejora de legibilidad comparado con la versión original.
     */
    private static void printAllItems(GildedRose app) {
        for (int i = 0; i < app.getItemCount(); i++) {
            Item item = app.getItem(i);
            System.out.printf("│ %-40s │  %3d  │   %3d   │%n", 
                             item.name, item.sellIn, item.quality);
        }
        System.out.println("└─────────────────────────────────────────────────────────────┘");
        System.out.println();
    }
    
    /**
     * Explica los beneficios del refactoring realizado.
     * Propósito educativo: mostrar las mejoras obtenidas.
     */
    private static void printRefactoringBenefits() {
        System.out.println("\n" + "=".repeat(65));
        System.out.println("                    BENEFICIOS DEL REFACTORING");
        System.out.println("=".repeat(65));
        System.out.println();
        
        System.out.println("🏗️  PRINCIPIOS SOLID APLICADOS:");
        System.out.println("   • Single Responsibility: Cada clase tiene una sola responsabilidad");
        System.out.println("   • Open/Closed: Fácil agregar nuevos tipos sin modificar código existente");
        System.out.println("   • Liskov Substitution: Estrategias intercambiables");
        System.out.println("   • Interface Segregation: Interfaces específicas y enfocadas");
        System.out.println("   • Dependency Inversion: Depende de abstracciones, no implementaciones");
        System.out.println();
        
        System.out.println("📈 MEJORAS DE MANTENIBILIDAD:");
        System.out.println("   • Código más legible y fácil de entender");
        System.out.println("   • Fácil testing con estrategias independientes");
        System.out.println("   • Reducción de complejidad ciclomática");
        System.out.println("   • Eliminación de código duplicado (DRY)");
        System.out.println();
        
        System.out.println("⚡ BENEFICIOS DE ESCALABILIDAD:");
        System.out.println("   • Agregar nuevos tipos de items es trivial");
        System.out.println("   • Modificar reglas existentes sin afectar otras");
        System.out.println("   • Testing unitario más efectivo");
        System.out.println("   • Mejor separación de responsabilidades");
        System.out.println();
    }
} 