package com.cargopro.controller;

import com.cargopro.dto.FacilityDTO;
import com.cargopro.dto.LoadRequest;
import com.cargopro.model.LoadStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class LoadControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateAndGetLoad() throws Exception {
        LoadRequest loadRequest = new LoadRequest();
        loadRequest.setShipperId("shipperA");

        FacilityDTO facilityDTO = new FacilityDTO();
        facilityDTO.setLoadingPoint("LocA");
        facilityDTO.setUnloadingPoint("LocB");
        facilityDTO.setLoadingDate(Instant.now());
        facilityDTO.setUnloadingDate(Instant.now().plusSeconds(3600));
        loadRequest.setFacility(facilityDTO);

        loadRequest.setProductType("Electronics");
        loadRequest.setTruckType("Open");
        loadRequest.setNoOfTrucks(1);
        loadRequest.setWeight(5000);
        loadRequest.setComment("Fragile");

        // Create Load
        String result = mockMvc.perform(post("/load")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loadRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(LoadStatus.POSTED.name()))
                .andReturn().getResponse().getContentAsString();

        // Extract created load ID
        String loadId = objectMapper.readTree(result).get("id").asText();

        // Get Load by ID and verify
        mockMvc.perform(get("/load/" + loadId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shipperId").value("shipperA"));
    }
}
