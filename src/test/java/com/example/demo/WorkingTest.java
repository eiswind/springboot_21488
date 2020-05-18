package com.example.demo;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.AdditionalAnswers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@SpringBootTest
class WorkingTest {

    @TestConfiguration
    static class TestConfig {
        @Primary
        @Bean
        MyInterface testBean(MyInterface real) {
            var mock = mock(MyInterface.class, AdditionalAnswers.delegatesTo(real));
            when(mock.getValue()).thenReturn("Mocked");
            return mock;
        }
    }

    @Autowired
    MyInterface bean;

    @Test
    void contextLoads() {
        assertThat(bean.getValue()).isEqualTo("Mocked");
        verify(bean, times(1)).getValue();
    }

}
