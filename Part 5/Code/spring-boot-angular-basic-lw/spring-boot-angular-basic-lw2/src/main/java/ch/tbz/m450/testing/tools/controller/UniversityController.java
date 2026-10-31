package ch.tbz.m450.testing.tools.controller;

import ch.tbz.m450.testing.tools.repository.UniversityRepository;
import ch.tbz.m450.testing.tools.repository.entities.University;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4027")
public class UniversityController {

    private final UniversityRepository universityRepository;

    public UniversityController(UniversityRepository universityRepository) {
        this.universityRepository = universityRepository;
    }

    @GetMapping("/universities")
    public List<University> getUniversities() {
        return (List<University>) universityRepository.findAll();
    }

    @GetMapping("/{id}")
    public University getUniversity(@PathVariable UUID id) {
        return universityRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/universities")
    void addUniversity(@RequestBody University university) {
        universityRepository.save(university);
    }

    @DeleteMapping("/{id}")
    public void deleteUniversity(@PathVariable UUID id) {
        universityRepository.deleteById(id);
    }

    @PutMapping("/{id}")
    public University updateUniversity(@PathVariable UUID id, @RequestBody University university) {
        if (!universityRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        university.setId(id);
        return universityRepository.save(university);
    }


}


