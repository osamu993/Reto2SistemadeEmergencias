
#  Sistema de Gestión de Emergencias Urbanas

##  Descripción

Este proyecto consiste en un sistema de consola en Java que simula la gestión de emergencias urbanas como **incendios**, **robos** y **accidentes vehiculares**. El sistema permite registrar emergencias, asignar recursos (policía, bomberos, ambulancias, rescate), monitorear su atención, y generar reportes de desempeño.


---

##  Objetivos

- Simular un sistema urbano de atención de emergencias.
- Aplicar principios de **POO**, como herencia, encapsulamiento y polimorfismo.
- Implementar patrones de diseño: **Singleton**, **Strategy**, **Observer**.
- Promover buenas prácticas de codificación, modularidad y reutilización de código.

---

## 🛠 Tecnologías utilizadas

- Java 17+
- Entorno: Visual Studio Code (VSCode)
- Sistema Operativo de desarrollo: Windows 11
- Formato de ejecución: Consola

---

##  Estructura del proyecto

```bash
src/
├── controller/
│   ├── Responder.java
│   └── SistemaEmergencias.java
│
├── model/
│   ├── factory
│       └── FactoryEmergencias.java
│   ├── interfaces
│       └──  IServicioEmergencia.java
│   ├── observer
│       ├──  NotificadorEmergencias.java
│       ├──  ObserverEmergencias.java
│       └──  SujetoEmergencias.java
│   ├── services
│       ├──  Ambulancia.java
│       ├──  Bomberos.java
│       ├──  GestorRecursos.java
│       ├──  Policia.java
│       ├──  ServicioBase.java
│       └──  UnidadRescate.java
│   ├── strategy
│       ├──  IEstrategyAsignacion.java
│       ├──  StrategyPrioridad.java
│       ├──  StrategyPrioridadCercania.java
│       └──  StrategyPrioridadGravedad.java
│   ├── AccidenteVehicular.java
│   ├── Emergencia.java
│   ├── Incendio.java
│   └── Robo.java
│
├── utils/
│   ├── CityMap.java
│   ├── Main.java
│   ├── NivelGravedad.java
│   ├── StatisticsSystem.java
│   ├── SystemRegistration.java
│   ├── SystemReport.java
│   └── TipoEmergencia.java
│
└── view/
    └── MenuSistemaEmergencia.java
```

---

##  Funcionalidades principales

-  **Registro de emergencias** con tipo, ubicación, gravedad y tiempo estimado.
-  **Asignación automática de recursos** según disponibilidad y estrategia de prioridad.
-  **Reasignación de recursos** desde otras emergencias de baja prioridad.
-  **Estadísticas del día**: tasa de éxito, tiempo promedio de atención, eficiencia.
-  **Generación de reportes** en archivos `.txt`.
-  **Lectura y limpieza** de historial de registros y reportes.
-  **Simulación del tiempo de atención** usando `System.currentTimeMillis()`.
-  Patrón **Strategy** para elegir lógica de asignación.
-  Patrón **Observer** para notificar cambios en las emergencias.
-  Patrón **Singleton** en el núcleo del sistema.

---

##  Cómo ejecutar el proyecto

1. Asegúrate de tener **Java 17+** instalado.
2. Clona o descarga este repositorio.
3. Abre el proyecto en tu IDE favorito (Visual Studio Code recomendado).
4. Ejecuta la clase `utils.Main`.
5. Interactúa con el menú para registrar emergencias, asignar recursos, ver reportes, etc.

---

##  Archivos generados

- `registro_emergencias.txt`: Registro histórico de todas las emergencias creadas.
- `reporte_emergencias.txt`: Reportes de las emergencias atendidas por jornada.

---

##  Patrones de diseño aplicados

| Patrón      | Ubicación / Aplicación                                                                 |
|-------------|------------------------------------------------------------------------------------------|
| Singleton   | `SistemaEmergencias.java` (controlador único del sistema)                               |
| Strategy    | `StrategyPrioridadGravedad.java` (asignación de prioridad)                              |
| Observer    | Emergencias notifican a observadores sobre cambios de estado (pendiente/ampliable)      |
| Polimorfismo| `Emergencia` y sus subclases: `Incendio`, `Robo`, `AccidenteVehicular`                  |
| Interfaces  | `IServicioEmergencia`, `IEstrategyAsignacion`, `Responder`                              |

---

##  Ejemplo de interacción en consola

```
--- MENÚ DEL SISTEMA DE EMERGENCIAS ---
1. Registrar nueva emergencia
2. Mostrar emergencias activas
3. Atender emergencia
4. Reasignar recursos
5. Ver estadísticas del día
6. Generar y ver reporte
7. Ver historial completo
8. Limpiar registros
9. Salir
```

---

##  Créditos

Desarrollado por: **Marlon Xavier Delgado Ruiz y Kevin Esteban Sanchez Mendez**  
Curso: Java cero senior
Acamemia: Dev Senior Code  
Año: 2025
