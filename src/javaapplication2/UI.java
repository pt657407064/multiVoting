/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/**
 *
 * @author Tao
 */
public class UI extends JFrame implements ActionListener{
    
    /* 
        Server UI components 
    */
    private final JButton startServer;
    private final JButton addVote;
    private final JButton update;
    private final JButton closeVoting;
    
    private final JLabel questionLabel;
    private static JLabel[] choices;
    
    public static JPanel resultPanel;
    private final JPanel buttonsPanel;    
    public static ArrayList<Integer> voteNum = new ArrayList<>();
    public static ArrayList<String> voteText = new ArrayList<>();
    //Constructor
    public UI(String Title){
        super(Title);
        
        questionLabel = new JLabel();
        
        startServer = new JButton("START SERVER");
        startServer.addActionListener(this);
        
        
        addVote = new JButton("ADD VOTE");
        addVote.addActionListener(this);
        
        update = new JButton("UPDATE");
        update.addActionListener(this);
        
        closeVoting = new JButton("CLOSE VOTING");
        closeVoting.addActionListener(this);
        
        buttonsPanel = new JPanel ();
        buttonsPanel.setSize(450,200);
        buttonsPanel.setLayout(new GridLayout(1,4,10,10));
        
        buttonsPanel.add(startServer);
        buttonsPanel.add(update);
        buttonsPanel.add(closeVoting);
        buttonsPanel.add(addVote);
    
        this.setLayout(new BorderLayout()); 
        this.add(resultPanelUI(),BorderLayout.NORTH); 
        this.add(buttonsPanel, BorderLayout.SOUTH);
       // this.setSize(500, 500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.pack();
    }
    public JPanel resultPanelUI()
    {
        
        int size;
        if(choices == null) //The choices label are null when initiate so the size is 0
        {
            size = 0;
        }
        else
        {
            size = choices.length;
        }
        resultPanel = new JPanel();
        resultPanel.add(questionLabel);
        resultPanel.setSize(450,300);
        if(choices == null)   //The choices label are null when initiate so the size is 0
        {
            resultPanel.add(new JLabel("Please create a voting survey"));
        }
        
        return resultPanel;
    }
    
    public static void updateResultPanel(String question, String... answers)
    {
        //ans = answers;
        for(int i = 0;i < answers.length;i++)
        {
            voteNum.add(0);
            voteText.add(answers[i]);
            choices[0] = new JLabel(answers[i]);
            
        }
        //int count = answers.length;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
               
                UI.resultPanel.removeAll(); 
                resultPanel.setLayout(new GridLayout(answers.length+1,1));
                UI.resultPanel.add(new JLabel(question));
                for(String ans: answers){
                    int i = 0;
                UI.resultPanel.add(new JLabel(ans + " \t\t" + voteNum.get(i)));
                i++;
                }
                
                UI.resultPanel.revalidate();
                UI.resultPanel.repaint();
                //UI.resultPanel.add(new JLabel("Hey"));
                
            }
        }
        );
        //this.revalidate();
        //this.repaint();
    }
    
    public JPanel resetPanel()
    {
        JPanel empty = new JPanel ();
        empty.add(new JLabel("Your voting has been closed"));
        return empty;
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource()== closeVoting)
        {
            this.remove(resultPanel); //remove result panel
            this.add(resetPanel(),BorderLayout.NORTH); //add reset panel
            this.revalidate();    //revalidate automatically not manually      
            this.repaint(); // repaint
            
           // updateResultPanel("test");
        }
        else if (ae.getSource()== addVote)
        {
            new AddVoteUI();
        }
        else if(ae.getSource() == startServer)
        {
            try {
                new Server();
                startServer.setEnabled(false);
            } catch (IOException ex) {
                Logger.getLogger(UI.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
    public static void updateVote(int num)
    {
        voteNum.set(num,voteNum.get(num)+1);
    }
}
