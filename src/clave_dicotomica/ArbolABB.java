/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clave_dicotomica;

/**
 *
 * @author lenovo
 */

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import javax.swing.JOptionPane;


public class ArbolABB 
{    
    //Atributos de la clase
    private NodoABB root;

    //Constructor de ArbolABB vacio
    public ArbolABB() 
    {
        this.root = null;
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
    public NodoABB Buscar(String valor, NodoABB root) 
    {
        if (nodoEstaVacio(root)) 
        {
            return null; 
        } 
        else 
        {
            int comparacion = valor.compareTo(root.getDato());
            if (comparacion < 0) 
            {
                return Buscar(valor, root.getHijoIzq());
            }
            else if (comparacion > 0) 
            {
                return Buscar(valor, root.getHijoDer());
            } 
            else 
            {
                return root;
            }
        }
    }

    
    //Este Metodo Implementa el metodo de Buscar(int valor, NodoABB) para indicar el tiempo de busqueda
    public NodoABB buscarConTiempo(String valor, NodoABB root) 
    {
        long inicio = System.nanoTime();
        NodoABB resultado = Buscar(valor, root);
        long fin = System.nanoTime();
        System.out.println("Tiempo de búsqueda en árbol: " + (fin - inicio) + " nanosegundos");
        return resultado;
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
    
    public static ArbolABB cargarDeJSON(String jsonContent) 
    {
        ArbolABB arbol = new ArbolABB();

        if (jsonContent.startsWith("{") && jsonContent.endsWith("}")) 
        {
            jsonContent = jsonContent.substring(1, jsonContent.length() - 1); // Quita las llaves externas

            // Divide el contenido en pares clave-valor
            String[] entradas = jsonContent.split("},\\s*\\{"); // Divide por objetos separados
            for (String entrada : entradas) 
            {
                entrada = entrada.replace("{", "").replace("}", ""); // Limpia las llaves

                // Divide clave y valor
                String[] partes = entrada.split(":\\s*\\[");
                String nombreEspecie = partes[0].replace("\"", "").trim(); // Limpia comillas
                String preguntas = partes[1].replace("]", "").trim();

                // Parsear las preguntas
                String[] listaPreguntas = preguntas.split(",\\s*\\{");
                NodoABB nodoActual = null;

                for (String pregunta : listaPreguntas) 
                {
                    pregunta = pregunta.replace("{", "").replace("}", "").trim();
                    String[] preguntaValor = pregunta.split(":");
                    String textoPregunta = preguntaValor[0].replace("\"", "").trim();
                    boolean respuesta = Boolean.parseBoolean(preguntaValor[1].trim());

                    NodoABB nuevoNodo = new NodoABB(textoPregunta);
                    if (nodoActual == null) 
                    {
                        nodoActual = nuevoNodo;
                        arbol.setRoot(nodoActual); // Establece la raíz
                    } 
                    else 
                    {
                        if (respuesta) 
                        {
                            nodoActual.setHijoDer(nuevoNodo);
                        } else 
                        {
                            nodoActual.setHijoIzq(nuevoNodo);
                        }
                        
                        nodoActual = nuevoNodo; // Actualiza el nodo actual
                    }
                }

                // Inserta la especie como hoja
                NodoABB nodoEspecie = new NodoABB(nombreEspecie);
                if (nodoActual != null) 
                {
                    nodoActual.setHijoDer(nodoEspecie);
                }
            }
        }
        
        return arbol;
    }


    private static String leerArchivo(String path) 
    {
        StringBuilder jsonContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) 
        {
            String line;
            while ((line = br.readLine()) != null) 
            {
                jsonContent.append(line.trim()); // Agrega cada línea al contenido JSON
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        
        return jsonContent.toString();
    }



    
    public String determinarEspecie(NodoABB root) 
    {
        NodoABB nodoActual = root;
        while (nodoActual != null && !nodoActual.esHoja()) 
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
        
        return nodoActual != null ? nodoActual.getDato() : "Especie no encontrada.";
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
