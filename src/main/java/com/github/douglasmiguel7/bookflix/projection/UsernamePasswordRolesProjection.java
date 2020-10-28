package com.github.douglasmiguel7.bookflix.projection;

import com.github.douglasmiguel7.bookflix.domain.RoleDomain;

import java.util.Set;

public interface UsernamePasswordRolesProjection {

	String getUsername();

	String getPassword();

	Set<RoleDomain> getRoles();

}
