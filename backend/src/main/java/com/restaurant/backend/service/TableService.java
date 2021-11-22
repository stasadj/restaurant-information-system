package com.restaurant.backend.service;

import java.io.FileWriter;
import java.io.IOException;

import javax.transaction.Transactional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.backend.dto.table.TableOrganizationDTO;

import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
@Transactional
public class TableService {

    // TODO: Check how to make this value cross platform for Linux
    private final String PATH_TO_STATIC_FILE = "C:/files/static/tables.json";

    private OrderService orderService;

    public Boolean setNewTableOrganization(TableOrganizationDTO dto) {
        if (orderService.getHasTablesTaken()) {
            return false;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(dto);
            FileWriter writer = new FileWriter(PATH_TO_STATIC_FILE);
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }
}
