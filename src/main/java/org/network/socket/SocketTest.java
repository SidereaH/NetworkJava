package org.network.socket;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * В этой программе устанавливается сокетное соеденинеие
 * с атомными часами в г. Боулдере, шт. Колорадо и выводится время,
 * передаваемое из сервера.
 * @version 1.0 11.09.2024
 * @author Siderea
 */
public class SocketTest {
    public static void main(String[] args) throws IOException{
        try(var s =  new Socket("time-a.nist.gov", 13)){
            var in = new Scanner(s.getInputStream(), StandardCharsets.UTF_8);
            while (in.hasNextLine()){
                String line = in.nextLine();
                System.out.println(line);
            }
        }
    }
}
