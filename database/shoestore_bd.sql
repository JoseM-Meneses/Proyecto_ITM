CREATE DATABASE tenis_col;

USE tenis_col;

CREATE TABLE tenis (
    id_tenis INT IDENTITY(1,1) PRIMARY KEY,
    marca VARCHAR(50) NOT NULL,
    modelo VARCHAR(50) NOT NULL,
    precio FLOAT NOT NULL,
    stock INT DEFAULT 0
);

CREATE TABLE sucursal (
    id_sucursal INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    ciudad VARCHAR(50)
);

CREATE TABLE cliente (
    id_cliente INT IDENTITY(1,1) PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    correo VARCHAR(100) UNIQUE,
    telefono VARCHAR(20)
);

CREATE TABLE compra (
    id_compra INT IDENTITY(1,1) PRIMARY KEY,
    id_cliente INT FOREIGN KEY REFERENCES cliente(id_cliente),
    id_sucursal INT FOREIGN KEY REFERENCES sucursal(id_sucursal),
    fecha DATETIME DEFAULT GETDATE()
);

CREATE TABLE detalle_compra (
    id_detalle INT IDENTITY(1,1) PRIMARY KEY,
    id_compra INT FOREIGN KEY REFERENCES compra(id_compra),
    id_tenis INT FOREIGN KEY REFERENCES tenis(id_tenis),
    talla INT NOT NULL,
    cantidad INT NOT NULL,
    precio_unitario FLOAT NOT NULL
);


GO
INSERT INTO tenis (marca, modelo, precio, stock)
VALUES
('Nike', 'Air Max', 500000, 10);

GO
INSERT INTO sucursal (nombre, ciudad)
VALUES
	('TenisCol Centro', 'Medellín');

GO
 INSERT INTO cliente (nombre, correo, telefono)
 VALUES
	('Ana Perez', 'ana@gmail.com', '3001111111');
