package org.witchcraft.seam.action;

import java.lang.reflect.Field;

import org.jboss.seam.annotations.In;
//import org.primefaces.component.chart.
import org.witchcraft.base.entity.BaseEntity;

import com.oreon.cerebrum.web.action.patient.PatientAction;

/**
 * The base action class - contains common persistence related methods, also
 * contains comment related functionality
 * 
 * @author jsingh
 * 
 * @param <T>
 */
public abstract class BaseAction<T extends BaseEntity> extends WCBaseAction<T> {

	//@In(create=true)
	//protected PatientAction patientAction;
	
	
	

}
