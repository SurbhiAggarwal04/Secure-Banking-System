package com.sbs.internetbanking.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbs.internetbanking.enums.AccountStatus;
import com.sbs.internetbanking.enums.Roles;
import com.sbs.internetbanking.model.Role;
import com.sbs.internetbanking.persistence.UserManager;

@Service("userDetailsService")
public class SBSUserDetailsService implements UserDetailsService {

	@Autowired
	UserManager userManager;

	@Transactional(readOnly = true)
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		com.sbs.internetbanking.model.User user = userManager.getUserByUserName(username);
		Set<GrantedAuthority> authorities = prepareUserAuthorityList(user);
		System.out.println(authorities);
		return prepareUserForAuthentication(user, authorities);
	}

	private User prepareUserForAuthentication(com.sbs.internetbanking.model.User user, Set<GrantedAuthority> authorities) {
		if (user.getAccountStatus().equals(AccountStatus.LOCKED.name())) {
			return new User(user.getUsername(), user.getPassword(), true, true, true, false, authorities);
		} else if(user.getAccountStatus().equals(AccountStatus.ACTIVE.name()))
			return new User(user.getUsername(), user.getPassword(), true, true, true, true, authorities);
		else{
			return new User(user.getUsername(), user.getPassword(), false, true, true, true, authorities);
		}
	}

	private Set<GrantedAuthority> prepareUserAuthorityList(com.sbs.internetbanking.model.User user) {
		String roleString = getRoleStringForRole(user);
		Set<GrantedAuthority> authList = new HashSet<GrantedAuthority>();
		authList.add(new SimpleGrantedAuthority(roleString));
		return authList;
	}

	private String getRoleStringForRole(com.sbs.internetbanking.model.User user) {
		Role role = userManager.getRoleName(user.getRole());
		if(role.getRoleName().equals(Roles.ROLE_CUSTOMER.name())){
			return Roles.ROLE_PREOTP_USER.name();
		}
		return role.getRoleName();
	}
}