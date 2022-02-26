package com.fonctionnels.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fonctionnels.entities.Invoice;
import com.fonctionnels.service.IEbmsService;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RestController
@RequestMapping("/ebms_api")
public class EbmsController
{
	@Autowired
	IEbmsService ibEbmsService;
	
	@PostMapping(value = "/addInvoice")
	public ResponseEntity<Invoice> addInvoice(@RequestBody Invoice invoice)
	{
		Invoice invoiceSave = ibEbmsService.addInvoice(invoice);
		return new ResponseEntity<>(invoiceSave, HttpStatus.OK);
	}
	
	@PostMapping("/getInvoice")
	public ResponseEntity<Invoice> getInvoice(@RequestBody Invoice invoice2)
	{
		System.out.println("--------------"+invoice2.getInvoiceSignature());
		Invoice invoice = ibEbmsService.getInvoice("1111222333445");
		return new ResponseEntity<>(invoice, HttpStatus.OK);
	}
	
	@GetMapping(value = "/getInvoices")
	public ResponseEntity<List<Invoice>> getInvoices()
	{
		List<Invoice> invoices = ibEbmsService.getInvoices();
		return new ResponseEntity<>(invoices, HttpStatus.OK);
	}
}
