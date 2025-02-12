package com.code.challenge.repository.db;

import com.code.challenge.entity.Alumno;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveMongoCustomerRepository extends ReactiveMongoRepository<Alumno, String> {
    Flux<Alumno> findByEstado(String estado);
    Mono<Boolean> existsByCode(String code);
}
