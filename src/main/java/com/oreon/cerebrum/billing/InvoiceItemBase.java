package com.oreon.cerebrum.billing;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.witchcraft.base.entity.BaseEntity;

//Impl 

/**
 * This file is generated by Witchcraftmda.
 * DO NOT MODIFY any changes will be overwritten with the next run of the code generator.
 *
 */

/**
 * 
 *
 */

@MappedSuperclass
public class InvoiceItemBase extends BaseEntity {
	private static final long serialVersionUID = -843792017L;

	@Column(unique = false)
	protected Integer units = 1

	;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "service_id", nullable = false, updatable = true)
	protected Service service

	;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "invoice_id", nullable = false, updatable = true)
	protected Invoice invoice

	;

	@NotNull
	@Column(name = "appliedPrice", unique = false)
	protected BigDecimal appliedPrice

	;

	@Transient
	protected BigDecimal total

	;

	public void setUnits(Integer units) {
		this.units = units;
	}

	public Integer getUnits() {

		return units;

	}

	public void setService(Service service) {
		this.service = service;
	}

	public Service getService() {

		return service;

	}

	public void setInvoice(Invoice invoice) {
		this.invoice = invoice;
	}

	public Invoice getInvoice() {

		return invoice;

	}

	public void setAppliedPrice(BigDecimal appliedPrice) {
		this.appliedPrice = appliedPrice;
	}

	public BigDecimal getAppliedPrice() {

		return appliedPrice;

	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public BigDecimal getTotal() {

		try {
			return appliedPrice.multiply(new BigDecimal(units));
		} catch (Exception e) {

			return null;

		}

	}

	@Transient
	public String getDisplayName() {
		try {
			return units + "";
		} catch (Exception e) {
			return "Exception - " + e.getMessage();
		}
	}

	//Empty setter , needed for richfaces autocomplete to work 
	public void setDisplayName(String name) {
	}

	/** This method is used by hibernate full text search - override to add additional fields
	 * @see org.witchcraft.model.support.BaseEntity#retrieveSearchableFieldsArray()
	 */
	@Override
	public List<String> listSearchableFields() {
		List<String> listSearchableFields = new ArrayList<String>();
		listSearchableFields.addAll(super.listSearchableFields());

		return listSearchableFields;
	}

	@Field(index = Index.YES, name = "searchData")
	@Analyzer(definition = "entityAnalyzer")
	public String getSearchData() {
		StringBuilder builder = new StringBuilder();

		if (getService() != null)
			builder.append("service:" + getService().getDisplayName() + " ");

		if (getInvoice() != null)
			builder.append("invoice:" + getInvoice().getDisplayName() + " ");

		return builder.toString();
	}

	/*
	<param name="serviceId" value="#{serviceId}" />
	<param name="invoiceId" value="#{invoiceId}" />
	
	 */

}
