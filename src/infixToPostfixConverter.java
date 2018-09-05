/*
        Haoran He
        23528972
        Assignment 6
        Infix to postfix converter/calculator
 */

import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Stack;

import static java.lang.Character.isDigit;


public class infixToPostfixConverter extends JFrame {
    private JButton convert;
    private JTextArea textArea;
    private JPanel panel;
    private final BorderLayout layout;
    private JScrollPane scrollPane;
    private JPanel pane; //calculator pane

    private JButton[] cal = new JButton[10]; //contains 0 to 9 total 10 elements
    private JButton[] op = new JButton[8]; //contain +-*/%^() total 8 elements
    private JButton[] control = new JButton[2];

    public static boolean checkPrecedence(char op, char check) {
        int levelStack = 0;
        int levelCurrent = 0;
        String order = "+-*/%^";
        if (order.indexOf(check) != -1) {
            if (order.indexOf(check) == 0 || order.indexOf(check) == 1) {
                levelStack = 1;
            } else if (order.indexOf(check) == 2 || order.indexOf(check) == 3 || order.indexOf(check) == 4) {
                levelStack = 2;
            } else levelStack = 3;
        }

        if (order.indexOf(op) != -1 || order.indexOf(check) == 1) {
            if (order.indexOf(op) == 0) {
                levelCurrent = 1;
            } else if (order.indexOf(check) == 2 || order.indexOf(check) == 3 || order.indexOf(check) == 4) {
                levelCurrent = 2;
            } else levelCurrent = 3;
        }

        if (levelStack >= levelCurrent) return true;
        else return false;
    }

    public String startConv() {
        StringBuffer infix = new StringBuffer();
        StringBuffer postfix = new StringBuffer();
        Stack<Character> operant = new Stack<>();

        infix.append(textArea.getText());
        infix.append(")");
        String operator = "+-*/^%";
        operant.push('(');

        while (!operant.empty()) {
            for (int i = 0; i < infix.length(); i++) {
                char currentChar = infix.charAt(i);
                if (isDigit(currentChar)) {
                    postfix.append(currentChar);
                    if(!isDigit(infix.charAt(i+1))) postfix.append(" ");
                } else if (currentChar == '(') {
                    operant.push(currentChar);
                } else if (operator.indexOf(String.valueOf(currentChar)) != -1) {
                    if (checkPrecedence(currentChar, operant.peek())) {
                        postfix.append(operant.pop());
                    }
                    operant.push(currentChar);
                } else if (currentChar == ')') {
                    while (true) {
                        if (operant.peek() != '(') {
                            postfix.append(operant.pop());
                        } else break;
                    }
                    operant.pop();
                }
            }
        }
        return String.valueOf(postfix);
    }

    public infixToPostfixConverter() {
        super("Infix to Postfix Calculator");
        layout = new BorderLayout(5, 5);
        setLayout(layout);
        convert = new JButton("Convert");
        panel = new JPanel();
        textArea = new JTextArea(3, 20);
        textArea.setLineWrap(true);
        scrollPane = new JScrollPane(textArea);

        panel.add(scrollPane);
        panel.add(convert);

        textArea.setFont(textArea.getFont().deriveFont(18f));

        add(panel, BorderLayout.CENTER);

        pane = new JPanel();
        pane.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.VERTICAL;

        cal[0] = new JButton("7");
        c.insets = new Insets(0, 0, 10, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 0;
        pane.add(cal[0], c);
        cal[1] = new JButton("8");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 1;
        c.gridy = 0;
        pane.add(cal[1], c);
        cal[2] = new JButton("9");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 2;
        c.gridy = 0;
        pane.add(cal[2], c);
        cal[3] = new JButton("4");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 1;
        pane.add(cal[3], c);
        cal[4] = new JButton("5");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 1;
        c.gridy = 1;
        pane.add(cal[4], c);
        cal[5] = new JButton("6");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 2;
        c.gridy = 1;
        pane.add(cal[5], c);
        cal[6] = new JButton("1");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 2;
        pane.add(cal[6], c);
        cal[7] = new JButton("2");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 1;
        c.gridy = 2;
        pane.add(cal[7], c);
        cal[8] = new JButton("3");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 2;
        c.gridy = 2;
        pane.add(cal[8], c);
        cal[9] = new JButton("0");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 3;
        pane.add(cal[9], c);

        op[0] = new JButton("/");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.insets = new Insets(0, 5, 10, 0);
        c.gridx = 4;
        c.gridy = 0;
        pane.add(op[0], c);
        op[1] = new JButton("*");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 4;
        c.gridy = 1;
        pane.add(op[1], c);
        op[2] = new JButton("-");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 4;
        c.gridy = 2;
        pane.add(op[2], c);
        op[3] = new JButton("+");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 4;
        c.gridy = 3;
        pane.add(op[3], c);
        op[4] = new JButton("^");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.insets = new Insets(0, 46, 10, 0);
        c.gridx = 5;
        c.gridy = 0;
        pane.add(op[4], c);
        op[5] = new JButton("%");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 5;
        c.gridy = 1;
        pane.add(op[5], c);
        op[6] = new JButton("=");
        c.fill = GridBagConstraints.VERTICAL;
        c.weighty = 0;
        c.gridx = 5;
        c.gridy = 2;
        c.gridheight = 4;
        pane.add(op[6], c);

        control[0] = new JButton("Delete");
        c.insets = new Insets(0, 100, 0, 0);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 6;
        c.gridy = 2;
        pane.add(control[0], c);

        control[1] = new JButton("Clear");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0;
        c.gridx = 6;
        c.gridy = 0;
        pane.add(control[1], c);


        add(pane, BorderLayout.SOUTH);

        control[0].addActionListener(new ActionListener() { //delete
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder tmp = new StringBuilder();
                tmp.append(textArea.getText());
                if (tmp.length() != 0) tmp.setLength(tmp.length() - 1);
                textArea.setText(String.valueOf(tmp));
            }
        });

        control[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                textArea.setText("");
            }
        });

        cal[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + 7);
            }
        });
        cal[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + 8);
            }
        });
        cal[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + 9);
            }
        });
        cal[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + 4);
            }
        });
        cal[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + 5);
            }
        });
        cal[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + 6);
            }
        });
        cal[6].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + 1);
            }
        });
        cal[7].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + 2);
            }
        });
        cal[8].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + 3);
            }
        });
        cal[9].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + 0);
            }
        });

        op[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + '/');
            }
        });
        op[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + '*');
            }
        });
        op[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + '-');
            }
        });
        op[3].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + '+');
            }
        });
        op[4].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + '^');
            }
        });
        op[5].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(textArea.isEditable()==false) textArea.setText("");
                textArea.setEditable(true);
                textArea.setText(textArea.getText() + '%');
            }
        });
        op[6].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String[] lines = textArea.getText().split("\\n");
                    String post = startConv();
                    String ans = String.valueOf((new ScriptEngineManager().getEngineByName("JavaScript").eval(lines[0])));
                if(lines.length==1) textArea.setText(textArea.getText()+"\n"+"Postfix: "+post);
                    textArea.setText(textArea.getText()+"\n"+"Answer: "+ans);
                    textArea.setEditable(false);
                } catch (ScriptException e1) {
                    //e1.printStackTrace();
                    textArea.setText("ERROR");
                    textArea.setEditable(false);
                }
            }
        });

        convert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] lines = textArea.getText().split("\\n");
                if(!(lines.length>=2)){
                String tmp = startConv();
                textArea.setText(textArea.getText() + "\n"+ "Postfix: " + tmp);}
            }
        });
    }

        public static void main(String args[]){
            infixToPostfixConverter run = new infixToPostfixConverter();
            run.setSize(400, 300);
            run.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            run.setVisible(true);
        }
}
