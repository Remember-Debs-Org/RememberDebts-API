TRUNCATE TABLE roles CASCADE;

-- Crear los roles básicos
INSERT INTO roles (id, rol_nombre) VALUES
    (1, 'ADMIN'),
    (2, 'USUARIO');