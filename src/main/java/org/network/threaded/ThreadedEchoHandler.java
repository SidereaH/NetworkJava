package org.network.threaded;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Этот класс обрабатывает данные, получаемые сервером
 * от клиента через одно сокетное соединение
 */
class ThreadedEchoHandler implements Runnable
{
    private Socket incoming;

    /**
     Конструирует обработчик
     @param incomingSocket Входящий сокет
     */
    public ThreadedEchoHandler(Socket incomingSocket)
    {
        incoming = incomingSocket;
    }

    public void run()
    {
        try (InputStream inStream = incoming.getInputStream();
             OutputStream outStream = incoming.getOutputStream();
             var in = new Scanner(inStream, StandardCharsets.UTF_8);
             var out = new PrintWriter(
                     new OutputStreamWriter(outStream, StandardCharsets.UTF_8),
                     true /* autoFlush */))
        {
            out.println("Hello! Enter BYE to exit.");
            out.flush();
            out.println("flush?!");
            // передать обратно данные, полученные от клиента
            var done = false;
            while (!done && in.hasNextLine())
            {
                String line = in.nextLine();
                out.println("Echo: " + line);
                System.out.println("Echo: " + line);
                if (line.trim().equals("BYE"))
                    done = true;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
