package com.oreon.cerebrum.users.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.List;
import java.util.Date;

import org.witchcraft.base.entity.FileAttachment;

import org.witchcraft.base.dto.BaseDto;
import java.math.BigDecimal;

public class AppRoleDto extends BaseDto {

	protected String name;

	private List<AppUserDto> appUsersDto = new ArrayList<AppUserDto>();

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAppUsers(List<AppUserDto> appUsersDto) {
		this.appUsersDto = appUsersDto;
	}
	public List<AppUserDto> getAppUsers() {
		return appUsersDto;
	}

}
