package com.oreon.cerebrum.prescription;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Transient;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.witchcraft.base.entity.BaseEntity;

import com.oreon.cerebrum.ProjectUtils;

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
public class PrescriptionBase extends BaseEntity {
	private static final long serialVersionUID = -1190166836L;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JoinColumn(name = "prescription_ID", nullable = false)
	@OrderBy("id DESC")
	private Set<PrescriptionItem> prescriptionItems = new HashSet<PrescriptionItem>();

	public void addPrescriptionItem(PrescriptionItem prescriptionItem) {

		prescriptionItem.setPrescription((Prescription) this);

		this.prescriptionItems.add(prescriptionItem);
	}

	@Transient
	public List<com.oreon.cerebrum.prescription.PrescriptionItem> getListPrescriptionItems() {
		return new ArrayList<com.oreon.cerebrum.prescription.PrescriptionItem>(
				prescriptionItems);
	}

	//JSF Friendly function to get count of collections
	public int getPrescriptionItemsCount() {
		return prescriptionItems.size();
	}

	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "patient_id", nullable = false, updatable = true)
	protected com.oreon.cerebrum.patient.Patient patient

	;

	@Lob
	@Column(unique = false)
	@Field(index = Index.YES)
	@Analyzer(definition = "entityAnalyzer")
	protected String directivesForPatient

	;

	@Column(unique = false)
	protected Boolean active

	;

	@Transient
	@Field(index = Index.YES)
	@Analyzer(definition = "entityAnalyzer")
	protected String drugs

	;

	public void setPrescriptionItems(Set<PrescriptionItem> prescriptionItems) {
		this.prescriptionItems = prescriptionItems;
	}

	public Set<PrescriptionItem> getPrescriptionItems() {
		return prescriptionItems;
	}

	public void setPatient(com.oreon.cerebrum.patient.Patient patient) {
		this.patient = patient;
	}

	public com.oreon.cerebrum.patient.Patient getPatient() {

		return patient;

	}

	public void setDirectivesForPatient(String directivesForPatient) {
		this.directivesForPatient = directivesForPatient;
	}

	public String getDirectivesForPatient() {

		return directivesForPatient;

	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getActive() {

		return active;

	}

	public void setDrugs(String drugs) {
		this.drugs = drugs;
	}

	public String getDrugs() {

		try {
			return ProjectUtils.getPrescripitonItems(this);
		} catch (Exception e) {

			return "";

		}

	}

	@Transient
	public String getDisplayName() {
		try {
			return drugs;
		} catch (Exception e) {
			return "Exception - " + e.getMessage();
		}
	}

	@Transient
	public String getDirectivesForPatientAbbreviated() {
		try {
			return org.apache.commons.lang.WordUtils.abbreviate(
					directivesForPatient.trim(), 100, 200, "...");
		} catch (Exception e) {
			return directivesForPatient != null ? directivesForPatient : "";
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

		listSearchableFields.add("directivesForPatient");

		listSearchableFields.add("drugs");

		listSearchableFields.add("prescriptionItems.strength");

		listSearchableFields.add("prescriptionItems.remarks");

		listSearchableFields.add("prescriptionItems.brandName");

		return listSearchableFields;
	}

	@Field(index = Index.YES, name = "searchData")
	@Analyzer(definition = "entityAnalyzer")
	public String getSearchData() {
		StringBuilder builder = new StringBuilder();

		builder.append(getDirectivesForPatient() + " ");

		builder.append(getDrugs() + " ");

		if (getPatient() != null)
			builder.append("patient:" + getPatient().getDisplayName() + " ");

		for (BaseEntity e : prescriptionItems) {
			builder.append(e.getDisplayName() + " ");
		}

		return builder.toString();
	}

	/*
	<param name="patientId" value="#{patientId}" />
	
	 */

}
