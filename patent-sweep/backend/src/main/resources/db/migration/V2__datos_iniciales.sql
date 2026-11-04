-- Catálogo IPC/CPC precargado (DAS DA-09).
INSERT INTO clasificacion_patente (codigo, descripcion, tipo) VALUES
    ('B27B 17/00', 'Sierras de cadena y equipos relacionados', 'IPC'),
    ('B24D 3/00', 'Cuerpos abrasivos: características físicas y constituyentes', 'IPC'),
    ('B23D 61/00', 'Herramientas para máquinas o dispositivos de aserrado', 'IPC'),
    ('C22C 38/00', 'Aleaciones ferrosas, p. ej. aceros aleados', 'IPC'),
    ('C22C 19/07', 'Aleaciones basadas en cobalto', 'CPC'),
    ('B22F 3/00', 'Fabricación de piezas a partir de polvo metálico', 'IPC');

-- Configuración inicial (PB HU-01 4.3, HU-06 criterio 6 y HU-11 2.6).
-- [Supuesto] Valores tomados de los ejemplos del PB: frecuencia de 1 DIAS (24 h),
-- clasificaciones B27B 17/00 y B24D 3/00, y rango de fechas de 5 ANOS.
INSERT INTO configuracion (usuario_id, frecuencia_valor, frecuencia_unidad, frecuencia_horas,
                           rango_cantidad, rango_unidad, clasificaciones, activa, vigente_desde)
VALUES (NULL, 1, 'DIAS', 24, 5, 'ANOS', '["B27B 17/00", "B24D 3/00"]'::jsonb, TRUE, CURRENT_TIMESTAMP);
