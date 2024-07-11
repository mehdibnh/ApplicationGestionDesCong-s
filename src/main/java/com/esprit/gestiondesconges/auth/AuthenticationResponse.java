package com.esprit.gestiondesconges.auth;
import com.esprit.gestiondesconges.user.Role;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponse {

  @JsonProperty("access_token")
  private String accessToken;
  @JsonProperty("refresh_token")
  private String refreshToken;

  @JsonProperty("email")
  private String email;

  @JsonProperty("id")
  private Integer id;

  @JsonProperty("password")
  private String password;

  @JsonProperty("role")
  private Role role;
}
