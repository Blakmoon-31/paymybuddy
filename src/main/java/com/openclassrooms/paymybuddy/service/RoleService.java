package com.openclassrooms.paymybuddy.service;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.model.Role;
import com.openclassrooms.paymybuddy.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Collection<Role> getRoles() {
		return roleRepository.findAll();
	}

	public Optional<Role> getRoleByRoleId(int roleId) {
		return roleRepository.findByRoleId(roleId);
	}

	public Optional<Role> getRoleByRoleName(String roleName) {
		return roleRepository.findByRoleName(roleName);
	}

}
