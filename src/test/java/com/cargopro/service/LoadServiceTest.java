package com.cargopro.service;

import com.cargopro.dto.FacilityDTO;
import com.cargopro.dto.LoadRequest;
import com.cargopro.model.Load;
import com.cargopro.model.LoadStatus;
import com.cargopro.repository.LoadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.data.domain.*;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoadServiceTest {

    @InjectMocks
    private LoadService loadService;

    @Mock
    private LoadRepository loadRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private LoadRequest createLoadRequest() {
        LoadRequest request = new LoadRequest();
        request.setShipperId("shipper1");
        FacilityDTO facility = new FacilityDTO();
        facility.setLoadingPoint("PointA");
        facility.setUnloadingPoint("PointB");
        facility.setLoadingDate(Instant.parse("2025-08-06T10:00:00Z"));
        facility.setUnloadingDate(Instant.parse("2025-08-07T10:00:00Z"));
        request.setFacility(facility);
        request.setProductType("Electronics");
        request.setTruckType("Flatbed");
        request.setNoOfTrucks(2);
        request.setWeight(1000.0);
        request.setComment("Handle with care");
        return request;
    }

    @Test
    void createLoad_shouldReturnCreatedLoadResponse() {
        LoadRequest request = createLoadRequest();

        Load savedLoad = new Load();
        savedLoad.setId(UUID.randomUUID());
        savedLoad.setShipperId(request.getShipperId());
        savedLoad.setStatus(LoadStatus.POSTED);
        savedLoad.setDatePosted(Instant.now());

        when(loadRepository.save(any(Load.class))).thenReturn(savedLoad);

        var response = loadService.createLoad(request);

        assertNotNull(response.getId());
        assertEquals(request.getShipperId(), response.getShipperId());
        assertEquals(LoadStatus.POSTED, response.getStatus());
        verify(loadRepository, times(1)).save(any(Load.class));
    }

    @Test
    void getLoad_shouldReturnLoadResponse_whenLoadExists() {
        UUID id = UUID.randomUUID();
        Load load = new Load();
        load.setId(id);
        load.setShipperId("shipper1");
        load.setStatus(LoadStatus.POSTED);

        when(loadRepository.findById(id)).thenReturn(Optional.of(load));

        var response = loadService.getLoad(id);

        assertNotNull(response);
        assertEquals(id, response.getId());
        assertEquals("shipper1", response.getShipperId());
    }

    @Test
    void getLoad_shouldThrowException_whenLoadNotFound() {
        UUID id = UUID.randomUUID();
        when(loadRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> loadService.getLoad(id));
    }

    @Test
    void updateLoad_shouldUpdateAndReturnResponse() {
        UUID id = UUID.randomUUID();
        LoadRequest request = createLoadRequest();

        Load existingLoad = new Load();
        existingLoad.setId(id);
        existingLoad.setShipperId("oldShipper");

        when(loadRepository.findById(id)).thenReturn(Optional.of(existingLoad));

        var response = loadService.updateLoad(id, request);

        assertEquals(request.getShipperId(), response.getShipperId());
        verify(loadRepository, times(1)).findById(id);
    }

    @Test
    void deleteLoad_shouldDeleteSuccessfully() {
        UUID id = UUID.randomUUID();
        when(loadRepository.existsById(id)).thenReturn(true);

        loadService.deleteLoad(id);

        verify(loadRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteLoad_shouldThrowException_whenLoadDoesNotExist() {
        UUID id = UUID.randomUUID();
        when(loadRepository.existsById(id)).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> loadService.deleteLoad(id));
    }
}
