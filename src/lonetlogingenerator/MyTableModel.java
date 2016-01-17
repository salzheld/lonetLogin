package lonetlogingenerator;

import java.util.ArrayList;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.TableModel;

/**
 *
 * @author Jörn
 */
public class MyTableModel implements TableModel {

    private ArrayList<Student> arrayListStudent;
    private ArrayList<TableModelListener> tableListener;

    public MyTableModel() {
        arrayListStudent = new ArrayList<>();
        tableListener = new ArrayList<>();
    }

    public void addStudents( ArrayList<Student> student ) {
        arrayListStudent.clear();
        for( Student s : student ) {
            addStudent(s);
        }
    }
    
    public void addStudent( Student student ){
        // Das wird der Index der Fächer werden
        int index = arrayListStudent.size();
        arrayListStudent.add( student );

        // Jetzt werden alle Listeners benachrichtigt

        // Zuerst ein Event, "neue Row an der Stelle index" herstellen
        TableModelEvent e = new TableModelEvent( this, index, index,
                TableModelEvent.ALL_COLUMNS, TableModelEvent.INSERT );

        // Nun das Event verschicken
        for(int i = 0, n = tableListener.size(); i<n; i++){
            (tableListener.get(i)).tableChanged(e);
        }
    }

    @Override
    public int getRowCount() {
        return arrayListStudent.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex){
            case 0: return "Klasse";
            case 1: return "Login";
            case 2: return "Passwort";
            case 3: return "Name";
            default: return null;
        }
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        switch(columnIndex) {
            case 0: return String.class;
            case 1: return String.class;
            case 2: return String.class;
            case 3: return String.class;
            default: return null;
        }
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student = arrayListStudent.get(rowIndex);

        switch(columnIndex){
            case 0: return student.getKlasse();
            case 1: return student.getLogin();
            case 2: return student.getPasswort();
            case 3: return student.getName();
            default: return null;
        }
    }

    @Override
    public void addTableModelListener(TableModelListener l) {
        tableListener.add(l);
    }

    @Override
    public void removeTableModelListener(TableModelListener l) {
        tableListener.remove(l);
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}