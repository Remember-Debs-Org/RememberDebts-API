TRUNCATE TABLE alerts CASCADE;
TRUNCATE TABLE payments CASCADE;
TRUNCATE TABLE debts CASCADE;
TRUNCATE TABLE categorias_deuda CASCADE;
TRUNCATE TABLE users CASCADE;
-- Insertar usuarios
INSERT INTO users (id, user_nombre, user_apellido, user_email, user_password, user_telefono, created_at, estado_usuario)
VALUES
    (1, 'Juan', 'Pérez', 'juan.perez@example.com', 'hashed_password1', '5551234567', CURRENT_DATE, 'ACTIVO'),
    (2, 'María', 'Gómez', 'maria.gomez@example.com', 'hashed_password2', '5559876543', CURRENT_DATE, 'ACTIVO');

-- Insertar categorías de deuda
INSERT INTO categorias_deuda (id, nombre, imagen_url)
VALUES
    (1, 'Proveedor', 'https://example.com/images/proveedor.png'),
    (2, 'Impuesto', 'https://example.com/images/impuesto.png'),
    (3, 'Préstamo Bancario', 'https://example.com/images/prestamo.png');

-- Insertar deudas
INSERT INTO debts (id, user_id, nombre, categoria_id, descripcion, monto, fecha_vencimiento, estado, recurrente, frecuencia, fecha_creacion)
VALUES
    (1, 1, 'Crédito Proveedor ABC', 1, 'Compra de materiales', 1500.00, '2025-05-15', 'PENDIENTE', TRUE, 'MENSUAL', CURRENT_DATE),
    (2, 1, 'Impuesto Predial', 2, 'Impuesto anual del local', 800.00, '2025-06-01', 'PENDIENTE', FALSE, NULL, CURRENT_DATE),
    (3, 2, 'Préstamo Banco XYZ', 3, 'Cuota mensual préstamo', 500.00, '2025-05-10', 'PENDIENTE', TRUE, 'MENSUAL', CURRENT_DATE);

-- Insertar pagos
INSERT INTO payments (id, deuda_id, fecha_pago, monto_pagado, notas, fecha_registro)
VALUES
    (1, 3, '2025-04-10', 500.00, 'Pago puntual', CURRENT_DATE),
    (2, 1, '2025-04-15', 1500.00, 'Pago total crédito proveedor', CURRENT_DATE);

-- Insertar alertas
INSERT INTO alerts (id, deuda_id, fecha_alerta, tipo, enviada, mensaje)
VALUES
    (1, 1, '2025-05-10', 'RECORDATORIO_PREVIO', FALSE, 'Tu deuda "Crédito Proveedor ABC" vence en 5 días.'),
    (2, 2, '2025-05-31', 'RECORDATORIO_PREVIO', FALSE, 'Tu impuesto predial vence mañana.'),
    (3, 3, '2025-05-10', 'RECORDATORIO_DIA', FALSE, 'Hoy vence tu cuota del préstamo.');

