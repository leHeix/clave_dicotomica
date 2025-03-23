/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clave_dicotomica;

/**
 * Clase del nodo utilizado en el árbol binario de búsqueda
 * @author Gabriel
 */
public class NodoABB 
{  
    private String dato; // Ahora las preguntas o nombres de especies serán cadenas de texto
    private NodoABB hijoIzq; // Nodo falso
    private NodoABB hijoDer; // Nodo verdadero
    private NodoABB padre;
    private boolean esEspecie; // Identifica si el nodo es una especie
    private String especie;

    /**
     * Crea un nuevo nodo
     * @param dato La pregunta o la especie del nodo
     */
    public NodoABB(String dato) 
    {
        this.dato = dato;
        this.hijoIzq = null;
        this.hijoDer = null;
        this.padre = null;
        this.esEspecie = false; // Por defecto no es especie
    }

    /**
     * Crea un nuevo nodo especificando si es una especie
     * @param dato La pregunta o la especie del nodo
     * @param esEspecie El nodo contiene una especie o no
     */
    public NodoABB(String dato, boolean esEspecie) 
    {
        this.dato = dato;
        this.hijoIzq = null;
        this.hijoDer = null;
        this.padre = null;
        this.esEspecie = esEspecie;
    }
    
    /**
     * Obtiene la pregunta almacenada por el nodo
     * @return 
     */
    public String getDato() 
    {
        return dato;
    }
    
    /**
     * Asigna la pregunta almacenada por el nodo
     * @param dato Pregunta a almacenar
     */
    public void setDato(String dato) 
    {
        this.dato = dato;
    }
    
    /**
     * Obtiene el nodo del hijo izquierdo (falso)
     * @return Nodo hijo izquierdo
     */
    public NodoABB getHijoIzq() 
    {
        return hijoIzq;
    }
    
    /**
     * Asigna el nodo del hijo izquierdo (falso)
     * @param hijoIzq El nodo del hijo izquierdo
     */
    public void setHijoIzq(NodoABB hijoIzq) 
    {
        this.hijoIzq = hijoIzq;
    }
    
    /**
     * Obtiene el nodo del hijo derecho (verdadero)
     * @return El nodo derecho
     */
    public NodoABB getHijoDer() 
    {
        return hijoDer;
    }
    
    /**
     * Asigna el nodo del hijo derecho (verdadero)
     * @param hijoDer El nodo del hijo derecho
     */
    public void setHijoDer(NodoABB hijoDer) 
    {
        this.hijoDer = hijoDer;
    }
    
    /**
     * Obtiene el nodo padre del nodo actual
     * @return El nodo padre
     */
    public NodoABB getPadre() 
    {
        return padre;
    }
    
    /**
     * Asigna el nodo padre
     * @param padre El nodo padre
     */
    public void setPadre(NodoABB padre) 
    {
        this.padre = padre;
    }
    
    /**
     * Determina si el nodo almacena una especie o una pregunta
     * @return true si el nodo almacena una especie, falso si almacena una pregunta
     */
    public boolean esEspecie() 
    {
        return esEspecie && hijoIzq == null && hijoDer == null;
    }
    
    /**
     * Asigna si el nodo almacena una especie o no
     * @param esEspecie true si el nodo almacena una especie, falso de lo contrario.
     */
    public void setEsEspecie(boolean esEspecie) 
    {
        this.esEspecie = esEspecie;
    }
    
    /**
     * Obtiene el nombre de la especie almacenada por el nodo
     * @return El String nombre de la especie almacenada por el nodo
     */
    public String getEspecie()
    {
        return this.especie;
    }
    
    /**
     * Asigna el nombre de la especie a almacenar por el nodo
     * @param especie El nombre de la especie
     */
    public void setEspecie(String especie)
    {
        this.especie = especie;
    }
}
