
package calendargui;

import calendarparts.Event;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class CalendarPanel extends JPanel{
    static JTable tblCalendar;
    static JLabel monthLabel;
    static JLabel yearLabel;
    static DefaultTableModel mtblCalendar;
    static JScrollPane stblCalendar;
    static int realDay, realMonth, realYear, currentMonth, currentYear;
    static JButton left;
    static JButton right;
    static ArrayList<Event> eventsList;
    public CalendarPanel(){
        //TEST EVENTOW
        eventsList = new ArrayList<Event>();
        
        
        
        
        
        //KONIEC TESTU EVENTOW
        
        setLayout(null);
        mtblCalendar = new DefaultTableModel()
    {
         public boolean isCellEditable(int row, int column)
        {
            return false;
        }
     };
        tblCalendar = new JTable(mtblCalendar);
        stblCalendar = new JScrollPane(tblCalendar);
        monthLabel = new JLabel();
        yearLabel = new JLabel();
        right = new JButton("Next");
        right.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
                if(currentMonth == 11){
                    currentMonth=0;
                    currentYear = currentYear+1;
                }else{
                    currentMonth = currentMonth +1;
                }
                refreshCalendar(currentMonth, currentYear);
            }
        });
        left = new JButton("Prev");
        left.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
                if(currentMonth == 0){
                    currentMonth=11;
                    currentYear = currentYear-1;
                }else{
                    currentMonth = currentMonth -1;
                }
                refreshCalendar(currentMonth, currentYear);
            }
        });
        add(stblCalendar);
        add(monthLabel);
        add(yearLabel);
        add(right);
        add(left);
        stblCalendar.setBounds(10, 50, 900, 650);
        monthLabel.setBounds(10,0,100,20);
        yearLabel.setBounds(110,0,100,20);
        right.setBounds(920, 100, 100, 20);
        left.setBounds(920, 150, 100, 20);
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year
        currentYear = realYear;
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        //naglowke
        for (int i=0; i<7; i++){
        mtblCalendar.addColumn(headers[i]);
        }
        
        eventsList.add(new Event("no to jest testowy event",currentMonth,currentYear,1));
        eventsList.add(new Event("ato jest inny testowy event",currentMonth,currentYear,1));
        eventsList.add(new Event("lorem ipsum kurde",currentMonth,currentYear,1));
        
        tblCalendar.setRowHeight(75);   
        mtblCalendar.setColumnCount(7); 
        mtblCalendar.setRowCount(6);
        tblCalendar.addMouseListener(new MouseAdapter() {
            
            //wydarzenie dwukrotnego klikniecia w komorke, które wyswietla wszystkie eventy tego dnia
   public void mouseClicked(MouseEvent e) {
      if (e.getClickCount() == 2) {
         JTable target = (JTable)e.getSource();
         int row = target.getSelectedRow();
         int column = target.getSelectedColumn();
         String zawartosc = new String((String) target.getValueAt(row, column));
         int d =  Integer.parseInt(zawartosc.substring(6,7));
         JFrame frame = new JFrame("Events: " + d+"."+(currentMonth+1)+"."+currentYear);
         JPanel eventPanel = new JPanel();
         JButton newButton = new JButton("New Event");
         frame.add(eventPanel);
         eventPanel.setLayout(null);
         eventPanel.add(newButton);
         newButton.setBounds(0, 0, 100, 20);
         newButton.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
                JFrame frameNew = new JFrame("Nowy");
                frameNew.setLayout(null);
                frameNew.setVisible(true);
                frameNew.setSize(300,500);
                JLabel startLabel = new JLabel("Podaj godzine rozpoczęcia:");
                JLabel endLabel = new JLabel("Podaj godzinę zakończenia:");
                JLabel textLabel = new JLabel("Podaj nazwę zdarzenia:");
                JTextField startField = new JTextField();
                JTextField endField = new JTextField();
                JTextField textField = new JTextField();
                JButton acceptButton = new JButton("Akceptuj");
                acceptButton.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                            eventsList.add(new Event(textField.getText(),currentMonth,currentYear,d));
                            refreshCalendar(currentMonth,currentYear);
                            frameNew.dispose();
                            frame.dispose();
                        }
                });
                startLabel.setBounds(100,100,100,20);
                startField.setBounds(100,130,100,20);
                endLabel.setBounds(100, 160, 100, 20);
                endField.setBounds(100,190,100,20);
                textLabel.setBounds(100,220,100,20);
                textField.setBounds(100,250,100,20);
                acceptButton.setBounds(100,280,100,20);
                frameNew.add(startLabel);
                frameNew.add(endLabel);
                frameNew.add(textLabel);
                frameNew.add(startField);
                frameNew.add(endField);
                frameNew.add(textField);
                frameNew.add(acceptButton);
            }
        });
         int height=50;
         int width=50;
         for(int i=0;i<eventsList.size();i++){
                   Event item = eventsList.get(i);
                    if ((d == item.day)&&(currentMonth==item.month)&&(currentYear == item.year)){
                    JLabel textLabel = new JLabel(item.text);
                    JLabel hourLabel = new JLabel(item.hourStart + ":" + item.minutesStart);
                   eventPanel.add(textLabel);
                   eventPanel.add(hourLabel);
                   hourLabel.setBounds(width,height-20,100,10);
                   textLabel.setBounds(width,height,200,20);
                    height=height+50;
        }
         }
         frame.setSize(500,500);
         frame.setVisible(true);
         }
   }
});
        //wyswietlenie miesiaca
        refreshCalendar(realMonth,realYear);


    }
            //Odświeżanie miesiąca 
        public static void refreshCalendar(int month, int year){
            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            monthLabel.setText(months[month]);
            yearLabel.setText(Integer.toString(year));
            GregorianCalendar cal = new GregorianCalendar(year, month, 1);
            int  nod = cal.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
            int som = cal.get(GregorianCalendar.DAY_OF_WEEK);
            //czyszczenie tabeli z zawartosci
            for (int i=0; i<6; i++){
                for (int j=0; j<7; j++){
                    mtblCalendar.setValueAt(null, i, j);
                
                }
            }
            
            //wypełnianie komórek
           for (int i=1; i<=nod; i++){
                int row = new Integer((i+som-2)/7);
                int column  =  (i+som-2)%7;
                int number =  countEvents(i,month,year);
                mtblCalendar.setValueAt("<html>" + Integer.toString(i) + "<br>" + "Events: [" + Integer.toString(number)+"]</html>" , row, column);
           }


        }
        // int day, int month, int year
        public static int countEvents(int day, int month, int year){
            int count =0 ;
            for(Iterator<Event> i = eventsList.iterator(); i.hasNext(); ) {
                Event item = i.next();
                if ((day == item.day)&&(month==item.month)&&(year == item.year)){
                    count++;
                }
            }
            return count;
        }
        
        public static ArrayList<Event> findEvents(int day, int month, int year){
            ArrayList<Event> eventOfDay = null;
               for(int i=0;i<eventsList.size();i++){
                   Event item = eventsList.get(i);
                    if ((day == item.day)&&(month==item.month)&&(year == item.year)){
                    System.out.println(item.text);
                    eventOfDay.add(eventsList.get(i));
                }
               }
            return eventOfDay;
        }
        
           
        
        
}
