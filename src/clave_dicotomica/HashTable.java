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
    /**
     * Clase Nodo de la lista enlazada de la HashTable
     */
    public class Nodo
    {
        String key;
        NodoABB value;
        Nodo siguiente;
        
        /**
         * Crea un nuevo nodo
         * @param key Llave del nodo
         * @param value Valor del nodo
         */
        public Nodo(String key, NodoABB value)
        {
            this.key = key;
            this.value = value;
            this.siguiente = null;
        }
    }
    
    private Nodo[] tabla;
    private int tamaño;
    
    /**
     * Crea una HashTable con tamaño predeterminado
     */
    public HashTable()
    {
        this.tabla = new Nodo[16];
        this.tamaño = 0;
    }
    
    /**
     * Retorna el hash de una llave para la HashTable
     * @param key Llave a hashear
     * @return El hash de la llave
     */
    private int hash(String key)
    {
        return Math.abs(key.hashCode()) % this.tabla.length;
    }
    
    /**
     * Inserta un elemento en la HashTable
     * @param key Llave correspondiente al valor a insertar
     * @param value Valor a almacenar en la HashTable
     */
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
    
    /**
     * Recrea la tabla con un espacio mayor en el caso de quedarse sin espacio
     */
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
    
    /**
     * Busca un valor en la tabla por su llave
     * @param key Llave del valor almacenado en la tabla
     * @return El NodoABB vinculado a la llave, o null en caso de no encontrar un valor
     */
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
    
    /**
     * Retorna el tamaño de la tabla
     * @return Tamaño de la tabla
     */
    public int tamaño()
    {
        return this.tamaño;
    }
    
    /**
     * Determina si la tabla esta vacía
     * @return true si la tabla esta vacía, false de lo contrario
     */
    public boolean vacia()
    {
        return this.tamaño == 0;
    }
}
