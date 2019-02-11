package dojo.spring.boot.service;

import dojo.spring.boot.model.Student;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.util.Arrays.asList;

@Service
public class StudentService {

    public List<Student> retrieveStudents() {
        return asList(
                new Student("S0101", "Michael", "Male"),
                new Student("S0102", "Jane", "Female")
        );
    }
}
