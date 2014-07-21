package com.oreon.cerebrum.facility;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.MappedSuperclass;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
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
public class FacilityBase extends BaseEntity {
	private static final long serialVersionUID = -840267156L;

	@Column(unique = false)
	@Field(index = Index.YES)
	@Analyzer(definition = "entityAnalyzer")
	protected String name

	;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@JoinColumn(name = "facility_ID", nullable = true)
	@OrderBy("id DESC")
	private Set<Ward> wards = new HashSet<Ward>();

	public void addWard(Ward ward) {

		ward.setFacility((Facility) this);

		this.wards.add(ward);
	}

	@Transient
	public List<com.oreon.cerebrum.facility.Ward> getListWards() {
		return new ArrayList<com.oreon.cerebrum.facility.Ward>(wards);
	}

	//JSF Friendly function to get count of collections
	public int getWardsCount() {
		return wards.size();
	}

	@Column(unique = false)
	protected Boolean isResidential

	;

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {

		return name;

	}

	public void setWards(Set<Ward> wards) {
		this.wards = wards;
	}

	public Set<Ward> getWards() {
		return wards;
	}

	public void setIsResidential(Boolean isResidential) {
		this.isResidential = isResidential;
	}

	public Boolean getIsResidential() {

		return isResidential;

	}

	@Transient
	public String getDisplayName() {
		try {
			return name;
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

		listSearchableFields.add("name");

		listSearchableFields.add("wards.name");

		return listSearchableFields;
	}

	@Field(index = Index.YES, name = "searchData")
	@Analyzer(definition = "entityAnalyzer")
	public String getSearchData() {
		StringBuilder builder = new StringBuilder();

		builder.append(getName() + " ");

		for (BaseEntity e : wards) {
			builder.append(e.getDisplayName() + " ");
		}

		return builder.toString();
	}

	/*
	
	 */

}
