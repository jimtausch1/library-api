package com.luv2code.library_api.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.luv2code.library_api.model.IdToken;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ExtractJWT {

  private static final String URL_USERINFO = "https://dev-l625782tnvb0qtsd.us.auth0.com/userinfo";

  public static String payloadJWTExtraction(String token, String extraction) {

    String parsed = token.replace("Bearer ", "");

    String[] chunks = parsed.split("\\.");
    Base64.Decoder decoder = Base64.getUrlDecoder();

    String payload = new String(decoder.decode(chunks[1]));

    String[] entries = payload.split(",");
    Map<String, String> map = new HashMap<String, String>();

    for (String entry : entries) {
      String[] keyValue = entry.split(":");
      if (keyValue[0].equals(extraction)) {

        int remove = 1;
        if (keyValue[1].endsWith("}")) {
          remove = 2;
        }
        keyValue[1] = keyValue[1].substring(0, keyValue[1].length() - remove);
        keyValue[1] = keyValue[1].substring(1);

        map.put(keyValue[0], keyValue[1]);
      }
    }
    if (map.containsKey(extraction)) {
      return map.get(extraction);
    }
    return null;
  }

  public static String emailJWTExtraction(String token) {
    try {
      // HttpHeaders
      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_JSON);
      headers.set("Authorization", token);

      // HttpEntity<String>: To get result as String.
      HttpEntity<String> entity = new HttpEntity<>(headers);

      // RestTemplate
      RestTemplate restTemplate = new RestTemplate();

      // Send request with GET method, and Headers.
      ResponseEntity<IdToken> response = restTemplate.exchange(URL_USERINFO, //
          HttpMethod.GET, entity, IdToken.class);

      IdToken result = response.getBody();

      System.out.println(result.getEmail());
      return result.getEmail();
    } catch (HttpClientErrorException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }
}
