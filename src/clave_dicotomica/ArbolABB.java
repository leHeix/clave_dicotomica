/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clave_dicotomica;

import javax.swing.JOptionPane;
import java.util.Random;
import org.json.*;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.graph.Node;
import org.graphstream.ui.view.Viewer;

/**
 * Clase de Árbol binario de búsqueda, se encarga de parsear datos de JSON y almacenarlos
 * @author Gabriel
 */
public class ArbolABB 
{    
    //Atributos de la clase
    private NodoABB root;
    private HashTable hashtable;
    private SingleGraph graph;

    /**
     * Construye un árbol binario de búsqueda vacío
     */
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
    
    /**
     * Vacía el árbol binario y elimina todos sus valores
     */
    public void vaciar()
    {
        root = null;
        this.graph = new SingleGraph("ABB" + new Random().nextInt(Integer.MAX_VALUE));
        this.graph.setAttribute("ui.stylesheet", ""
                + "node {"
                + "   text-alignment: right;"
                + "   text-offset: 10px, 0px;"
                + "   size: 5px, 5px;"
                + "}"
        );
    } 
    
    /**
     * Recorre el árbol con el método de Preorden
     * @param root Nodo desde el cual empezar el recorrido
     * @param cadena String inicial a la que sumar el recorrido
     * @return String conteniendo el valor de "cadena" + el recorrido
     */
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
    
    /**
     * Recorre el árbol con el método de Postorden
     * @param root Nodo desde el cual empezar el recorrido
     * @param cadena String inicial a la que sumar el recorrido
     * @return String conteniendo el valor de "cadena" + el recorrido
     */
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
    
    /**
     * Recorre el árbol con el método de Inorden
     * @param root Nodo desde el cual empezar el recorrido
     * @param cadena String inicial a la que sumar el recorrido
     * @return String conteniendo el valor de "cadena" + el recorrido
     */
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
    
    /**
     * Busca un valor por su nombre en la HashTable del árbol vinario
     * @param valor El nombre del valor almacenado
     * @return El nodo que representa el valor encontrado, o null si no se encontró
     */
    public NodoABB Buscar(String valor) 
    {
        return this.hashtable.buscar(valor);
    }

    
    /**
     * Equivalente al método Buscar, pero cronometrando el tiempo que toma la ejecución de la función
     * @param valor El nombre del valor almacenado
     * @return Un tuple conteniendo el nodo encontrado en el primer valor y el tiempo que demoró la función en nanosegundos en el segundo valor
     */
    public Tuple<NodoABB, Long> buscarConTiempo(String valor) 
    {
        long inicio = System.nanoTime();
        NodoABB resultado = Buscar(valor);
        long fin = System.nanoTime();
        return new Tuple<>(resultado, inicio - fin);
    }

    /**
     * Inserta un valor en el árbol binario
     * @param dato La llave por la cual representar al valor
     * @param nodoPadre El nodo padre al cual vincular el nuevo nodo
     * @param respuesta true si el nodo nuevo debe ser el hijo derecho del padre, falso si debe ser el izquierdo
     * @return El nodo insertado
     */
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
    
    /**
     * Construye un árbol binario basado en los contenidos de un array de JSON
     * @param jsonContent El objeto JSONArray que contiene los datos con los cuales construir el árbol
     * @return El objeto del árbol binario creado
     * @throws JSONException 
     */
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
        
        return arbol;
    }
    
    /**
     * Busca si ya existe un nodo con un nombre
     * @param nodo El nodo desde el cual empezar el recorrido
     * @param pregunta El dato con el cual comparar si existe el nodo
     * @return El objeto NodoABB del nodo encontrado, o null si no existe
     */
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
    
    /**
     * Crea un nuevo nodo o retorna el nodo si ya existe en el árbol
     * @param nodo El nodo desde el cual empezar el recorrido
     * @param pregunta La pregunta que debe contener el nodo en la búsqueda o creación
     * @param respuesta true si representa el hijo derecho, false si el izquierdo
     * @return El NodoABB creado o encontrado
     */
    private NodoABB buscarOCrearNuevoNodo(NodoABB nodo, String pregunta, boolean respuesta)
    {
        NodoABB existente = this.buscarNodo(nodo, pregunta);
        if(existente != null)
            return existente;
        
        if(respuesta)
        {
            if(nodo.getHijoDer() == null)
            {
                nodo.setHijoDer(new NodoABB(pregunta));
                nodo.getHijoDer().setPadre(nodo);
            }
            
            return nodo.getHijoDer();
        }
        else
        {
            if(nodo.getHijoIzq() == null)
            {
                nodo.setHijoIzq(new NodoABB(pregunta));
                nodo.getHijoIzq().setPadre(nodo);
            }
            
            return nodo.getHijoIzq();
        }
    }
    
    /**
     * Recorre el árbol mientras hace preguntas al usuario para encontrar una especie, luego
     * imprime el output en el JTextArea proporcionado
     * @param root El nodo desde el cual empezar el recorrido
     * @param output El JTextArea donde imprimir el resultado
     */
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
    
    /**
     * Construye el graph desde la raíz del árbol
     */
    private void construirGraph()
    {
        this.construirGraph(this.root, 0.f, 0.f);
    }
    
    /**
     * Construye el graph partiendo desde un nodo y coordenadas del graph
     * @param nodo El nodo desde el cual empezar a construir el graph
     * @param x La coordenada X desde la cual empezar a generar los nodos en el graph
     * @param y La coordenada Y desde la cual empezar a generar los nodos en el graph
     */
    private void construirGraph(NodoABB nodo, float x, float y)
    {
        if(nodo == null)
            return;
        
        Node nodoGraph = this.graph.addNode(nodo.getDato());
        nodoGraph.setAttribute("ui.label", nodo.getDato());
        
        Random rng = new Random();
        nodoGraph.setAttribute("x", (nodo.esEspecie() ? x + 1.5f : x + rng.nextFloat(-1.0f, 1.0f)));
        nodoGraph.setAttribute("y", (nodo.esEspecie() ? y + 0.5f : y + rng.nextFloat(0.6f)));

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
    
    /**
     * Muestra el graph del árbol
     */
    public void visualizarConGraphStream() 
    {
        Viewer viewer = this.graph.display(false);
        viewer.setCloseFramePolicy(Viewer.CloseFramePolicy.CLOSE_VIEWER);
    }
    
    /**
     * Obtiene todas las preguntas que hacen resultar a una especie
     * @param nombreEspecie El nombre de la especie a obtener
     * @return String conteniendo todo el recorrido en el árbol que deriva en la especie
     */
    public String obtenerCaminoEspecie(String nombreEspecie)
    {
        NodoABB Especie = this.hashtable.buscar(nombreEspecie);
                
        if (Especie == null) 
        {
            return "Especie no encontrada en el árbol binario.";
        } 
        else 
        {
            long tiempoInicio = System.nanoTime();

            String resultado = Especie.getEspecie();
            NodoABB padre = Especie.getPadre();
            NodoABB nodoAnterior = Especie;
            
            do
            {
                resultado += " <- " + padre.getDato() + " - " + (padre.getHijoDer() == nodoAnterior ? "Sí" : "No");
                nodoAnterior = padre;
                padre = padre.getPadre();
            }
            while(padre != null);
            
            long tiempoFin = System.nanoTime();
            
            resultado += "\n\n(tiempo de búsqueda: " + (tiempoFin - tiempoInicio) + "ns)";
            return resultado;
        }
    }
    
    /**
     * Obtiene la raíz del árbol
     * @return El nodo raíz del árbol
     */
    public NodoABB getRoot() 
    {
        return root;
    }
    
    /**
     * Asigna la raíz del árbol
     * @param Root El nodo raíz del árbol
     */
    public void setRoot(NodoABB Root) 
    {
        this.root = Root;
    } 
}
