package com.code.challenge.repository.impl;


import com.code.challenge.entity.Alumno;
import com.code.challenge.repository.AlumnoRepository;
import com.code.challenge.repository.db.ReactiveMongoCustomerRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@AllArgsConstructor
@RequiredArgsConstructor
public class AlumnoRepositoryImpl implements AlumnoRepository {

    @Autowired
    private ReactiveMongoCustomerRepository reactiveMongoCustomerRepository;

    @Override
    public Flux<Alumno> findByEstado(String estado) {
        return reactiveMongoCustomerRepository.findByEstado(estado);
    }

    @Override
    public Mono<Alumno> save(Alumno alumno) {
        return reactiveMongoCustomerRepository.save(alumno);
    }

    @Override
    public Mono<Boolean> existsByCode(String code) {
        return reactiveMongoCustomerRepository.existsByCode(code);
    }

}
