package pro.sky.hw35.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.hw35.model.AverageAgeOfStudents;
import pro.sky.hw35.model.LastFiveStudentsById;
import pro.sky.hw35.model.NumberOfStudents;
import pro.sky.hw35.model.Student;
import pro.sky.hw35.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private final Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student add(String name, Integer age) {
        logger.info("Was invoked method for add student");
        Student newStudent = new Student(name, age);
        newStudent = studentRepository.save(newStudent);
        return newStudent;
    }

    public Student get(long id) {
        logger.info("Was invoked method for get student");
        return studentRepository.findById(id).get();
    }

    public Student update(long id, String name, Integer age) {
        logger.info("Was invoked method for update student");
        Student studentForUpdate = get(id);
        studentForUpdate.setName(name);
        studentForUpdate.setAge(age);
        return studentRepository.save(studentForUpdate);
    }

    public Student delete(long id) {
        logger.info("Was invoked method for delete student");
        Student studentForDelete = get(id);
        studentRepository.deleteById(id);
        return studentForDelete;
    }

    public List<Student> getWhenAgeBetween(Integer min, Integer max) {
        logger.info("Was invoked method for get age between students");
        return studentRepository.findAllByAgeBetween(min, max);
    }

    public List<NumberOfStudents> getNumberOfStudents() {
        logger.info("Was invoked method for get number of students");
        return studentRepository.getNumberOfStudents();
    }

    public List<AverageAgeOfStudents> getAvgAgeOfStudents() {
        logger.info("Was invoked method for get average age of students");
        return studentRepository.getAvgAgeOfStudents();
    }

    public List<LastFiveStudentsById> getLastFiveStudents() {
        logger.info("Was invoked method for get last five students");
        return studentRepository.lastFiveStudentsById();
    }

    public List<String> getAllNamesStartingWithA() {
        String firstSymbol = "А";
        return studentRepository.findAll().stream()
                .map(student -> student.getName().toUpperCase())
                .filter(name -> name.startsWith(firstSymbol))
                .sorted()
                .collect(Collectors.toList());
    }

    public double getAvgAgeWithStream() {
        return studentRepository.findAll().stream()
                .mapToDouble(Student::getAge)
                .average()
                .orElse(-1);
    }

    public void printStudents() {
        List<Student> students = studentRepository.findAll();

        printStudent(students.get(0));
        printStudent(students.get(1));

        Thread thread1 = new Thread(() -> {
            printStudent(students.get(2));
            printStudent(students.get(3));
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            printStudent(students.get(4));
            printStudent(students.get(5));
        });
        thread2.start();
    }

    private void printStudent(Student student) {
        logger.info("Thread: {}. Student: {}", Thread.currentThread(), student);
    }

    private synchronized void printStudentSync(Student student) {
        printStudent(student);
    }

    public void printStudentsSync() {
        List<Student> students = studentRepository.findAll();

        printStudentSync(students.get(0));
        printStudentSync(students.get(1));

        Thread thread1 = new Thread(() -> {
            printStudentSync(students.get(2));
            printStudentSync(students.get(3));
        });
        thread1.start();

        Thread thread2 = new Thread(() -> {
            printStudentSync(students.get(4));
            printStudentSync(students.get(5));
        });
        thread2.start();
    }
}
