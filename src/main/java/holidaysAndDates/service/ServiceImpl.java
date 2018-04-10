package holidaysAndDates.service;

import holidaysAndDates.dao.HolidayDao;
import holidaysAndDates.dao.UserDao;
import holidaysAndDates.entity.Holiday;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class ServiceImpl implements holidaysAndDates.service.Service {

    @Autowired
    private HolidayDao holidayDao;

    @Autowired
    private UserDao userDao;

    @Override
    public boolean validate(String userName, String password) {
        return userDao.validate(userName, password);
    }

    public int getDaysOffBetweenTwoDates(Date dateFrom, Date dateTo) {

        Calendar calendarFrom = Calendar.getInstance();
        calendarFrom.setTime(dateFrom);
        Calendar calendarTo = Calendar.getInstance();
        calendarTo.setTime(dateTo);

        int startYear = calendarFrom.get(Calendar.YEAR);
        int startMonth = calendarFrom.get(Calendar.MONTH);
        int startDay = calendarFrom.get(Calendar.DAY_OF_MONTH);
        int endYear = calendarTo.get(Calendar.YEAR);
        int endMonth = calendarTo.get(Calendar.MONTH);
        int endDay = calendarTo.get(Calendar.DAY_OF_MONTH);
        List<Calendar> holidayDateList = new ArrayList<>();
        List<Holiday> holidays = holidayDao.getAllHolidays();
        for (Holiday holiday : holidays) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(holiday.getDate());
            holidayDateList.add(calendar);
        }
        Calendar startCal = Calendar.getInstance();
        startCal.set(startYear, startMonth, startDay);
        Calendar endCal = Calendar.getInstance();
        endCal.set(endYear, endMonth, endDay);
        int daysOff = 0;

        //Return 0 if start and end are the same
        if (startCal.getTimeInMillis() == endCal.getTimeInMillis()) {
            return 0;
        }

        if (startCal.getTimeInMillis() > endCal.getTimeInMillis()) {
            Calendar tmp = startCal;
            startCal = endCal;
            endCal = tmp;
        }

        int currentYear = startCal.get(Calendar.YEAR);
        java.util.Date easterDate = findEasterDate(currentYear);
        Calendar easterCal = Calendar.getInstance();
        easterCal.setTime(easterDate);

        Calendar trinityCalendar = Calendar.getInstance();
        trinityCalendar.setTime(easterDate);
        trinityCalendar.add(Calendar.DAY_OF_MONTH, 49);


        holidayDateList.add(easterCal);
        holidayDateList.add(trinityCalendar);

        //excluding start date
        startCal.add(Calendar.DAY_OF_MONTH, 1);

        //excluding end date
        while (startCal.before(endCal)) {
            //every new year recalculating easter and trinity date
            if (startCal.get(Calendar.YEAR) != currentYear) {

                currentYear = startCal.get(Calendar.YEAR);
                easterDate = findEasterDate(currentYear);
                easterCal.setTime(easterDate);
                easterCal.add(Calendar.DAY_OF_MONTH, 49);
                trinityCalendar = easterCal;

                holidayDateList.set(holidayDateList.size() - 2, easterCal);
                holidayDateList.set(holidayDateList.size() - 1, trinityCalendar);

            }
            if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                ++daysOff;
                System.out.println(startCal.getTime());
            }
            if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY && holidayListContainsCurrentMonthAndDay(holidayDateList, startCal)
                    && isCurrentDateHolidayMovedToMondaySmallerThanEndDate(startCal, endCal, 2)) {
                ++daysOff;
                System.out.println(startCal.getTime());
            } else if (startCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
                    && holidayListContainsCurrentMonthAndDay(holidayDateList, startCal)
                    && isCurrentDateHolidayMovedToMondaySmallerThanEndDate(startCal, endCal, 1)) {
                ++daysOff;
                System.out.println(startCal.getTime());

            }
            startCal.add(Calendar.DAY_OF_MONTH, 1);
        }

        return daysOff;
    }

    // usually holidays which date comes on weekend are moved to monday
    private static boolean isCurrentDateHolidayMovedToMondaySmallerThanEndDate(Calendar startCal, Calendar endCal, int daysToMove) {
        Calendar tmp = Calendar.getInstance();
        tmp.setTime(startCal.getTime());
        tmp.add(Calendar.DAY_OF_MONTH, daysToMove);
        tmp.getTime();
        return tmp.getTimeInMillis() < endCal.getTimeInMillis();
    }

    private static java.util.Date findEasterDate(int year) {

        int a = (19 * (year % 19) + 15) % 30;
        int b = ((2 * (year % 4) + 4 * (year % 7) + 6 * a + 6) % 7);

        Calendar calendar = Calendar.getInstance();
        if (a + b > 10) {
            calendar.set(Calendar.MONTH, 3);
            calendar.set(Calendar.DAY_OF_MONTH, a + b - 9);
        } else {
            calendar.set(Calendar.MONTH, 2);
            calendar.set(Calendar.DAY_OF_MONTH, 22 + a + b);
        }

        calendar.add(Calendar.DAY_OF_MONTH, 13);

        return calendar.getTime();

    }

    private static boolean holidayListContainsCurrentMonthAndDay(List<Calendar> holidayList, Calendar calendar) {
        for (Calendar currCalendar : holidayList) {
            if (currCalendar.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) && currCalendar.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)) {
                return true;
            }
        }
        return false;
    }

}
