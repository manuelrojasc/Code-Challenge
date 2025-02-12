package com.code.challenge.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlumnoDto {
    private String id;
    private String code;
    private String nombre;
    private String apellido;
    private String estado;
    private int edad;
}
