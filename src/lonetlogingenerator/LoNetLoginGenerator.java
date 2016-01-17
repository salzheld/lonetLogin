package lonetlogingenerator;

import com.sun.java.swing.plaf.windows.WindowsLookAndFeel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableColumnModel;

/**
 *
 * @author Joern
 */
public class LoNetLoginGenerator {

    private final String dbName = "Danis61128";
    private final String dbUser = "danisroot61128";
    private final String dbPass = "tischTuch";
    private String sqlStatement =
        "SELECT" +
        " g.Bezeichnung,p.Nachname,p.Rufname,p.Geschlecht from person p" +
        " inner join jahrgangsdaten j on p.Id=j.SchuelerId" +
        " inner join gruppe g on j.GruppeId=g.Id where" +
        " j.Status='0' and g.Bezeichnung='5a' or " +
        " j.Status='0' and g.Bezeichnung='5b' or " +
        " j.Status='0' and g.Bezeichnung='5c' or " +
        " j.Status='0' and g.Bezeichnung='5d' or " +
        " j.Status='0' and g.Bezeichnung='5e' or " +

        " j.Status='0' and g.Bezeichnung='6a' or " +
        " j.Status='0' and g.Bezeichnung='6b' or " +
        " j.Status='0' and g.Bezeichnung='6c' or " +
        " j.Status='0' and g.Bezeichnung='6d' or " +
        " j.Status='0' and g.Bezeichnung='6e' or " +

        " j.Status='0' and g.Bezeichnung='7a' or " +
        " j.Status='0' and g.Bezeichnung='7b' or " +
        " j.Status='0' and g.Bezeichnung='7c' or " +
        " j.Status='0' and g.Bezeichnung='7d' or " +
        " j.Status='0' and g.Bezeichnung='7e' or " +

        " j.Status='0' and g.Bezeichnung='8a' or " +
        " j.Status='0' and g.Bezeichnung='8b' or " +
        " j.Status='0' and g.Bezeichnung='8c' or " +
        " j.Status='0' and g.Bezeichnung='8d' or " +
        " j.Status='0' and g.Bezeichnung='8e' or " +

        " j.Status='0' and g.Bezeichnung='9a' or " +
        " j.Status='0' and g.Bezeichnung='9b' or " +
        " j.Status='0' and g.Bezeichnung='9c' or " +
        " j.Status='0' and g.Bezeichnung='9d' or " +
        " j.Status='0' and g.Bezeichnung='9e' or " +

        " j.Status='0' and g.Bezeichnung='10a' or " +
        " j.Status='0' and g.Bezeichnung='10b' or " +
        " j.Status='0' and g.Bezeichnung='10c' or " +
        " j.Status='0' and g.Bezeichnung='10d' or " +
        " j.Status='0' and g.Bezeichnung='10e' " +
        " order by g.Bezeichnung, p.Geschlecht, p.Nachname;";

    private JFrame view;
    private MyTableModel tableModel;
    private JTable loginTable;
    private InputStreamReader file;
    private ArrayList<Student> arrayListStudents;
    
    public LoNetLoginGenerator() {
        arrayListStudents = new ArrayList<>();
        tableModel = new MyTableModel();
        loginTable = initTable(tableModel);
        view = new JFrame("Webweaver Login Erzeuger - (c) 2012 - J. Kretzschmar");
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.add(initGUI());
        view.pack();
        view.setSize(610, 768);
        view.setVisible(true);
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[])
    {
        try {
            UIManager.setLookAndFeel(new WindowsLookAndFeel());
        } catch (Exception e) {}

        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                LoNetLoginGenerator loNetLoginGenerator = new LoNetLoginGenerator();
            }
        });
    }
    
    
    private JPanel initGUI() {
        MySQL sql = new MySQL(dbName, dbUser, dbPass);
        ArrayList<Student> students;
        arrayListStudents = sql.getStudents(sqlStatement);
        tableModel.addStudents( arrayListStudents );
        
        JPanel mainPanel = new JPanel(new BorderLayout()); 
        mainPanel.setBorder(new EmptyBorder(10,10,10,10));

        JButton printButton = new JButton("Drucken");
        printButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                printLogins();
            }
        });
        
        JPanel buttonPanel = new JPanel(new GridLayout(0, 2));
        buttonPanel.add(printButton);
        
        mainPanel.add(new JScrollPane(initTable(tableModel)), BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        return mainPanel;
    }
    
    private JTable initTable(MyTableModel model) {
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(false);
        table.setIntercellSpacing(new Dimension(5, 5));
        //table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        table.setColumnSelectionAllowed(true);
        table.setRowSelectionAllowed(true);

        TableColumnModel columnModel = table.getColumnModel();
        // Die einzelnen Spalten ansprechen und die Grösse setzen
        columnModel.getColumn(0).setPreferredWidth(50);
        columnModel.getColumn(1).setPreferredWidth(250);
        columnModel.getColumn(2).setPreferredWidth(90);
        columnModel.getColumn(3).setPreferredWidth(200);
        
        return table;
    }

    private void printLogins() {
        printKlasse("5a");
        printKlasse("5b");
        printKlasse("5c");
        printKlasse("5d");
        printKlasse("5e");
    }
    
    private void printKlasse(String klasse) {
        MyTableModel printModel = new MyTableModel();
        JTable printTable = initTable(printModel);
        ArrayList<Student> printAL = new ArrayList<>();

        for( Student s : arrayListStudents) {
            if(s.getKlasse().equals(klasse)) {
                printAL.add(s);
            }
        }
        if(printAL.isEmpty()) {
            return;
        }
        
        printModel.addStudents(printAL);

        printTable.setSize(800, printTable.getRowCount() * printTable.getRowHeight());
        printTable.getTableHeader().setSize(printTable.getWidth(), 20);
        printTable.getColumnModel().getColumn(0).setWidth(50);
        printTable.getColumnModel().getColumn(1).setWidth(250);
        printTable.getColumnModel().getColumn(2).setWidth(60);
        printTable.getColumnModel().getColumn(3).setWidth(200);

        try {
            printTable.print(JTable.PrintMode.FIT_WIDTH, null, null, false, null, true);
        } catch (PrinterException ex) {
            Logger.getLogger(LoNetLoginGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
}