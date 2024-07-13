package org.facturacion.facturacion.repositories;

import org.facturacion.facturacion.domain.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Usuario findByUsuarioAndContrasena(String usuario, String contrasena);
}
