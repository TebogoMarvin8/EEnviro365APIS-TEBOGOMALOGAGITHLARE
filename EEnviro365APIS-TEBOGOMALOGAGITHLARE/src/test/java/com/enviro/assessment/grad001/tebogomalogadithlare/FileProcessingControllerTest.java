package com.enviro.assessment.grad001.tebogomalogadithlare;

import com.enviro.assessment.grad001.tebogomalogadithlare.model.EnvironmentalData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FileProcessingControllerTest {

    @Mock
    private EnvironmentalDataRepository environmentalDataRepository;

    @InjectMocks
    private FileProcessingController fileProcessingController;

    @Before
    public void setUp() {
        // Mock repository behavior for getAllData
        List<EnvironmentalData> mockData = new ArrayList<>();
        mockData.add(new EnvironmentalData("Data 1"));
        mockData.add(new EnvironmentalData("Data 2"));
        when(environmentalDataRepository.findAll()).thenReturn(mockData);

        // Mock repository behavior for handleFileUpload
        when(environmentalDataRepository.saveAll(any())).thenReturn(Collections.emptyList());
    }

    @Test
    public void testHandleFileUpload_Success() throws IOException {
        // Mock multipart file
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Test data".getBytes());

        // Call the controller method
        ResponseEntity<String> response = fileProcessingController.handleFileUpload(file);

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("File uploaded and processed successfully.", response.getBody());
    }

    @Test
    public void testHandleFileUpload_EmptyFile() throws IOException {
        // Mock an empty multipart file
        MockMultipartFile file = new MockMultipartFile("file", "", "text/plain", "".getBytes());

        // Call the controller method
        ResponseEntity<String> response = fileProcessingController.handleFileUpload(file);

        // Assert the response
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Please select a file to upload.", response.getBody());
    }

    @Test
    public void testGetAllData_Success() {
        // Call the controller method
        ResponseEntity<List<EnvironmentalData>> response = fileProcessingController.getAllData();

        // Assert the response
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<EnvironmentalData> responseData = response.getBody();
        assertEquals(2, responseData.size()); // Ensure correct number of data items
        assertEquals("Data 1", responseData.get(0).getData()); // Ensure correct data values
        assertEquals("Data 2", responseData.get(1).getData());
    }

}

