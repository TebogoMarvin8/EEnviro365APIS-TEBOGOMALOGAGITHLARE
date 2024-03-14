package com.enviro.assessment.grad001.tebogomalogadithlare.controllers;

import com.enviro.assessment.grad001.tebogomalogadithlare.model.EnvironmentalData;
import com.enviro.assessment.grad001.tebogomalogadithlare.RepositoryInterface.EnvironmentalDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.http.MediaType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping(path="/api")
public class FileProcessingController {

    private final EnvironmentalDataRepository environmentalDataRepository;
    private final Logger logger = LoggerFactory.getLogger(FileProcessingController.class);

    @Autowired
    public FileProcessingController(EnvironmentalDataRepository environmentalDataRepository) {
        this.environmentalDataRepository = environmentalDataRepository;
    }

    @PostMapping(path = "/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file to upload.");
            }

            // Check if the uploaded file is a text file
            if (!MediaType.TEXT_PLAIN.equals(MediaType.parseMediaType(Objects.requireNonNull(file.getContentType())))) {
                return ResponseEntity.badRequest().body("Please upload a text file.");
            }

            // Process the file and save environmental data to the database
            List<EnvironmentalData> dataList = new ArrayList<>();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    EnvironmentalData environmentalData = new EnvironmentalData();
                    environmentalData.setData(line);
                    dataList.add(environmentalData);
                }
            } catch (IOException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to read the file: " + e.getMessage());
            }

            environmentalDataRepository.saveAll(dataList);

            return ResponseEntity.ok("File uploaded and processed successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to process the file: " + e.getMessage());
        }
    }

    @GetMapping(path="/data")
    public ResponseEntity<?> getAllData() {
        try {
            List<EnvironmentalData> data = environmentalDataRepository.findAll();
            return ResponseEntity.ok(data);
        } catch (Exception e) {
            // Log the error
            logger.error("Failed to retrieve environmental data", e);

            // Return an error response to the client
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Failed to retrieve environmental data. Please try again later.");
        }
    }

}

