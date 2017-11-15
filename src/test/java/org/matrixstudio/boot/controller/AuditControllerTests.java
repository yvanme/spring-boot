package org.matrixstudio.boot.controller;

import org.junit.*;
import org.junit.runner.RunWith;
import org.matrixstudio.boot.MockMvcWebTests;
import org.matrixstudio.boot.model.document.Action;
import org.matrixstudio.boot.model.document.Audit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
public class AuditControllerTests extends MockMvcWebTests {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Before
    public void prepareData() {
        List<Audit> audits = Stream.generate(() -> UUID.randomUUID().toString())
                .limit(10)
                .map(strUuid -> {
                    Audit audit = new Audit();
                    audit.setAction(Action.CREATE);
                    audit.setDate(System.currentTimeMillis());
                    audit.setObjectId(strUuid);
                    return audit;
                }).collect(Collectors.toList());

        mongoTemplate.insertAll(audits);
    }

    @Test
    public void testList() throws Exception {
        mockMvc.perform(get("/audits")
                .param("page", "0")
                .param("size", "10")
                .param("sort", "oid,ASC"))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @After
    public void flushData() {
        mongoTemplate.dropCollection(Audit.class);
    }
}
