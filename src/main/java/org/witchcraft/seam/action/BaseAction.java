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

	@In(create=true)
	protected PatientAction patientAction;
	
	private Long patientId;
	
	//LineChart

	public Long getPatientId() {
		return patientId;
	}

	public void setPatientId(Long patientId) {
		try {
			
			//Meth m = getInstance().getClass().getMethod("setPatient" );
			Class c = getInstance().getClass();
			Class sc = c.getSuperclass();
			
			Field patient = getInstance().getClass().getSuperclass().getField("patient");
			patient.set(getInstance(), patientAction.getInstance());
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.patientId = patientId;
	}
	
	

}
