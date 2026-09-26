package com.example.d308vacationplanner.entities;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;


@Entity(tableName = "excursion_table")
public class Excursion {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int vacationId;
    public String title;
    public String date;


    public Excursion(int id, int vacationId, String title, String date) {
        this.id = id;
        this.vacationId = vacationId;
        this.title = title;
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getVacationId() {
        return vacationId;
    }

    public void setVacationId(int vacationId) {
        this.vacationId = vacationId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}