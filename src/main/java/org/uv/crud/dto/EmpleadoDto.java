package org.uv.crud.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class EmpleadoDto {

    @NotBlank
    private String nombre;
    @Min(0)
    private String direccion;

    public EmpleadoDto() {
    }

    public EmpleadoDto(@NotBlank String nombre, @Min(0) Float precio) {
        this.nombre = nombre;
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

     public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
}
