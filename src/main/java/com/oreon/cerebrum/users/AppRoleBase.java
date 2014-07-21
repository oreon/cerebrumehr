package com.oreon.cerebrum.users;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.validator.constraints.Length;
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
public class AppRoleBase extends BaseEntity {
	private static final long serialVersionUID = -1618836781L;

	//@Unique(entityName = "com.oreon.cerebrum.users.AppRole", fieldName = "name")

	@NotNull
	@Length(min = 1, max = 250)
	@Column(name = "name", unique = true)
	@Field(index = Index.YES)
	@Analyzer(definition = "entityAnalyzer")
	protected String name

	;

	@ManyToMany(mappedBy = "appRoles")
	private Set<AppUser> appUsers = new HashSet<AppUser>();

	public void addAppUser(AppUser appUser) {

		this.appUsers.add(appUser);
	}

	@Transient
	public List<com.oreon.cerebrum.users.AppUser> getListAppUsers() {
		return new ArrayList<com.oreon.cerebrum.users.AppUser>(appUsers);
	}

	//JSF Friendly function to get count of collections
	public int getAppUsersCount() {
		return appUsers.size();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {

		return name;

	}

	public void setAppUsers(Set<AppUser> appUsers) {
		this.appUsers = appUsers;
	}

	public Set<AppUser> getAppUsers() {
		return appUsers;
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

		return listSearchableFields;
	}

	@Field(index = Index.YES, name = "searchData")
	@Analyzer(definition = "entityAnalyzer")
	public String getSearchData() {
		StringBuilder builder = new StringBuilder();

		builder.append(getName() + " ");

		return builder.toString();
	}

	/*
	
	 */

}
