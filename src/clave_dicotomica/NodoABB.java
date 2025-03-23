/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clave_dicotomica;

/**
 *
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

    public NodoABB(String dato) 
    {
        this.dato = dato;
        this.hijoIzq = null;
        this.hijoDer = null;
        this.padre = null;
        this.esEspecie = false; // Por defecto no es especie
    }

    public NodoABB(String dato, boolean esEspecie) 
    {
        this.dato = dato;
        this.hijoIzq = null;
        this.hijoDer = null;
        this.padre = null;
        this.esEspecie = esEspecie;
    }

    public String getDato() 
    {
        return dato;
    }

    public void setDato(String dato) 
    {
        this.dato = dato;
    }

    public NodoABB getHijoIzq() 
    {
        return hijoIzq;
    }

    public void setHijoIzq(NodoABB hijoIzq) 
    {
        this.hijoIzq = hijoIzq;
    }

    public NodoABB getHijoDer() 
    {
        return hijoDer;
    }

    public void setHijoDer(NodoABB hijoDer) 
    {
        this.hijoDer = hijoDer;
    }

    public NodoABB getPadre() 
    {
        return padre;
    }

    public void setPadre(NodoABB padre) 
    {
        this.padre = padre;
    }

    public boolean esEspecie() 
    {
        return esEspecie && hijoIzq == null && hijoDer == null;
    }

    public void setEsEspecie(boolean esEspecie) 
    {
        this.esEspecie = esEspecie;
    }
    
    public String getEspecie()
    {
        return this.especie;
    }
    
    public void setEspecie(String especie)
    {
        this.especie = especie;
    }
}
