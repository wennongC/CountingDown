package wennong.cai.countingdown.Provider;

import java.util.Date;

public class ItemValues {
    private int id;
    private String itemTitle;
    private String itemDescription;
    private int itemYear;
    private int itemMonth;
    private int itemDay;

    public ItemValues(){}

    public ItemValues(int id, String itemTitle, String itemDescription, int itemYear, int itemMonth, int itemDay){
        this.id = id;
        this.itemTitle = itemTitle;
        this.itemDescription = itemDescription;
        this.itemYear = itemYear;
        this.itemMonth = itemMonth;
        this.itemDay = itemDay;
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
