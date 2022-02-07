package com.employeRecord.data.models;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Data
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(unique = false, nullable = false)
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    @CreationTimestamp
    private LocalDateTime dateCreated;

}
