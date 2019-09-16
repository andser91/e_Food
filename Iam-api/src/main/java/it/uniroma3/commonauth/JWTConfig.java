package it.uniroma3.commonauth;

public class JWTConfig {

    private Integer expiration = 24 * 60 * 60;
    private String uri = "/login/**";
    private String secret = "B97D656F7EBB0654B8E8304755519C34D98FB045C88A3CFA0CF6BAF690C6F383";
    private String header = "Authorization";
    private String prefix = "Bearer ";

   private static JWTConfig instance;

   public static JWTConfig getInstance() {
       return instance == null ? instance = new JWTConfig() : instance;
   }

    public Integer getExpiration() {
        return expiration;
    }

    public String getUri() {
        return uri;
    }

    public String getSecret() {
        return secret;
    }

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }
}
