package org.network.urlconnection;

import java.io.*;
import java.net.*;
import java.nio.charset.*;
import java.util.*;

/**
 * This program establishes a connection to a specified URL,
 * displays the header data from the response, and prints
 * the first 10 lines of the requested data. The URL, along with
 * an optional username and password (for basic HTTP authentication),
 * can be provided via the command line.
 *
 * @version 1.12 2018-03-17
 * @author Cay Horstmann
 */
public class URLConnectionTest {
    public static void main(String[] args) {
        try {
            String urlName;
            if (args.length > 0) {
                urlName = args[0];
            } else {
                urlName = "https://ok.ru";
            }

            var url = new URL(urlName);
            URLConnection connection = url.openConnection();

            // Set username and password if provided via command line
            if (args.length > 2) {
                String username = args[1];
                String password = args[2];
                String input = username + ":" + password;
                Base64.Encoder encoder = Base64.getEncoder();
                String encoding = encoder.encodeToString(input.getBytes(StandardCharsets.UTF_8));
                connection.setRequestProperty("Authorization", "Basic " + encoding);
            }

            connection.connect();

            // Print header fields
            Map<String, List<String>> headers = connection.getHeaderFields();
            for (Map.Entry<String, List<String>> entry : headers.entrySet()) {
                String key = entry.getKey();
                for (String value : entry.getValue()) {
                    System.out.println(key + ": " + value);
                }
            }

            // Print header values using convenient methods
            System.out.println("----------");
            System.out.println("getContentType: " + connection.getContentType());
            System.out.println("getContentLength: " + connection.getContentLength());
            System.out.println("getContentEncoding: " + connection.getContentEncoding());
            System.out.println("getDate: " + connection.getDate());
            System.out.println("getExpiration: " + connection.getExpiration());
            System.out.println("getLastModified: " + connection.getLastModified());
            System.out.println("----------");

            String encoding = connection.getContentEncoding();
            if (encoding == null) encoding = "UTF-8";

            // Print the first 10 lines of the content
            try (var in = new Scanner(connection.getInputStream(), encoding)) {
                for (int n = 1; in.hasNextLine() && n <= 10; n++) {
                    System.out.println(in.nextLine());
                }
                if (in.hasNextLine()) {
                    System.out.println(". . .");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
