# Gilded Rose - Refactoring con Principios SOLID y Patrón Strategy

### Contexto del Negocio
La Posada Rosa Dorada (Gilded Rose Inn) maneja un inventario de items mágicos y mundanos con reglas complejas de degradación/mejora de calidad:

- **Items Regulares**: Pierden calidad con el tiempo
- **Aged Brie**: Mejora con la edad
- **Backstage Passes**: Aumentan de valor conforme se acerca el concierto
- **Sulfuras**: Item legendario que nunca cambia
- **Conjured Items**: Se degradan el doble de rápido

## Instrucciones de Ejecución

#### **Crear directorio build, compilar hacia el directorio y ejecutar el programa**
```powershell
mkdir build
javac -d build src/*.java
java -cp build com.gildedrose.GildedRoseDemo
```

## Análisis de Malas Prácticas

### Código Original - Problemas Identificados

#### 1. **Violación del Single Responsibility Principle**
```java
// ❌ ANTES: Una sola clase con múltiples responsabilidades
public void updateQuality() {
    // 60+ líneas de lógica compleja mezclando diferentes responsabilidades:
    // - Validación de tipos de items
    // - Cálculo de calidad para cada tipo
    // - Actualización de sellIn
    // - Aplicación de límites
}
```

#### 2. **Violación del Open/Closed Principle**
```java
// ❌ ANTES: Hardcoded item names, difícil extensión
if (!items[i].name.equals("Aged Brie") 
    && !items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
    // Para agregar un nuevo tipo de item, hay que modificar esta lógica
}
```

#### 3. **Violación del DRY Principle**
```java
// ❌ ANTES: Código duplicado en múltiples lugares
if (items[i].quality < 50) {
    items[i].quality = items[i].quality + 1;
}
// Esta lógica se repite 6+ veces en el código original
```

#### 4. **Violación del KISS Principle**
```java
// ❌ ANTES: Lógica anidada muy compleja
if (!items[i].name.equals("Aged Brie")) {
    if (!items[i].name.equals("Backstage passes to a TAFKAL80ETC concert")) {
        if (items[i].quality > 0) {
            if (!items[i].name.equals("Sulfuras, Hand of Ragnaros")) {
                items[i].quality = items[i].quality - 1;
            }
        }
    }
}
```

#### 5. **Problemas de Encapsulación**
```java
// ❌ ANTES: Campos públicos sin validación
public class Item {
    public String name;    // Sin validación de null
    public int sellIn;     // Sin control de cambios
    public int quality;    // Sin límites controlados
}
```

#### 6. **Alta Complejidad Ciclomática**
- El método `updateQuality()` original tenía **complejidad ciclomática > 25**
- Difícil de entender, mantener y testear
- Múltiples caminos de ejecución entrelazados

## Refactoring Implementado - Patrón Stategy

### **Implementación del Patrón**

#### **Context**: `ItemUpdateStrategyManager`
- Mantiene referencia a las estrategias
- Selecciona la estrategia apropiada
- Delega la ejecución

#### **Strategy Interface**: `ItemUpdateStrategy`
- Define el contrato común
- Métodos: `updateItem()` y `canHandle()`

#### **Concrete Strategies**:
- `RegularItemStrategy` - Items estándar
- `AgedBrieStrategy` - Queso que mejora
- `BackstagePassStrategy` - Pases de concierto
- `SulfurasStrategy` - Item legendario
- `ConjuredItemStrategy` - Items mágicos

### Estrategia de Refactoring

#### **Fase 1: Aplicación del Patrón Strategy**
```java
// ✅ DESPUÉS: Interface clara y enfocada
public interface ItemUpdateStrategy {
    void updateItem(Item item);
    boolean canHandle(Item item);
}
```

#### **Fase 2: Implementación de Estrategias Específicas**
```java
// ✅ DESPUÉS: Una clase por cada tipo de responsabilidad
public class RegularItemStrategy implements ItemUpdateStrategy {
    // Solo maneja items regulares
}

public class AgedBrieStrategy implements ItemUpdateStrategy {
    // Solo maneja Aged Brie
}

public class BackstagePassStrategy implements ItemUpdateStrategy {
    // Solo maneja Backstage passes
}
```

#### **Fase 3: Manager para Coordinar Estrategias**
```java
// ✅ DESPUÉS: Coordinador simple y extensible
public class ItemUpdateStrategyManager {
    public void updateItem(Item item) {
        ItemUpdateStrategy strategy = findStrategy(item);
        strategy.updateItem(item);
    }
}
```

#### **Fase 4: Clase Principal Simplificada**
```java
// ✅ DESPUÉS: Método principal ultra-simplificado
public void updateQuality() {
    for (Item item : items) {
        if (item != null) {
            strategyManager.updateItem(item);
        }
    }
}
```

## Principios SOLID Aplicados

### **S - Single Responsibility Principle**
```java
// ✅ Cada clase tiene una sola responsabilidad
public class AgedBrieStrategy {
    // Solo responsable de la lógica de Aged Brie
}

public class ItemUpdateStrategyManager {
    // Solo responsable de seleccionar y ejecutar estrategias
}
```

### **O - Open/Closed Principle**
```java
// ✅ Fácil agregar nuevos tipos sin modificar código existente
public class NewMagicItemStrategy implements ItemUpdateStrategy {
    // Nueva funcionalidad sin tocar código existente
}

// En el manager:
strategyManager.addStrategy(new NewMagicItemStrategy());
```

### **L - Liskov Substitution Principle**
```java
// ✅ Todas las estrategias son intercambiables
ItemUpdateStrategy strategy1 = new RegularItemStrategy();
ItemUpdateStrategy strategy2 = new AgedBrieStrategy();
// Ambas pueden usarse indistintamente donde se espere ItemUpdateStrategy
```

### **I - Interface Segregation Principle**
```java
// ✅ Interface específica y enfocada
public interface ItemUpdateStrategy {
    void updateItem(Item item);      // Solo lo necesario
    boolean canHandle(Item item);    // Funcionalidad específica
}
```

### **D - Dependency Inversion Principle**
```java
// ✅ Depende de abstracciones, no de implementaciones
public class GildedRose {
    private final ItemUpdateStrategyManager strategyManager;
    
    // Constructor con inyección de dependencias para testing
    public GildedRose(Item[] items, ItemUpdateStrategyManager strategyManager) {
        this.strategyManager = strategyManager;
    }
}
```