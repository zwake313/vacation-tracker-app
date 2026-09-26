package com.example.d308vacationplanner.database;

import android.content.Context;
import com.example.d308vacationplanner.entities.Vacation;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.dao.VacationDAO;
import com.example.d308vacationplanner.dao.ExcursionDAO;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Vacation.class, Excursion.class}, version = 3, exportSchema = false)
public abstract class VacationDatabaseBuilder extends RoomDatabase {


    public abstract VacationDAO vacationDAO();
    public abstract ExcursionDAO excursionDAO();
    private static volatile VacationDatabaseBuilder INSTANCE;

    static VacationDatabaseBuilder getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (VacationDatabaseBuilder.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    VacationDatabaseBuilder.class, "vacation_database.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }

            }
        }
        return INSTANCE;

    }

}
