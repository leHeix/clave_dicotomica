/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clave_dicotomica;

/**
 * Tuple, dos valores
 * @author Naim
 */
public class Tuple<V1, V2> 
{
    public V1 primero;
    public V2 segundo;
    
    public Tuple(V1 primero, V2 segundo)
    {
        this.primero = primero;
        this.segundo = segundo;
    }
}
