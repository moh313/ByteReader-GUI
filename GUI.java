package ASSIGNMENT;

import java.awt.TextArea;
import javax.swing.*;
import java.io.File;

import javax.swing.JFileChooser;

public class GUI extends javax.swing.JFrame {

	private static final long serialVersionUID = 1L;
	public javax.swing.JLabel allFilesNames;
    public javax.swing.JLabel fileDirectory;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenu jMenuFile;
    private javax.swing.JMenuItem jMenuItemOpen;
    private javax.swing.JMenuItem jMenuItemOpenFolder;
    private javax.swing.JMenuItem jMenuItemLoadP;
    private javax.swing.JMenuItem jMenuAbout;
    private javax.swing.JMenuItem jMenuExit;
    public static TextArea patternInfo;
    public javax.swing.JLabel total_files;

    JFileChooser fileChooser = new JFileChooser();
    String textToSearch = "file";
    String all_names = "";
    ReadFile readFile = new ReadFile();
    byte[] patternABC = new byte[]{0x41, 0x42, 0x43};
    byte[] patternXYZ = new byte[]{0x58, 0x59, 0x5A};

    public GUI() {
    	initializeComponents();
    }

    /**
     * All components are initialised here each component needs to be
     * Initialised before using, other wise it will throw exception
     *
     * @return null
     */
    public void initializeComponents() {                                        // creates the features used on GUI

        patternInfo = new TextArea();
        fileDirectory = new javax.swing.JLabel("File Directory: ");
        allFilesNames = new javax.swing.JLabel();
        total_files = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenuFile = new javax.swing.JMenu();
        jMenuItemOpen = new javax.swing.JMenuItem();
        jMenuItemOpenFolder = new javax.swing.JMenuItem();
        jMenuItemLoadP = new javax.swing.JMenuItem();
        jMenuExit = new javax.swing.JMenuItem();
        jMenuAbout = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);               //allows you to exit GUI

        fileDirectory.setBounds(60, 30, 400, 30); // x,y , width, height
        total_files.setBounds(450, 30, 400, 30);
        patternInfo.setBounds(60, 90, 600, 400);
        allFilesNames.setBounds(60, 120, 400, 30);

        patternInfo.setFont(new java.awt.Font("Tahoma", 0, 14));                          
        fileDirectory.setFont(new java.awt.Font("Tahoma", 0, 14));
        allFilesNames.setFont(new java.awt.Font("Tahoma", 0, 14));
        total_files.setFont(new java.awt.Font("Tahoma", 0, 14));
        patternInfo.setEnabled(false);
        add(patternInfo);
        add(fileDirectory);
        add(allFilesNames);
        add(total_files);

        jMenuFile.setText("File");
        jMenuItemOpen.setText("Open");
        jMenuItemOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                fileChooser.showOpenDialog(null);
                File f = fileChooser.getSelectedFile();                        // gives ability to launch a file from dir
                
                if (f != null) {                                                //when file is selected 
                    String txt = "Filename: " + f.getName() + "\n";
                    String result = readFile.checkPatternsInFile(f, patternABC);
                    String result1 = readFile.checkPatternsInFile(f, patternXYZ);

                    txt += result + "\n";
                    txt += result1 + "\n";
                    patternInfo.setText(txt);
                }
                
            }
        });
        
        jMenuFile.add(jMenuItemOpen);
        
        jMenuItemOpenFolder.setText("Open Folder");
        jMenuItemOpenFolder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);          // so only folder is selected
                fileChooser.showOpenDialog(null);

                File currentDirectory = fileChooser.getSelectedFile();
                if (currentDirectory != null) {

                	File[] files = currentDirectory.listFiles();
                    fileDirectory.setText("File Directory: " + currentDirectory.getAbsolutePath());
                    
                    String txt = "";
                    for (File f : files) {
                        txt ="Filename: " + f.getName() + "\n";
                        if (f.isFile() && f != null) {                                           // displays only those with patterns
                            String result = readFile.checkPatternsInFile(f, patternABC);
                            String result1 = readFile.checkPatternsInFile(f, patternXYZ);
                            total_files.setText("(" + files.length + " files)");

                            txt += result + "\n";
                            txt += result1 + "\n"; 
                            //ensures display of filename and patterns found
                        }
                    }
                    patternInfo.setText(txt);
                }
            }

        });
        jMenuFile.add(jMenuItemOpenFolder);       
        
        
        
        
        /////////////////////////////////////////////////////////////////
        jMenuItemLoadP.setText("Load Pattern File");                               
        jMenuItemLoadP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                fileChooser.showOpenDialog(null);
                File f = fileChooser.getSelectedFile();
                
                if (f != null) {                                                   
                	
                	String result = readFile.checkPatternsInFile(f, patternABC);
                    String result1 = readFile.checkPatternsInFile(f, patternXYZ);

                    patternInfo.setText(result + "\n" + result1);
                  
                }
                
            }
        });
        jMenuFile.add(jMenuItemLoadP);                    
        /////////////////////////////////////////////////////////////////
       
  
        

        jMenuAbout.setText("About");                                              // simple text displayed
        jMenuFile.add(jMenuAbout);
        jMenuAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {

                JOptionPane.showMessageDialog(null, "This program is a graphical user interface, based on AWT and Swing libraries. "
                		+ "The intention is for it to specifically read bytes within a file\n" 
                + "Copright is prohibited. Author: Muhammad Hasan (c3532162)\n");
            }
        });
        

        jMenuExit.setText("Exit");
        jMenuFile.add(jMenuExit);

        jMenuExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                JOptionPane.showMessageDialog(null, "Exiting.............\n");
                System.exit(0);                                                     //exits the GUI
            }
        });
        
        

        // for setting the menu items to File
        jMenuBar1.add(jMenuFile);
        setJMenuBar(jMenuBar1);
        /**
         * setting the frame layout which is null and resizable, a user can
         * resize the frame, i-e title is the application title will be shown on
         * the title bar of Application
         */
        setLayout(null);
        setResizable(true);
        setTitle("The Byte Reader");
        setBounds(200, 80, 800, 600);

    }
}
