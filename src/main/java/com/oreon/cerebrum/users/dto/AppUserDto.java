package com.oreon.cerebrum.users.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.Date;

import org.witchcraft.base.entity.FileAttachment;

import org.witchcraft.base.dto.BaseDto;
import java.math.BigDecimal;

public class AppUserDto extends BaseDto {

	protected String userName;

	protected String password;

	protected Boolean enabled;

	private List<AppRoleDto> appRolesDto = new ArrayList<AppRoleDto>();

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setEnabled(Boolean enabled) {
		this.enabled = enabled;
	}

	public Boolean getEnabled() {
		return enabled;
	}

	public void setAppRoles(List<AppRoleDto> appRolesDto) {
		this.appRolesDto = appRolesDto;
	}
	public List<AppRoleDto> getAppRoles() {
		return appRolesDto;
	}

}
