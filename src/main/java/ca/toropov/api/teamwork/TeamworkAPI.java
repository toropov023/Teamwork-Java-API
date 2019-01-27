package ca.toropov.api.teamwork;

import ca.toropov.api.teamwork.command.Command;
import ca.toropov.api.teamwork.util.Base64Coder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Author: toropov
 * Date: 1/26/2019
 */
@RequiredArgsConstructor
public class TeamworkAPI {
    @Getter
    private static TeamworkAPI i;
    private final String apiKey;
    private final String subdomain;

    {
        if (i == null) {
            i = this;
        }
    }

    /**
     * Send the command to TeamWork API
     *
     * @param command The command to be sent
     */
    public void dispatch(Command command) {
        String address = "https://" + subdomain.replace("https://", "")
                .replace("http://", "")
                .replace("www.", "")
                .replace("teamwork.com", "") + ".teamwork.com"; //Just a bit of idiot proofing
        address += command.getRequestUrl();
        try {
            URL url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(command.getRequestMethod().toString());

            String pass = Base64Coder.encodeString(apiKey + ":xxx");
            connection.setRequestProperty("Authorization", "Basic " + pass);

            String json = command.getJson();
            if (json != null) {
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");

                OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
                osw.write(json);
                osw.flush();
                osw.close();
            }

            int responseCode = connection.getResponseCode();
            if (responseCode > 0 && responseCode != 200 && responseCode != 201) {
                command.onError(streamToString(connection.getErrorStream()));
            }
            command.onCallback(streamToString(connection.getInputStream()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String streamToString(InputStream in) {
        try (Scanner scanner = new Scanner(in, StandardCharsets.UTF_8.name())) {
            return scanner.useDelimiter("\\A").next();
        }
    }
}
