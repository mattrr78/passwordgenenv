package org.mattrr78.passwordgenenv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@RestController
public class Application {

    public static final String VERSION = "1.0.0";

    private final PasswordGenerator generator;

    private final String ipAddress;

    private final GeneratePasswordRequest request;

    public Application()  {
        generator = new PasswordGenerator();
        ipAddress = getIpAddress();

        request = new GeneratePasswordRequest();
        request.setCount(getEnvironmentVariableIntegerValue("PASSWORD_COUNT"));
        request.setMinimumLength(getEnvironmentVariableIntegerValue("PASSWORD_MINIMUM_LENGTH"));
        request.setMaximumLength(getEnvironmentVariableIntegerValue("PASSWORD_MAXIMUM_LENGTH"));
    }

    private String getIpAddress()  {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    @GetMapping(path = "/api/password")
    public ResponseEntity<GeneratePasswordResponse> generate() {
        String[] passwords = generator.generate(request);

        GeneratePasswordResponse response = new GeneratePasswordResponse();
        response.setPasswords(passwords);
        response.setFootnote(new Footnote(VERSION, ipAddress));

        return ResponseEntity.ok(response);
    }

    public int getEnvironmentVariableIntegerValue(String variableName)  {
        String valueStr = System.getenv(variableName);
        if (valueStr == null || valueStr.isBlank())  {
            throw new IllegalStateException("Environment variable '" + variableName + "' not set.");
        }
        return Integer.parseInt(valueStr);
    }

    public static void main(String[] args) {
        setupLogging();

        SpringApplication.run(Application.class, args);
    }

    private static void setupLogging()  {
        String name = System.getenv("NAME");
        if (name == null || name.isBlank())  {
            name = "spring";
        }

        System.setProperty("logging.file.name", "logs/" + name + ".log");
    }

}
