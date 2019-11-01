

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;

public class Calculator extends JFrame {
    private JPanel TopPanel;
    private JPanel CentrPanel;
    private  String [][] buttonnames={{"7","8","9","+","c"},{"4","5","6","-","ce"},{"1","2","3","*","x^"},{"0","/",".","=","Gip"}};
    private HashSet<String> nums;
    private HashSet<String> mathoper;
    private JButton[][] calcbuttons;
    private JTextField display = new JTextField();
    private JTextField logdisplay = new JTextField();
    private Font BigFont=new Font("TimesRoman", Font.BOLD, 15);
    public static void main(String[] args) {
        new Calculator();
    }
    public Calculator(){
        JFrame frame = new JFrame();

        frame.setTitle("Мой Калькулятор");
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        TopPanel =createJpanel(frame,BorderLayout.NORTH,new GridLayout(2,0,0,0));
        CentrPanel =createJpanel(frame,BorderLayout.CENTER,new GridLayout(buttonnames.length,buttonnames[0].length,1,1));
        calcbuttons = createbuttons(buttonnames,new CalcAction(),CentrPanel,BigFont);
        TopPanel.add(logdisplay);
        TopPanel.add(display);
        TopPanel.setPreferredSize(new Dimension(640,50));


        display.setHorizontalAlignment(JTextField.RIGHT);
        display.setEditable(false);
        display.setFont(BigFont);

        logdisplay.setHorizontalAlignment(JTextField.RIGHT);
        logdisplay.setEditable(false);
        logdisplay.setFont(BigFont);



        nums=fillNums();
        mathoper=fillMathoper();

        frame.setSize(500,250);
        frame.setVisible(true);

    }
    private HashSet<String> fillNums (){
        HashSet<String> nums = new HashSet<>();
        for (int i = 0; i<10; i++){
            nums.add(String.valueOf(i));
        }
        nums.add(".");
        return nums;
    }
    private HashSet<String> fillMathoper(){
        HashSet<String> mathoper = new HashSet<>();
        mathoper.add("+");
        mathoper.add("-");
        mathoper.add("*");
        mathoper.add("/");
        mathoper.add("x^");
        mathoper.add("Gip");
       return mathoper;
    }
    private JPanel createJpanel(JFrame frame,String layout,LayoutManager layoutManager ){
        JPanel panel =new JPanel();
        frame.add(panel,layout);
        panel.setLayout(layoutManager);
        return panel;
    }
    private JButton[][] createbuttons(String[][] buttonnames,ActionListener listener,JPanel panel,Font font){

        JButton[][] calcbuttons= new JButton[buttonnames.length][buttonnames[0].length];
        for (int i=0;i<calcbuttons.length;i++){
            for (int j=0;j<calcbuttons[0].length;j++){
                JButton button= new JButton(buttonnames[i][j]);
                button.setMaximumSize(new Dimension(200,40));
                button.addActionListener(listener);
                button.setFont(font);
                button.setFont(BigFont);
                calcbuttons[i][j] = button;
                panel.add(calcbuttons[i][j]);
            }
        }
        return calcbuttons;
    }
    class CalcAction implements ActionListener{
        String precommand="";
        String mathoperator;
        String dispview="";
        String logdisplaytext="";
        double result =0;
        double secondDouble=0;
        @Override
        public void actionPerformed(ActionEvent e) {
           String command = e.getActionCommand();
           if(nums.contains(command)){
               System.out.println("1");
               if(command.equals(".")&&precommand.equals(".")){
                   precommand=command;
               }else if (dispview.contains(".")&& command.equals(".")){
                   precommand=command;
               }
               else if (precommand.equals("")){
                   System.out.println(1.1);
                   dispview =dispview+command;

                   logdisplaytext=logdisplaytext+command;
                   logdisplay.setText(logdisplaytext);

                   display.setText(dispview);
                   precommand=command;
               }else if (precommand.equals("=")){
                   System.out.println(1.2);
                   dispview="";
                   logdisplaytext="";
                   dispview =dispview+command;
                   display.setText(dispview);
                   logdisplaytext=logdisplaytext+command;
                   precommand=command;
               } else if(nums.contains(precommand)){
                   System.out.println(1.3);
                   dispview =dispview+command;
                   display.setText(dispview);
                   logdisplaytext=logdisplaytext+command;
                   logdisplay.setText(logdisplaytext);
                   precommand=command;
               } else if(mathoper.contains(precommand)){
                   System.out.println(1.4);
                   System.out.println("Мы тут");
                   dispview="";
                   dispview= dispview+command;
                   display.setText(dispview);
                   System.out.println("дада проблема туту");
                   System.out.println(logdisplaytext);
                   logdisplaytext=logdisplaytext+command;
                   logdisplay.setText(logdisplaytext);
                   precommand=command;
               }
           }
           else if(mathoper.contains(command)){
               System.out.println("2");

                if(precommand.equals("")){
                   precommand="";
                   mathoperator="";
                }else if(mathoper.contains(command)&&mathoper.contains(precommand)){
                   System.out.println(2.1);
                   precommand=command;


               }else if(precommand.equals("=")){
                   System.out.println(2.2);
                   logdisplaytext=result+command;
                   logdisplay.setText(logdisplaytext);
                   mathoperator=command;
                   precommand=command;
               } else if (mathoper.contains(mathoperator)) {
                   System.out.println(2.3);
                   try {
                       secondDouble = Double.parseDouble(display.getText());
                       result=resultir(mathoperator, result, secondDouble);
                       display.setText(String.valueOf(result));
                       logdisplaytext=logdisplaytext+command;
                       logdisplay.setText(logdisplaytext);
                       mathoperator=command;
                       precommand=command;

                   } catch (Exception ex) {
                       display.setText("Некорректный ввод");
                       logdisplay.setText("Некорректный ввод");
                       mathoperator="";
                       precommand="";
                   }
               }else if(nums.contains(precommand)) {
                   System.out.println(2.4);
                   try {
                       logdisplaytext=logdisplaytext+command;
                       logdisplay.setText(logdisplaytext);
                       mathoperator = command;
                       result = Double.parseDouble(display.getText());
                       precommand =command;
                   }catch (Exception ex){
                       display.setText("Некорректный ввод");
                   }
               }
           }
           else if(command.equals("=")){
               System.out.println("3");

               if (precommand.equals("=")) {

               }else if (nums.contains(precommand)&& !mathoper.contains(mathoperator)) {
                   command = precommand;
               }else if (mathoper.contains(mathoperator)){
                    System.out.println(3.1);
                   try{

                       logdisplaytext ="";
                       logdisplay.setText(logdisplaytext);
                       secondDouble = Double.parseDouble(display.getText());
                       result=resultir(mathoperator,result,secondDouble);
                       display.setText(String.valueOf(result));
                       mathoperator="";
                       precommand = command;
                   }catch (Exception ex){
                       display.setText("Неправильный ввод");
                       mathoperator="";
                       precommand = command;
                   }
                }
               System.out.println(result);
           }else if(command.equals("ce")){
               mathoperator="";
               precommand="";
               dispview="";
               logdisplaytext="";
               display.setText(dispview);
               logdisplay.setText(logdisplaytext);

           }else if(command.equals("c")){
               System.out.println(4);
               if (mathoper.contains(precommand)){
                   System.out.println(4.1);
               }else if(nums.contains(precommand)){
                   System.out.println(4.2);
                   char[] dispviewChar = dispview.toCharArray();
                   char[] logdispviewChar = logdisplaytext.toCharArray();
                   dispview="";

                   if (dispviewChar.length>0){
                       logdisplaytext="";
                       for (int i =0; i<logdispviewChar.length-1;i++){
                           logdisplaytext+=String.valueOf(logdispviewChar[i]);
                       }
                   }
                   for (int i =0; i<dispviewChar.length-1;i++){
                       dispview+=String.valueOf(dispviewChar[i]);
                   }

                   display.setText(dispview);
                   logdisplay.setText(logdisplaytext);
               }
           }
        }

    }
    public double resultir(String mathoperator, double result,double secondDouble){

        double res=0;
        if (mathoperator.equals("+"))
            res=result+secondDouble;
        else if(mathoperator.equals("-"))
            res=result-secondDouble;
        else if(mathoperator.equals("*"))
            res=result*secondDouble;
        else if(mathoperator.equals("/"))
            res=result/secondDouble;
        else if(mathoperator.equals("x^"))
            res = Math.pow(result,secondDouble);
        else if (mathoperator.equals("Gip"))
            res=  Math.hypot(result,secondDouble);
        return (double) res;
    }
}
