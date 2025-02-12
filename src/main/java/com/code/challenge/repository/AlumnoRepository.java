package com.code.challenge.repository;

import com.code.challenge.entity.Alumno;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface AlumnoRepository {
    Flux<Alumno> findByEstado(String estado);
    Mono<Alumno> save(Alumno alumno);
    Mono<Boolean> existsByCode(String code);
}
