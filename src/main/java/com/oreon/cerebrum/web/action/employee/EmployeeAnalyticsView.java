package com.oreon.cerebrum.web.action.employee;
/* CAN BE MODIFIED - GENERATED ONCE */

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.jboss.seam.framework.EntityQuery;

import org.witchcraft.seam.action.BaseQuery;

import org.witchcraft.base.entity.Range;

import org.primefaces.model.SortOrder;
import org.witchcraft.seam.action.EntityLazyDataModel;
import org.primefaces.model.LazyDataModel;
import java.util.Map;

import org.jboss.seam.annotations.Observer;

import java.math.BigDecimal;
import javax.faces.model.DataModel;

import org.jboss.seam.annotations.security.Restrict;

import org.jboss.seam.annotations.In;
import org.jboss.seam.Component;

@Name("employeeAnalyticsView")
@Scope(ScopeType.PAGE)
public class EmployeeAnalyticsView
		extends
			org.witchcraft.seam.action.BaseAnalyticsViewBean
		implements
			java.io.Serializable {

	private String[] groupByFlds = {

	};

	@Override
	public String[] getGroupByFlds() {
		return groupByFlds;
	}

	@Override
	public String getEntityName() {

		return "Employee";
	}

}
