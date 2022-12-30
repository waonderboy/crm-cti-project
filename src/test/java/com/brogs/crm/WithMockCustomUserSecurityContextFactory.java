package com.brogs.crm;

import com.brogs.crm.common.AccountPrincipal;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.Set;
import java.util.stream.Collectors;

public class WithMockCustomUserSecurityContextFactory
	implements WithSecurityContextFactory<WithMockCurrentAccount> {
	@Override
	public SecurityContext createSecurityContext(WithMockCurrentAccount customUser) {
		SecurityContext context = SecurityContextHolder.createEmptyContext();

		Set<SimpleGrantedAuthority> authorities = Set.of(customUser.role()).stream()
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toUnmodifiableSet());

		AccountPrincipal principal =
			new AccountPrincipal(customUser.identifier(), null, true, authorities);
		Authentication auth =
			new UsernamePasswordAuthenticationToken(principal, "password", principal.getAuthorities());
		context.setAuthentication(auth);
		return context;
	}
}