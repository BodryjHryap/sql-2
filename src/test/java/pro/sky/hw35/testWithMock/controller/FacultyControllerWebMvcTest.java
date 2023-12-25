package pro.sky.hw35.testWithMock.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pro.sky.hw35.controller.FacultyController;
import pro.sky.hw35.model.Faculty;
import pro.sky.hw35.service.FacultyService;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class FacultyControllerWebMvcTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyService facultyService;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void create_success() throws Exception {
        //Подготовка входных данных
        String name = "Slytherin";
        String color = "yellow";

        Faculty facultyForCreate = new Faculty(name, color);

        String request = objectMapper.writeValueAsString(facultyForCreate);

        //Подготовка ожидаемого результата
        long id = 1L;
        Faculty facultyAfterCreate = new Faculty(name, color);
        facultyAfterCreate.setId(id);

        when(facultyService.add(name, color)).thenReturn(facultyAfterCreate);

        //Начало теста
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                                .post("/faculty")
                                .content(request)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(facultyForCreate.getName()))
                .andExpect(jsonPath("$.color").value(facultyForCreate.getColor()))
                .andReturn();
    }

    @Test
    public void get_success() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Gryffindor";
        String color = "red";
        long id = 1L;
        Faculty facultyForCreate = new Faculty(name, color);
        facultyForCreate.setId(id);

        when(facultyService.get(id)).thenReturn(facultyForCreate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/faculty")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(facultyForCreate.getName()))
                .andExpect(jsonPath("$.color").value(facultyForCreate.getColor()))
                .andReturn();

    }

    @Test
    public void update_success() throws Exception {
        //Подготовка входных данных
        String name = "Gryffindor";
        String color = "red";
        long id = 1L;
        Faculty facultyForUpdate = new Faculty(name, color);
        facultyForUpdate.setId(id);

        String request = objectMapper.writeValueAsString(facultyForUpdate);

        //Подготовка ожидаемого результата
        when(facultyService.update(id, name, color)).thenReturn(facultyForUpdate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/faculty")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(facultyForUpdate.getName()))
                .andExpect(jsonPath("$.color").value(facultyForUpdate.getColor()))
                .andReturn();
    }

    @Test
    public void delete_success() throws Exception {
        //Подготовка входных данных
        String name = "Gryffindor";
        String color = "red";
        long id = 1L;
        Faculty facultyForDelete = new Faculty(name, color);
        facultyForDelete.setId(id);

        //Подготовка ожидаемого результата
        when(facultyService.delete(id)).thenReturn(facultyForDelete);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/faculty")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(facultyForDelete.getName()))
                .andExpect(jsonPath("$.color").value(facultyForDelete.getColor()))
                .andReturn();
    }
}
