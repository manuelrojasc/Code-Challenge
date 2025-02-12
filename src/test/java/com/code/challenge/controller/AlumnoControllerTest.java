package com.code.challenge.controller;

import com.code.challenge.dto.AlumnoDto;
import com.code.challenge.service.AlumnoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AlumnoControllerTest {

    @Mock
    private AlumnoService alumnoService;

    @InjectMocks
    private AlumnoController alumnoController;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(alumnoController).build();
    }

    @Test
    @DisplayName("return success when create alumno")
    void returnSuccessWhenCreateAlumno() {

        when(alumnoService.save(any(AlumnoDto.class))).thenReturn(Mono.empty());

        webTestClient.post()
                .uri("/alumnos")
                .bodyValue(getalumnoDto())
                .exchange()
                .expectStatus().isCreated();
    }

    @Test
    @DisplayName("return bad request when code is null")
    void returnBadRequestWhenCodeIsNull() {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setCode(null);
        webTestClient.post()
                .uri("/alumnos")
                .bodyValue(alumnoDto)
                .exchange()
                .expectStatus().isBadRequest();
    }

    @ParameterizedTest
    @ValueSource(strings = {"activo","inactivo"})
    @DisplayName("return success when get alumnos by estado")
    void returnSuccessWhenGetAlumnosByEstado(String estado) {
        AlumnoDto alumnoDto = getalumnoDto();
        if (estado.equals("activo")) {
            alumnoDto.setEstado("activo");
        } else {
            alumnoDto.setEstado("inactivo");
        }

        when(alumnoService.findByEstado(estado)).thenReturn(Flux.just(alumnoDto));

        webTestClient.get()
                .uri("/alumnos?estado="+estado)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AlumnoDto.class)
                .hasSize(1)
                .contains(alumnoDto);
    }

    public AlumnoDto getalumnoDto() {
        AlumnoDto alumnoDto = new AlumnoDto();
        alumnoDto.setCode("A001");
        alumnoDto.setNombre("Juan");
        alumnoDto.setApellido("Pérez");
        alumnoDto.setEdad(25);
        alumnoDto.setEstado("activo");
        return alumnoDto;
    }

}