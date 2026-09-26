package com.example.d308vacationplanner.database;

import android.app.Application;

import com.example.d308vacationplanner.dao.ExcursionDAO;
import com.example.d308vacationplanner.dao.VacationDAO;
import com.example.d308vacationplanner.entities.Excursion;
import com.example.d308vacationplanner.entities.Vacation;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Repository {

    public static VacationDAO mvacationDao;
    public static ExcursionDAO mexcursionDao;
    private List<Vacation> mAllVacations;
    private List<Excursion> mAllExcursions;

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public Repository(Application application) {
        VacationDatabaseBuilder db =
                VacationDatabaseBuilder.getDatabase(application);

        mvacationDao = db.vacationDAO();
        mexcursionDao = db.excursionDAO();
    }

    public List<Vacation> getAllVacations() {

        try {
            return databaseWriteExecutor.submit(() ->
                    mvacationDao.getAllVacations()
            ).get();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insert(Vacation vacation) {
        databaseWriteExecutor.execute(() -> mvacationDao.insert(vacation));
    }

    public void update(Vacation vacation) {
        databaseWriteExecutor.execute(() -> mvacationDao.update(vacation));
    }

    public void delete(Vacation vacation) {
        databaseWriteExecutor.execute(() -> mvacationDao.delete(vacation));
    }

    public List<Excursion> getAllExcursions() {
        try {
            return databaseWriteExecutor.submit(() ->
                    mexcursionDao.getAllExcursions()
            ).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Excursion> getAssociatedExcursions(int vacationID) {
        try {
            return databaseWriteExecutor.submit(() ->
                    mexcursionDao.getAssociatedExcursions(vacationID)
            ).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void insert(Excursion excursion) {
        databaseWriteExecutor.execute(() -> mexcursionDao.insert(excursion));
    }

    public void update(Excursion excursion) {
        databaseWriteExecutor.execute(() -> mexcursionDao.update(excursion));
    }

    public void delete(Excursion excursion) {
        databaseWriteExecutor.execute(() -> mexcursionDao.delete(excursion));
    }

    public Excursion getExcursionById(int excursionId) {
        try {
            return databaseWriteExecutor.submit(() ->
                    mexcursionDao.getExcursionById(excursionId)
            ).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Vacation> getVacationsByDateRange(String startDate, String endDate) {
        try {
            return databaseWriteExecutor.submit(() ->
                    mvacationDao.getVacationsByDateRange(startDate, endDate)
            ).get();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}