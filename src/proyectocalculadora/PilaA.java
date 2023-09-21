package proyectocalculadora;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author emiliosaid
 */
public class PilaA <T> implements PilaADT <T> {
    private T[] pila;
    private int tope;
    private final int MAX = 20;
    
    public PilaA(){
        this.pila = (T[]) new Object[this.MAX];
        this.tope = -1;
    }
    
    public PilaA(int tamaño){
        this.pila = (T[]) new Object[tamaño];
        this.tope = -1;
    }
    
    
    @Override
    public void push(T dato) {
        if(this.tope == this.pila.length - 1){
            expande();
        }
        this.tope++;
        this.pila[this.tope] = dato;
    }

    @Override
    public T pop() {
        T resp;
        if(isEmpty()){
            resp = null;
        }else{
            resp = this.pila[this.tope];
            this.tope--;
        }
        return resp;
    }

    @Override
    public boolean isEmpty() {
        return this.tope == -1;
    }

    @Override
    public T peek() {
        T resp;
        if(isEmpty()){
            resp = null;
        }else{
            resp = this.pila[this.tope];
        }
        return resp;
        
    }
    
    private void expande(){
        T[] nuevaPila;
        int i;
        nuevaPila = (T[]) new Object[this.pila.length * 2];
        for(i = 0; i < this.pila.length; i++){
            nuevaPila[i] = this.pila[i];
        }
        this.pila = nuevaPila;
        
    }
}
