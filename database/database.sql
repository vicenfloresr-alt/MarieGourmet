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

-- Catálogo inspirado en productos y categorías visibles en Marie Gourmet:
-- canapés, tapaditos, crostinis, brochetas, bocados dulces, coffee break, brunch y coctelería.

INSERT INTO producto (nombre, categoria, descripcion, precio, activo) VALUES
('Hummus tradicional', 'Bocados fríos', 'Dip frío para acompañar cóctel o brunch.', 8900, TRUE),
('50 Canapés gourmet', 'Bocados fríos', 'Canapés sobre pan de miga, formato para cóctel.', 37900, TRUE),
('100 Canapés gourmet', 'Bocados fríos', 'Formato grande de canapés para eventos corporativos.', 69900, TRUE),
('30 Crostinis gourmet', 'Bocados fríos', 'Crostinis surtidos para mesa de cóctel.', 37900, TRUE),
('50 Crostinis gourmet', 'Bocados fríos', 'Formato ampliado de crostinis para celebraciones.', 63900, TRUE),
('12 Rollitos de jamón serrano con mozzarella al pesto', 'Bocados fríos', 'Rollitos premium para cóctel.', 17900, TRUE),
('12 Rollitos de salmón ahumado', 'Bocados fríos', 'Bocados fríos con salmón ahumado.', 18900, TRUE),
('12 Brochetas gourmet', 'Bocados fríos', 'Brochetas surtidas de presentación individual.', 27900, TRUE),

('10 Empanaditas de cóctel', 'Bocados calientes', 'Empanaditas pequeñas para servicio de cóctel.', 9900, TRUE),
('12 Brochetas de pollo a la mostaza', 'Bocados calientes', 'Brochetas calientes de pollo para evento.', 27900, TRUE),
('18 Pizzetas Napolitanas', 'Bocados calientes', 'Mini pizzetas para cóctel o coffee break.', 20900, TRUE),
('24 Mini quiche de hoja', 'Bocados calientes', 'Mini quiches para servicio caliente.', 26900, TRUE),
('30 Tortillitas horneadas para cóctel', 'Bocados calientes', 'Tortillitas horneadas en formato de cóctel.', 5900, TRUE),

('12 Shots de postres', 'Bocados dulces', 'Postres individuales para mesa dulce.', 22800, TRUE),
('20 Mini alfajores de maicena', 'Bocados dulces', 'Mini alfajores para coffee break.', 19900, TRUE),
('20 Mini brownies fudge', 'Bocados dulces', 'Brownies pequeños para servicio dulce.', 19900, TRUE),
('20 Mini pastelitos chilenos', 'Bocados dulces', 'Pastelitos tradicionales en formato pequeño.', 22900, TRUE),
('20 Mini pastelitos de cóctel', 'Bocados dulces', 'Mini pasteles surtidos para evento.', 22900, TRUE),
('24 Brochetas de frutas frescas', 'Bocados dulces', 'Brochetas de fruta para brunch o coffee break.', 39000, TRUE),

('12 Tapaditos medianos rellenos premium', 'Brunch / Coffee Break', 'Tapaditos premium para desayunos y brunch.', 27900, TRUE),
('12 Tapaditos medianos rellenos tradicionales', 'Brunch / Coffee Break', 'Tapaditos tradicionales para coffee break.', 22900, TRUE),
('12 Tapaditos mini rellenos premium', 'Brunch / Coffee Break', 'Tapaditos mini premium para reuniones.', 22900, TRUE),
('12 Tapaditos mini rellenos tradicionales', 'Brunch / Coffee Break', 'Tapaditos mini tradicionales para eventos.', 17900, TRUE),
('24 Rolls de tortilla gourmet', 'Brunch / Coffee Break', 'Rolls de tortilla surtidos para brunch.', 31900, TRUE),
('Sándwich gourmet surtido', 'Brunch / Coffee Break', 'Sándwiches surtidos para reunión ejecutiva.', 24900, TRUE),
('Box coffee break ejecutivo', 'Brunch / Coffee Break', 'Caja de coffee break con salado, dulce y bebida.', 45900, TRUE),
('Desayuno corporativo premium', 'Brunch / Coffee Break', 'Desayuno completo para reuniones de empresa.', 54900, TRUE),
('Mini bagels rellenos surtidos', 'Brunch / Coffee Break', 'Mini bagels con rellenos fríos y frescos.', 28900, TRUE),
('Mini wraps de pollo y vegetales', 'Brunch / Coffee Break', 'Wraps pequeños para brunch o capacitación.', 26900, TRUE),

('12 Brochetas de verduras', 'Productos vegetarianos', 'Brochetas vegetarianas para menú especial.', 26900, TRUE),
('12 Pinchos de queso', 'Productos vegetarianos', 'Pinchos vegetarianos con queso.', 17900, TRUE),
('Tabla de verduras asadas', 'Productos vegetarianos', 'Alternativa vegetariana para mesa de cóctel.', 24900, TRUE),
('Mini wraps vegetarianos', 'Productos vegetarianos', 'Wraps vegetarianos para asistentes especiales.', 23900, TRUE),
('Bocados veganos surtidos', 'Productos veganos', 'Selección especial para asistentes veganos.', 27900, TRUE),
('Mini brownies veganos', 'Productos veganos', 'Opción dulce vegana para coffee break.', 21900, TRUE),

('Jugo natural botella 1 litro', 'Bebidas sin alcohol', 'Jugo natural para acompañamiento de eventos.', 6900, TRUE),
('Agua mineral premium pack', 'Bebidas sin alcohol', 'Pack de aguas para servicio de mesa.', 4900, TRUE),
('Café de grano para coffee break', 'Bebidas sin alcohol', 'Café para servicio de coffee break.', 12900, TRUE),

('Pisco sour artesanal botella 1 litro', 'Alcohol', 'Botella de pisco sour lista para servir.', 19900, TRUE),
('Mango sour botella 1 litro', 'Alcohol', 'Sour sabor mango listo para servir.', 19900, TRUE),
('Frutilla sour botella 1 litro', 'Alcohol', 'Sour sabor frutilla listo para servir.', 19900, TRUE),
('Espumante brut botella 750 cc', 'Alcohol', 'Espumante para brindis o cóctel.', 14900, TRUE),

('Tabla premium de quesos y charcutería', 'Cóctel', 'Tabla para cóctel elegante y eventos sociales.', 42900, TRUE),
('Servicio de montaje de mesa buffet', 'Servicios gourmet', 'Montaje y presentación de mesa para evento.', 65000, TRUE),
('Garzón adicional por evento', 'Servicios gourmet', 'Apoyo de servicio para eventos corporativos.', 45000, TRUE);

INSERT INTO evento (nombre_evento, tipo_evento, fecha, asistentes, estado, observaciones) VALUES
('Almuerzo Corporativo Q3 2025', 'Almuerzo corporativo', '2025-07-15', 80, 'confirmado', 'Evento corporativo con menú ejecutivo y apoyo de garzones.'),
('Coffee Break Gerencial', 'Coffee break', '2025-08-20', 35, 'pendiente', 'Reunión de mañana con café, tapaditos y dulces.'),
('Lanzamiento de Producto', 'Cóctel', '2025-09-05', 120, 'pendiente', 'Cóctel con canapés, crostinis y pisco sour artesanal.'),
('Seminario Comercial', 'Coffee break', '2025-09-18', 60, 'confirmado', 'Dos bloques de coffee break para jornada de capacitación.'),
('Brunch Equipo Directivo', 'Brunch', '2025-10-02', 25, 'cancelado', 'Brunch con tapaditos, frutas y jugos.');
