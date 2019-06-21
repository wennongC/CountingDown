package wennong.cai.countingdown.Provider;

import java.time.LocalDate;
import java.time.Month;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;

public class ItemValues {
    private int id;
    private String itemTitle;
    private String itemDescription;
    private int itemYear;
    private int itemMonth;
    private int itemDay;

    private final Month[] MONTHS = {Month.JANUARY, Month.FEBRUARY, Month.MARCH,
            Month.APRIL, Month.MAY, Month.JUNE, Month.JULY, Month.AUGUST,
            Month.SEPTEMBER, Month.OCTOBER, Month.NOVEMBER, Month.DECEMBER};
    private final String[] MonthsName = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "July", "Aug", "Sep", "Oct", "Nov", "Dec"};

    public ItemValues(){}

    public ItemValues(int id, String itemTitle, String itemDescription, int itemYear, int itemMonth, int itemDay){
        this.id = id;
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemYear = itemYear;
        this.itemMonth = itemMonth;
        this.itemDay = itemDay;
    }

    public String getDueDateAsString(){
        String retVal = MonthsName[getItemMonth() - 1] + " " + getItemDay() + ", " + getItemYear();
        return retVal;
    }

    public long calculateDayLeft(){
        Calendar calendar = Calendar.getInstance();
        int currentYear = calendar.get(Calendar.YEAR);
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentDay = calendar.get(Calendar.DAY_OF_MONTH);

        LocalDate current = LocalDate.of(currentYear, MONTHS[currentMonth], currentDay);
        LocalDate due = LocalDate.of(getItemYear(), MONTHS[getItemMonth() - 1], getItemDay());
        long noOfDaysBetween = ChronoUnit.DAYS.between(current, due);

        return noOfDaysBetween;
    }

    public int getId() {
        return id;
    }

    public String getItemTitle() {
        return itemTitle;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public int getItemYear() {
        return itemYear;
    }

    public int getItemMonth() {
        return itemMonth;
    }

    public int getItemDay() {
        return itemDay;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public void setItemYear(int itemYear) {
        this.itemYear = itemYear;
    }

    public void setItemMonth(int itemMonth) {
        this.itemMonth = itemMonth;
    }

    public void setItemDay(int itemDay) {
        this.itemDay = itemDay;
    }
}
