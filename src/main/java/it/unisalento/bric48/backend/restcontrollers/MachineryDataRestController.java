package it.unisalento.bric48.backend.restcontrollers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import it.unisalento.bric48.backend.domain.MachineryData;
import it.unisalento.bric48.backend.dto.MachineryDataDTO;
import it.unisalento.bric48.backend.repositories.MachineryDataRepository;


@RestController
@CrossOrigin
@RequestMapping("/data")
public class MachineryDataRestController {

    @Autowired
    MachineryDataRepository machineryDataRepository;

    // Add a new machinery data
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public MachineryDataDTO addMachineryData(@RequestBody MachineryDataDTO machineryDataDTO) {

        MachineryData newMachineryData = new MachineryData();
        newMachineryData.setMserial(machineryDataDTO.getMserial());
        newMachineryData.setType(machineryDataDTO.getType());
        newMachineryData.setValue(machineryDataDTO.getValue());
        newMachineryData.setDescription(machineryDataDTO.getDescription());
        newMachineryData.setIsSolved(machineryDataDTO.getIsSolved());
        newMachineryData.setTimestamp(machineryDataDTO.getTimestamp());
        
        newMachineryData = machineryDataRepository.save(newMachineryData);

        machineryDataDTO.setId(newMachineryData.getId());

        return machineryDataDTO;
    }


    //Get machineryData by type
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECURITY_MANAGER')")
    @RequestMapping(value="/find/{type}", method= RequestMethod.GET)
    public List<MachineryDataDTO> getMachineryDataByType(@PathVariable("type") String type) {

        List<MachineryDataDTO> data = new ArrayList<>();

        for(MachineryData machineryData : machineryDataRepository.findByType(type)) {

            MachineryDataDTO machineryDataDTO = new MachineryDataDTO();

            machineryDataDTO.setId(machineryData.getId());
            machineryDataDTO.setType(machineryData.getType());
            machineryDataDTO.setValue(machineryData.getValue());
            machineryDataDTO.setDescription(machineryData.getDescription());
            machineryDataDTO.setIsSolved(machineryData.getIsSolved());
            machineryDataDTO.setTimestamp(machineryData.getTimestamp());
            machineryDataDTO.setMserial(machineryData.getMserial());

            data.add(machineryDataDTO);
        }

        return data;

    }


    //Get machineryData by type and mserial and isSolved
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECURITY_MANAGER')")
    @RequestMapping(value = "/find/machinery", method = RequestMethod.GET)
    public List<MachineryDataDTO> getMachineryDataByTypeAndMserialAndIsSolved(@RequestParam("type") String type, @RequestParam("mserial") String mserial, @RequestParam(value = "isSolved", required = false) String isSolved){

        List<MachineryDataDTO> data = new ArrayList<>();
        List<MachineryData> machineryDataList;

        if (isSolved == null) {
            machineryDataList = machineryDataRepository.findByTypeAndMserial(type, mserial);
        } else {
            machineryDataList = machineryDataRepository.findByTypeAndMserialAndIsSolved(type, mserial, isSolved);
        }

        for (MachineryData machineryData : machineryDataList) {
            MachineryDataDTO machineryDataDTO = new MachineryDataDTO();
            machineryDataDTO.setId(machineryData.getId());
            machineryDataDTO.setType(machineryData.getType());
            machineryDataDTO.setValue(machineryData.getValue());
            machineryDataDTO.setDescription(machineryData.getDescription());
            machineryDataDTO.setIsSolved(machineryData.getIsSolved());
            machineryDataDTO.setTimestamp(machineryData.getTimestamp());
            machineryDataDTO.setMserial(machineryData.getMserial());
            data.add(machineryDataDTO);
        }

        return data;
    }


    //Get all data
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECURITY_MANAGER')")
    @RequestMapping(value="/getAll", method= RequestMethod.GET)
    public List<MachineryDataDTO> getAll() {

        List<MachineryDataDTO> dataArray = new ArrayList<>();

        for(MachineryData data : machineryDataRepository.findAll()) {

            MachineryDataDTO dataDTO = new MachineryDataDTO();

            dataDTO.setId(data.getId());
            dataDTO.setMserial(data.getMserial());
            dataDTO.setType(data.getType());
            dataDTO.setDescription(data.getDescription());
            dataDTO.setIsSolved(data.getIsSolved());
            dataDTO.setValue(data.getValue());
            dataDTO.setTimestamp(data.getTimestamp());

            dataArray.add(dataDTO);
        }

        return dataArray;
    }


    //Get data from-to filtered
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECURITY_MANAGER')")
    @RequestMapping(value="/getDataFromTo", method= RequestMethod.GET)
    public List<MachineryDataDTO> getFromTo(@RequestParam("from") String from, 
                                            @RequestParam("to") String to, 
                                            @RequestParam(value= "mserial", required = false) String mserial,
                                            @RequestParam(value= "type", required = false) String type,
                                            @RequestParam(value= "startDate", required = false) String startDate,
                                            @RequestParam(value= "endDate", required = false) String endDate) {

        
        int i = Integer.parseInt(from);
        int j = Integer.parseInt(to);

        List<MachineryDataDTO> dataArray = new ArrayList<>();

        List<MachineryData> allData = machineryDataRepository.findAll();

        Collections.reverse(allData);

        for(MachineryData data : allData) {

            MachineryDataDTO dataDTO = new MachineryDataDTO();

            dataDTO.setId(data.getId());
            dataDTO.setMserial(data.getMserial());
            dataDTO.setType(data.getType());
            dataDTO.setDescription(data.getDescription());
            dataDTO.setIsSolved(data.getIsSolved());
            dataDTO.setValue(data.getValue());
            dataDTO.setTimestamp(data.getTimestamp());

            dataArray.add(dataDTO);

            if(mserial != ""){
                dataArray = dataArray.stream()
                .filter(obj -> obj.getMserial().toLowerCase().contains(mserial.toLowerCase()))
                .collect(Collectors.toList());
            }

            if(type != ""){
                dataArray = dataArray.stream()
                .filter(obj -> obj.getType().toLowerCase().contains(type.toLowerCase()))
                .collect(Collectors.toList());
            }


            if(startDate != ""){
                // Conversione delle stringhe in LocalDate e LocalDateTime
                DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

                LocalDate startDateFormat = LocalDate.parse(startDate, dateFormatter);
                LocalDateTime startTime = startDateFormat.atStartOfDay();

                dataArray = dataArray.stream()
                        .filter(obj -> {
                                    LocalDateTime objTimestamp = LocalDateTime.parse(obj.getTimestamp(), dateTimeFormatter);
                                    return !objTimestamp.isBefore(startTime);
                                })
                        .collect(Collectors.toList());
            }

            if(endDate != ""){
                // Conversione delle stringhe in LocalDate e LocalDateTime
                DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

                LocalDate endDateFormat = LocalDate.parse(endDate, dateFormatter);
                LocalDateTime endTime = endDateFormat.atTime(LocalTime.MAX);

                dataArray = dataArray.stream()
                        .filter(obj -> {
                                    LocalDateTime objTimestamp = LocalDateTime.parse(obj.getTimestamp(), dateTimeFormatter);
                                    return !objTimestamp.isAfter(endTime);
                                })
                        .collect(Collectors.toList());
            }

        }

        if(dataArray.size() < j){
            j = dataArray.size();
        }

        return dataArray.subList(i-1, j);
    }


    //Get data filtered
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECURITY_MANAGER')")
    @RequestMapping(value="/getDataFiltered", method= RequestMethod.GET)
    public List<MachineryDataDTO> getDataFilteres(@RequestParam(value= "mserial", required = false) String mserial,
                                            @RequestParam(value= "type", required = false) String type,
                                            @RequestParam(value= "startDate", required = false) String startDate,
                                            @RequestParam(value= "endDate", required = false) String endDate) {


        List<MachineryDataDTO> dataArray = new ArrayList<>();

        List<MachineryData> allData = machineryDataRepository.findAll();

        Collections.reverse(allData);

        for(MachineryData data : allData) {

            MachineryDataDTO dataDTO = new MachineryDataDTO();

            dataDTO.setId(data.getId());
            dataDTO.setMserial(data.getMserial());
            dataDTO.setType(data.getType());
            dataDTO.setDescription(data.getDescription());
            dataDTO.setIsSolved(data.getIsSolved());
            dataDTO.setValue(data.getValue());
            dataDTO.setTimestamp(data.getTimestamp());

            dataArray.add(dataDTO);

        }

        if(mserial != ""){
            dataArray = dataArray.stream()
            .filter(obj -> obj.getMserial().toLowerCase().contains(mserial.toLowerCase()))
            .collect(Collectors.toList());
        }

        if(type != ""){
            dataArray = dataArray.stream()
            .filter(obj -> obj.getType().toLowerCase().contains(type.toLowerCase()))
            .collect(Collectors.toList());
        }


        if(startDate != ""){
            // Conversione delle stringhe in LocalDate e LocalDateTime
            DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            LocalDate startDateFormat = LocalDate.parse(startDate, dateFormatter);
            LocalDateTime startTime = startDateFormat.atStartOfDay();

            dataArray = dataArray.stream()
                    .filter(obj -> {
                                LocalDateTime objTimestamp = LocalDateTime.parse(obj.getTimestamp(), dateTimeFormatter);
                                return !objTimestamp.isBefore(startTime);
                            })
                    .collect(Collectors.toList());
        }

        if(endDate != ""){
            // Conversione delle stringhe in LocalDate e LocalDateTime
            DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            LocalDate endDateFormat = LocalDate.parse(endDate, dateFormatter);
            LocalDateTime endTime = endDateFormat.atTime(LocalTime.MAX);

            dataArray = dataArray.stream()
                    .filter(obj -> {
                                LocalDateTime objTimestamp = LocalDateTime.parse(obj.getTimestamp(), dateTimeFormatter);
                                return !objTimestamp.isAfter(endTime);
                            })
                    .collect(Collectors.toList());
        }

        return dataArray;
    }


    // Update isSolved
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECURITY_MANAGER')")
    @RequestMapping(value="/updateIsSolved", method = RequestMethod.PUT)
    public ResponseEntity<String> updateIsSolved(@RequestParam("id") String id) {

        Optional<MachineryData> existingMachineryData = machineryDataRepository.findById(id);

        if (existingMachineryData.isPresent()) {
            MachineryData data = existingMachineryData.get();
            data.setIsSolved("True");
            data = machineryDataRepository.save(data);
        }

        return ResponseEntity.ok().build();
    }

    
    
}
