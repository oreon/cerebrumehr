package com.oreon.cerebrum.web.action.patient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.Query;

import org.apache.commons.collections.MultiHashMap;
import org.apache.commons.collections.MultiMap;
import org.jboss.seam.Component;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.security.Restrict;
import org.primefaces.event.SelectEvent;
import org.witchcraft.seam.action.UserUtilAction;

import com.oreon.cerebrum.admission.Admission;
import com.oreon.cerebrum.charts.AppliedChart;
import com.oreon.cerebrum.charts.ChartProcedure;
import com.oreon.cerebrum.encounter.Encounter;
import com.oreon.cerebrum.patient.Patient;
import com.oreon.cerebrum.patient.TrackedVital;
import com.oreon.cerebrum.patient.VitalValue;

//@Scope(ScopeType.CONVERSATION)
@Name("patientAction")
public class PatientAction extends PatientActionBase implements
		java.io.Serializable {

	private ArrayList<BloodPressure> bpList;
	
	@In(create=true)
	private UserUtilAction userUtilAction;

	public PatientAction() {
		// TODO Auto-generated constructor stub

	}
	@Override
	public void setEntityId(Long entityId) {
		// TODO Auto-generated method stub
		if(instance == null)
			instance = createInstance();
		super.setEntityId(entityId);
		//userUtilAction.setCurrentPatient(getInstance());
	}
	
	public void handlePatientSelect(SelectEvent se){
		Patient patient = (Patient)se.getObject();
		FacesMessage facesMessage = new FacesMessage(FacesMessage.SEVERITY_INFO, "Selected patient " + patient.getDisplayName(), null);
		FacesContext.getCurrentInstance().addMessage(null, facesMessage);
		setEntityId( ((Patient) se.getObject()).getId());
	}

	public String getPatientInfo(){
		
		Patient current = getInstance();
		
		if(current == null  || isNew())
			return "No Patient Selected";
		else
			return current.getDetailedInfo();
		
	}
	
	
	@Override
	public boolean getHasCustomView() {
		// TODO Auto-generated method stub
		return true;
	}
	
	@Override
	public Patient getDefaultInstance() {
		return userUtilAction.getCurrentPatient();
	}
	
	
	

	/**
	 * Should calculate upcoming chart procedure dates
	 */
	public List<List<ChartProcedure>> viewUpcomingChartProcedures() {

		List<AppliedChart> charts = instance.getAppliedCharts();

		List<List<ChartProcedure>> procedures = new ArrayList<List<ChartProcedure>>();

		/*
		for (AppliedChart appliedChart : charts) {
			Date beginDate = appliedChart.getDateCreated();
			
			List<ChartProcedure> proceduresOfType = new ArrayList<ChartProcedure>();

			Set<ChartItem> items = appliedChart.getChart().getChartItems();
			
			
			for (ChartItem chartItem : items) {
				
				int duration = chartItem.getDuration();

				for (int i = 0; i < 5; i++) {
					ChartProcedure procedure = new ChartProcedure();
					DateTime dt = new DateTime();
					procedure.setDueDate(dt.plusMinutes((int) (duration * i
							* chartItem.getFrequencyPeriod().getValue())).toDate());
					procedure.setPatient(instance);
					
					procedure.setChartItem(chartItem);
					proceduresOfType.add(procedure);
				}
			}
			procedures.add(proceduresOfType);
		}
		*/
		return procedures;
	}

	public void initBloodPressureValues() {

		if (instance == null)
			load(0L);

		bpList = new ArrayList<BloodPressure>();

		List<Encounter> encounters = getInstance().getEncounters();
		for (Encounter encounter : encounters) {
			if (encounter.getVitals() != null
					&& encounter.getVitals().getSysBP() != null
					&& encounter.getVitals().getDiasBP() != null)
				bpList.add(new BloodPressure(encounter.getDateCreated(),
						encounter.getVitals().getSysBP(), encounter.getVitals()
								.getDiasBP()));
		}
	}

	public List<BloodPressure> getBloodPressureValues() {
		if (bpList == null)
			initBloodPressureValues();
		return bpList;
	}
	
	
	List<List<VitalValue>> listVitals;

	public List<List<VitalValue>> getTrackedVals() {
		
		if(listVitals != null)
			return listVitals;
		
		listVitals = new ArrayList<List<VitalValue>>();
		Map<TrackedVital, List<VitalValue>> mapVitals = new HashMap<TrackedVital, List<VitalValue>>();
		
		VitalValueListQuery vitalValueListQuery = (VitalValueListQuery) Component.getInstance("vitalValueList");

		List<VitalValue> vitals = vitalValueListQuery.getAllVitalValuesByPatient(getInstance());
		for (VitalValue vitalValue : vitals) {
			if (!mapVitals.containsKey(vitalValue.getTrackedVital())) {
				mapVitals.put(vitalValue.getTrackedVital(),
						new ArrayList<VitalValue>());
			}
			mapVitals.get(vitalValue.getTrackedVital()).add(vitalValue);
		}
		Set<TrackedVital> tvs = mapVitals.keySet();
		for (TrackedVital trackedVital : tvs) {
			listVitals.add(mapVitals.get(trackedVital));
		}
		return listVitals;
	}
	
	/* (non-Javadoc)
	 * @see org.witchcraft.seam.action.BaseAction#onRowSelect(org.primefaces.event.SelectEvent)
	 */
	@Override
	public void onRowSelect(SelectEvent event) throws Exception {
		FacesContext.getCurrentInstance().getExternalContext()
				.redirect("/admin/entities/patient/patient/editPatient.seam?patientId="
								+ getPatientId() + "&conversationPropagation=none");
		
	}
	
	class DateComparator implements Comparator<BloodPressure> {

		//@Override
		public int compare(BloodPressure bp1, BloodPressure bp2) {

			if (bp1.getDate().getTime() > bp2.getDate().getTime())
				return 1;
			return 0;
		}

	}
	
	public List<Object[]> findPatientsByGenderAge() {
		
		List<String> groupFields = new ArrayList<String>();
		
		groupFields.add("u.address.State");
		groupFields.add("u.address.city");
		groupFields.add("u.gender");
		
		
		
		if(groupFields.isEmpty())
			return null;
		
		StringBuilder sb = new StringBuilder();
		
		for (String grpField : groupFields) {
			sb.append(grpField + "," );
		}
		
		sb.deleteCharAt(sb.length() -1);

		String qry = "select  %s,  count(u.id) from Patient  u "
				+ " group by %s order by %s  ";
		
		qry = String.format(qry, sb.toString(), sb.toString(), sb.toString());
		
		String datepart = " where u.dateCreated > '20131001'";

		Query query = getEntityManager().createQuery(qry);

		List<Object[]> listExpected = query.getResultList();
		
		int interval = 10;
		
		List<String> results = new ArrayList<String>();
		
		//int count = results.get(0).length();
		
		MultiMap root = new MultiHashMap();
		int start = 1;
		
		for (Object[] objects : listExpected) {
			
			//root.put(key, value)
			List<String> vals = new ArrayList<String>();
			//vals.addAll(c)
			for (int i = start; i < objects.length; i++) {
				System.out.print(objects[i] + " ");
				vals.add(objects[i] + "");
			}
			
			root.put(objects[start - 1] + "", vals);
			
			System.out.println();
			
			start++;
		}
		/*
		for (Object[] object : listExpected) {
			if (object[0] == null)
				continue;
			int start = ((Integer) object[0]) * interval;
			int end = start + interval;
			System.out.println(start + "-" + end + " " + object[1] + " "
					+ object[2]);
		}*/

		return listExpected;

	}
	
	
	public static void main(String[] args) {
		List<String> groupFields = new ArrayList<String>();
		
		groupFields.add("u.ageInterval");
		groupFields.add("u.gender");
		
		if(groupFields.isEmpty())
			return ;
		
		StringBuilder sb = new StringBuilder();
		
		for (String grpField : groupFields) {
			sb.append(grpField + "," );
		}
		
		sb.deleteCharAt(sb.length() -1);

		String qry = "select  %1,  count(u.id) from Patient  u "
				+ " group by %2 ";
		
		qry = String.format(qry, sb.toString(), sb.toString());
		
		System.out.println(qry);
	}
	
	
	/**
	 * @return null if no admission else current admission
	 */
	public Admission getCurrentAdmission(){
		List<Admission> admissions = getInstance().getAdmissions();
		for (Admission admission : admissions) {
			if(admission.getDischargeDate() == null ){
				return admission;
			}
		}
		return null;
	}
	
	
	


}
