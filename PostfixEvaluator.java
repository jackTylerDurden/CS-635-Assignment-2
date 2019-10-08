import java.util.*;
public class PostfixEvaluator{
    public static String evaluateExpression(String postfixExpression){            
        String result = "";
        if(postfixExpression == null || postfixExpression.equals("error")){
            return "error";
        }
        try{
            postfixExpression = postfixExpression.toLowerCase();
            postfixExpression = postfixExpression.replaceAll("sin", String.valueOf(Expression.sinAbbreviaition));
            postfixExpression = postfixExpression.replaceAll("log", String.valueOf(Expression.logAbbreviaition));
            // System.out.println("postfixExpression--------->>"+postfixExpression);
            Stack<String> postfixStack = new Stack<String>();
            int stackValidationCounter = 0;       
            for(String term : postfixExpression.split(" ")) {                
                if(!Expression.operators.contains(term)){
                    String operand = "";                                            
                    operand = term;
                    if(operand != null && !operand.isEmpty()){
                        postfixStack.push(operand);
                        stackValidationCounter ++;
                    }
                }else{
                    if(term.equals("+")){                        
                        Expression op1 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression op2 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression addition = new AdditionExpression(op1, op2);
                        postfixStack.push(String.valueOf(addition.evaluate(Client.con)));
                    }else if(term.equals("-")){                        
                        Expression op1 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression op2 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression subtraction = new SubtractionExpression(op1, op2);
                        postfixStack.push(String.valueOf(subtraction.evaluate(Client.con)));
                    }else if(term.equals("*")){                        
                        Expression op1 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression op2 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression multiplication = new MultiplicationExpresion(op1, op2);
                        postfixStack.push(String.valueOf(multiplication.evaluate(Client.con)));
                    }else if(term.equals("/")){
                        Expression op1 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression op2 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression division = new DivisionExpression(op1, op2);
                        postfixStack.push(String.valueOf(division.evaluate(Client.con)));
                    }else if(term.equals(Expression.sinAbbreviaition)){
                        Expression op = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression sin = new SineExpression(op);
                        postfixStack.push(String.valueOf(sin.evaluate(Client.con)));
                    }else if(term.equals(Expression.logAbbreviaition)){
                        Expression op = new ConstantExpression(Double.valueOf(postfixStack.pop()));
                        Expression log = new LogExpression(op);
                        postfixStack.push(String.valueOf(log.evaluate(Client.con)));
                    }
                }                                   
            }
            result = postfixStack.pop();
            // System.out.println("result----------->>>"+result);
        }catch(Exception e){
            e.printStackTrace();    
            return "error from 3";
        }                
        return result;
    }
}