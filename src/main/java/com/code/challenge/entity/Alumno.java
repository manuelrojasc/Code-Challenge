package com.code.challenge.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "alumno")
public class Alumno {
    @Id
    private String id;
    private String code;
    private String nombre;
    private String apellido;
    private String estado;
    private int edad;
}
