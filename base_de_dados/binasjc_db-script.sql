
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
    status ENUM('DISPONIVEL', 'RESERVADA', 'EM_USO', 'MANUTENCAO') DEFAULT 'disponivel',
    FOREIGN KEY (id_estacao) REFERENCES Estacoes(id_estacao) ON DELETE SET NULL
);

CREATE TABLE Reservas (
    id_reserva INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    id_bicicleta INT NOT NULL,
    id_estacao_retirada INT NOT NULL,
    id_estacao_devolucao INT,
    status_reserva ENUM('RESERVADA', 'EM_USO', 'FINALIZADA', 'CANCELADA') DEFAULT 'reservada',
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
    latitude_inicio DECIMAL(10, 8) NOT NULL,
    longitude_inicio DECIMAL(11, 8) NOT NULL,
    latitude_fim DECIMAL(10, 8),
    longitude_fim DECIMAL(11, 8),
    horario_inicio DATETIME DEFAULT CURRENT_TIMESTAMP,
    horario_fim DATETIME NULL,
    FOREIGN KEY (id_reserva) REFERENCES Reservas(id_reserva) ON DELETE CASCADE
);

CREATE TABLE PontosIntermediarios (
    id_ponto INT AUTO_INCREMENT PRIMARY KEY,
    id_trajetoria INT NOT NULL,
    latitude DECIMAL(10, 8) NOT NULL,
    longitude DECIMAL(11, 8) NOT NULL,
    FOREIGN KEY (id_trajetoria) REFERENCES Trajetorias(id_trajetoria) ON DELETE CASCADE
);

-- Inserindo Estações
INSERT INTO Estacoes (nome, latitude, longitude) VALUES
('Estação Central', 40.712776, -74.005974),
('Estação Parque', 40.735657, -73.991199),
('Estação Praia', 40.758896, -73.985130);

-- Inserindo Bicicletas
INSERT INTO Bicicletas (id_estacao, status) VALUES
(1, 'DISPONIVEL'),
(2, 'em_uso'),
(3, 'manutencao'),
(1, 'reservada'),
(2, 'disponivel');

DELETE FROM Reservas
WHERE id_reserva = 3;

ALTER TABLE trajetorias 
MODIFY latitude_inicio DECIMAL(10, 8), 
MODIFY longitude_inicio DECIMAL(10, 8), 
MODIFY latitude_fim DECIMAL(10, 8), 
MODIFY longitude_fim DECIMAL(10, 8);

ALTER TABLE Trajetorias
DROP COLUMN horario_inicio;
