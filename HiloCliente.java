//Guillermo Ortega vargas
//Sistemas distribuidos
//
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.logging.*;
import java.util.Scanner;

class HiloCliente extends Thread {
    protected Socket sk;
    protected DataOutputStream dos;
    protected DataInputStream dis;

    Scanner sc = new Scanner(System.in);
    int v=0;




@Override
 public void run() {
        System.out.println("\nConectando con el server\n");
             menuClien();
    }

public void conectar(){
     try {
            sk = new Socket("127.0.0.1", 10578);
            dos = new DataOutputStream(sk.getOutputStream());
            dis = new DataInputStream(sk.getInputStream());
             } catch (IOException ex) {
           System.out.println("\nError al conectar con server");
            System.exit(0);
             }
}

public void menuClien(){
  boolean salir = false;
       int opcion; //Guardaremos la opcion del usuario
     Scanner sn = new Scanner(System.in);
       while(!salir){
           System.out.println("********** MENU **************\n");
           System.out.println("1. Ingresar Cliente");
           System.out.println("2. Monstrar todos los clientes");
           System.out.println("3. Buscar Cliente");
           System.out.println("4. Salir\n");
           System.out.println("******************************");
           System.out.println("Escribe una de las opciones");
           opcion = sn.nextInt();
            
           switch(opcion){
               case 1:
                  conectar();
                   try {
                        System.out.println(" Ingrese datos Separados por 'Nombre,Sueldo,Edad' ");
                        String msn = sc.nextLine();
                        dos.writeUTF(msn);// enviamos mensaje por socket
                        String respuesta = dis.readUTF();//recibimos una respuesta del server
                        System.out.println(respuesta);
                        dis.close();
                        dos.close();
                        sk.close();
                     } catch (IOException ex) {
                       System.out.println(" Error al enviar datos al server");
                      }
                   break;
               case 2:
                     conectar();
                   try {
                        System.out.println("Mostrando todos los clientes... ");
                        dos.writeUTF("0,0");// enviamos mensaje por socket
                        String respuesta = dis.readUTF();//recibimos una respuesta del server
                        System.out.println(respuesta);//imprime clientes enviados
                        dis.close();
                        dos.close();
                        sk.close();
                     } catch (IOException ex) {
                       System.out.println(" Error al enviar datos al server");
                      }
                   break;
                case 3:
                   conectar();
                   try {
                        System.out.println("Ingrece Nombre de cliente a buscar");
                        String msn = sc.nextLine();
                        dos.writeUTF(msn);// enviamos mensaje por socket
                        String respuesta = dis.readUTF();//recibimos una respuesta del server
                        System.out.println(respuesta);//imprime cliente buscado por el server
                        dis.close();
                        dos.close();
                        sk.close();
                     } catch (IOException ex) {
                       System.out.println(" Error al enviar datos al server");
                      }
                   break;
                case 4: 
                conectar();
                   salir=true;
                   break;
                default:
                   System.out.println("Solo números entre 1 y 4");
           }
            
       }
}        

}