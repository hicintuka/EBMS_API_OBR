package com.fonctionnels.service;

import java.util.List;

import com.fonctionnels.entities.AppRole;
import com.fonctionnels.entities.AppUser;
import com.fonctionnels.entities.Invoice;

public interface IEbmsService
{
	public Invoice addInvoice(Invoice invoice);
	public Invoice getInvoice(String invoice_signature);
	public Invoice getTin(String tp_tin);
	public List<Invoice> getInvoices();
	
  /*****/
	public AppUser addAppUser(AppUser appUser);
	public AppRole addAppRole(AppRole appRole);
	public void AddRoleToUser(String username, String roleName);
	public AppUser loadUserByUsername(String username);
	public List<AppUser> listUsers();
	public void addRolesToUser(Long id, List<AppRole> listRoles);
}
