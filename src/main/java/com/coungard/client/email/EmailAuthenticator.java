package com.coungard.client.email;

import javax.mail.PasswordAuthentication;

public class EmailAuthenticator extends javax.mail.Authenticator {
    private String login;
    private String password;

    public EmailAuthenticator(final String login, final String password) {
        this.login = login;
        this.password = password;
    }

    public PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(login, password);
    }
}