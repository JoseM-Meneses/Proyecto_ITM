CREATE DATABASE tenis_col

GO
USE tenis_col

GO
CREATE TABLE tenis (
    id_tenis INT IDENTITY(1,1) PRIMARY KEY,
    marca VARCHAR(50),
    modelo VARCHAR(50),
    precio FLOAT,
    stock INT
);

GO
CREATE TABLE compra (
    id_compra INT IDENTITY(1,1) PRIMARY KEY,
    id_cliente INT FOREIGN KEY REFERENCES cliente(id_cliente),
    id_sucursal INT FOREIGN KEY REFERENCES sucursal(id_sucursal),
    fecha DATETIME DEFAULT GETDATE()
);

GO
INSERT INTO tenis (marca, modelo, precio, stock)
VALUES
('Nike', 'Air Max', 500000, 10),
('Adidas', 'Forum', 500000, 8),
('Nike', 'Jordan', 800000, 10),
('Fila', 'Original', 200000, 8),
('Nike', 'Air Force', 600000, 10),
('Adidas', 'Predator', 450000, 8),
('Vans', 'Era', 250000, 10),
('Adidas', 'Campus', 550000, 8),
('Nike', 'Shox', 800000, 10),
('Puma', 'Speedcat', 300000, 5);


GO
SELECT * FROM compra

GO
SELECT * FROM tenis
