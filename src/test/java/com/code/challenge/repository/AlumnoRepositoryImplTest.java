package com.code.challenge.repository;

import com.code.challenge.entity.Alumno;
import com.code.challenge.repository.db.ReactiveMongoCustomerRepository;
import com.code.challenge.repository.impl.AlumnoRepositoryImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlumnoRepositoryImplTest {

    @Mock
    private ReactiveMongoCustomerRepository reactiveMongoCustomerRepository;

    @InjectMocks
    private AlumnoRepositoryImpl alumnoRepository;

    @ParameterizedTest
    @ValueSource(strings = {"activo", "inactivo"})
    @DisplayName("return success when find by estado")
    void returnSuccessWhenSaveAlumnoByEstado(String state) {
        Alumno alumno = new Alumno();
        alumno.setCode("A001");
        alumno.setNombre("Juan");
        if(state.equals("activo")){
            alumno.setEstado("activo");
        }else {
            alumno.setEstado("inactivo");
        }

        when(reactiveMongoCustomerRepository.findByEstado(state)).thenReturn(Flux.just(alumno));

        Flux<Alumno> result = alumnoRepository.findByEstado(state);

        StepVerifier.create(result)
                .expectNextMatches(a -> a.getEstado().equals(state))
                .verifyComplete();
    }

    @Test
    @DisplayName("return empty when estado not found")
    void returnEmptyWhenEstadoNotFound() {
        when(reactiveMongoCustomerRepository.findByEstado("inactivo")).thenReturn(Flux.empty());

        Flux<Alumno> result = alumnoRepository.findByEstado("inactivo");

        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("return success when save alumno")
    void returnSuccessWhenSaveAlumno() {
        Alumno alumno = new Alumno();
        alumno.setCode("A001");
        alumno.setNombre("Juan");

        when(reactiveMongoCustomerRepository.save(any(Alumno.class))).thenReturn(Mono.just(alumno));

        Mono<Alumno> result = alumnoRepository.save(alumno);

        StepVerifier.create(result)
                .expectNextMatches(a -> a.getCode().equals("A001"))
                .verifyComplete();
    }

    @Test
    @DisplayName("return true when exist code")
    void returnTrueWhenExistCode() {
        when(reactiveMongoCustomerRepository.existsByCode("A001")).thenReturn(Mono.just(true));

        Mono<Boolean> result = alumnoRepository.existsByCode("A001");

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();
    }

}
