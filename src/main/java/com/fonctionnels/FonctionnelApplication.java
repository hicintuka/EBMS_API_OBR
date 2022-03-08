package com.fonctionnels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.fonctionnels.dao.AppUserRepository;
import com.fonctionnels.dao.InvoiceRepository;
import com.fonctionnels.entities.AppRole;
import com.fonctionnels.entities.AppUser;
import com.fonctionnels.entities.Invoice;

@SpringBootApplication
public class FonctionnelApplication implements CommandLineRunner
{

	@Autowired
	private InvoiceRepository invoiceRepository;

	@Autowired
	private AppUserRepository appUserRepository;

	public static void main(String[] args)
	{
		ApplicationContext ctx = SpringApplication.run(FonctionnelApplication.class, args);
		boolean b = new BCryptPasswordEncoder().matches("321",
				"$2a$10$IDT3SjVV9ivIA11/.bnK9euPGIUu2MlHzAS.jGXmEnR2Nzb.5nCzm");
		System.out.println("******"+b);
	}

	@Override
	public void run(String... args) throws Exception
	{
		 
	}

	@Bean
	public PasswordEncoder passwordEncoder()
	{
		return new BCryptPasswordEncoder();
	}
}
