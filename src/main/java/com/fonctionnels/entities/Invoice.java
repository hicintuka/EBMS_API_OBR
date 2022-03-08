package com.fonctionnels.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonFormatTypes;
import com.sun.istack.NotNull;

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
	@NotEmpty
	@NotNull
	String invoice_number;
	@NonNull
//	@NotEmpty
//	@NotNull
//	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	Date invoice_date;
	@NonNull
	@NotEmpty
	@NotNull
	String tp_type;
	@NonNull
	@NotEmpty
	@NotNull
	String tp_name;
	@NonNull
	@NotEmpty
	@NotNull
	String tpTIN;
	@NonNull
	@NotEmpty
	@NotNull
	String tp_trade_number;
	@NonNull
	@NotEmpty
	@NotNull
	String tp_postal_number;
	@NonNull
	@NotEmpty
	@NotNull
	String tp_phone_number;
	@NonNull
	@NotEmpty
	@NotNull
	String tp_address_commune;
	@NonNull
	@NotEmpty
	@NotNull
	String tp_address_quartier;
	@NonNull
	@NotEmpty
	@NotNull
	String tp_address_avenue;
	@NonNull
	@NotEmpty
	@NotNull
	String tp_address_number;
	@NonNull
	@NotEmpty
	@NotNull
	String vat_taxpayer;
	@NonNull
	@NotEmpty
	@NotNull
	String ct_taxpayer;
	@NonNull
	@NotEmpty
	@NotNull
	String t1_taxpayer;
	@NonNull
	@NotEmpty
	@NotNull
	String tp_fiscal_center;
	@NonNull
	@NotEmpty
	@NotNull
	String tp_activity_sector;
	@NonNull
	@NotEmpty
	@NotNull
	String tp_legal_form;
	@NonNull
	@NotEmpty
	@NotNull
	String payment_type;
	@NonNull
	@NotEmpty
	@NotNull
	String customer_name;
	@NonNull
	@NotEmpty
	@NotNull
	String customer_TIN;
	@NonNull
	@NotEmpty
	@NotNull
	String customer_address;
	@NonNull
	@NotEmpty
	@NotNull
	String vat_customer_payer;
	@NonNull
	@NotEmpty
	@NotNull
	String invoice_type;
	@NonNull
	@NotEmpty
	@NotNull
	String cancelled_invoice_ref;
	@NonNull
	@NotEmpty
	@NotNull
	String invoiceSignature;
	@NonNull
//	@NotEmpty
//	@NotNull
//	@Temporal(TemporalType.DATE)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	Date invoice_signature_date;
	
	@OneToMany(mappedBy = "invoice")
	List<Item> invoice_items;
	

}
