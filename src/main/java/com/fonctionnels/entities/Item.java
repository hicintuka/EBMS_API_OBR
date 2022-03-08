package com.fonctionnels.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

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
public class Item
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@NonNull
	@NotEmpty
	@NotNull
	String item_designation;
	@NonNull
	@NotEmpty
	@NotNull
	double item_quantity;
	@NonNull
	@NotEmpty
	@NotNull
	double item_price;
	@NonNull
	@NotEmpty
	@NotNull
	double item_ct;
	@NonNull
	@NotEmpty
	@NotNull
	double item_t1;
	@NonNull
	@NotEmpty
	@NotNull
	double item_price_nvat;
	@NonNull
	@NotEmpty
	@NotNull
	double vat;
	@NonNull
	@NotEmpty
	@NotNull
	double item_price_wvat;
	@NonNull
	@NotEmpty
	@NotNull
	double item_total_amount;
	
	@JsonProperty(access = Access.WRITE_ONLY)
	@ManyToOne
	@JoinColumn(name = "id_invoice")
	Invoice invoice;
}
