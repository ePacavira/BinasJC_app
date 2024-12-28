
-- Criar a base de dados
CREATE DATABASE db_binasjc;

-- Usar a base de dados
USE db_binasjc;

CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    palavra_passe VARCHAR(255) DEFAULT '1234',
    pontuacao INT DEFAULT 0
);

-- Tabela de Estações
CREATE TABLE Estacoes (
    id_estacao INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL
);

CREATE TABLE Bicicletas (
    id_bicicleta INT AUTO_INCREMENT PRIMARY KEY,
    id_estacao INT,
    status ENUM('disponivel', 'reservada', 'em_uso', 'manutencao') DEFAULT 'disponivel',
    FOREIGN KEY (id_estacao) REFERENCES Estacoes(id_estacao) ON DELETE SET NULL
);

CREATE TABLE Reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_bicicleta INT NOT NULL,
    id_estacao_retirada INT NOT NULL,
    id_estacao_devolucao INT,
    status_reserva ENUM('reservada', 'em_uso', 'finalizada', 'cancelada') DEFAULT 'reservada',
    horario_reserva DATETIME DEFAULT CURRENT_TIMESTAMP,
    horario_retirada DATETIME NULL,
    horario_devolucao DATETIME NULL,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE,
    FOREIGN KEY (id_bicicleta) REFERENCES Bicicletas(id_bicicleta) ON DELETE CASCADE,
    FOREIGN KEY (id_estacao_retirada) REFERENCES Estacoes(id_estacao) ON DELETE CASCADE,
    FOREIGN KEY (id_estacao_devolucao) REFERENCES Estacoes(id_estacao) ON DELETE SET NULL
);

CREATE TABLE Trajetorias (
    id_trajetoria INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva INT NOT NULL,
    id_bicicleta INT NOT NULL,
    id_usuario INT NOT NULL,
    latitude_inicio DECIMAL(10, 8) NOT NULL,
    longitude_inicio DECIMAL(11, 8) NOT NULL,
    latitude_fim DECIMAL(10, 8),
    longitude_fim DECIMAL(11, 8),
    horario_inicio DATETIME DEFAULT CURRENT_TIMESTAMP,
    horario_fim DATETIME NULL,
    FOREIGN KEY (id_reserva) REFERENCES Reservas(id_reserva) ON DELETE CASCADE,
    FOREIGN KEY (id_bicicleta) REFERENCES Bicicletas(id_bicicleta) ON DELETE CASCADE,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario) ON DELETE CASCADE
);

-- Inserindo Estações
INSERT INTO Estacoes (nome, latitude, longitude) VALUES
('Estação Central', 40.712776, -74.005974),
('Estação Parque', 40.735657, -73.991199),
('Estação Praia', 40.758896, -73.985130);

-- Inserindo Bicicletas
INSERT INTO Bicicletas (id_estacao, status) VALUES
(1, 'disponivel'),
(2, 'em_uso'),
(3, 'manutencao'),
(1, 'reservada'),
(2, 'disponivel');
