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
    
    // Nota para cualquier desarrollador: El siguiente programa es una representación útil de una calculadora básica.
    // Existen limitantes con respecto a su uso que se pueden encontrar en el reporte del proyecto. 
    // Todas las comparaciones numericas fueron hechas utilizando Expresiones Regulares; nos apoyamos de la siguiente liga para poder validar 
    // que estas que definimos fueran correctas: https://regex101.com/
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
            // El siguiente condicional agrega a una pila la apertura de un paréntesis. Además se valida si el elemento anterior, si es que existe, sea un operando y que el elemento siguiente sea un operando
            if(operacion.get(i).equals("(")){
                if(i > 0 && !(operacion.get(i - 1).equals("+") || operacion.get(i - 1).equals("-") || operacion.get(i - 1).equals("/") || operacion.get(i - 1).equals("*") || operacion.get(i - 1).equals("^")))
                    bandera = false;
                if(i < operacion.size() - 1 && !operacion.get(i + 1).matches("^[-+]?\\d*\\.?\\d+$"))
                    bandera = false;
                pila.push(")");
            }
            // El siguiente condicional revisa si cada cerradura de paréntesis tiene uno de apertura para verificar que los paréntesis estén balanceados.  Además, si es que lo que le sigue a la cerradura del paréntesis es un operador, y si lo que le procede al parentesis es un operando. 
            if (operacion.get(i).equals(")")){
                if(pila.isEmpty()){
                    bandera = false;
                }else{
                    if(i < operacion.size() - 1 && !(operacion.get(i + 1).equals("+") || operacion.get(i + 1).equals("-") || operacion.get(i + 1).equals("/") || operacion.get(i + 1).equals("*") || operacion.get(i + 1).equals("^")) ){
                       bandera = false;
                    }
                    if(i > 0 &&  !operacion.get(i - 1).matches("^[-+]?\\d*\\.?\\d+$")){
                        bandera = false;
                    }
                        
                    pila.pop();
                    
                }
            }
            // El siguiente condicional valida que no haya 2 terminos consecutivos sin un operador entre ellos. Recipricamente, revisa si no hay 2 operadores consecutivos
            if(i < operacion.size() - 1 ){ 
                if(operacion.get(i).matches("^[-+]?\\d*\\.?\\d+$") && operacion.get(i + 1).matches("^[-+]?\\d*\\.?\\d+$" )){
                    bandera = false;
                } else if((operacion.get(i).equals("+") || operacion.get(i).equals("-") || operacion.get(i).equals("*") || operacion.get(i).equals("/") || operacion.get(i).equals("^")) && (operacion.get(i + 1).equals("+") || operacion.get(i + 1).equals("-") || operacion.get(i + 1).equals("*") || operacion.get(i + 1).equals("/") || operacion.get(i + 1).equals("^"))){
                    bandera = false;
                }
            }
            
            
            // El siguiente condicional verifica que si un número está presente en la expresión, entonces el segundo subsecuente debe de ser un paréntesis o un operando. Además, da excepción al elevar 0 a la potencia 0. 
            if(i < operacion.size() - 2 ){
                
                if(operacion.get(i).equals("0") && operacion.get(i + 1).equals("^") && operacion.get(i + 2).equals("0")){
                    bandera = false;
                }
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
        
        // El siguiente condicional valida si es que la pila que se usa para validar el balance de paréntesis esté vacia, lo que significa que cada parentesis de apertura le corresponde uno de cerradura.
        // Además, se valida que la bandera siga en true, lo que significa que pasó todas las pruebas arriba descritas.
        if(!pila.isEmpty() || !bandera){
            operacion.clear();
            operacion.add("Cadena inconsistente");
        }
        // Regresa el Un ArrayList con la operación validada, lista para pasarse a postfija. 
        return operacion;
    }
    
    private ArrayList <String> pasarAPostfija(ArrayList <String> expresion){
        ArrayList postfija;
        PilaA<String> pila;
        String elemento;
        pila = new PilaA <>();
        postfija = new ArrayList<Character>();
        // El siguiente ciclo itera por un ArrayList para pasar de infijo a postifo la expresión matemática ya validada.
        for(int i = 0; i < expresion.size(); i++){
            elemento = expresion.get(i);
            //Si el elemento i es un número se guarda en el ArrayList de postfijo.
            if(elemento.matches("^[-+]?\\d*\\.?\\d+$")){
                postfija.add(elemento);
            }
            //Si el elemento i es una apertura de paréntesis, se guarda en la pila de operadores.
            if(elemento.equals("(")){
                pila.push(elemento);
            }
            //Si el elemento i es una cerradura de paréntesis, hasta que el tope de la pila de operadores sea la apertura de paréntesis, se sacan los operadores y se agregan al arreglo postfijo.
            if(elemento.equals(")")){
                while(!pila.peek().equals("(")){
                    postfija.add(pila.pop());
                }
                pila.pop();
            }
            // El siguiente condicional valida si el elemento i es un operador. Si lo es, se dispone a un cíclo, donde si la pila de operadores no esta vacia y la jerarquía del elemento tope de la pila es de mayor o igual jerarquia
            // que el el elemento i, entonces se saca el elemento tope y se agrega al postfijo. De otra manera, y aunque no entre al ciclo, el elemento es agregado a la pila de operadores. 
            if(elemento.equals("*") || elemento.equals("/") || elemento.equals("+") || elemento.equals("-") || elemento.equals("^")){
                while(!pila.isEmpty() && this.jerarquia(pila.peek()) >= this.jerarquia(elemento)){
                    postfija.add(pila.pop());
                }
                pila.push(elemento);            
            }
        }
        // El siguiente ciclo purga la pila de operadores y las agrega al postfijo.
        while(!pila.isEmpty()){
            postfija.add(pila.pop());
        }

        // Se regresa un ArrayList con la expresión matemática en postfijo para poder ser evaluada.
        return postfija;  
    }
    
    private double evaluarExpresion(ArrayList <String> postfija){
        PilaA <Double> pila;
        double a, b, resp = 0;
        String elemento;
        int i;
        pila = new PilaA();
        // El siguiente ciclo estará iterando por la expresión psotfija para evaluarla utilizando como estructura de dato una pila.
        for(i = 0; i < postfija.size(); i++){
            // El siguiente conducional valida si el elemento i de la expresion es un número elemento de los reales. Si es que lo es, lo mete a la pila como Double. 
            // De otra manera, se 
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
         String regex = "-?\\d*\\.?\\d+|[-+*/()^]" ;
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
                if(!resultado.get(i - 1).equals("^")){
                    resultado.set(i - 1, "-");
                    resultado.set(i, Math.abs(Double.parseDouble(resultado.get(i))) + "");   
                }
            }
        }
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
        resp = calculadora.calcularExpresion("-1*5^2");
        if(resp[0].equals("1")){
            System.out.println(resp[1]);
        }else{
            System.out.println("Cadena inconsistente");
        }
        
    }

    
}
