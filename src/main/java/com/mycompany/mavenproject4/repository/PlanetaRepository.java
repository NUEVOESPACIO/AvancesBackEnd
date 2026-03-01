/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mavenproject4.repository;

import com.mycompany.mavenproject4.entidades.Planeta;
import org.springframework.data.jpa.repository.JpaRepository;


 public interface PlanetaRepository extends JpaRepository<Planeta, Long> {
}
