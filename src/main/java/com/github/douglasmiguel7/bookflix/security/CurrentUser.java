package com.github.douglasmiguel7.bookflix.security;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class CurrentUser implements UserDetails {

	private final String username;

	private final String password;

	private final Set<GrantedAuthority> roles;

	public CurrentUser(String username, String password, Collection<? extends GrantedAuthority> roles) {
		if (StringUtils.isAllBlank(username, password)) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}

		if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}

		this.username = username;
		this.password = password;
		this.roles = Collections.unmodifiableSet(sortAuthorities(roles));
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
		Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
		SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet(new AuthorityComparator());
		Iterator var2 = authorities.iterator();

		while(var2.hasNext()) {
			GrantedAuthority grantedAuthority = (GrantedAuthority)var2.next();
			Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
			sortedAuthorities.add(grantedAuthority);
		}

		return sortedAuthorities;
	}

	private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
		private static final long serialVersionUID = 530L;

		private AuthorityComparator() {
		}

		public int compare(GrantedAuthority g1, GrantedAuthority g2) {
			if (g2.getAuthority() == null) {
				return -1;
			} else {
				return g1.getAuthority() == null ? 1 : g1.getAuthority().compareTo(g2.getAuthority());
			}
		}
	}
}
