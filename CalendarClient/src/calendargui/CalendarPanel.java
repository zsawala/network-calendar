
package calendargui;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.GregorianCalendar;
import javax.swing.JButton;
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
    static JButton left;
    static JButton right;
    public CalendarPanel(){
        setLayout(null);
        mtblCalendar = new DefaultTableModel()
    {
         public boolean isCellEditable(int row, int column)
        {
            return false;//This causes all cells to be not editable
        }
     };
        tblCalendar = new JTable(mtblCalendar);
        stblCalendar = new JScrollPane(tblCalendar);
        monthLabel = new JLabel();
        right = new JButton("Next");
        right.addMouseListener(new MouseAdapter() {
        public void mouseClicked(MouseEvent e) {
                if(currentMonth == 11){
                    currentMonth=0;
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
                }else{
                    currentMonth = currentMonth -1;
                }
                refreshCalendar(currentMonth, currentYear);
            }
        });
        add(stblCalendar);
        add(monthLabel);
        add(right);
        add(left);
        stblCalendar.setBounds(10, 50, 900, 650);
        monthLabel.setBounds(10,0,100,20);
        right.setBounds(920, 100, 100, 20);
        left.setBounds(920, 150, 100, 20);
        GregorianCalendar cal = new GregorianCalendar(); //Create calendar
        realDay = cal.get(GregorianCalendar.DAY_OF_MONTH); //Get day
        realMonth = cal.get(GregorianCalendar.MONTH); //Get month
        realYear = cal.get(GregorianCalendar.YEAR); //Get year
        currentMonth = realMonth; //Match month and year
        currentYear = realYear;
        String[] headers = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; //All headers
        for (int i=0; i<7; i++){
        mtblCalendar.addColumn(headers[i]);
        }
        
        tblCalendar.setRowHeight(75);   
        mtblCalendar.setColumnCount(7); 
        mtblCalendar.setRowCount(6);
        
        refreshCalendar(realMonth,realYear);


    }
            //Odświeżanie miesiąca 
        public static void refreshCalendar(int month, int year){
            String[] months = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
            monthLabel.setText(months[month]);
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
            mtblCalendar.setValueAt(i, row, column);
        }


        }
        
      
        
        
}
