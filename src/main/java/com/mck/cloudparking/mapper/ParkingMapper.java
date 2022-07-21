package com.mck.cloudparking.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import com.mck.cloudparking.dto.ParkingCreateDTO;
import com.mck.cloudparking.dto.ParkingDTO;
import com.mck.cloudparking.model.Parking;

@Component
public class ParkingMapper {

    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    public ParkingDTO parkingToDTO(Parking parking){
        return MODEL_MAPPER.map(parking, ParkingDTO.class);
    }

    public Parking toParking(ParkingDTO parkingDTO){
        return MODEL_MAPPER.map(parkingDTO, Parking.class);
    }

    public List<ParkingDTO> toParkingDTOList(List<Parking> parkingList){
        return parkingList.stream().map(this::parkingToDTO).collect(Collectors.toList());
    }

    public Parking ParkingCreateToParking(ParkingCreateDTO dto){
        return MODEL_MAPPER.map(dto, Parking.class);
    }
    
}
