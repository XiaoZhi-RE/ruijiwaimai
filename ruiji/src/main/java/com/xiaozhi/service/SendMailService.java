package com.xiaozhi.service;

import javax.mail.MessagingException;

/**
 * @author 20232
 */
public interface SendMailService {
    void send(String email, String code) throws MessagingException;
}
