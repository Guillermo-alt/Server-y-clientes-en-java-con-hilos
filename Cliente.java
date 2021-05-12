//Guillermo Ortega vargas
//Sistemas distribuidos
import java.io.*;
import java.net.Socket;
import java.util.*;
import java.util.logging.*;

public class Cliente {
    public static void main(String[] args) {
            HiloCliente client = new HiloCliente();   
            client.start();  
        }
}