package com.cargopro.controller;

import com.cargopro.dto.BookingRequest;
import com.cargopro.dto.FacilityDTO;
import com.cargopro.dto.LoadRequest;
import com.cargopro.model.BookingStatus;
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
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // First, create a load, then create a booking for that load
    @Test
    public void testBookingProcess_andStatusTransition() throws Exception {
        // 1. Create load
        LoadRequest loadRequest = new LoadRequest();
        loadRequest.setShipperId("shipperB");

        FacilityDTO facility = new FacilityDTO();
        facility.setLoadingPoint("CityX");
        facility.setUnloadingPoint("CityY");
        facility.setLoadingDate(Instant.now());
        facility.setUnloadingDate(Instant.now().plusSeconds(7200));
        loadRequest.setFacility(facility);

        loadRequest.setProductType("Food");
        loadRequest.setTruckType("Closed");
        loadRequest.setNoOfTrucks(2);
        loadRequest.setWeight(2000);
        loadRequest.setComment("Keep refrigerated");

        String loadResult = mockMvc.perform(post("/load")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loadRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(LoadStatus.POSTED.name()))
                .andReturn().getResponse().getContentAsString();

        String loadId = objectMapper.readTree(loadResult).get("id").asText();

        // 2. Create booking for the load
        BookingRequest bookingRequest = new BookingRequest();
        bookingRequest.setLoadId(java.util.UUID.fromString(loadId));
        bookingRequest.setTransporterId("transporterX");
        bookingRequest.setProposedRate(1500);
        bookingRequest.setComment("Will deliver by evening");

        String bookingResult = mockMvc.perform(post("/booking")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(bookingRequest)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.status").value(BookingStatus.PENDING.name()))
                .andReturn().getResponse().getContentAsString();

        // 3. Verify load status has changed to BOOKED
        mockMvc.perform(get("/load/" + loadId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value(LoadStatus.BOOKED.name()));
    }
}
