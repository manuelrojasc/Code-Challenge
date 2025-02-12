package com.code.challenge.service.impl;


import com.code.challenge.dto.AlumnoDto;
import com.code.challenge.entity.Alumno;
import com.code.challenge.repository.AlumnoRepository;
import com.code.challenge.service.AlumnoService;
import com.code.challenge.utils.AppUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Service
@AllArgsConstructor
public class AlumnoServiceImpl implements AlumnoService {

    private AlumnoRepository alumnoRepository;
    
    @Override
    public Mono<AlumnoDto> save(AlumnoDto alumnoDto) {

        Alumno alumno = AppUtils.dtoToEntity(alumnoDto);
        return alumnoRepository.existsByCode(alumno.getCode())
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.error(new ResponseStatusException(
                                HttpStatus.CONFLICT,
                                "Ya existe un alumno con el código: " + alumno.getCode()
                        ));
                    } else {
                        return alumnoRepository.save(alumno).map(AppUtils::entityToDto);
                    }
                });
    }

    @Override
    public Flux<AlumnoDto> findByEstado(String estado) {
        return alumnoRepository.findByEstado(estado).map(AppUtils::entityToDto);
    }
}