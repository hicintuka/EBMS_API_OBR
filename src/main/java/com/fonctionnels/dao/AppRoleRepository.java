package com.fonctionnels.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fonctionnels.entities.AppRole;

public interface AppRoleRepository extends JpaRepository<AppRole, Long>
{
	public AppRole findByRoleName(String rolename);
}
