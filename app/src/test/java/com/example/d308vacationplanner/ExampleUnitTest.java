package com.example.d308vacationplanner;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ExampleUnitTest {

    @Test
    public void vacationEndDateCannotBeBeforeStartDate() throws Exception {

        SimpleDateFormat sdf =
                new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Date startDate = sdf.parse("09/20/2026");
        Date endDate = sdf.parse("09/15/2026");

        boolean validVacationDates =
                !endDate.before(startDate);

        assertFalse(
                "Vacation should be invalid when the end date is before the start date.",
                validVacationDates
        );
    }
    @Test
    public void excursionDateMustBeWithinVacationDates() throws Exception {

        SimpleDateFormat sdf =
                new SimpleDateFormat("MM/dd/yyyy", Locale.US);

        Date vacationStart =
                sdf.parse("09/15/2026");

        Date vacationEnd =
                sdf.parse("09/20/2026");

        Date excursionDate =
                sdf.parse("09/25/2026");

        boolean validExcursionDate =
                !excursionDate.before(vacationStart)
                        && !excursionDate.after(vacationEnd);

        assertFalse(
                "Excursion should be invalid when it occurs outside the vacation dates.",
                validExcursionDate
        );
    }
}