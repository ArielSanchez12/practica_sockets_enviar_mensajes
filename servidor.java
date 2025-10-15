import java.net.DatagramSocket;
import java.net.DatagramPacket;
//Vamos a usar hilos para esta activdad y el metodo UDP
public class servidor{
  public static void main(String[] args){
    int puerto = 4000; //definimos el puerto estatico (el sevidor no puede cambiar de puerto)
    //crear el socket UDP
    try {
      DatagramSocket socket = new DatagramSocket(puerto); //DatagramSocket es una clase interna de Java
      System.out.println("Esperando conexiones...");
      //Arreglo de bytes para recibir datos (donde recibimos los datos del cliente)
      byte[] bufferEntrada = new byte[1024]; //debemos establecer la longitud del datagrama (si el paquete excede la longitud, se divide)
      //Ahora vamos a crear un datagrama para recibir los datos que el cliente envie
      DatagramPacket datagrama = new DatagramPacket(bufferEntrada, bufferEntrada.length);  //debemos pasar el buffer de entrada y su longitud
      //El servidor va a estar esperando el mensaje del cliente
      socket.receive(datagrama);

      hiloCliente hilo = new hiloCliente(socket, datagrama); //pasamos el socket y datagrama para que sepa donde establecemos la conexion
      hilo.start();

    } catch (Exception e) { //para que tome todas las excepciones le borramos lo del socket
      e.printStackTrace();
    } 

  }
}