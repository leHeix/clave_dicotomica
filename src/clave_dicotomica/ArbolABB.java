/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clave_dicotomica;

/**
 *
 * @author Gabriel
 */

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import org.json.*;

public class ArbolABB 
{    
    //Atributos de la clase
    private NodoABB root;
    private HashTable hashtable;

    //Constructor de ArbolABB vacio
    public ArbolABB() 
    {
        this.root = null;
        this.hashtable = new HashTable();
    }
    
    //Metodo para vaciar un arbol convirtiendo la raiz en null
    public void vaciar()
    {
        root = null;
    }
    
    //Metodo para chequear si un nodo es vacio
    public static boolean nodoEstaVacio(NodoABB node)
    {
        return node == null;
    }
    
    
    //Metodo de recorrido Preorden
    public String Preorden(NodoABB root, String cadena)
    {
        if(root != null)
        {
            cadena = cadena + root.getDato() + ",";
            cadena = Preorden(root.getHijoIzq(), cadena);
            cadena = Preorden(root.getHijoDer(), cadena);
        }
        return cadena;
    }
    
    //Metodo de recorrido Postorden
    public String Postorden(NodoABB root, String cadena)
    {
        if(root != null)
        {
            cadena = Postorden(root.getHijoIzq(), cadena);
            cadena = Postorden(root.getHijoDer(), cadena);
            cadena = cadena + root.getDato() + ",";
        }
        return cadena;
    }
    
    //Metodo Inorden, al recorrerlo deberian salir los valores en orden
    public String Inorden(NodoABB root, String cadena)
    {
        if(root != null)
        {
            cadena = Inorden(root.getHijoIzq(), cadena);
            cadena = cadena + root.getDato() + ",";
            cadena = Inorden(root.getHijoIzq(), cadena);
        }
        return cadena;
    }
    
    //Metodo para buscar un valor especifico recorriendo cada uno de los nodos del arbol binario que se desee
    public NodoABB Buscar(String valor) 
    {
        return this.hashtable.buscar(valor);
    }

    
    //Este Metodo Implementa el metodo de Buscar(int valor, NodoABB) para indicar el tiempo de busqueda
    public Tuple<NodoABB, Long> buscarConTiempo(String valor) 
    {
        long inicio = System.nanoTime();
        NodoABB resultado = Buscar(valor);
        long fin = System.nanoTime();
        return new Tuple<>(resultado, inicio - fin);
    }

    //Metodo para insertar un nodo al Arbol Binario de Busqueda
    public NodoABB Insertar(String dato, NodoABB nodoPadre, boolean respuesta) {
        NodoABB nuevoNodo = new NodoABB(dato);

        if (this.root == null) {
            this.root = nuevoNodo;
            return nuevoNodo;
        }
        if (respuesta) {
            nodoPadre.setHijoDer(nuevoNodo);
        } else {
            nodoPadre.setHijoIzq(nuevoNodo);
        }

        return nuevoNodo;
    }
    
    public static ArbolABB cargarDeJSON(JSONArray jsonContent) throws JSONException
    {
        ArbolABB arbol = new ArbolABB();
        
        int array_length = jsonContent.length();
        for(int i = 0; i < array_length; ++i)
        {
            JSONObject obj = jsonContent.getJSONObject(i);
            String nombreEspecie = obj.keys().next();
            
            NodoABB nodo = arbol.getRoot();
            NodoABB anterior = null;
            boolean respuestaAnterior = false;
            
            JSONArray arrayPreguntas = obj.getJSONArray(nombreEspecie);
            int arrayPreguntasLength = arrayPreguntas.length();
            for(int j = 0; j < arrayPreguntasLength; ++j)
            {
                JSONObject preguntaObj = arrayPreguntas.getJSONObject(j);
                String pregunta = preguntaObj.keys().next();
                boolean respuesta = preguntaObj.getBoolean(pregunta);
                
                // Crear la raíz si no existe
                if(nodo == null)
                {
                    nodo = new NodoABB(pregunta);
                    if(arbol.root == null)
                        arbol.root = nodo;
                }
                else
                {            
                    if(nodo.getDato().equals(pregunta))
                    {
                        if(respuesta)
                        {
                            if(nodo.getHijoDer() == null)
                                nodo.setHijoDer(new NodoABB(pregunta));
                            
                            nodo = nodo.getHijoDer();
                        }
                        else
                        {
                            if(nodo.getHijoIzq() == null)
                                nodo.setHijoIzq(new NodoABB(pregunta));
                            
                            nodo = nodo.getHijoIzq();
                        }
                    }
                    else
                    {
                        NodoABB nodoNuevo = new NodoABB(pregunta);
                        if(respuestaAnterior)
                        {
                            anterior.setHijoDer(nodoNuevo);
                        }
                        else
                        {
                            anterior.setHijoIzq(nodoNuevo);
                        }
                        
                        nodo = nodoNuevo;
                    }
                }
                
                anterior = nodo;
                respuestaAnterior = respuesta;
            }
            
            if(nodo != null && nodo.getHijoDer() == null && nodo.getHijoIzq() == null)
            {
                nodo.setEspecie(nombreEspecie);
                nodo.setEsEspecie(true);
                arbol.hashtable.insertar(nombreEspecie, nodo);
            }
            else System.out.println("el nodo no es null????");
        }
        
        return arbol;
    }
    
    public String determinarEspecie(NodoABB root) 
    {
        NodoABB nodoActual = root;
        while (nodoActual != null && !nodoActual.esEspecie()) 
        {
            int respuesta = JOptionPane.showConfirmDialog(null, nodoActual.getDato(), "Pregunta", JOptionPane.YES_NO_OPTION);
            if (respuesta == JOptionPane.YES_OPTION) 
            {
                nodoActual = nodoActual.getHijoDer();
            } 
            else 
            {
                nodoActual = nodoActual.getHijoIzq();
            }
        }
        
        return nodoActual != null ? nodoActual.getEspecie() : "Especie no encontrada.";
    }

    
    
    public void visualizarConGraphStream() 
    {
        // Implementación para conectar con GraphStream
    }

    public NodoABB getRoot() 
    {
        return root;
    }

    public void setRoot(NodoABB Root) 
    {
        this.root = Root;
    } 
}
