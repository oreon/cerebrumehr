package com.oreon.cerebrum.web.action.admission;

import java.util.List;

import javax.persistence.Query;

import org.junit.Test;

import com.oreon.cerebrum.patient.Patient;

public class AdmissionActionTest extends AdmissionActionTestBase {

	@Test
	public void getPatientsByGenderAge() {
		/*
		 * Session session = (Session) getEntityManager().getDelegate();
		 * 
		 * CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
		 * 
		 * CriteriaQuery<Patient> cq = cb.createQuery(Patient.class);
		 * Root<Patient> ptroot = cq.from(Patient.class); Expression
		 * minExpression = cb.count(ptroot);
		 * cq.groupBy(ptroot.get("dateOfBirth.year"));
		 * 
		 * TypedQuery<Object> typedQuery = entityManager.createQuery(cq); List
		 * listActual = typedQuery.getResultList(); 
		 */

		String qry = "select  concat ( u.ageInterval *10 , '-', (u.ageInterval +1 )*10 )  , u.gender,  count(u.id) from Patient  u where u.dateCreated > '20131001'"
				+ " group by u.ageInterval, u.gender  ";

		Query query = em.createQuery(qry);

		List<Object[]> listExpected = query.getResultList();
		
		int interval = 10;

		for (Object[] object : listExpected) {
			if (object[0] == null)
				continue;
			//int start = ((Integer) object[0]) * interval;
			//int end = start + interval;
			System.out.println(object[0] + " " + object[1] + " "
					+ object[2]);
		}

		// return null;

	}

}
