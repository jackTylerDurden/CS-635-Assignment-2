import java.util.*;
public class Context {
    LinkedHashMap<String,String> values = new LinkedHashMap<String,String>();
    LinkedHashMap<String,String> colIndexToExpressionMap = new LinkedHashMap<String,String>();    
    public String getValue(String key){
        return values.get(key);
    }

    public void setValue(String key,String value){
        values.put(key,value);
    }
    
    public static void switchToEquationView(){
        for(String cell : Client.con.values.keySet()){
            System.out.println("cell------->>>"+cell);
            String val = Client.con.getValue(cell);
            val = val.trim();            
            System.out.println("val------->>>"+val);
            if(!val.isEmpty()){
                // if cell contains an expression
                if(!val.matches(Expression.numberRegex)){
                    Client.con.colIndexToExpressionMap.put(cell,val);
                    val = PostfixEvaluator.evaluateExpression(cell,val);
                    Client.con.values.put(cell,val);
                    Client.dataModel.setValueAt(val, 0,  Integer.valueOf(cell));                    
                }
            }
        }
    }
    
    public static void switchToValueView(){
        for(String cell : Client.con.values.keySet()){
            System.out.println("cell------->>>"+cell);
            String val = Client.con.getValue(cell);
            val = val.trim();            
            System.out.println("val------->>>"+val);
            if(!val.isEmpty()){
                // if cell contains an expression
                if(val.matches(Expression.numberRegex)){
                    String expression = Client.con.colIndexToExpressionMap.get(cell);
                    Client.con.values.put(cell,expression);
                    Client.dataModel.setValueAt(expression, 0,  Integer.valueOf(cell));
                }
            }
        }
    }

    // public static void evaluateGrid(){
    //     Stack<String> postfixStack = new Stack<String>();       
    //     for(String cell : Client.con.values.keySet()){
    //         System.out.println("cell------->>>"+cell);
    //         String val = Client.con.getValue(cell);
    //         val = val.trim();            
    //         if(!val.isEmpty()){
    //             System.out.println("v alue--------->>>"+val);
    //             if(!Expression.operators.contains(val)){
    //                 postfixStack.push(val);
    //             }else{                
    //                 if(val.equals("+")){
    //                     System.out.println("inside plus");
    //                     Expression op1 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
    //                     Expression op2 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
    //                     Expression addition = new AdditionExpression(op1, op2);
    //                     postfixStack.push(String.valueOf(addition.evaluate(Client.con)));
    //                 }else if(val.equals("*")){
    //                     Expression op1 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
    //                     Expression op2 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
    //                     Expression multiplication = new MultiplicationExpresion(op1, op2);
    //                     postfixStack.push(String.valueOf(multiplication.evaluate(Client.con)));
    //                 }else if(val.equals("sin")){
    //                     Expression op1 = new ConstantExpression(Double.valueOf(postfixStack.pop()));
    //                     Expression sin = new SineExpression(op1);
    //                     postfixStack.push(String.valueOf(sin.evaluate(Client.con)));
    //                 }
    //             }
    //         }
    //     }

    //     System.out.println("Postfix--------->>>"+postfixStack.pop());
    // }
    
}