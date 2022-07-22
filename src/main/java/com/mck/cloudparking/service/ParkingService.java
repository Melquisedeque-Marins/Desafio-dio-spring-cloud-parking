package com.mck.cloudparking.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.mck.cloudparking.dto.ParkingCreateDTO;
import com.mck.cloudparking.dto.ParkingDTO;
import com.mck.cloudparking.mapper.ParkingMapper;
import com.mck.cloudparking.model.Parking;
import com.mck.cloudparking.repository.ParkingRepository;
import com.mck.cloudparking.service.exceptions.ObjectNotFoundException;

@Service
public class ParkingService {

    private final ParkingMapper parkingMapper;
    
    private final ParkingRepository parkingRepository;
  
    public ParkingService(ParkingMapper parkingMapper, ParkingRepository parkingRepository) {
        this.parkingMapper = parkingMapper;
        this.parkingRepository = parkingRepository;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<ParkingDTO> findAll(){
        List<Parking> listParking = parkingRepository.findAll();
        List<ParkingDTO> listDTO = parkingMapper.toParkingDTOList(listParking);
        return listDTO;
    }
    @Transactional(readOnly = true)
    public Parking findById(Long id){
       Optional<Parking> parking = parkingRepository.findById(id);
       return parking.orElseThrow(() -> new ObjectNotFoundException("O Objeto com o id " + id + " não foi encontrado"));
       }

    @Transactional
    public ParkingDTO create(ParkingCreateDTO parkingDTO) {
        Parking parkingToSave = parkingMapper.ParkingCreateToParking(parkingDTO);
        parkingToSave.setEntryDate(LocalDateTime.now());
        Parking parking = parkingRepository.save(parkingToSave);
        ParkingDTO result = parkingMapper.parkingToDTO(parking);  
        return result;
    }

    @Transactional
    public void delete(Long id) {
        Parking parking = findById(id);
        parkingRepository.delete(parking);
    }

    @Transactional
    public ParkingDTO update(ParkingCreateDTO dto, Long id) {
        var parkingSaved = findById(id);
        parkingSaved.setColor(dto.getColor());
        parkingSaved.setState(dto.getState());
        parkingSaved.setLicense(dto.getLicense());
        parkingSaved.setModel(dto.getModel());
      //Parking parkintToUpdate = parkingMapper.ParkingCreateToParking(dto);
        parkingRepository.save(parkingSaved);
        return parkingMapper.parkingToDTO(parkingSaved);
    }

    @Transactional
    public Parking checkOut(Long id){
        Parking parking = findById(id);
        parking.setExitDate(LocalDateTime.now());
        parking.setBill(ParkingCheckOut.getBill(parking));
        parkingRepository.save(parking);
        return parking;
    }
}
