package com.example.demo;

import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class FailingJpaTest {

	@TestConfiguration
	static class TestConfig{
		@Primary
		@Bean
		MyRepository testBean(MyRepository real){
			var mock = mock(MyRepository.class,
					AdditionalAnswers.delegatesTo(real));
			when(mock.count()).thenReturn(100L);
			return mock;
		}
	}

	@Autowired
	MyRepository repository;

	@Test
	void verifyFails() {
		assertThat(repository.count()).isEqualTo(100); // so it is the mock
		verify(repository, times(1)).count();
	}

}
