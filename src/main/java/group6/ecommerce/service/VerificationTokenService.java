package group6.ecommerce.service;

import group6.ecommerce.model.Users;
import group6.ecommerce.model.VerificationToken;

import java.util.Date;

public interface VerificationTokenService {
    public VerificationToken createVerificationToken (Users user);
    public VerificationToken getVerificationToken (String token);
    public Users verifyUser (String token);
    public Date calculateExpiryDate (int expiryTimeInMinutes);
}
