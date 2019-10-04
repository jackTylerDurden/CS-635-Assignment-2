import java.util.*;
public class PostfixEvaluator{
    public static String evaluateExpression(String currentCell, String postfixExpression){
        // Stack<String> postfixStack = new Stack<String>();
        System.out.println("inside evaluateExpression--------->>"+postfixExpression);
        int firstAlphabetAscii = 97;
        String result = "";
        try{
            postfixExpression = postfixExpression.toLowerCase();
            postfixExpression = postfixExpression.replaceAll("sin", String.valueOf(Expression.sinAbbreviaition));
            postfixExpression = postfixExpression.replaceAll("log", String.valueOf(Expression.logAbbreviaition));
            System.out.println("postfixExpression--------->>"+postfixExpression);
            Stack<String> postfixStack = new Stack<String>();
            int stackValidationCounter = 0;       
            for(String term : postfixExpression.split(" ")) {                
                if(!Expression.operators.contains(term)){
                    String operand = "";
                    if(term.matches("[a-z]")){
                        System.out.println("cell reference--------->>"+term);
                        int termAscii = (int) term.toCharArray()[0];
                        int colIndex = termAscii - firstAlphabetAscii;
                        System.out.println("colIndex--------->>"+colIndex);            
                        if(Integer.valueOf(currentCell) == colIndex){
                            return "error from 1"; //circular dependency
                        }
                        operand = Client.con.values.get(String.valueOf(colIndex)) != null ? Client.con.values.get(String.valueOf(colIndex)) : "0";                        
                    }else{
                        System.out.println("number--------->>"+term);
                        operand = term;
                    }                  
                    if(operand != null && !operand.isEmpty()){
                        postfixStack.push(operand);
                        stackValidationCounter ++;
                    }
                }else{
                    if(term.equals("+")){
                        stackValidationCounter = stackValidationCounter - 2;
                        if(stackValidationCounter < 0)
                            return "error from 2";
                        Expression op1 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression op2 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression addition = new AdditionExpression(op1, op2);
                        postfixStack.push(String.valueOf(addition.evaluate(Client.con)));
                    }else if(term.equals("-")){
                        stackValidationCounter = stackValidationCounter - 2;
                        if(stackValidationCounter < 0)
                            return "error from 2";
                        Expression op1 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression op2 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression subtraction = new SubtractionExpression(op1, op2);
                        postfixStack.push(String.valueOf(subtraction.evaluate(Client.con)));
                    }else if(term.equals("*")){
                        stackValidationCounter = stackValidationCounter - 2;
                        if(stackValidationCounter < 0)
                            return "error from 2";
                        Expression op1 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression op2 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression multiplication = new MultiplicationExpresion(op1, op2);
                        postfixStack.push(String.valueOf(multiplication.evaluate(Client.con)));
                    }else if(term.equals("/")){
                        stackValidationCounter = stackValidationCounter - 2;
                        if(stackValidationCounter < 0)
                            return "error from 2";
                        Expression op1 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression op2 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression division = new DivisionExpression(op1, op2);
                        postfixStack.push(String.valueOf(division.evaluate(Client.con)));
                    }else if(term.equals(Expression.sinAbbreviaition)){
                        stackValidationCounter--;
                        if(stackValidationCounter < 0)
                            return "error from 2";
                        Expression op = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression sin = new SineExpression(op);
                        postfixStack.push(String.valueOf(sin.evaluate(Client.con)));
                    }else if(term.equals(Expression.logAbbreviaition)){
                        stackValidationCounter--;
                        if(stackValidationCounter < 0)
                            return "error from 2";
                        Expression op = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression log = new LogExpression(op);
                        postfixStack.push(String.valueOf(log.evaluate(Client.con)));
                    }
                }                                   
            }
            result = postfixStack.pop();
            System.out.println("result----------->>>"+result);
        }catch(Exception e){
            return "error from 3";
        }                
        return result;
    }
}