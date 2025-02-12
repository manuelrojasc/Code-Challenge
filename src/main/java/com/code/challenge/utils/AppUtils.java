package com.code.challenge.utils;

import com.code.challenge.dto.AlumnoDto;
import com.code.challenge.entity.Alumno;
import org.springframework.beans.BeanUtils;

import javax.validation.Valid;

public class AppUtils {

    public static AlumnoDto entityToDto(Alumno alumnoEntity) {
        AlumnoDto alumnoDto = new AlumnoDto();
        BeanUtils.copyProperties(alumnoEntity, alumnoDto);
        return alumnoDto;
    }

    public static Alumno dtoToEntity(@Valid AlumnoDto alumnoDto) {
        Alumno alumnoEntity = new Alumno();
        BeanUtils.copyProperties(alumnoDto, alumnoEntity);
        return alumnoEntity;
    }
}
