package com.enviro.assessment.grad001.tebogomalogadithlare;

import com.enviro.assessment.grad001.tebogomalogadithlare.controllers.FileProcessingController;
import org.junit.Test;
import org.springframework.restdocs.mockmvc.MockMvcRestDocumentation;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class ApiDocumentation {

    private MockMvc mockMvc;

    @Test
    public void documentGetRequest() throws Exception {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new FileProcessingController()).apply(documentationConfiguration())
                .build();

        this.mockMvc.perform(get("/api/upload"))
                .andExpect(status().isOk())
                .andDo(document("upload"));
    }
}
