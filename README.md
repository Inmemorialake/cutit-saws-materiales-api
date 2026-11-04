# Materiales/Fórmulas API — Cutit Saws

API REST de Materiales/Fórmulas (Equipo 1) para la plataforma modular de Cutit Saws — curso 750021C Desarrollo de Software II, Univalle. Gestiona materiales y fórmulas de fabricación, valida riesgo de patentes vía Patent Sweep y es consumida por Fabricación. CI/CD con SonarCloud, despliegue en AWS (Docker) y monitoreo con Prometheus/Grafana.

---

## 📋 Contexto del proyecto

Cutit Saws Ltd. es un fabricante boutique de motosierras hidráulicas de diamante que, tras el éxito de su modelo "Ripit 5000", enfrenta una demanda que supera su capacidad de manufactura. Su sistema homegrown de contabilidad e inventario ya no escala, por lo que la dirección definió como prioridad estratégica construir una plataforma backend modular basada en 5 APIs REST independientes.

Esta API (**Equipo 1**) es responsable de la **gestión de materiales y fórmulas** usados en la fabricación de cuchillas.

| Rol en la plataforma | API |
|---|---|
| Consume | Patent Sweep (Equipo 2) — validación cruzada de riesgo de patentes |
| Es consumida por | Fabricación (Equipo 3) — validación de lotes contra materiales/fórmulas |

## 🎯 Endpoints mínimos

| Método | Ruta | Descripción |
|---|---|---|
| `POST` | `/materiales` | Registra un nuevo material o fórmula |
| `GET` | `/materiales/{id}` | Consulta un material o fórmula por id |

## 🛠️ Stack tecnológico

- **Lenguaje / Framework:** _por definir_
- **Base de datos:** _por definir_
- **Contenedores:** Docker (obligatorio según el caso de estudio)
- **CI/CD:** pipeline con build, pruebas unitarias y análisis estático con SonarCloud (obligatorio según el caso de estudio)
- **Despliegue:** AWS EC2 (contenedor Docker), condicionado al quality gate de SonarCloud (obligatorio según el caso de estudio)
- **Monitoreo:** Prometheus + Grafana (obligatorio según el caso de estudio)

## 🚀 Cómo correr el proyecto localmente

> Pendiente de completar una vez el equipo defina el stack (lenguaje, framework, base de datos y comandos de arranque).

## 🔄 Flujo de trabajo (Git)

- Versionamiento en GitHub.
- Todo cambio se integra vía **Pull Request** con aprobación obligatoria.
- Cada PR aprobado dispara el pipeline: build → pruebas unitarias → análisis estático (SonarCloud).
- Solo si el *quality gate* pasa, se despliega automáticamente el contenedor en AWS EC2.

## 📁 Estructura del proyecto

> Pendiente de definir según el stack que elija el equipo.

## 👥 Equipo 1 — Materiales/Fórmulas

| Nombre | Rol |
|---|---|
| — | — |
| — | — |

## 📚 Curso

**750021C — Desarrollo de Software II**
Semestre 2026-2 (agosto - diciembre)
Profesor: Manuel Alejandro Pastrana Pardo, PhD.
Universidad del Valle

## 📄 Licencia

Proyecto académico — Universidad del Valle.
