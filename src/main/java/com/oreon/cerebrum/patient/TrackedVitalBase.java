package com.oreon.cerebrum.patient;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Date;

import com.google.common.base.Objects;

import javax.persistence.*;
import org.hibernate.validator.*;

import com.google.common.base.Objects;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import org.apache.solr.analysis.LowerCaseFilterFactory;
import org.apache.solr.analysis.SnowballPorterFilterFactory;
import org.apache.solr.analysis.StandardTokenizerFactory;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.Filters;
import org.hibernate.annotations.Cascade;

import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.IndexedEmbedded;

import org.hibernate.annotations.Filter;

import org.hibernate.validator.constraints.*;
import javax.validation.constraints.*;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import org.jboss.seam.annotations.Name;

import org.witchcraft.model.support.audit.Auditable;

import org.witchcraft.utils.*;

import org.witchcraft.base.entity.FileAttachment;
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
public class TrackedVitalBase extends BaseEntity {
	private static final long serialVersionUID = 700076891L;

	@NotNull
	@Length(min = 1, max = 250)
	@Column(unique = false)
	@Field(index = Index.YES)
	@Analyzer(definition = "entityAnalyzer")
	protected String name

	;

	@Column(unique = false)
	protected Double minVal

	;

	@Column(unique = false)
	protected Double maxVal

	;

	@ManyToMany(mappedBy = "trackedVitals")
	private Set<com.oreon.cerebrum.charts.ChartItem> chartItems = new HashSet<com.oreon.cerebrum.charts.ChartItem>();

	public void addChartItem(com.oreon.cerebrum.charts.ChartItem chartItem) {

		this.chartItems.add(chartItem);
	}

	@Transient
	public List<com.oreon.cerebrum.charts.ChartItem> getListChartItems() {
		return new ArrayList<com.oreon.cerebrum.charts.ChartItem>(chartItems);
	}

	@Transient
	public String getListChartItemsAsString() {
		StringBuilder result = new StringBuilder();

		List<com.oreon.cerebrum.charts.ChartItem> tempList = getListChartItems();
		int count = 0;
		for (com.oreon.cerebrum.charts.ChartItem chartItem : tempList) {
			++count;
			result.append(chartItem.getDisplayName());
			if (count < tempList.size())
				result.append(", ");
		}

		return result.toString();
	}

	//JSF Friendly function to get count of collections
	public int getChartItemsCount() {
		return chartItems.size();
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {

		return name;

	}

	public void setMinVal(Double minVal) {
		this.minVal = minVal;
	}

	public Double getMinVal() {

		return minVal;

	}

	public void setMaxVal(Double maxVal) {
		this.maxVal = maxVal;
	}

	public Double getMaxVal() {

		return maxVal;

	}

	public void setChartItems(
			Set<com.oreon.cerebrum.charts.ChartItem> chartItems) {
		this.chartItems = chartItems;
	}

	public Set<com.oreon.cerebrum.charts.ChartItem> getChartItems() {
		return chartItems;
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
