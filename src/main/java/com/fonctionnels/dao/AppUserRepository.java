package com.fonctionnels.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.fonctionnels.entities.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long>
{
   public AppUser findByUsername(String username);
}
