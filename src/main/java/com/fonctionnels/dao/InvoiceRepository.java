package com.fonctionnels.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.fonctionnels.entities.Invoice;

@RepositoryRestResource
public interface InvoiceRepository extends JpaRepository<Invoice, Long>
{
   public Invoice findByInvoiceSignature(String invoice_signature);
}
