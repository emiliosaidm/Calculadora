/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectocalculadora;

import java.util.ArrayList;

/**
 *
 * @author emiliosaid
 */
public class Calculadora {
    
     public Calculadora(){
        
    }
    
    
    private ArrayList <String> validez (String op) {
        ArrayList<String> operacion = new ArrayList <> ();
        PilaADT pila = new PilaA ();
        boolean bandera;
        String num;
        int i, j;
        for (i = 0; i < op.length(); i ++) {
            if(Character.isDigit(op.charAt(i))){
                j = i;
                num = "";
                while(j < op.length() && (Character.isDigit(op.charAt(j)) || op.charAt(j) == '.')){
                    num += op.charAt(j);
                    j++;
                }
                i = j - 1;
                operacion.add(num);
            }else{
                operacion.add(String.valueOf(op.charAt(i)));
            }
        }
        i = 0;
        if(!operacion.isEmpty() && ((operacion.get(i).matches("^[-+]?\\d*\\.?\\d+$") || operacion.get(i).equals("(")) && (operacion.get(operacion.size() - 1).matches("^[-+]?\\d*\\.?\\d+$") || operacion.get(operacion.size() - 1).equals(")")))){
            bandera = true;
        }else{
            bandera = false;
        }
        while(i < operacion.size() && bandera ){
            if(operacion.get(i).equals("(")){
                pila.push(")");
            }
            if (operacion.get(i).equals(")") && !pila.isEmpty()){
                pila.pop();
            }
            if(operacion.get(i).matches("^[-+]?\\d*\\.?\\d+$") && i < operacion.size() - 2 ){
                if(operacion.get(i + 2).matches("^[-+]?\\d*\\.?\\d+$") && operacion.get(i + 2).equals("(")){
                    bandera = false;
                }  
            }
            if(operacion.get(i).equals("/") && operacion.get(i + 1).equals("0")){
                bandera = false;
            }
            i++;
        }
                System.out.println(operacion);
        if(!pila.isEmpty() || !bandera){
            operacion.clear();
            operacion.add("Cadena inconsistente");
        }
        return operacion;
    }
    
    private ArrayList <String> pasarAPostfija(ArrayList <String> expresion){
        ArrayList postfija;
        PilaA<String> pila;
        String elemento;
        pila = new PilaA <>();
        postfija = new ArrayList<Character>();
        for(int i = 0; i < expresion.size(); i++){
            elemento = expresion.get(i);
            if(elemento.matches("^[-+]?\\d*\\.?\\d+$")){
                postfija.add(elemento);
            }
            if(elemento.equals("(")){
                pila.push(elemento);
            }
            if(elemento.equals(")")){
                while(!pila.peek().equals("(")){
                    postfija.add(pila.pop());
                }
                pila.pop();
            }
            if(elemento.equals("*") || elemento.equals("/") || elemento.equals("+") || elemento.equals("-") || elemento.equals("^")){
                while(!pila.isEmpty() && this.jerarquia(pila.peek()) >= this.jerarquia(elemento)){
                    postfija.add(pila.pop());
                }
                pila.push(elemento);            
            }
        }
        while(!pila.isEmpty()){
            postfija.add(pila.pop());
        }

        
        return postfija;  
    }
    
    private double evaluarExpresion(ArrayList <String> postfija){
        PilaA <Double> pila;
        double a, b, resp = 0;
        String elemento;
        int i;
        pila = new PilaA();
        for(i = 0; i < postfija.size(); i++){
            elemento = postfija.get(i);
            if(elemento.matches("^[-+]?\\d*\\.?\\d+$")){
                pila.push(Double.valueOf(elemento));
            }else{
                b = pila.pop();
                a = pila.pop();
                switch(elemento){
                    case "+":
                        resp = a + b;
                        break;
                    case "-":
                        resp = a - b;
                        break;
                    case "*":
                        resp = a * b;
                        break;
                    case "/":
                        resp = a / b;
                        break;
                    case "^": 
                        resp = Math.pow(a, b);
                        break;   
                }
                pila.push(resp);
            }
            
        }
        if(!pila.isEmpty()){
            resp = pila.pop();
        }
        return resp;
    }
    
    private int jerarquia(String operador){
        int resp;
        resp = 0;
        switch(operador){
            case "(": 
                resp = 1;
                break;
            case "+":
                resp = 2;
                break;
            case "-":
                resp = 2;
                break;
            case "*": 
                resp = 3;
                break;
            case "/":
                resp = 3;
                break;
            case "^":
                resp = 4;
                break;
        }
        return resp;
    }
    
    public String[] calcularExpresion(String expresion){
        ArrayList <String> infija, postfija;
        double calculo;
        String[] resp;   
        resp = new String[2];
        
        infija = this.validez(expresion);
        if(infija.size() == 1 && infija.get(0).equals("Cadena inconsistente")){
            resp[0] = "0";
        }else{
            postfija = this.pasarAPostfija(infija);
            calculo = evaluarExpresion(postfija);
            resp[0] = "1";
            resp[1] = calculo + "";
        }

        return resp;
        
    }
    
    public static void main(String[] args) {
       String[] resp;
        Calculadora calculadora = new Calculadora();
        resp = calculadora.calcularExpresion("0.5-0.5");
        if(resp[0].equals("1")){
            System.out.println(resp[1]);
        }else{
            System.out.println("Cadena inconsistente");
        }
        
    }

    
}