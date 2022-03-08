package com.fonctionnels.service;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.fonctionnels.entities.AppUser;

@Service
public class UserDetailsServiceImpl implements UserDetailsService
{
	@Autowired
	private IEbmsService iEbmsService;

	@Override
	public UserDetails loadUserByUsername(String username) 
	{
		AppUser appUser = null;
		  Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();;
		try
		{
			appUser = iEbmsService.loadUserByUsername(username);
			 
			appUser.getAppRoles().forEach(role -> {
				authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			}); 
		}
		catch (UsernameNotFoundException | NullPointerException e)
		{
			System.out.println("loadUserByUsername----------exception");
		}
		return new User(appUser.getUsername(), appUser.getPassword(), authorities);
	}

}
