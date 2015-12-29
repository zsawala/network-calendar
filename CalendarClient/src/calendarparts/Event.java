
package calendarparts;


public class Event {
    public int day;
    public int month;
    public int year;
    public int hourStart;
    public int minutesStart;
    public int hoursEnd;
    public int minutesEnd;
    public int length;
    public String text;
    public Event(String mess,int month,int year, int day){
       text = mess;
       this.day=day;
       this.month=month;
       this.year=year;
       hourStart=10;
       minutesStart=55;
    }
}
