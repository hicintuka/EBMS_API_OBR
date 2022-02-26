package com.fonctionnels;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.fonctionnels.dao.InvoiceRepository;
import com.fonctionnels.entities.Invoice;


@SpringBootApplication
public class FonctionnelApplication implements CommandLineRunner
{
	 
	@Autowired
	private InvoiceRepository invoiceRepository;
	
	public static void main(String[] args)
	{
		ApplicationContext ctx = SpringApplication.run(FonctionnelApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception
	{
	}

}
