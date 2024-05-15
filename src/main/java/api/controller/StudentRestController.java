package api.controller;

import api.entity.Student;
import api.service.CompositeService;
import api.util.COURSE;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/students")
public class StudentRestController {
    private final CompositeService compositeService;

    @Autowired
    public StudentRestController(CompositeService compositeService) {
        this.compositeService = compositeService;
    }


    @GetMapping
    public List<Student> getAllStudents() {
        return compositeService.getAllStudents();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        Student student = compositeService.getStudentById(id);

        if (student != null) {
            return ResponseEntity.ok(student);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(
            @RequestParam String num,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam COURSE course,
            @RequestParam double classification
    ) {
        Student s = compositeService.createStudent(num, name, email, course, classification);
        if (s == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(s);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(
            @PathVariable Long id,
            @RequestParam String num,
            @RequestParam String name,
            @RequestParam String email,
            @RequestParam COURSE course,
            @RequestParam Double classification
    ) {
        try {
            Student s = compositeService.updateStudent(id, num, name, email, course, classification);
            if (s != null) {
                return ResponseEntity.ok(s);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        compositeService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}
