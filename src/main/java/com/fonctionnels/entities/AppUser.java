package com.fonctionnels.entities;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import javax.persistence.CascadeType;
import javax.persistence.FetchType;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class AppUser
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
   Long id;
	@NotEmpty
	@Size(min = 1)
   String username;
	@JsonProperty(access = Access.WRITE_ONLY)
   String password;
   
   @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   Collection<AppRole> appRoles; 
}
