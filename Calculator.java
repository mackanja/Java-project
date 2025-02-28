import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Calculator extends JFrame implements ActionListener {
    private final String [] symbols={
            "π","e","sin","cos","tan",".","+/-","%","÷",
            "e^x","10^x","sin^-1","cos^-1","tan^-1","7","8","9","x",
            "ln","log","x^y","x^3","x^2","4","5","6","-",
            "MOD","n!","x^(1/y)","x^(1/3)","x^(1/2)","1","2","3","+",
            " "," ","(",")","1/x","0","DEL","C","="
    };
    private final JPanel panel = new JPanel(new BorderLayout(10,10));
    private final JPanel btnPanel = new JPanel(new GridLayout(5,5,5,5));
    private final JButton[] btns = new JButton[45];
    private final JTextArea screen = new JTextArea(5,40);
    private double firstNum = 0, secondNum = 0;
    private boolean isOperatorPressed = false;
    private String operatorType = "";

    public Calculator(){
        init();
        secondNum = 0;
    }

    private void init() {
        screen.setBackground(Color.BLACK);
        screen.setForeground(Color.WHITE);
        screen.setEditable(false);
        panel.setBackground(Color.DARK_GRAY);
        btnPanel.setBackground(Color.DARK_GRAY);

        for(int i=0; i<btns.length; i++){
           btns[i] =new JButton(symbols[i]);
           btns[i].setOpaque(true);
           btns[i].setBorderPainted(false);
           btns[i].setForeground(Color.WHITE);
            // Set arithmetic operation buttons (divide, multiply, plus, minus, equals) to orange
            if (symbols[i].equals("÷") || symbols[i].equals("x") || symbols[i].equals("+") || symbols[i].equals("-") || symbols[i].equals("=")) {
                btns[i].setBackground(Color.orange);
            }
            // numbers 0-9 set to blue
            else if ("0123456789".contains(symbols[i])) {
                btns[i].setBackground(Color.LIGHT_GRAY);
            }
            // DEL and . button set to
            else if (symbols[i].equals("DEL") || "C".contains(symbols[i])) {
                btns[i].setBackground(Color.ORANGE);
            }
            // Set other buttons to gray (default color)
            else {
                btns[i].setBackground(Color.GRAY);
            }

           btns[i].addActionListener(this);
           btnPanel.add(btns[i]);

        }
        panel.add(screen, BorderLayout.NORTH);
        panel.add(btnPanel, BorderLayout.CENTER);


        add(panel);
        setSize(350,450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if ("0123456789.".contains(command)) {
            if (isOperatorPressed) {
                screen.setText(command);
                isOperatorPressed = false;
            } else {
                screen.append(command);
            }
        } else if (command.equals("C")) {
            screen.setText("");
            firstNum = secondNum = 0;
            operatorType = "";
        } else if (command.equals("=")) {
            secondNum = Double.parseDouble(screen.getText());

            switch (operatorType) {
                case "+":
                    screen.setText(String.valueOf(firstNum + secondNum));
                    break;
                case "-":
                    screen.setText(String.valueOf(firstNum - secondNum));
                    break;
                case "x":
                    screen.setText(String.valueOf(firstNum * secondNum));
                    break;
                case "÷":
                    if (secondNum != 0) {
                        screen.setText(String.valueOf(firstNum / secondNum));
                    } else {
                        screen.setText("Error");
                    }
                    break;
                case "x^y":
                    screen.setText(String.valueOf(Math.pow(firstNum, secondNum)));
                    break;
                case "x^2":
                    screen.setText(String.valueOf(Math.pow(firstNum, 2)));
                    break;
                case "x^3":
                    screen.setText(String.valueOf(Math.pow(firstNum, 3)));
                    break;
                case "MOD":
                    screen.setText(String.valueOf(firstNum % secondNum));
                    break;
                case "1/x":
                    if (firstNum != 0) {
                        screen.setText(String.valueOf(1 / firstNum));
                    } else {
                        screen.setText("Error");
                    }
                    break;
                case "sin":
                    screen.setText(String.valueOf(Math.sin(Math.toRadians(firstNum))));
                    break;
                case "cos":
                    screen.setText(String.valueOf(Math.cos(Math.toRadians(firstNum))));
                    break;
                case "tan":
                    screen.setText(String.valueOf(Math.tan(Math.toRadians(firstNum))));
                    break;
                case "sin^-1":
                    if (firstNum >= -1 && firstNum <= 1) {
                        screen.setText(String.valueOf(Math.toDegrees(Math.asin(firstNum))));
                    } else {
                        screen.setText("Error");
                    }
                    break;
                case "cos^-1":
                    if (firstNum >= -1 && firstNum <= 1) {
                        screen.setText(String.valueOf(Math.toDegrees(Math.acos(firstNum))));
                    } else {
                        screen.setText("Error");
                    }
                    break;
                case "tan^-1":
                    screen.setText(String.valueOf(Math.toDegrees(Math.atan(firstNum))));
                    break;
                case "ln":
                    if (firstNum > 0) {
                        screen.setText(String.valueOf(Math.log(firstNum)));
                    } else {
                        screen.setText("Error");
                    }
                    break;
                case "log":
                    if (firstNum > 0) {
                        screen.setText(String.valueOf(Math.log10(firstNum)));
                    } else {
                        screen.setText("Error");
                    }
                    break;
                case "n!":
                    if (firstNum >= 0 && firstNum == (int) firstNum) {
                        screen.setText(String.valueOf(factorial(firstNum)));
                    } else {
                        screen.setText("Error");
                    }
                    break;
                case "x^(1/y)":
                    if (secondNum != 0) {
                        screen.setText(String.valueOf(Math.pow(firstNum, 1 / secondNum)));
                    } else {
                        screen.setText("Error");
                    }
                    break;
                case "x^(1/3)":
                    screen.setText(String.valueOf(Math.cbrt(firstNum)));
                    break;
                case "x^(1/2)":
                    if (firstNum >= 0) {
                        screen.setText(String.valueOf(Math.sqrt(firstNum)));
                    } else {
                        screen.setText("Error");
                    }
                    break;
                case "10^x":
                    firstNum = Double.parseDouble(screen.getText());
                    screen.setText(String.valueOf(Math.pow(10, firstNum)));
                    break;
                default:
                    break;
            }
            operatorType = "";
        } else if (command.equals("+") || command.equals("-") || command.equals("x") || command.equals("÷")
                || command.equals("x^y") || command.equals("x^2") || command.equals("x^3") || command.equals("MOD")
                || command.equals("1/x") || command.equals("sin") || command.equals("cos") || command.equals("tan")
                || command.equals("sin^-1") || command.equals("cos^-1") || command.equals("tan^-1")||command.equals("10^x")
                || command.equals("ln") || command.equals("log") || command.equals("n!")
                || command.equals("x^(1/y)") || command.equals("x^(1/3)") || command.equals("x^(1/2)")) {

            operatorType = command;
            firstNum = Double.parseDouble(screen.getText());
            isOperatorPressed = true;
            //DEL button
        } else if (command.equals("DEL")) {
            String currentText = screen.getText();
            if (!currentText.isEmpty()) {
                screen.setText(currentText.substring(0, currentText.length() - 1));
            }
        }
        // Pi button
        else if (command.equals("π")) {
            screen.setText(String.valueOf(Math.PI)); // Display the value of Pi
        }
        // Euler's number (e) button
        else if (command.equals("e")) {
            screen.setText(String.valueOf(Math.E)); // Display the value of e
        }
        // e^x button
        else if (command.equals("e^x")) {
            firstNum = Double.parseDouble(screen.getText());
            screen.setText(String.valueOf(Math.exp(firstNum))); // e raised to the power of x
        }
}

    private int factorial(double firstNum) {
        int result = 1;
        for (int i = 1; i <= firstNum; i++) {
            result *= i;
        }
        return result;
    }


    public static void main(String[] args){
        new Calculator();

    }

}
