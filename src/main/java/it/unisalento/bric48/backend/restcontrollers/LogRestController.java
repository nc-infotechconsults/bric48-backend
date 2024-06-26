package it.unisalento.bric48.backend.restcontrollers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import it.unisalento.bric48.backend.domain.Log;
import it.unisalento.bric48.backend.dto.LogDTO;
import it.unisalento.bric48.backend.repositories.LogRepository;

@RestController
@CrossOrigin
@RequestMapping("/log")
public class LogRestController {

    @Autowired
    LogRepository logRepository;

    // Add a log
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECURITY_MANAGER')")
    @RequestMapping(value="/add", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public LogDTO addLog(@RequestBody LogDTO logDTO) {

        Log newLog = new Log();
        newLog.setLog(logDTO.getLog());
        newLog.setTimestamp(logDTO.getTimestamp());

        newLog = logRepository.save(newLog);

        logDTO.setId(newLog.getId());

        return logDTO;
    }

    //Get all logs
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECURITY_MANAGER')")
    @RequestMapping(value="/getAll", method= RequestMethod.GET)
    public List<LogDTO> getAllLogs() {

        List<LogDTO> logs = new ArrayList<>();

        for(Log log : logRepository.findAll()) {

            LogDTO logDTO = new LogDTO();

            logDTO.setLog(log.getLog());
            logDTO.setTimestamp(log.getTimestamp());


            logs.add(logDTO);
        }

        return logs;
    }

    //Get logs from-to filtered
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECURITY_MANAGER')")
    @RequestMapping(value="/getLogsFromTo", method= RequestMethod.GET)
    public List<LogDTO> getFromTo(@RequestParam("from") String from, 
                                            @RequestParam("to") String to, 
                                            @RequestParam(value= "text", required = false) String text,
                                            @RequestParam(value= "startDate", required = false) String startDate,
                                            @RequestParam(value= "endDate", required = false) String endDate) {

        
        int i = Integer.parseInt(from);
        int j = Integer.parseInt(to);

        List<LogDTO> logs = new ArrayList<>();

        List<Log> allLogs = logRepository.findAll();

        Collections.reverse(allLogs);

        for(Log log : allLogs) {

            LogDTO logDTO = new LogDTO();

            logDTO.setId(log.getId());
            logDTO.setLog(log.getLog());
            logDTO.setTimestamp(log.getTimestamp());

            logs.add(logDTO);

            if(text != ""){
                logs = logs.stream()
                .filter(obj -> obj.getLog().toLowerCase().contains(text.toLowerCase()))
                .collect(Collectors.toList());
            }

            if(startDate != ""){
                // Conversione delle stringhe in LocalDate e LocalDateTime
                DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

                LocalDate startDateFormat = LocalDate.parse(startDate, dateFormatter);
                LocalDateTime startTime = startDateFormat.atStartOfDay();

                logs = logs.stream()
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

                logs = logs.stream()
                        .filter(obj -> {
                                    LocalDateTime objTimestamp = LocalDateTime.parse(obj.getTimestamp(), dateTimeFormatter);
                                    return !objTimestamp.isAfter(endTime);
                                })
                        .collect(Collectors.toList());
            }

        }

        if(logs.size() < j){
            j = logs.size();
        }

        return logs.subList(i-1, j);
    }

    //Get logs filtered
    @PreAuthorize("hasRole('ADMIN') or hasRole('SECURITY_MANAGER')")
    @RequestMapping(value="/getLogsFiltered", method= RequestMethod.GET)
    public List<LogDTO> getLogsFiltered(@RequestParam(value= "text", required = false) String text,
                                            @RequestParam(value= "startDate", required = false) String startDate,
                                            @RequestParam(value= "endDate", required = false) String endDate) {


        List<LogDTO> logs = new ArrayList<>();

        List<Log> allLogs = logRepository.findAll();

        Collections.reverse(allLogs);

        for(Log log : allLogs) {

            LogDTO logDTO = new LogDTO();

            logDTO.setId(log.getId());
            logDTO.setLog(log.getLog());
            logDTO.setTimestamp(log.getTimestamp());

            logs.add(logDTO);

        }

        if(text != ""){
            logs = logs.stream()
            .filter(obj -> obj.getLog().toLowerCase().contains(text.toLowerCase()))
            .collect(Collectors.toList());
        }


        if(startDate != ""){
            // Conversione delle stringhe in LocalDate e LocalDateTime
            DateTimeFormatter dateFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

            LocalDate startDateFormat = LocalDate.parse(startDate, dateFormatter);
            LocalDateTime startTime = startDateFormat.atStartOfDay();

            logs = logs.stream()
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

            logs = logs.stream()
                    .filter(obj -> {
                                LocalDateTime objTimestamp = LocalDateTime.parse(obj.getTimestamp(), dateTimeFormatter);
                                return !objTimestamp.isAfter(endTime);
                            })
                    .collect(Collectors.toList());
        }

        return logs;
    }

}
