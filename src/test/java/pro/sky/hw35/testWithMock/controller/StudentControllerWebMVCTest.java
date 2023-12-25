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
import pro.sky.hw35.controller.StudentController;
import pro.sky.hw35.model.Faculty;
import pro.sky.hw35.model.Student;
import pro.sky.hw35.service.StudentService;


import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StudentController.class)
public class StudentControllerWebMVCTest {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    @Test
    public void create_success() throws Exception {
        //Подготовка входных данных
        String name = "Anthony Martial";
        int age = 25;
        long id = 1L;
        Student studentForCreate = new Student(name, age);

        String request = objectMapper.writeValueAsString(studentForCreate);

        //Подготовка ожидаемого результата
        Student studentAfterCreate = new Student(name, age);
        studentAfterCreate.setId(id);

        when(studentService.add(name, age)).thenReturn(studentAfterCreate);

        //Начало теста
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post("/student")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(studentForCreate.getName()))
                .andExpect(jsonPath("$.age").value(studentForCreate.getAge()))
                .andReturn();
    }

    @Test
    public void get_success() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Anthony Martial";
        int age = 25;
        long id = 1L;
        Student studentForCreate = new Student(name, age);
        studentForCreate.setId(id);

        when(studentService.get(id)).thenReturn(studentForCreate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/student")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(studentForCreate.getName()))
                .andExpect(jsonPath("$.age").value(studentForCreate.getAge()))
                .andReturn();
    }

    @Test
    public void update_success() throws Exception {
        //Подготовка входных данных
        String name = "Anthony Martial";
        int age = 25;
        long id = 1L;
        Student studentForUpdate = new Student(name, age);
        studentForUpdate.setId(id);

        String request = objectMapper.writeValueAsString(studentForUpdate);

        //Подготовка ожидаемого результата
        when(studentService.update(id, name, age)).thenReturn(studentForUpdate);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .put("/student")
                        .content(request)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(studentForUpdate.getName()))
                .andExpect(jsonPath("$.age").value(studentForUpdate.getAge()))
                .andReturn();
    }

    @Test
    public void delete_success() throws Exception {
        //Подготовка ожидаемого результата
        String name = "Anthony Martial";
        int age = 25;
        long id = 1L;
        Student studentForDelete = new Student(name, age);
        studentForDelete.setId(id);

        when(studentService.delete(id)).thenReturn(studentForDelete);

        //Начало теста
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/student")
                        .param("id", String.valueOf(id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value(studentForDelete.getName()))
                .andExpect(jsonPath("$.age").value(studentForDelete.getAge()))
                .andReturn();
    }
}
