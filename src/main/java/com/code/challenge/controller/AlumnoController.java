package com.code.challenge.controller;

import com.code.challenge.dto.AlumnoDto;
import com.code.challenge.service.AlumnoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;


@RestController
@RequestMapping("/alumnos")
@AllArgsConstructor
public class AlumnoController {

    private AlumnoService alumnoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> saveAlumno(@RequestBody AlumnoDto alumnoDto) {

        if(!Objects.nonNull(alumnoDto.getCode())){
            return Mono.error(new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "el codigo no debe ser vacio o nulo "));
        }
        return alumnoService.save(alumnoDto).then();
    }

    @GetMapping()
    public Flux<AlumnoDto> getAlumnosActivos(@RequestParam String estado) {
        return alumnoService.findByEstado(estado);
    }
}