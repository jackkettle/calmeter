package com.calmeter.core.account.model;

import com.calmeter.core.json.LocalDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "weight_log_entry")
public class WeightLogEntry implements Serializable {

    private static final long serialVersionUID = 1L;

    Long id;

    Double weightInKgs;

    LocalDateTime dateTime;

    public WeightLogEntry(){
    }


    public WeightLogEntry(Double weightInKgs, LocalDateTime dateTime){
        this.weightInKgs = weightInKgs;
        this.dateTime = dateTime;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getWeightInKgs() {
        return weightInKgs;
    }

    public void setWeightInKgs(Double weightInGrams) {
        this.weightInKgs = weightInGrams;
    }

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
