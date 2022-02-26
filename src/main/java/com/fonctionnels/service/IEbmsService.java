package com.fonctionnels.service;

import java.util.List;

import com.fonctionnels.entities.Invoice;

public interface IEbmsService
{
	public Invoice addInvoice(Invoice invoice);
	public Invoice getInvoice(String invoice_signature);
	public List<Invoice> getInvoices();
}
