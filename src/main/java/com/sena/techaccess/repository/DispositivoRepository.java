package com.sena.techaccess.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.Dispositivo;
import com.sena.techaccess.model.Usuario;


@Repository
public interface DispositivoRepository extends JpaRepository<Dispositivo, Integer> {

	Dispositivo findByTipoD(String tipoD);

	List<Dispositivo> findByUsuario(Usuario usuario);

	Dispositivo findByMarca(String marca);

	Optional<Dispositivo> findByColor(String color);

}
