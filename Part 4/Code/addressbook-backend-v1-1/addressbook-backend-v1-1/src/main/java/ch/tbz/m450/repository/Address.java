package ch.tbz.m450.repository;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Table(name="address")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @Column(name = "user_id")

    private int id;
    private Integer priority;
    private String firstname; //0
    private String lastname; //1
    private String phonenumber;//2
    private Date registrationDate;//3
}
