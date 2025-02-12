package com.code.challenge.service;

import com.code.challenge.dto.AlumnoDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoService {
    Mono<AlumnoDto> save(AlumnoDto alumnoDto);
    Flux<AlumnoDto> findByEstado(String estado);
}
