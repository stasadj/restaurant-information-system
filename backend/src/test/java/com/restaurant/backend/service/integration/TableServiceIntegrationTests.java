package com.restaurant.backend.service.integration;

import com.restaurant.backend.dto.table.*;
import com.restaurant.backend.service.TableService;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@TestPropertySource(locations = "classpath:test.properties")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TableServiceIntegrationTests {
    @Autowired
    private TableService tableService;

    @Test @Order(1)
    public void testSet() {
        TableDTO table1 = new TableDTO(1, 45, new TableSizeDTO(50, 100), 10, new TablePositionDTO(40, 60));
        TableDTO table2 = new TableDTO(2, 0, new TableSizeDTO(50, 100), 10, new TablePositionDTO(140, 160));
        RoomDTO room = new RoomDTO("Room1", List.of(table1, table2));
        TableOrganizationDTO organizationDTO = new TableOrganizationDTO(List.of(room));

        tableService.setNewTableOrganization(organizationDTO);
    }

    @Test @Order(2)
    public void testGet() {
        TableOrganizationDTO organization = tableService.getTableOrganization();
        assertNotNull(organization);

        assertEquals(1, organization.getRooms().size());
        assertEquals(2, organization.getRooms().get(0).getTables().size());
    }
}
