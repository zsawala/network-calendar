
package calendarparts;


public class Event {
    public int day;
    public int month;
    public int year;
    public int hour;
    public int minutes;
    public int length;
    public String text;
    public Event(String mess){
       text = mess;
       day=1;
       month=1;
       year=2015;
    }
}
