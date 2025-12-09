package com.sena.techaccess.repository;

import java.time.LocalDate;
import java.util.List;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.sena.techaccess.model.Acceso;
import com.sena.techaccess.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByEmail(String email);

	Usuario findByNombre(String nombres);

	List<Usuario> findByRol(String rol);

	List<Usuario> findByEstadoCuenta(String estadoCuenta);

	Usuario findByDocumento(String documento);

	// En funcion con la base de datos
	@Query("""
			SELECT u FROM Usuario u
			WHERE (:nombre IS NULL OR :nombre = '' OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) 
			AND (:documento IS NULL OR :documento = '' OR u.documento LIKE CONCAT('%', :documento, '%'))
			AND (:rol IS NULL OR :rol = '' OR u.rol = :rol)
			AND (:estado IS NULL OR :estado = '' OR u.estadoCuenta = :estado)
			""")
	//LOWER ayuda a que el sistema pueda realiar la busqueda sin importar mayusculas o tildes, no usar con datos numericos
	//CONCAT sirve para encontrar coincidencias dentro de un texto, por ejemplo si busco Juan me busca todo los nombres que tengan relacion asi uno de ellos diga "Juancho"
	Page<Usuario> filtrarUsuarios(
			@Param("nombre") String nombre,
			@Param("documento") String documento,
			@Param("rol") String rol,
			@Param("estado") String estado,
			Pageable pageable);

	int countByEstadoCuenta(String string);

	int countByRol(String string);

	List<Usuario> findByFichaId(Integer fichaId);
	
	@Query("""
		    SELECT u FROM Usuario u
		    WHERE u.ficha.id = :fichaId
		      AND (:nombre IS NULL OR :nombre = '' OR LOWER(u.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
		      AND (:documento IS NULL OR :documento = '' OR u.documento LIKE CONCAT('%', :documento, '%'))
		      AND (:rol IS NULL OR :rol = '' OR u.rol = :rol)
		      AND (:estado IS NULL OR :estado = '' OR u.estadoCuenta = :estado)
		    """)
		Page<Usuario> filtrarUsuariosEnFicha(
		    @Param("fichaId") Integer fichaId,
		    @Param("nombre") String nombre,
		    @Param("documento") String documento,
		    @Param("rol") String rol,
		    @Param("estado") String estado,
		    Pageable pageable);


	Page<Usuario> findByFichaId(Integer fichaId, Pageable pageable);
	
	@Query("""			
			SELECT u.rol, COUNT(u) 
	        FROM Usuario u
	        WHERE u.estadoCuenta = 'Activo' 
	        GROUP BY u.rol""")
	List<Object[]> contarUsuariosActivosPorRol();

	List<Usuario> findByRolAndEstadoCuenta(String rol, String estadoCuenta);

	int countByRolAndEstadoCuenta(String rol, String estadoCuenta );


	// ================================================


}