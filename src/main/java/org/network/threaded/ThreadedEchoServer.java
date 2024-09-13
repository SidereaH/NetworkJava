package org.network.threaded;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

/**
 * В этой программе реализуется многопоточный сервер,
 * прослушивающий порт 8189 и передающий обратно все данные,
 * полученные от всех клиентов
 * @author Sidera
 * @version 1.23 2024-09-12
 */
public class ThreadedEchoServer
{
    public static void main(String[] args )
    {
        try (var s = new ServerSocket(8189))
        {
            int i = 1;

            while (true)
            {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                Runnable r = new ThreadedEchoHandler(incoming);
                var t = new Thread(r);
                t.start();
                i++;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
