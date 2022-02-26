package com.fonctionnels.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
@Entity
public class Invoice
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@NonNull
	String invoice_number;
	@NonNull
	Date invoice_date;
	@NonNull
	String tp_type;
	@NonNull
	String tp_name;
	@NonNull
	String tp_TIN;
	@NonNull
	String tp_trade_number;
	@NonNull
	String tp_postal_number;
	@NonNull
	String tp_phone_number;
	@NonNull
	String tp_address_commune;
	@NonNull
	String tp_address_quartier;
	@NonNull
	String tp_address_avenue;
	@NonNull
	String tp_address_number;
	@NonNull
	String vat_taxpayer;
	@NonNull
	String ct_taxpayer;
	@NonNull
	String t1_taxpayer;
	@NonNull
	String tp_fiscal_center;
	@NonNull
	String tp_activity_sector;
	@NonNull
	String tp_legal_form;
	@NonNull
	String payment_type;
	@NonNull
	String customer_name;
	@NonNull
	String customer_TIN;
	@NonNull
	String customer_address;
	@NonNull
	String vat_customer_payer;
	@NonNull
	String invoice_type;
	@NonNull
	String cancelled_invoice_ref;
	@NonNull
	String invoiceSignature;
	@NonNull
	Date invoice_signature_date;
	
	@OneToMany(mappedBy = "invoice")
	List<Item> invoice_items;
	

}
