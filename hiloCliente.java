import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

//Este hilo se va a ejecutar en el servidor para ejecutar varios clientes
public class hiloCliente extends Thread {
  private DatagramSocket socket;
  private DatagramPacket datagrama;

  //constructor
  public hiloCliente(DatagramSocket socket, DatagramPacket datagrama){
    this.socket = socket;
    this.datagrama = datagrama;
  }
  
  @Override
  public void run(){
    //extraer la informacion del datagrama recibido
    String mensaje = new String(datagrama.getData(),0, datagrama.getLength()); //ni idea para que es el offset pero sin eso no funciona (no hay que escribir offset, solo escribir 0)
    System.out.println("Mensaje recibido: " + mensaje); //el offset de arriba segun ayuda a que si se reciba el mensaje, de no estar, no va a recibir el mensaje por alguna razon
    
    //Una vez recibimos el mensaje, le vamos a responder al cliente
    //para eso primero necesitamos obtener la IP del cliente
    InetAddress ip_cliente = datagrama.getAddress();
    int puerto_cliente = datagrama.getPort();

    //mensaje de respuesta (para enviar el msj toca ponerlo dentro de un datagrama (si, todo es por medio de datagramas))
    String respuesta = "Hola, desde el servidor";
    //Arreglo de bytes para enviar datos
    byte[] bufferSalida = respuesta.getBytes(); //le asignamos directamente la longitud en bytes que tenga el mensaje ya que es un mensaje quemado
    //Crear datagrama para enviar el mensaje
    DatagramPacket datagrama_salida = new DatagramPacket(bufferSalida, bufferSalida.length, ip_cliente, puerto_cliente); //aqui tiene que ir, el buffer, longitud del buffer, ip del cliente y su puerto

    try {
        socket.send(datagrama_salida);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
  
}
