package com.fonctionnels.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fonctionnels.dao.InvoiceRepository;
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
	
	@Override
	public Invoice addInvoice(Invoice invoice)
	{
		return invoiceRepository.save(invoice);
		
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

}
