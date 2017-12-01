package com.calmeter.core.account.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import com.calmeter.core.json.LocalDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Entity
@Table(name = "user_profile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;

    private List<WeightLogEntry> weightLog;

    private Double height;

    private Sex sex;

    private LocalDate dateOfBirth;

    public UserProfile(){
        this.weightLog = new ArrayList<>();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL)
    public List<WeightLogEntry> getWeightLog() {
        return weightLog;
    }

    public void setWeightLog(List<WeightLogEntry> weightLog) {
        this.weightLog = weightLog;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Enumerated(EnumType.STRING)
    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    @JsonSerialize(using = LocalDateSerializer.class)
    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

}
