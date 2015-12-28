
package calendargui;

import java.util.GregorianCalendar;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class CalendarPanel extends JPanel{
    static JTable tblCalendar;
    static JLabel monthLabel;
    static JLabel yearLabel;
    static DefaultTableModel mtblCalendar;
    static JScrollPane stblCalendar;
    static int realDay, realMonth, realYear, currentMonth, currentYear;
    public CalendarPanel(){
        setLayout(null);
        mtblCalendar = new DefaultTableModel();
        tblCalendar = new JTable(mtblCalendar);
        stblCalendar = new JScrollPane(tblCalendar);
        monthLabel = new JLabel("January");
        add(stblCalendar);
        stblCalendar.setBounds(10, 50, 300, 250);
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year
        currentYear = realYear;


    }
}
