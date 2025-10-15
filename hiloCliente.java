import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

//Este hilo se va a ejecutar en el servidor para ejecutar varios clientes
public class hiloCliente extends Thread {
  private DatagramSocket socket;
  private static DatagramPacket ultimoDatagrama;

  //constructor
  public hiloCliente(DatagramSocket socket, DatagramPacket datagrama){
    this.socket = socket;
    ultimoDatagrama = datagrama;
  }
  
  @Override
  public void run(){
    //extraer la informacion del datagrama recibido
    String mensaje = new String(ultimoDatagrama.getData(),0, ultimoDatagrama.getLength()); //ni idea para que es el offset pero sin eso no funciona (no hay que escribir offset, solo escribir 0)
    System.out.println("Mensaje recibido: " + ultimoDatagrama.getAddress() + ":" + ultimoDatagrama.getPort()+ ": " + mensaje); //el offset de arriba segun ayuda a que si se reciba el mensaje, de no estar, no va a recibir el mensaje por alguna razon
    
    //Una vez recibimos el mensaje, le vamos a responder al cliente
    //para eso primero necesitamos obtener la IP del cliente
    

    //mensaje de respuesta (para enviar el msj toca ponerlo dentro de un datagrama (si, todo es por medio de datagramas))
    while (true) { 
      Scanner input = new Scanner(System.in);
      System.out.println("Ingresa la respuesta para el cliente: " + ultimoDatagrama.getAddress() + ":" + ultimoDatagrama.getPort()+ ": " );
      String respuesta = input.nextLine();

      InetAddress ip_cliente = ultimoDatagrama.getAddress(); //direccion
      int puerto_cliente = ultimoDatagrama.getPort(); // y puerto se obtienen en automatico cuando recibo mensaje siendo el servidor

      //Arreglo de bytes para enviar datos
      byte[] bufferSalida = respuesta.getBytes(); //le asignamos directamente la longitud en bytes que tenga el mensaje ya que es un mensaje quemado
      //Crear datagrama para enviar el mensaje
      DatagramPacket datagrama_salida = new DatagramPacket(bufferSalida, bufferSalida.length, ip_cliente, puerto_cliente); //aqui tiene que ir, el buffer, longitud del buffer, ip del cliente y su puerto
      try {
        socket.send(datagrama_salida);
        System.out.println("Respuesta enviada a " + ip_cliente + ":" + puerto_cliente);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }
  
}
