/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Enumeration;
import javafx.scene.control.RadioButton;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;


public class AddVoteUI extends JFrame implements ActionListener{

    private final JLabel questionLabel;
    private final JTextField question;
    private final JButton questionOK;
    private final JButton questionEdit;
    private final JButton add;
    private final JButton remove;
    
    
    private final JButton clear;
    private final JButton done;
    private final JPanel buttonPanel;
    
    private ArrayList<JRadioButton> buttons = new ArrayList<>();
    private ArrayList<JLabel> labels = new ArrayList<>();
    
    private JPanel panel1;
    private JPanel choicePanel;
    
    ButtonGroup bg = new ButtonGroup();
    
    
    
    public AddVoteUI()
    {
        super("Add Vote");
        
        questionLabel = new JLabel("Question: ");
        question = new JTextField(10);
        
        questionOK = new JButton("OK");
        questionOK.addActionListener(this);
        questionEdit = new JButton("Edit");
        questionEdit.addActionListener(this);
        add = new JButton("ADD");
        add.addActionListener(this);
        remove = new JButton("REMOVE");
        remove.addActionListener(this);
        
        panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc =new GridBagConstraints();
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(questionLabel,gbc);
        

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(question,gbc);
        
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(questionOK,gbc);
        
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.25;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel1.add(questionEdit,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        //gbc.weightx = 0.5;
        panel1.add(add,gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        panel1.add(remove,gbc);
        
        
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2,1,10,10));
        
        done = new JButton("Done");
        done.addActionListener(this);
        clear = new JButton("RESET");
        clear.addActionListener(this);
        buttonPanel.add(done);
        buttonPanel.add(clear);
        
        choicePanel = new JPanel();
        choicePanel.setLayout(new GridLayout(2,1));
        this.setLayout(new BorderLayout());
        this.add(panel1,BorderLayout.NORTH);
        this.add(choicePanel,BorderLayout.CENTER);
        this.add(buttonPanel,BorderLayout.SOUTH);
        this.pack();     
        this.setSize(500,500);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if(ae.getSource() == this.questionOK)
        {
            this.question.setEditable(false);
            this.questionOK.setEnabled(false);
        }
        else if(ae.getSource() == this.questionEdit){
            this.question.setEditable(true);
            this.questionOK.setEnabled(true);
        }
        else if(ae.getSource() == this.add){
            addChoice();
        }
        else if(ae.getSource() == this.remove){
            removeChoice();
            System.out.println("Reset");
        }
        
        else if(ae.getSource() == this.done)
        {
            finishUp();
        }
        else if(ae.getSource() == this.clear)
        {
            resetEverything();
        }

    }

    private void addChoice() {
        SwingUtilities.invokeLater(new Runnable() {
            private JTextField choiceEn;
            private JButton ok;
            @Override
            public void run() {
                choiceEn = new JTextField(10);
                choicePanel.add(choiceEn);
                ok = new JButton("OK");
                choicePanel.setLayout(new GridLayout(buttons.size()+2,2));
                ok.addActionListener(new finishAdding());
                choicePanel.add(ok);
                choicePanel.revalidate();
                choicePanel.repaint();
                add.setEnabled(false);
            }
            class finishAdding implements ActionListener
            {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    
                    choicePanel.remove(choiceEn);
                    choicePanel.remove(ok);
                    JRadioButton rb1 = new JRadioButton();
                    JLabel lb = new JLabel(choiceEn.getText());
                    buttons.add(rb1);
                    labels.add(lb);
                    bg.add(rb1);
                    choicePanel.add(rb1);
                    choicePanel.add(lb);
                    choicePanel.revalidate();
                    choicePanel.repaint();
                    add.setEnabled(true);
                }
            }
        }
        );
    }

    private void removeChoice() throws ConcurrentModificationException {
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                //JRadioButton selectedbtn;
                for(JRadioButton button: buttons)
                {
                    int index = 0;
                    if(button.isSelected())
                    {
                        try{
                       JRadioButton selectedbtn = button;
                       bg.remove(button);
                       buttons.remove(button);
                       JLabel selectedL = labels.remove(index);//set to selected object
                       choicePanel.remove(selectedbtn);
                       choicePanel.remove(selectedL);
                       choicePanel.revalidate();
                       choicePanel.repaint();
                       }catch(Exception e){
                           System.out.println("Remomved");
                       }
                    }
                    index++;
                }
                    
                    //choicePanel.remove();
                    choicePanel.revalidate();
                    choicePanel.repaint();
               
                
            }
        });
    }

    private void finishUp() {
        String[] choiceText = new String[labels.size()];
        for(int i = 0; i < labels.size();i++)
        {
            choiceText[i] = labels.get(i).getText();
        }
        if(choiceText.length != 0){
            UI.updateResultPanel(question.getText(), choiceText);
            this.dispose();
        }
        else{
            JOptionPane.showMessageDialog(null, "Choice cant be empty");
        }
        
    }

    private void resetEverything() {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                choicePanel.removeAll();
                question.setText("");
                question.setEditable(true);
                questionOK.setEnabled(true);
                choicePanel.revalidate();
                choicePanel.repaint();
                add.setEnabled(true);
                
                
            }
        });
    }

   
}
