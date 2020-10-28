package com.github.douglasmiguel7.bookflix.repository;

import com.github.douglasmiguel7.bookflix.domain.UserDomain;
import com.github.douglasmiguel7.bookflix.projection.UsernamePasswordRolesProjection;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserDomain, Long> {

	UsernamePasswordRolesProjection findUsernamePasswordRolesByUsername(String username);

	UserDomain findByUsername(String username);

}
