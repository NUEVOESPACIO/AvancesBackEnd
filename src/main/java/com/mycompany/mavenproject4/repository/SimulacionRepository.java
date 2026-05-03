package com.mycompany.mavenproject4.repository;

import com.mycompany.mavenproject4.entidades.Simulacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SimulacionRepository extends JpaRepository<Simulacion, Long> {
}

//Operaciones CRUD básicas:
//save(Simulacion simulacion) — Para guardar o actualizar una simulación.
//findById(Long id) — Para encontrar una simulación por su ID.
//findAll() — Para obtener todas las simulaciones.
//deleteById(Long id) — Para eliminar una simulación por su ID.
//delete(Simulacion simulacion) — Para eliminar una simulación específica.
//count() — Para contar cuántas simulaciones hay en la base de datos.
//Métodos de búsqueda sencillos:
//existsById(Long id) — Para comprobar si existe una simulación con el ID dado.
//findAll(Sort sort) — Para obtener todas las simulaciones ordenadas de acuerdo a los parámetros de orden que pases.
//Operaciones de paginación y ordenación:
//findAll(Pageable pageable) — Para obtener simulaciones en forma de página (útil cuando hay muchos registros y se necesita paginar).
//findAll(Sort sort) — Para obtener todos los registros pero ordenados de alguna manera.