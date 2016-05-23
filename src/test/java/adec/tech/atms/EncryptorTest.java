package adec.tech.atms;

import adec.tech.atms.security.Encryptor;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/10/2016
 *         Time: 1:08 PM
 */
public class EncryptorTest {

    public static void main(String[] args) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        System.out.println(Encryptor.bcrypt("admin"));
    }

}
