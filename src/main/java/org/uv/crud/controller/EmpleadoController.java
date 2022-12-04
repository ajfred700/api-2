package org.uv.crud.controller;

import org.uv.crud.dto.Mensaje;
import org.uv.crud.dto.EmpleadoDto;
import org.uv.crud.entity.Empleado;
import org.uv.crud.service.EmpleadoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/empleados")
@CrossOrigin(origins = "*")
public class EmpleadoController {

    @Autowired
    EmpleadoService empleadoService;

    @GetMapping("/lista")
    public ResponseEntity<List<Empleado>> list(){
        List<Empleado> list = empleadoService.list();
        return new ResponseEntity(list, HttpStatus.OK);
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<Empleado> getById(@PathVariable("id") int id){
        if(!empleadoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Empleado producto = empleadoService.getOne(id).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @GetMapping("/detailname/{nombre}")
    public ResponseEntity<Empleado> getByNombre(@PathVariable("nombre") String nombre){
        if(!empleadoService.existsByNombre(nombre))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        Empleado producto = empleadoService.getByNombre(nombre).get();
        return new ResponseEntity(producto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody EmpleadoDto empleadoDto){
        if(StringUtils.isBlank(empleadoDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(empleadoDto.getDireccion()==null)
            return new ResponseEntity(new Mensaje("la direccion no debe estar vacia"), HttpStatus.BAD_REQUEST);
        if(empleadoService.existsByNombre(empleadoDto.getNombre()))
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        Empleado producto = new Empleado(empleadoDto.getNombre(), empleadoDto.getDireccion());
        empleadoService.save(producto);
        return new ResponseEntity(new Mensaje("Empleado creado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id")int id, @RequestBody EmpleadoDto empleadoDto){
        if(!empleadoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        if(empleadoService.existsByNombre(empleadoDto.getNombre()) && empleadoService.getByNombre(empleadoDto.getNombre()).get().getId() != id)
            return new ResponseEntity(new Mensaje("ese nombre ya existe"), HttpStatus.BAD_REQUEST);
        if(StringUtils.isBlank(empleadoDto.getNombre()))
            return new ResponseEntity(new Mensaje("el nombre es obligatorio"), HttpStatus.BAD_REQUEST);
        if(empleadoDto.getDireccion()==null)
            return new ResponseEntity(new Mensaje("la direccion no debe estar vacia"), HttpStatus.BAD_REQUEST);

        Empleado producto = empleadoService.getOne(id).get();
        producto.setNombre(empleadoDto.getNombre());
        producto.setDireccion(empleadoDto.getDireccion());
        empleadoService.save(producto);
        return new ResponseEntity(new Mensaje("empleado actualizado"), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")int id){
        if(!empleadoService.existsById(id))
            return new ResponseEntity(new Mensaje("no existe"), HttpStatus.NOT_FOUND);
        empleadoService.delete(id);
        return new ResponseEntity(new Mensaje("empleado eliminado"), HttpStatus.OK);
    }


}
