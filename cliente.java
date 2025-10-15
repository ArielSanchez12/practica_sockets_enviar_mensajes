
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class cliente {
  public static void main(String[] args) {
      int puerto_servidor = 4000; //el puerto del cliente puede ser otro puerto

      try {
        
        //ENVIAR
        InetAddress ip_servidor = InetAddress.getByName("localhost");
        DatagramSocket socket = new DatagramSocket();
        //mensaje de respuesta (para enviar el msj toca ponerlo dentro de un datagrama (si, todo es por medio de datagramas))
        String respuesta = "Hola, desde el cliente";
        //Arreglo de bytes para enviar datos
        byte[] bufferSalida = respuesta.getBytes(); //le asignamos directamente la longitud en bytes que tenga el mensaje ya que es un mensaje quemado
        //Crear datagrama para enviar el mensaje
        DatagramPacket datagrama_salida = new DatagramPacket(bufferSalida, bufferSalida.length, ip_servidor, puerto_servidor); //aqui tiene que ir, el buffer, longitud del buffer, ip del cliente y su puerto
        socket.send(datagrama_salida); //importante el orden arriba, buffer, buffer.length, ip del servidor y luego puerto
        
        //RECIBIR
        //Arreglo de bytes para recibir datos (donde recibimos los datos del cliente)
        byte[] bufferEntrada = new byte[1024]; //debemos establecer la longitud del datagrama (si el paquete excede la longitud, se divide)
        //Ahora vamos a crear un datagrama para recibir los datos que el cliente envie
        DatagramPacket datagrama = new DatagramPacket(bufferEntrada, bufferEntrada.length);  //debemos pasar el buffer de entrada y su longitud
        //El servidor va a estar esperando el mensaje del cliente
        socket.receive(datagrama);

        //Extraer el mensaje
        String mensaje = new String(datagrama.getData(),0, datagrama.getLength()); //ni idea para que es el offset pero sin eso no funciona (no hay que escribir offset, solo escribir 0)
        System.out.println("Mensaje recibido: " + mensaje);
        socket.close();
      } catch (Exception e) {
        e.printStackTrace();
      }
  }
}
