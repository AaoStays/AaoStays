package com.aao.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Utility class for generating unique booking reference codes.
 * Format: BKG-{YYMMDD}-{RANDOM_ALPHANUMERIC}
 * Example: BKG-251206-A3K9M2
 */
@Component
public class BookingReferenceGenerator {

    private static final String PREFIX = "BKG";
    private static final String ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int RANDOM_LENGTH = 6;
    private static final SecureRandom random = new SecureRandom();
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyMMdd");

    /**
     * Generates a unique booking reference.
     * Format: BKG-{YYMMDD}-{6-char-random}
     *
     * @return Unique booking reference string
     */
    public String generateReference() {
        String datePart = LocalDateTime.now().format(DATE_FORMATTER);
        String randomPart = generateRandomString(RANDOM_LENGTH);
        return String.format("%s-%s-%s", PREFIX, datePart, randomPart);
    }

    /**
     * Generates a random alphanumeric string of specified length.
     *
     * @param length Length of the random string
     * @return Random alphanumeric string
     */
    private String generateRandomString(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(ALPHANUMERIC.length());
            sb.append(ALPHANUMERIC.charAt(index));
        }
        return sb.toString();
    }
}
