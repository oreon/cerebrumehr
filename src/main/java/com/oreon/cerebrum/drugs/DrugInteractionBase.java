package com.oreon.cerebrum.drugs;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

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
public class DrugInteractionBase extends BaseEntity {
	private static final long serialVersionUID = -1546590855L;

	@Lob
	@Column(unique = false)
	@Field(index = Index.YES)
	@Analyzer(definition = "entityAnalyzer")
	protected String description

	;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "drug_id", nullable = false, updatable = true)
	protected Drug drug

	;

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "interactingDrug_id", nullable = false, updatable = true)
	protected Drug interactingDrug

	;

	@Column(unique = false)
	protected InteractionSeverity severity

	;

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {

		return description;

	}

	public void setDrug(Drug drug) {
		this.drug = drug;
	}

	public Drug getDrug() {

		return drug;

	}

	public void setInteractingDrug(Drug interactingDrug) {
		this.interactingDrug = interactingDrug;
	}

	public Drug getInteractingDrug() {

		return interactingDrug;

	}

	public void setSeverity(InteractionSeverity severity) {
		this.severity = severity;
	}

	public InteractionSeverity getSeverity() {

		return severity;

	}

	@Transient
	public String getDisplayName() {
		try {
			return description + "";
		} catch (Exception e) {
			return "Exception - " + e.getMessage();
		}
	}

	@Transient
	public String getDescriptionAbbreviated() {
		try {
			return org.apache.commons.lang.WordUtils.abbreviate(description
					.trim(), 100, 200, "...");
		} catch (Exception e) {
			return description != null ? description : "";
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

		listSearchableFields.add("description");

		return listSearchableFields;
	}

	@Field(index = Index.YES, name = "searchData")
	@Analyzer(definition = "entityAnalyzer")
	public String getSearchData() {
		StringBuilder builder = new StringBuilder();

		builder.append(getDescription() + " ");

		if (getDrug() != null)
			builder.append("drug:" + getDrug().getDisplayName() + " ");

		if (getInteractingDrug() != null)
			builder.append("interactingDrug:"
					+ getInteractingDrug().getDisplayName() + " ");

		return builder.toString();
	}

	/*
	<param name="drugId" value="#{drugId}" />
	<param name="interactingDrugId" value="#{interactingDrugId}" />
	
	 */

}
