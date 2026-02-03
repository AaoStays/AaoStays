package com.aao.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailService {

    @Autowired	
    private JavaMailSender mailSender;

    private void sendHtmlEmail(String to, String subject, String htmlContent) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("AaoStays <kottalarishi@gmail.com>"); 
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlContent, true); // HTML enabled

            mailSender.send(message);

        } catch (MessagingException e) {
            throw new RuntimeException("Failed to send email: " + e.getMessage());
        }
    }

    
    public void sendEmailVerificationLink(String toEmail, String verificationLink) {
        String html = "<p>Click below to verify your email:</p>"
                + "<a href='" + verificationLink + "'>Verify Email</a>";

        sendHtmlEmail(toEmail, "Verify Your Email - AaoStays", html);
    }

    public void sendLoginNotification(String toEmail, String userName) {

        String html =
            "<div style='font-family: Arial, sans-serif; background:#f6f7fb; padding:30px;'>" +

                "<div style='max-width:500px; margin:auto; background:#ffffff; padding:25px; " +
                "border-radius:12px; box-shadow:0 4px 12px rgba(0,0,0,0.1);'>" +

                    "<h2 style='color:##000000; margin-bottom:10px;'>AaoStays Login Alert</h2>" +

                    "<p style='font-size:15px; color:#333;'>Hello <b>" + userName + "</b>,</p>" +

                    "<p style='font-size:14px; color:#555; line-height:1.5;'>" +
                    "You have successfully logged in to your AaoStays account." +
                    "</p>" +

                    "<p style='font-size:14px; color:#555; line-height:1.5;'>" +
                    "If this wasn't you, please reset your password immediately to keep your account secure." +
                    "</p>" +

                    "<hr style='margin:20px 0; border:none; border-top:1px solid #eee;'/>" +

                    "<p style='font-size:12px; color:#999;'>" +
                    "© 2026 AaoStays · Secure Login Notification" +
                    "</p>" +

                "</div>" +
            "</div>";

        sendHtmlEmail(toEmail, "Login Alert - AaoStays", html);
    }

   
    public void sendBookingEmail(
            String toEmail,
            String userName,
            String bookingReference,
            String confirmationCode,
            String propertyName,
            LocalDate checkIn,
            LocalDate checkOut,
            BigDecimal totalAmount) {

        String html =
            "<div style='font-family:Arial,Helvetica,sans-serif; background:#f5f7fa; padding:30px;'>"
          + "  <div style='max-width:600px; margin:auto; background:#ffffff; border-radius:14px; padding:28px;'>"
          + "    <h2 style='margin-top:0; color:#111;'>Booking Confirmed 🎉</h2>"
          + "    <p style='font-size:14px;'>Dear <b>" + userName + "</b>,</p>"
          + "    <p style='font-size:14px;color:#444;'>Your booking with <b>AaoStays</b> has been confirmed.</p>"

          + "    <div style='background:#f9fafb; border-radius:10px; padding:16px; margin:20px 0;'>"
          + "      <p style='margin:6px 0;font-size:14px;'><b>Booking Reference:</b> " + bookingReference + "</p>"
          + "      <p style='margin:6px 0;font-size:14px;'><b>Confirmation Code:</b> " + confirmationCode + "</p>"
          + "      <p style='margin:6px 0;font-size:14px;'><b>Property:</b> " + propertyName + "</p>"
          + "      <p style='margin:6px 0;font-size:14px;'><b>Check-In:</b> " + checkIn + "</p>"
          + "      <p style='margin:6px 0;font-size:14px;'><b>Check-Out:</b> " + checkOut + "</p>"
          + "      <p style='margin:6px 0;font-size:14px;'><b>Total Amount:</b> ₹" + totalAmount + "</p>"
          + "    </div>"

          + "    <p style='font-size:14px;'>We look forward to hosting you soon.</p>"
          + "    <p style='font-size:12px;color:#777;'>If you didn't make this booking, contact support immediately.</p>"
          + "    <hr style='border:none;border-top:1px solid #eee;margin:20px 0;'>"
          + "    <p style='font-size:12px;color:#999;'>Warm regards,<br><b>AaoStays Team</b></p>"
          + "  </div>"
          + "</div>";

        sendHtmlEmail(toEmail, "Booking Confirmation - " + bookingReference, html);
    }

  
    public void sendCancellationEmail(
            String toEmail,
            String userName,
            String propertyName) {

        String html =
            "<div style='font-family:Arial,Helvetica,sans-serif; background:#f5f7fa; padding:30px;'>"
          + "  <div style='max-width:520px; margin:auto; background:#ffffff; border-radius:12px; padding:24px;'>"
          + "    <h2 style='margin-top:0;color:#b91c1c;'>Booking Cancelled</h2>"
          + "    <p style='font-size:14px;'>Hi <b>" + userName + "</b>,</p>"
          + "    <p style='font-size:14px;color:#444;'>Your booking at <b>" + propertyName + "</b> has been cancelled.</p>"
          + "    <p style='font-size:13px;color:#777;'>If you didn't perform this cancellation, contact support immediately.</p>"
          + "    <hr style='border:none;border-top:1px solid #eee;margin:20px 0;'>"
          + "    <p style='font-size:12px;color:#999;'>Regards,<br><b>AaoStays Team</b></p>"
          + "  </div>"
          + "</div>";

        sendHtmlEmail(toEmail, "Booking Cancelled - AaoStays", html);
    }

}
