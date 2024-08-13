package com.luv2code.library_api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class IdToken {
  private String sub;
	private String nickname;
  private String name;
  private String picture;
  @JsonProperty("updated_at")
  private String updatedAt;
  private String email;  
  @JsonProperty("email_verified")
  private boolean emailVerified;
}
