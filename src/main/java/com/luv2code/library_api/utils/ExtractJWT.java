package com.luv2code.library_api.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

public class ExtractJWT {

  public static String payloadJWTExtraction(String token, String extraction) {

    String parsed = token.replace("Bearer ", "");

    String[] chunks = parsed.split("\\.");
    Base64.Decoder decoder = Base64.getUrlDecoder();

    String payload = new String(decoder.decode(chunks[0]));

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

  public static String Validate(String jwtToken) {
    String AUTH0_SECRET = "50724c44a0af1aaea7c6488b487c4b21ca518a694b296dbc3796a945ba5d8ab9";
    String AUTH0_ISSUER_BASE_URL = "https://dev-l625782tnvb0qtsd.us.auth0.com";
    Algorithm algorithm = Algorithm.HMAC256(AUTH0_SECRET);
    JWTVerifier verifier = JWT.require(algorithm)
        .withIssuer(AUTH0_ISSUER_BASE_URL)
        .build();

    try {
      String parsed = jwtToken.replace("Bearer ", "");
      DecodedJWT decodedJWT = verifier.verify(parsed);
      Claim claim = decodedJWT.getClaim("userId");
      return claim.asString();
    } catch (JWTVerificationException e) {
      System.out.println(e.getMessage());
    }

    return null;
  }
}
