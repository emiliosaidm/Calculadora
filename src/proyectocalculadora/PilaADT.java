package proyectocalculadora;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

/**
 *
 * @author emiliosaid
 */
public interface PilaADT <T> {
    public void push(T dato);
    public T pop();
    public boolean isEmpty();
    public T peek();
}
