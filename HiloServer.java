//Guillermo Ortega vargas
//Sistemas distribuidos
import java.io.*;
import java.net.*;
import java.util.logging.*;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;

public class HiloServer extends Thread { //cramos  la clase extendida de hilos. para un hilo para cada cliente que entre

    private Socket socket;
    private DataOutputStream dos;
    private DataInputStream dis;
    Scanner sc = new Scanner(System.in);
    int v=0; 
    File archivo = new File("doc.txt");//ruta del archivo para guardar datos
    FileWriter escribir;
    PrintWriter line;
    private int idSessio;

    public HiloServer(Socket socket, int id) {
        this.socket = socket;
        this.idSessio = id;
        try {//inicializa flujos de entrada y salida
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream());
        } catch (IOException ex) {
            Logger.getLogger(HiloServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void desconnectar() {
        try {
            socket.close();
        } catch (IOException ex) {
            Logger.getLogger(HiloServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    /**************************************************************************/
    public void run() {//sobrescribe metodo run del hilo
         String datCadena="";
        try {
             datCadena = dis.readUTF(); //recibimos una cadena
             System.out.println(" Cliente dice: "+datCadena);
            
        } catch (IOException ex) {
             System.out.println(" Error al obtener los datos  ");
        }
         String[] tokensDat = datCadena.split(","); // separamos la cadena por tokens
         
//decide el server que hara dependiendo la solicitud del cliente
      if(tokensDat.length==3){ 
         crea_inserta(tokensDat[0],tokensDat[1],tokensDat[2]);

       }
        if(tokensDat.length==2){
         mostrarClientes();

       }
        if(tokensDat.length==1){
         buscarClien(tokensDat[0]);

       }
    }




    public void crea_inserta(String nom, String suel,String ed ){//recibe los datos del cliente

         try {

        if (!archivo.exists()){//si no existe el archivo
      
                archivo.createNewFile();
                escribir = new FileWriter(archivo,true);
                line = new PrintWriter(escribir);
                //escribe en el archivo 
                line.println("Nombre: "+ nom);// escribe en el documento
                line.println("Sueldo: "+ suel);
                line.println("Edad: "+ ed);
                line.println("-----");
                escribir.close();//cerramos el docmuento
                System.out.println("\nCliente insertado ");
       
        }else {//si existe el archivo

                escribir = new FileWriter(archivo,true);
                line = new PrintWriter(escribir);
                //
                line.println("Nombre: "+ nom);// escribe en el documento
                line.println("Sueldo: "+ suel);
                line.println("Edad: "+ ed);
                line.println("-----");
                escribir.close();//cerramos el docmuento
                System.out.println(" Cliente insertado ");

        }
          dos.writeUTF("Cliente Ingresado");//termina y envia mensaje al cliente, 
         } catch (IOException ex) {
             System.out.println(" Error al insertar cliente ");
        }
        desconnectar();
    }


//mostrar datos
    public void mostrarClientes(){
    
    File fichero = new File("doc.txt"); // abrimos el fichero

    String busqueda;
    String respuesta="";

    try {
        BufferedReader fil = new BufferedReader(new FileReader(fichero));

        String linea;
        boolean encontrado = false;
        System.out.println("Buscando Clientes ");
        while ((linea = fil.readLine()) != null) {


                 respuesta+="\n"+linea;// concatena todos los datos
        
                 encontrado=true;

        }
         dos.writeUTF(respuesta);//enviamos la cadena concatenada
        if (!encontrado) {
            System.out.println("No hay clientes ");
        }else{System.out.println("Clientes Encontrados - enviando ");}
    } catch (IOException e) {

        System.out.println("Ha ocurrido un Error" + e);
    }
}


    
//metodo para buscar cliente 
    public void buscarClien(String nom) {
    File fichero = new File("doc.txt");

    String busqueda;
    String respuesta;

    try {
        BufferedReader fil = new BufferedReader(new FileReader(fichero));
        busqueda = "Nombre: " + nom; //nombre a buscar 

        String linea;
        boolean encontrado = false;
        while ((linea = fil.readLine()) != null || encontrado ==true) {

            if (linea.toUpperCase().equalsIgnoreCase(busqueda)) { //si encuentra el cliente-- compara la cadena
                encontrado = true;
                respuesta = "\n" + linea + "\n";
                for (int i = 0; i < 3; i++) {
                    respuesta += fil.readLine() + "\n";//concatena los datos obtenidos del documento
                }
                System.out.println("Cliente Encontrado: "+ respuesta );// se enviara a cliente socket
                dos.writeUTF(respuesta);// envia la cadena como respuesta al cliente
                encontrado = true;// cuando lo encunetra, termina el ciclo
                break;

            }
        }
        if (!encontrado) {
            System.out.println("Cliente NO Encontrado: ");
        }
    } catch (IOException e) {

        System.out.println("Ha ocurrido un Error" + e);
    }
}

}




