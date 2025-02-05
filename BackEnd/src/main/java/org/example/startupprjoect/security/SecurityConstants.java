package org.example.startupprjoect.security;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Base64;

public class SecurityConstants {
    public static final long JWT_EXPIRATION = 86400000 ;

}
