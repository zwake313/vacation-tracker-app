package com.example.d308vacationplanner.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.d308vacationplanner.entities.Vacation;

import java.util.List;

@Dao
public interface VacationDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Vacation vacation);

    @Update
    void update(Vacation vacation);

    @Delete
    void delete(Vacation vacation);

    @Query("SELECT * FROM vacation_table ORDER BY id ASC")
    List<Vacation> getAllVacations();

    @Query("SELECT * FROM vacation_table WHERE id = :vacationId LIMIT 1")
    Vacation getVacationById (int vacationId);

    @Query("SELECT * FROM vacation_table WHERE startDate >= :startDate AND endDate <= :endDate")
    List<Vacation> getVacationsByDateRange(String startDate, String endDate);
}