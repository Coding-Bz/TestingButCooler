package ch.tbz.m450.testing.tools.repository.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Entity
    @Data
    public class University {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private UUID id;
        private final String name;
        private final String location;
        private final int acceptanceRate;
        private final String acceptanceCriteria;


    public University(UUID id, String name, String location, int acceptance_rate, String acceptance_criteria) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.acceptanceRate = acceptance_rate;
        this.acceptanceCriteria = acceptance_criteria;
    }

    public University() {
        this.name = "";
        this.location = "";
        this.acceptanceRate = 0;
        this.acceptanceCriteria = "";
    }
}
