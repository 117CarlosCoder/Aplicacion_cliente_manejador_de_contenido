package Cliente;

import UI.Inicio;
import static com.compi.proyecto.compiladores.Proyecto2Compiladores.app;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Cliente {
    ByteBuffer buffer = ByteBuffer.allocate(1024);
  
    public void startClient(String mensaje) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.connect(new InetSocketAddress("localhost", 8888));
            // Configurar el socket como bloqueante
            socketChannel.configureBlocking(true);
            
            // Enviar el mensaje al servidor
            buffer = ByteBuffer.allocate(Math.max(1024, mensaje.getBytes().length));
            buffer.put(mensaje.getBytes());
            buffer.flip();
            socketChannel.write(buffer);
            
            // Leer la respuesta del servidor

            respuestaServidor(socketChannel);
            // Cerrar la conexión
            socketChannel.close();
        } catch (IOException ex) {
            Logger.getLogger(Cliente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void respuestaServidor(SocketChannel socketChannel) throws IOException{
            buffer.clear();
            int bytesRead = socketChannel.read(buffer);
            if (bytesRead > 0) {
                buffer.flip();
                byte[] responseData = new byte[buffer.limit()];
                buffer.get(responseData);
                String responseMessage = new String(responseData).trim();
                app.getjTextPane2().setText(responseMessage);
                
            } else {
                System.out.println("No response received from server.");
            }
    }
}
