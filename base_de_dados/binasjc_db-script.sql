-- Criar a base de dados
CREATE DATABASE binasjc_db;

-- Usar a base de dados
USE binasjc_db;

-- Tabela Usuarios
CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    pontuacao INT DEFAULT 0
);

-- Tabela Estacoes
CREATE TABLE Estacoes (
    id_estacao INT AUTO_INCREMENT PRIMARY KEY,
    nome_estacao VARCHAR(255) NOT NULL,
    localizacao VARCHAR(255) NOT NULL
);

-- Tabela Bikes
CREATE TABLE Bikes (
    id_bicicleta INT AUTO_INCREMENT PRIMARY KEY,
    id_estacao INT NOT NULL,
    disponivel BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_estacao) REFERENCES Estacoes(id_estacao)
);

-- Tabela Trajetorias
CREATE TABLE Trajetorias (
    id_trajetoria INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT NOT NULL,
    data_hora_inicio DATETIME NOT NULL,
    data_hora_fim DATETIME,
    distancia FLOAT,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);

-- Tabela Transferencias_de_Pontos
CREATE TABLE Transferencias_de_Pontos (
    id_transferencia INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario_envia INT NOT NULL,
    id_usuario_recebe INT NOT NULL,
    quantidade_pontos INT NOT NULL,
    data_hora DATETIME NOT NULL,
    FOREIGN KEY (id_usuario_envia) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_usuario_recebe) REFERENCES Usuarios(id_usuario)
);
