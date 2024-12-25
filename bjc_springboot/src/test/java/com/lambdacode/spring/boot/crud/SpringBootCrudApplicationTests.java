package com.lambdacode.spring.boot.crud;

import com.lambdacode.spring.boot.crud.DAO.EstacaoDAO;
import com.lambdacode.spring.boot.crud.DAO.TrajectoryDAO;
import com.lambdacode.spring.boot.crud.entity.Estacao;
import com.lambdacode.spring.boot.crud.entity.Trajectory;
import com.lambdacode.spring.boot.crud.entity.User;
import com.lambdacode.spring.boot.crud.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
@SpringBootTest
class SpringBootCrudApplicationTests {
	@Autowired
	private TrajectoryDAO trajectoryDAO;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private EstacaoDAO estacaoDAO; // Injetando o EstacaoDAO

	@Test
	void addEstacao() {
		Estacao estacao = new Estacao();
		estacao.setNome("EstacaoProxima1");
		estacao.setLatitude(-8.8370);
		estacao.setLongitude(13.2885);

		// Agora o estacaoDAO será gerenciado pelo Spring e a dependência será injetada corretamente
		estacaoDAO.save(estacao);
	}
}
