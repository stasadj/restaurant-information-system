package com.restaurant.backend.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restaurant.backend.dto.table.TableOrganizationDTO;
import com.restaurant.backend.validation.DTOValidator;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.FileWriter;
import java.io.IOException;

@Service
@AllArgsConstructor
@Transactional
public class TableService {

    private static final String PATH_TO_TABLES = "public/static/tables.json";

    private Resource loadTablesJsonFile() {
        return new ClassPathResource(PATH_TO_TABLES);
    }

    private final OrderService orderService;

    public Boolean setNewTableOrganization(TableOrganizationDTO dto) {
        DTOValidator.validate(dto);

        if (orderService.getHasTablesTaken()) {
        return false;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(dto);
            FileWriter writer = new FileWriter(loadTablesJsonFile().getFile());
            writer.write(json);
            writer.close();
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    public TableOrganizationDTO getTableOrganization() {
        try {
            return new ObjectMapper().readValue(loadTablesJsonFile().getFile(), TableOrganizationDTO.class);
        } catch (IOException e) {
            return null;
        }
    }
}
