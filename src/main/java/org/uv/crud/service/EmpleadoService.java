package org.uv.crud.service;

import org.uv.crud.entity.Empleado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import org.uv.crud.repository.EmpleadoRepository;

@Service
@Transactional
public class EmpleadoService {

    @Autowired
    EmpleadoRepository productoRepository;

    public List<Empleado> list(){
        return productoRepository.findAll();
    }

    public Optional<Empleado> getOne(int id){
        return productoRepository.findById(id);
    }

    public Optional<Empleado> getByNombre(String nombre){
        return productoRepository.findByNombre(nombre);
    }

    public void  save(Empleado producto){
        productoRepository.save(producto);
    }

    public void delete(int id){
        productoRepository.deleteById(id);
    }

    public boolean existsById(int id){
        return productoRepository.existsById(id);
    }

    public boolean existsByNombre(String nombre){
        return productoRepository.existsByNombre(nombre);
    }
}
