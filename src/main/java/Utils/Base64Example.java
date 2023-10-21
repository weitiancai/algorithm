package Utils;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Base64Example {

    public static void main(String[] args) {
        String originalText = "Hello, World!";

        // Base64 编码
        String encodedText = Base64.getEncoder().encodeToString(originalText.getBytes(StandardCharsets.UTF_8));
        System.out.println("Encoded Text: " + encodedText);

        String contactCode = "ZGVlcmlubGVvCg==";
        // Base64 解码
        byte[] decodedBytes = Base64.getDecoder().decode(contactCode);
        String decodedText = new String(decodedBytes, StandardCharsets.UTF_8);
        System.out.println("Decoded Text: " + decodedText);

    }
}
