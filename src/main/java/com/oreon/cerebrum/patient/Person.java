
/**
 * This file is generated by Witchcraftmda.
 * DO NOT MODIFY any changes will be overwritten with the next run of the code generator.
 *
 */

package com.oreon.cerebrum.patient;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Person extends PersonBase implements java.io.Serializable {
	private static final long serialVersionUID = -1283387220L;
	
	public ContactDetails getContactDetails() {
		if(contactDetails == null){
			contactDetails = new ContactDetails();
		}
		return contactDetails;
	}
}
