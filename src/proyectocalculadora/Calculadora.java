/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proyectocalculadora;

import java.util.*;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
        String num = "";
        int i, j;
        operacion = separarExpresion(op);
        i = 0;
        if(!operacion.isEmpty() && ((operacion.get(i).matches("^[-+]?\\d*\\.?\\d+$") || operacion.get(i).equals("(")) && (operacion.get(operacion.size() - 1).matches("^[-+]?\\d*\\.?\\d+$") || operacion.get(operacion.size() - 1).equals(")")))){
            bandera = true;
        }else{
            bandera = false;
        }
        while(i < operacion.size() && bandera ){
            // El siguiente condicional agrega a una pila la apertura de un paréntesis. Además se valida si el elemento anterior, si es que existe, sea un operando.
            if(operacion.get(i).equals("(")){
                if(i > 0 && operacion.get(i - 1).matches("^[-+]?\\d*\\.?\\d+$"))
                    bandera = false;
                pila.push(")");
            }
            // El siguiente condicional revisa si cada cerradura de paréntesis tiene uno de apertura para verificar que los paréntesis estén balanceados.  Además, si es que lo que le sigue a la cerradura del paréntesis es un operador.
            if (operacion.get(i).equals(")")){
                if(pila.isEmpty()){
                    bandera = false;
                }else{
                    if(i < operacion.size() - 1 && !(operacion.get(i + 1).equals("+") || operacion.get(i + 1).equals("-") || operacion.get(i + 1).equals("/") || operacion.get(i + 1).equals("*") || operacion.get(i + 1).equals("^")) ){
                       bandera = false;
                    }
                    pila.pop();
                    
                }
            }
            // El siguiente condicional valida que no haya 2 terminos consecutivos sin un operador entre ellos.
            if(operacion.get(i).matches("^[-+]?\\d*\\.?\\d+$") && i < operacion.size() - 1 ){
                if(operacion.get(i + 1).matches("^[-+]?\\d*\\.?\\d+$")){
                    bandera = false;
                    
                } 
            }
            // El siguiente condicional verifica que si un número está presente en la expresión, entonces el segundo subsecuente debe de ser un paréntesis o un operando.
            if(operacion.get(i).matches("^[-+]?\\d*\\.?\\d+$") && i < operacion.size() - 2 ){
                if(operacion.get(i + 2).matches("^[-+]?\\d*\\.?\\d+$") && operacion.get(i + 2).equals("(")){
                    bandera = false;
                }  
            }
            // El siguiente condicional verifica que no haya divisiones entre 0.
            if(operacion.get(i).equals("/") && operacion.get(i + 1).equals("0")){
                bandera = false;
            }
            i++;
        }
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
    
    private ArrayList<String> separarExpresion(String texto){
         String regex = "-?\\d*\\.?\\d+|[-+*/()^]";
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(texto);
         ArrayList<String> resultado = new ArrayList<String>();

        while (matcher.find()) {
            resultado.add(matcher.group());
        }
        for(int i = 0; i < resultado.size(); i++){
            if(i < resultado.size() - 1 && resultado.get(i).matches("^[-+]?\\d*\\.?\\d+$") && resultado.get(i + 1).matches("^[-+]?\\d*\\.?\\d+$")){
                resultado.add(i + 1, "+");
            } else if(i < resultado.size() - 1 && resultado.get(i).equals(")") && resultado.get(i + 1).matches("^[-+]?\\d*\\.?\\d+$")){
               resultado.add(i + 1, "+"); 
            }
            if(resultado.get(i).matches("^[-+]?\\d*\\.?\\d+$") && Double.parseDouble(resultado.get(i)) < 0 && i > 0 && !resultado.get(i - 1).equals("(")){
                resultado.set(i - 1, "-");
                resultado.set(i, Math.abs(Double.parseDouble(resultado.get(i))) + "");
            }
        }
        System.out.println(resultado);
        return resultado;
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
        resp = calculadora.calcularExpresion("(78/6-3)*63/96+-32^2");
        if(resp[0].equals("1")){
            System.out.println(resp[1]);
        }else{
            System.out.println("Cadena inconsistente");
        }
        
    }

    
}
