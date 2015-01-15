package org.witchcraft.seam.action;

import org.jboss.seam.annotations.In;
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

	@In(create=true)
	protected PatientAction patientAction;

}
