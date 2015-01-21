package org.witchcraft.base.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import org.primefaces.model.DefaultUploadedFile;

@Embeddable
public class FileAttachment extends DefaultUploadedFile{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8339731595094765260L;

	@Column(length = 4194304)
	private byte[] data ;

	private String fileName;
	
	private String contentType;

	

	

	
	public String getContentType() {
		return this.contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return fileName;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public byte[] getData() {
		return data;
	}
	
	boolean getImage() {
		return contentType.startsWith("image"); 
	}

	@Override
	public byte[] getContents() {
		// TODO Auto-generated method stub
		return data;
	}

	@Override
	public String getFileName() {
		// TODO Auto-generated method stub
		return fileName;
	}

	public void seFiletName(String name) {
		this.fileName = name;
	}
	
}
