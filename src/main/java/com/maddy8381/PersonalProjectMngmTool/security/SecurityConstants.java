package com.maddy8381.PersonalProjectMngmTool.security;

public class SecurityConstants {

    public static final String SIGN_UP_URLS = "/api/users/**";
    public static final String H2_URL = "h2-console/**";
    public static final String SECRET = "SecretKeyToGenJWTs";
    public static final String TOKEN_PREFIX = "Bearer "; //Space Important
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 3000_000; //3000 seconds
}
