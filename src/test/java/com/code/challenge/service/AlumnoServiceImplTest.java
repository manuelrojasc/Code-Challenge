package com.code.challenge.service;

import com.code.challenge.dto.AlumnoDto;
import com.code.challenge.entity.Alumno;
import com.code.challenge.repository.AlumnoRepository;
import com.code.challenge.service.impl.AlumnoServiceImpl;
import com.code.challenge.utils.AppUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static com.code.challenge.utils.AppUtils.dtoToEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlumnoServiceImplTest {

    @Mock
    private AlumnoRepository alumnoRepository;

    @InjectMocks
    private AlumnoServiceImpl alumnoService;

    @Test
    @DisplayName("return success when save")
    void returnSuccessWhenSave() {
        when(alumnoRepository.existsByCode("A001")).thenReturn(Mono.just(false));
        when(alumnoRepository.save(any(Alumno.class))).thenReturn(Mono.just(dtoToEntity(getAlumnoDto())));

        Mono<AlumnoDto> result = alumnoService.save(getAlumnoDto());

        StepVerifier.create(result)
                .expectNextMatches(dto -> dto.getCode().equals("A001"))
                .verifyComplete();
    }

    @Test
    @DisplayName("return conflict when code already exists ")
    void returnConflictWhenCodeAlreadyExists() {

        when(alumnoRepository.existsByCode("A001")).thenReturn(Mono.just(true));

        Mono<AlumnoDto> result = alumnoService.save(getAlumnoDto());

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof ResponseStatusException &&
                        ((ResponseStatusException) throwable).getStatus() == HttpStatus.CONFLICT)
                .verify();
    }

    @Test
    @DisplayName("return success when get alumnos by estado")
    void returnSuccessWhenGetAlumnosByEstado() {
        when(alumnoRepository.findByEstado("activo")).thenReturn(Flux.just(getAlumno()));

        Flux<AlumnoDto> result = alumnoService.findByEstado("activo");

        StepVerifier.create(result)
                .expectNextMatches(dto -> dto.getEstado().equals("activo"))
                .verifyComplete();
    }

    @Test
    @DisplayName("return empty when get alumnos by estado is inactivo")
    void returnEmptyWhenGetAlumnosByEstadoIsInactivo() {
        when(alumnoRepository.findByEstado("inactivo")).thenReturn(Flux.empty());
        Flux<AlumnoDto> result = alumnoService.findByEstado("inactivo");
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

    public AlumnoDto getAlumnoDto(){
        AlumnoDto alumno = new AlumnoDto();
        alumno.setCode("A001");
        alumno.setNombre("Juan");
        alumno.setApellido("Pérez");
        alumno.setEstado("activo");
        alumno.setEdad(25);;
        return alumno;
    }

    public Alumno getAlumno(){
        Alumno alumno = new Alumno();
        alumno.setCode("A001");
        alumno.setNombre("Juan");
        alumno.setApellido("Pérez");
        alumno.setEstado("activo");
        alumno.setEdad(25);;
        return alumno;
    }
}