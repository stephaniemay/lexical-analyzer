package parser;

import java.util.*; 
import java.io.*;

public class Lexer {
    
    public static ArrayList<String> Tokens;
    
    public static void main(String args[]) throws FileNotFoundException, IOException {
        BufferedReader bR = new BufferedReader(new FileReader("testcase.txt")); 
        String theFile = new String(); 
        String line = bR.readLine(); 
        
        while (line != null) {
            line = line.trim();
            line=line.replaceAll(" ", ""); //removing all spaces
            theFile += line; 
            line = bR.readLine(); 
        }  
        
        bR.close();
        Tokenize(theFile);
    }
    
    public static void Tokenize(String fileName) throws IOException{
        char[] file = fileName.toCharArray();
        Tokens = new ArrayList<>(); 
        String lexeme;
        int placement = 0;
        
        while (placement < file.length-1) {
            lexeme = ""; 
            
            if(operatorCheck(file[placement])) { 
                   if(operatorCheck(file[placement + 1])) { //checking operators with more than 1 character
                       String first = Character.toString(file[placement]);
                       String second = Character.toString(file[placement + 1]);
                       String fullOp = first + second;
                       
                       if (fullOp.equals("++") || fullOp.equals("==") || fullOp.equals(">=") || fullOp.equals("<=")) { 
                            System.out.println(Lookup(fullOp));
                            Tokens.add(Lookup(fullOp));
                            placement++;
                       }else {
                           String single = Character.toString(file[placement]);
                           System.out.println(Lookup(single));
                            Tokens.add(Lookup(single));
                       }
                       
                   }else { 
                        String single = Character.toString(file[placement]);
                        System.out.println(Lookup(single));
                        Tokens.add(Lookup(single));
                   } 
                   placement++;
               }
            
            else if (Character.isAlphabetic(file[placement])) {
                
               while (Character.isLetter(file[placement])) {
                   lexeme = lexeme + file[placement];
                   
                   if (!(placement == file.length-1)) {
                       placement++;
                       if ((specialWords(lexeme))) {
                           break;
                       }
                   } else { 
                       break; 
                   }
                   
               }
               
               if (specialWords(lexeme)){
                   System.out.println(lexeme.toUpperCase());
               } else if ((lexeme.contains("if") || lexeme.contains("for") || lexeme.contains("while") || lexeme.contains("function") || 
                       lexeme.contains("return") || lexeme.contains("int") || lexeme.contains("else") || lexeme.contains("do") || 
                       lexeme.contains("break") || lexeme.contains("end") ) ) { //checking if special word next to identifier
                   System.out.println("IDENT");
                   specialwordinword(lexeme);
                   
               }
               else { 
                    System.out.println("IDENT: " + lexeme);
                    Tokens.add("IDENT");     
               }
           }
            
           else if (Character.isDigit(file[placement])) {
               
                while(Character.isDigit(file[placement])) {
                   lexeme = lexeme + file[placement];
                   
                    if(Character.isDigit(file[placement + 1])) {
                         placement++;
                    } else {
                        break;
                    }   
               }
                
                if(Character.isDigit(file[placement]) && Character.isLetter(file[placement + 1])) {
                    System.out.println("SYNTAX ERROR: INVALID IDENTITFER NAME");
                    break;
                } else { 
                    System.out.println("INT_LIT: " + lexeme);
                    Tokens.add("INT_LIT");
                    lexeme = "";
                    placement++;
                }
           } 
           
           else {
               System.out.println("error");
               break;
           }
        }
        
         Tokens.add("EOF");
    }
    
    public static String Lookup (String lexeme) {
        HashMap<String, String> tokens = new HashMap<>();
            tokens.put("=", "ASSIGN");
            tokens.put("+", "ADD");
            tokens.put("-", "SUB");
            tokens.put("*", "MUL");
            tokens.put("/", "DIV");
            tokens.put("%", "MOD");
            tokens.put(">", "GT");
            tokens.put("<", "LT");
            tokens.put(">=", "GE");
            tokens.put("<=", "LE");
        
            tokens.put("++", "INC");
            tokens.put("(", "LP");
            tokens.put(")", "RP");
            tokens.put("{", "LB");
            tokens.put("}", "RB");
            tokens.put("|", "OR");
            tokens.put("&", "AND");
            tokens.put("==", "EE");
            tokens.put("!", "NEG");
            tokens.put(",", "COMMA");
            tokens.put(";", "SEMI");  
        
            return tokens.get(lexeme);    
    }
    
    public static boolean operatorCheck(char lexeme) {
       String lexeme2 = Character.toString(lexeme);
       
       if ( lexeme2.equals("++") || lexeme2.equals(">=") || lexeme2.equals("<=") || lexeme2.equals("==") 
          || lexeme == '+' || lexeme == '=' || lexeme == '-' || lexeme == '*' || lexeme == '/' || lexeme == '%'
          || lexeme == '>' || lexeme == '<' || lexeme == '(' || lexeme == ')' || lexeme == '{' || lexeme == '}'
          || lexeme == '|' || lexeme == '&' || lexeme == '!' || lexeme == ',' || lexeme == ';' ) 
        {
            return true;
        } else {
          return false;
       }
    }
    public static boolean specialWords(String lexeme) {
        
        if (lexeme.equals("if") || lexeme.equals("for") || lexeme.equals("while") || lexeme.equals("function") || lexeme.equals("return") || 
            lexeme.equals("int") || lexeme.equals("else") || lexeme.equals("do") || lexeme.equals("break") || lexeme.equals("end") ) 
        {
            return true;
        } else {
            return false; 
        }
    }
    public static void specialwordinword(String lexeme) {
        
        if (lexeme.contains("if") ) { System.out.println("IF"); }
        if (lexeme.contains("for") ) System.out.println("FOR");
        if (lexeme.contains("while") ) System.out.println("WHILE");
        if (lexeme.contains("function") ) System.out.println("FUNCTION");
        if (lexeme.contains("return") ) System.out.println("RETURN");
        if (lexeme.contains("int") ) System.out.println("INT");
        if (lexeme.contains("else") ) System.out.println("ELSE");
        if (lexeme.contains("do") ) System.out.println("DO");
        if (lexeme.contains("break") ) System.out.println("BREAK");
        if (lexeme.contains("end") ) System.out.println("END");
    }  
}
