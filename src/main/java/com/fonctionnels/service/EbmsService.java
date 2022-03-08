package com.fonctionnels.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fonctionnels.dao.AppRoleRepository;
import com.fonctionnels.dao.AppUserRepository;
import com.fonctionnels.dao.InvoiceRepository;
import com.fonctionnels.entities.AppRole;
import com.fonctionnels.entities.AppUser;
import com.fonctionnels.entities.Invoice;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Transactional
@Service
public class EbmsService implements IEbmsService
{

	@Autowired
	InvoiceRepository invoiceRepository;
	
	@Autowired
	AppUserRepository appUserRepository;
	
	@Autowired
	AppRoleRepository appRoleRepository;
	
	@Autowired
	PasswordEncoder passEncoder;
	
	
	/*****invoice and item management****/
	
	@Override
	public Invoice addInvoice(Invoice invoice)
	{
		return invoiceRepository.save(invoice);
	}
	
	@Override
	public Invoice getTin(String tp_tin)
	{
		return invoiceRepository.findByTpTIN(tp_tin);
	}
	
	@Override
	public Invoice getInvoice(String invoice_signature)
	{
		Invoice invoice = invoiceRepository.findByInvoiceSignature(invoice_signature);
		
		return invoice;
	}

	@Override
	public List<Invoice> getInvoices()
	{
		return invoiceRepository.findAll();
	}

	/*****user management****/
	
	@Override
	public AppUser addAppUser(AppUser appUser)
	{
		String password = appUser.getPassword();
		appUser.setPassword(passEncoder.encode(password));
		return appUserRepository.save(appUser);
	}

	@Override
	public AppRole addAppRole(AppRole appRole)
	{
		return appRoleRepository.save(appRole);
	}

	@Override
	public void AddRoleToUser(String username, String roleName)
	{
		AppUser appUser = appUserRepository.findByUsername(username);
		AppRole appRole = appRoleRepository.findByRoleName(roleName);
		appUser.getAppRoles().add(appRole);
	}

	@Override
	public AppUser loadUserByUsername(String username)
	{  
		System.out.println("********EBMservice-loadUserByUsername********");
		AppUser appUser = appUserRepository.findByUsername(username);
		System.out.println("********EBMservice-loadUserByUsername********"+appUser);
		return appUser;
	}

	@Override
	public List<AppUser> listUsers()
	{
		return appUserRepository.findAll();
	}

	@Override
	public void addRolesToUser(Long id, List<AppRole> listRoles)
	{
		AppUser appUser = appUserRepository.findById(id).get();
		for (AppRole appRole : listRoles)
		{
			AppRole appRoleSearch = appRoleRepository.findByRoleName(appRole.getRoleName());
			appUser.getAppRoles().add(appRoleSearch);
		}		
	}

	

	 

	 
}
