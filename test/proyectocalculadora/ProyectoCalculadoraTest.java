/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package proyectocalculadora;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author emiliosaid
 */
public class ProyectoCalculadoraTest {
    
    public ProyectoCalculadoraTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of calcularExpresion method, of class ProyectoCalculadora.
     */
    @Test
    public void testCalcularExpresion() {
        System.out.println("calcularExpresion");
        String expresion = "+";
        ProyectoCalculadora instance = new ProyectoCalculadora();
        String[] expResult = new String[2];
        expResult[0] = "0";
        expResult[1] = "Cadena inconsistente";
        String[] result = instance.calcularExpresion(expresion);
        assertArrayEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of main method, of class ProyectoCalculadora.
     */
    
}
