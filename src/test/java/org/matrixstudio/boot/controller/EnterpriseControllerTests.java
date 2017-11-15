package org.matrixstudio.boot.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.matrixstudio.boot.MockMvcWebTests;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class EnterpriseControllerTests extends MockMvcWebTests {

    @Test
    public void testList() throws Exception {
        mockMvc.perform(get("/enterprises")
                    .param("page", "0")
                    .param("size", "10")
                    .param("sort", "name,ASC"))
                .andExpect(status().isOk())
                .andDo(print());
    }
}
