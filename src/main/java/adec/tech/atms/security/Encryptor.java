package adec.tech.atms.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * An encryption utility to encrypt strings to sha-256 or sha-512 hash format.
 *
 * @author Jonathan Leijendekker
 */
public class Encryptor {

    // ===================================
    // SHA-256 encryption
    // ===================================

    /**
     * Encrypt the string into sha-256 hash format
     *
     * @param s
     * @return Encrypted string
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha256(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(s.getBytes());
        byte[] digest = md.digest();

        return convertByteToHex(digest);
    }

    /**
     * Encrypt the string into sha-256 hash format. Requires java 8 and above
     *
     * @param s
     * @return Encrypted string using the Base64 encoder
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha256Base64(String s) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("SHA-256");

        md.update(s.getBytes());
        byte[] digest = md.digest();

        return Base64.getEncoder().encodeToString(digest);

    }

    // ===================================
    // SHA-512 encryption
    // ===================================

    /**
     * Encrypt the string into sha-512 hash format
     *
     * @param s
     * @return Encrypted string
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha512(String s) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        final MessageDigest md = MessageDigest.getInstance("SHA-512");

        md.update(s.getBytes());
        byte[] digest = md.digest();

        return convertByteToHex(digest);
    }

    /**
     * Encrypt the string into sha-512 hash format. Requires java 8 and above
     *
     * @param s
     * @return Encrypted string using the Base64 encoder
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    public static String sha512Base64(String s) throws NoSuchAlgorithmException {
        final MessageDigest md = MessageDigest.getInstance("SHA-512");

        md.update(s.getBytes());
        byte[] digest = md.digest();

        return Base64.getEncoder().encodeToString(digest);

    }

    private static String convertByteToHex(byte data[]) {
        StringBuilder hexData = new StringBuilder();
        for (byte aData : data) hexData.append(Integer.toString((aData & 0xff) + 0x100, 16).substring(1));

        return hexData.toString();
    }

    // ===================================
    // AES encryption
    // ===================================

    private static byte[] key = {
            0x2d, 0x2a, 0x2d, 0x42, 0x55, 0x49, 0x4c, 0x44, 0x41, 0x43, 0x4f, 0x44, 0x45, 0x2d, 0x2a, 0x2d
    };

    public static String encrypt(String plainText) {
        return getAesValue(plainText, Cipher.ENCRYPT_MODE);
    }

    public static String decrypt(String encryptedText) {
        return getAesValue(encryptedText, Cipher.DECRYPT_MODE);
    }

    private static String getAesValue(String text, int mode) {
        String value = null;

        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
            SecretKeySpec secretKey = new SecretKeySpec(key, "AES");
            cipher.init(mode, secretKey);

            byte[] cipherText;

            if (mode == Cipher.ENCRYPT_MODE) {
                cipherText = cipher.doFinal(text.getBytes("UTF8"));
                value = new String(Base64.getEncoder().encode(cipherText), "UTF-8");
            } else if (mode == Cipher.DECRYPT_MODE) {
                cipherText = Base64.getDecoder().decode(text.getBytes("UTF8"));
                value = new String(cipher.doFinal(cipherText), "UTF-8");
            }

            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public static String bcrypt(CharSequence charSequence) {

        SecureRandom random;

        try {
            random = SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            random = new SecureRandom();
        }

        byte[] randomBytes = new byte[256];
        random.nextBytes(randomBytes);

        int seedByteCount = 5;
        byte[] seed = random.generateSeed(seedByteCount);
        random.setSeed(seed);

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(16, random);
        return encoder.encode(charSequence);

    }

    public static boolean bcryptMatches(CharSequence charSequence, String s) {
        return new BCryptPasswordEncoder().matches(charSequence, s);
    }

}
