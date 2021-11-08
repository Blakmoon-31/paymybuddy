package com.openclassrooms.paymybuddy.controller;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.openclassrooms.paymybuddy.model.Role;
import com.openclassrooms.paymybuddy.service.RoleService;

@Controller
public class RoleController {

	@Autowired
	private RoleService roleService;

	public Collection<Role> getRoles() {
		return roleService.getRoles();
	}

	public Optional<Role> getRoleByRoleId(int roleId) {
		return roleService.getRoleByRoleId(roleId);
	}

	public Optional<Role> getRoleByRoleName(String roleName) {
		return roleService.getRoleByRoleName(roleName);
	}
}
