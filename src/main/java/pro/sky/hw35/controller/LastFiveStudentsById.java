package pro.sky.hw35.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pro.sky.hw35.service.StudentService;

import java.util.List;

@RestController
public class LastFiveStudentsById {

    private final StudentService studentService;

    public LastFiveStudentsById(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/last-five-students-by-id")
    public List<pro.sky.hw35.model.LastFiveStudentsById> getLastFiveStudents() {
        return studentService.getLastFiveStudents();
    }
}
