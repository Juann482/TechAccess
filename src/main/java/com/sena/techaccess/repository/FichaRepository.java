package com.sena.techaccess.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.sena.techaccess.model.Ficha;
import com.sena.techaccess.model.Usuario;
import java.util.List;

@Repository
public interface FichaRepository extends JpaRepository<Ficha, Integer> {
    
    // Buscar fichas por nombre del programa
    Ficha findByNombrePrograma(String nombrePrograma);
    
    // Buscar fichas por número de ficha
    Ficha findByNumFicha(Integer numFicha);
    
    // Buscar usuarios asociados a una ficha
    List<Usuario> findUsuariosByIdFicha(Integer idFicha);
}