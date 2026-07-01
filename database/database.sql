CREATE DATABASE IF NOT EXISTS marie_gourmet;
USE marie_gourmet;

DROP TABLE IF EXISTS evento;
DROP TABLE IF EXISTS producto;

CREATE TABLE producto (
    id_producto BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    categoria VARCHAR(100),
    descripcion VARCHAR(255),
    precio INT,
    activo BOOLEAN DEFAULT TRUE
);

CREATE TABLE evento (
    id_evento BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre_evento VARCHAR(150) NOT NULL,
    tipo_evento VARCHAR(100),
    fecha DATE,
    asistentes INT,
    estado VARCHAR(50),
    observaciones TEXT
);

INSERT INTO producto (nombre, categoria, descripcion, precio, activo) VALUES
('50 Canapés gourmet', 'Bocados fríos', 'Canapés surtidos sobre pan de miga.', 37900, TRUE),
('30 Crostinis gourmet', 'Bocados fríos', 'Crostinis surtidos para cóctel.', 37900, TRUE),
('12 Tapaditos mini premium', 'Bocados fríos', 'Tapaditos pequeños rellenos premium.', 22900, TRUE),
('12 Rollitos de salmón ahumado', 'Bocados fríos', 'Rollitos fríos de salmón para cóctel.', 18900, TRUE),
('12 Rollitos de jamón serrano', 'Bocados fríos', 'Rollitos con mozzarella y pesto.', 17900, TRUE),
('12 Brochetas gourmet', 'Bocados fríos', 'Brochetas frías surtidas.', 27900, TRUE),
('10 Empanaditas de cóctel', 'Bocados calientes', 'Empanaditas pequeñas para evento.', 9900, TRUE),
('18 Pizzetas napolitanas', 'Bocados calientes', 'Pizzetas pequeñas para cóctel.', 20900, TRUE),
('24 Mini quiche de hoja', 'Bocados calientes', 'Mini quiches para servicio caliente.', 26900, TRUE),
('12 Brochetas de pollo a la mostaza', 'Bocados calientes', 'Brochetas calientes de pollo.', 27900, TRUE),
('12 Shots de postres', 'Bocados dulces', 'Postres individuales para mesa dulce.', 22800, TRUE),
('20 Mini brownies fudge', 'Bocados dulces', 'Brownies pequeños para coffee break.', 19900, TRUE),
('20 Mini alfajores de maicena', 'Bocados dulces', 'Alfajores pequeños para evento.', 19900, TRUE),
('24 Brochetas de frutas frescas', 'Bocados dulces', 'Brochetas de fruta para brunch.', 39000, TRUE),
('Jugo natural 1 litro', 'Bebidas sin alcohol', 'Jugo natural para evento.', 6900, TRUE),
('Agua mineral individual', 'Bebidas sin alcohol', 'Botella individual de agua mineral.', 1200, TRUE),
('Jugo en caja individual', 'Bebidas sin alcohol', 'Jugo individual para menú niños.', 800, TRUE),
('Café para coffee break', 'Bebidas sin alcohol', 'Café de grano para servicio.', 12900, TRUE),
('Pisco sour artesanal 1 litro', 'Alcohol', 'Botella de pisco sour lista para servir.', 19900, TRUE),
('Espumante brut 750 cc', 'Alcohol', 'Botella de espumante para brindis.', 14900, TRUE),
('Servicio de montaje buffet', 'Servicios', 'Montaje de mesa y presentación.', 65000, TRUE),
('Garzón adicional', 'Servicios', 'Apoyo de servicio en evento.', 45000, TRUE),
('Chef adicional', 'Servicios', 'Apoyo extra de cocina.', 70000, TRUE);

INSERT INTO evento (nombre_evento, tipo_evento, fecha, asistentes, estado, observaciones) VALUES
('Almuerzo Corporativo Demo', 'Almuerzo corporativo', '2025-11-15', 80, 'pendiente', 'Evento de prueba para minuta operativa.'),
('Coffee Break Gerencial', 'Coffee break', '2025-11-20', 35, 'confirmado', 'Reunión ejecutiva con productos dulces y salados.'),
('Cóctel Lanzamiento', 'Cóctel', '2025-12-05', 120, 'pendiente', 'Cóctel con bocados fríos, calientes y bebidas.');
