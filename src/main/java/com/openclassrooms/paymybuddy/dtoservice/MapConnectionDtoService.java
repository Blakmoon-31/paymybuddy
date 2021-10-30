package com.openclassrooms.paymybuddy.dtoservice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.paymybuddy.dto.ConnectionDto;
import com.openclassrooms.paymybuddy.model.Connection;
import com.openclassrooms.paymybuddy.service.ConnectionService;

@Service
public class MapConnectionDtoService {

	@Autowired
	private ConnectionService connectionService;

	public Collection<ConnectionDto> getConnectionsDtoByUserId(int userid) {
		List<ConnectionDto> connectionsDtoList = new ArrayList<>();
		Collection<Connection> connectionsList = connectionService.getUserConnectionsByUserId(userid);

		for (Connection connection : connectionsList) {
			ConnectionDto connectionDto = new ConnectionDto();
			connectionDto.setUserId(connection.getUserConnection().getUserId());
			connectionDto.setConnectedUserId(connection.getConnectedUser().getUserId());
			connectionDto.setConnectedUserEmail(connection.getConnectedUser().getEmail());
			connectionDto.setConnectedUserFullName(
					connection.getConnectedUser().getFirstName() + " " + connection.getConnectedUser().getLastName());
			connectionDto.setConnectionName(connection.getNameConnectionUser());

			connectionsDtoList.add(connectionDto);
		}

		return connectionsDtoList;
	}

}
