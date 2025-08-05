
package com.cargopro.service;

import com.cargopro.dto.FacilityDTO;
import com.cargopro.dto.LoadRequest;
import com.cargopro.dto.LoadResponse;
import com.cargopro.model.Facility;
import com.cargopro.model.Load;
import com.cargopro.model.LoadStatus;
import com.cargopro.repository.LoadRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class LoadService {


    @Autowired
    private LoadRepository loadRepository;

    public LoadResponse createLoad(LoadRequest request) {
        Load load = mapToEntity(request);
        load.setDatePosted(Instant.now());
        load.setStatus(LoadStatus.POSTED);
        Load saved = loadRepository.save(load);
        return mapToResponse(saved);
    }

    public Page<LoadResponse> getLoads(String shipperId, String truckType, String statusStr, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("datePosted").descending());
        LoadStatus status = null;
        if (statusStr != null) {
            status = LoadStatus.valueOf(statusStr.toUpperCase());
        }
        Page<Load> loads = loadRepository.findByFilters(shipperId, truckType, status, pageable);
        return loads.map(this::mapToResponse);
    }

    public LoadResponse getLoad(UUID id) {
        Load load = loadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Load not found with id: " + id));
        return mapToResponse(load);
    }

    @Transactional
    public LoadResponse updateLoad(UUID id, LoadRequest request) {
        Load load = loadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Load not found with id: " + id));
        load.setShipperId(request.getShipperId());
        load.setFacility(mapToFacility(request.getFacility()));
        load.setProductType(request.getProductType());
        load.setTruckType(request.getTruckType());
        load.setNoOfTrucks(request.getNoOfTrucks());
        load.setWeight(request.getWeight());
        load.setComment(request.getComment());
        return mapToResponse(load);
    }

    public void deleteLoad(UUID id) {
        if (!loadRepository.existsById(id)) {
            throw new EntityNotFoundException("Load not found with id: " + id);
        }
        loadRepository.deleteById(id);
    }

    // Mapping LoadRequest DTO to Load entity
    private Load mapToEntity(LoadRequest request) {
        Load load = new Load();
        load.setShipperId(request.getShipperId());
        load.setFacility(mapToFacility(request.getFacility()));
        load.setProductType(request.getProductType());
        load.setTruckType(request.getTruckType());
        load.setNoOfTrucks(request.getNoOfTrucks());
        load.setWeight(request.getWeight());
        load.setComment(request.getComment());
        return load;
    }

    // Mapping FacilityDTO to Facility entity
    private Facility mapToFacility(FacilityDTO dto) {
        Facility facility = new Facility();
        facility.setLoadingPoint(dto.getLoadingPoint());
        facility.setUnloadingPoint(dto.getUnloadingPoint());
        facility.setLoadingDate(dto.getLoadingDate());
        facility.setUnloadingDate(dto.getUnloadingDate());
        return facility;
    }

    // Mapping Load entity to LoadResponse DTO
    private LoadResponse mapToResponse(Load load) {
        LoadResponse response = new LoadResponse();
        response.setId(load.getId());
        response.setShipperId(load.getShipperId());

        FacilityDTO facilityDTO = new FacilityDTO();
        Facility facility = load.getFacility();
        if (facility != null) {
            facilityDTO.setLoadingPoint(facility.getLoadingPoint());
            facilityDTO.setUnloadingPoint(facility.getUnloadingPoint());
            facilityDTO.setLoadingDate(facility.getLoadingDate());
            facilityDTO.setUnloadingDate(facility.getUnloadingDate());
        }
        response.setFacility(facilityDTO);

        response.setProductType(load.getProductType());
        response.setTruckType(load.getTruckType());
        response.setNoOfTrucks(load.getNoOfTrucks());
        response.setWeight(load.getWeight());
        response.setComment(load.getComment());
        response.setDatePosted(load.getDatePosted());
        response.setStatus(load.getStatus());
        return response;
    }

    // Method to update Load status if needed externally (e.g. from BookingService)
    @Transactional
    public void updateLoadStatus(UUID loadId, LoadStatus status) {
        Load load = loadRepository.findById(loadId)
                .orElseThrow(() -> new EntityNotFoundException("Load not found with id: " + loadId));
        load.setStatus(status);
        loadRepository.save(load);
    }
}
