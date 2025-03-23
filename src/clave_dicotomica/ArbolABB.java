/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clave_dicotomica;

/**
 *
 * @author Gabriel
 */

import javax.swing.JOptionPane;
import java.util.Random;
import org.json.*;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

public class ArbolABB 
{    
    //Atributos de la clase
    private NodoABB root;
    private HashTable hashtable;
    private SingleGraph graph;

    //Constructor de ArbolABB vacio
    public ArbolABB() 
    {
        this.root = null;
        this.hashtable = new HashTable();
        this.graph = new SingleGraph("ABB" + new Random().nextInt(Integer.MAX_VALUE));
        this.graph.setAttribute("ui.stylesheet", ""
                + "node {"
                + "   text-alignment: right;"
                + "   text-offset: 10px, 0px;"
                + "   size: 5px, 5px;"
                + "}"
        );
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
                        
            JSONArray arrayPreguntas = obj.getJSONArray(nombreEspecie);
            int arrayPreguntasLength = arrayPreguntas.length();
            boolean ultimaRespuesta = false;
            
            NodoABB nodo = arbol.getRoot();
                        
            for(int j = 0; j < arrayPreguntasLength; ++j)
            {
                JSONObject preguntaObj = arrayPreguntas.getJSONObject(j);
                String pregunta = preguntaObj.keys().next();
                boolean respuesta = preguntaObj.getBoolean(pregunta);
                
                if(nodo == null)
                {
                    nodo = new NodoABB(pregunta);
                    arbol.root = nodo;
                }
                
                nodo = arbol.buscarOCrearNuevoNodo(nodo, pregunta, respuesta);
                ultimaRespuesta = respuesta;
            }
            
            NodoABB nodoEspecie = new NodoABB(nombreEspecie);
            nodoEspecie.setEspecie(nombreEspecie);
            nodoEspecie.setEsEspecie(true);
            nodoEspecie.setPadre(nodo);

            if(ultimaRespuesta)
            {
                nodo.setHijoDer(nodoEspecie);
            }
            else
            {
                nodo.setHijoIzq(nodoEspecie);
            }

            arbol.hashtable.insertar(nombreEspecie, nodoEspecie);
        }
        
        arbol.construirGraph();
        
        Viewer viewer = arbol.graph.display();
        viewer.disableAutoLayout();
        
        return arbol;
    }
    
    private NodoABB buscarNodo(NodoABB nodo, String pregunta)
    {
        if(nodo == null)
            return null;
        
        if(nodo.getDato().equals(pregunta))
            return nodo;
        
        NodoABB izquierda = buscarNodo(nodo.getHijoIzq(), pregunta);
        if(izquierda != null)
            return izquierda;
        
        return buscarNodo(nodo.getHijoDer(), pregunta);
    }
    
    private NodoABB buscarOCrearNuevoNodo(NodoABB nodo, String pregunta, boolean respuesta)
    {
        NodoABB existente = this.buscarNodo(nodo, pregunta);
        if(existente != null)
            return existente;
        
        if(respuesta)
        {
            if(nodo.getHijoDer() == null)
                nodo.setHijoDer(new NodoABB(pregunta));
            
            return nodo.getHijoDer();
        }
        else
        {
            if(nodo.getHijoIzq() == null)
                nodo.setHijoIzq(new NodoABB(pregunta));
            
            return nodo.getHijoIzq();
        }
    }
    
    public void determinarEspecie(NodoABB root, javax.swing.JTextArea output) 
    {
        output.setText("");

        NodoABB nodoActual = root;
        while (nodoActual != null && !nodoActual.esEspecie()) 
        {
            int respuesta = JOptionPane.showConfirmDialog(null, nodoActual.getDato(), "Pregunta", JOptionPane.YES_NO_OPTION);
            output.setText(output.getText() + "\n" + nodoActual.getDato() + ": " + (respuesta == JOptionPane.YES_OPTION ? "Sí" : "No"));
            
            if (respuesta == JOptionPane.YES_OPTION) 
            {
                nodoActual = nodoActual.getHijoDer();
            } 
            else 
            {
                nodoActual = nodoActual.getHijoIzq();
            }            
        }
        
        output.setText(output.getText() + "\n\n" + (nodoActual == null ? "No se pudo encontrar la especie." : "Especie: " + nodoActual.getEspecie()));
    }
    
    private void construirGraph()
    {
        this.construirGraph(this.root, 0.f, 0.f);
    }
    
    private void construirGraph(NodoABB nodo, float x, float y)
    {
        if(nodo == null)
            return;
        
        Node nodoGraph = this.graph.addNode(nodo.getDato());
        nodoGraph.setAttribute("ui.label", nodo.getDato());
        nodoGraph.setAttribute("x", (nodo.esEspecie() ? x + 1.5f : x));
        nodoGraph.setAttribute("y", (nodo.esEspecie() ? y + 0.5f : y));

        this.construirGraph(nodo.getHijoIzq(), x - 3.0f, y - 3.0f);
        this.construirGraph(nodo.getHijoDer(), x + 3.0f, y - 3.0f);
        
        if(nodo.getHijoIzq() != null)
        {
            this.graph.addEdge(nodo.getDato() + nodo.getHijoIzq().getDato(), nodo.getDato(), nodo.getHijoIzq().getDato());
        }
        
        if(nodo.getHijoDer() != null)
        {
            this.graph.addEdge(nodo.getDato() + nodo.getHijoDer().getDato(), nodo.getDato(), nodo.getHijoDer().getDato());
        }
    }
    
    public void visualizarConGraphStream() 
    {
        Viewer viewer = this.graph.display(false);
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
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
