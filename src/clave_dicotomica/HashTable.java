/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clave_dicotomica;

/**
 * HashTable con una lista enlazada de arrays (porque soy muy vago para volver a hacer una lista enlazada por nodos)
 * @author Naim
 */
public class HashTable 
{
    public class Nodo
    {
        String key;
        NodoABB value;
        Nodo siguiente;
        
        public Nodo(String key, NodoABB value)
        {
            this.key = key;
            this.value = value;
            this.siguiente = null;
        }
    }
    
    private Nodo[] tabla;
    private int tamaño;
    
    public HashTable()
    {
        this.tabla = new Nodo[16];
        this.tamaño = 0;
    }
    
    private int hash(String key)
    {
        return Math.abs(key.hashCode()) % this.tabla.length;
    }
    
    public void insertar(String key, NodoABB value)
    {
        int idx = this.hash(key);
        
        Nodo actual = this.tabla[idx];
        while(actual != null)
        {
            if(actual.key.equals(key))
            {
                actual.value = value;
                return;
            }
            
            actual = actual.siguiente;
        }
        
        Nodo nodoNuevo = new Nodo(key, value);
        nodoNuevo.siguiente = this.tabla[idx];
        this.tabla[idx] = nodoNuevo;
        this.tamaño++;
        
        // Realloc el array de la tabla si no hay más espacio
        if((1.0 * this.tamaño) / tabla.length > 0.75)
        {
            this.realloc();
        }
    }
    
    private void realloc()
    {
        Nodo[] tablaAnterior = this.tabla;
        this.tabla = new Nodo[this.tabla.length * 2];
        this.tamaño = 0;
        
        for(Nodo nodo : tablaAnterior)
        {
            while(nodo != null)
            {
                this.insertar(nodo.key, nodo.value);
                nodo = nodo.siguiente;
            }
        }
    }
    
    public NodoABB buscar(String key)
    {
        int idx = this.hash(key);
        
        Nodo nodo = this.tabla[idx];
        while(nodo != null)
        {
            if(nodo.key.equals(key))
            {
                return nodo.value;
            }
            nodo = nodo.siguiente;
        }
        
        return null;
    }
    
    public int tamaño()
    {
        return this.tamaño;
    }
    
    public boolean vacia()
    {
        return this.tamaño == 0;
    }
}
