package desafio.pitang.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import desafio.pitang.model.Usuario;

@Repository
public interface UsuarioDAO extends JpaRepository<Usuario, Long> {

	@Query("SELECT user FROM Usuario user  WHERE user.email=(:pEmail)")
	Usuario findUsuarioByEmail(@Param("pEmail") String email);

	@Query("SELECT user FROM Usuario user  WHERE user.senha=(:pSenha)")
	Usuario findUsuarioByPassword(@Param("pSenha") String password);

}
