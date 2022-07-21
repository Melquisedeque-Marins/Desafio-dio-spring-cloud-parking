package com.mck.cloudparking.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.mck.cloudparking.dto.ParkingCreateDTO;
import com.mck.cloudparking.dto.ParkingDTO;
import com.mck.cloudparking.mapper.ParkingMapper;
import com.mck.cloudparking.model.Parking;
import com.mck.cloudparking.service.ParkingService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/parkings")
@Api(tags = "Parking controller")
public class ParkingController {
    
    private final ParkingService parkingService;
    private final ParkingMapper parkingMapper;
    
    public ParkingController(ParkingService parkingService, ParkingMapper parkingMapper) {
        this.parkingService = parkingService;
        this.parkingMapper = parkingMapper;
    }

    @GetMapping
    @ApiOperation("Find All Parkings")
    public List<ParkingDTO> findAll(){
        return parkingService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParkingDTO> findById(@PathVariable Long id){
        Parking parking = parkingService.findById(id);
        ParkingDTO parkingDTO = parkingMapper.parkingToDTO(parking);
        return ResponseEntity.ok().body(parkingDTO);
    }

    @PostMapping
    public ResponseEntity<ParkingDTO> create(@RequestBody ParkingCreateDTO parkingDTO){
        var parkingToSaveDTO = parkingService.create(parkingDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/parkings/{id}").buildAndExpand(parkingToSaveDTO.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Parking> delete(@PathVariable Long id){
        parkingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}



