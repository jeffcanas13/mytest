package adec.tech.atms;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author Jonathan Leijendekker
 *         Date: 2/11/2016
 *         Time: 6:15 PM
 */
public class DateTest {

    public static void main(String[] args) {

        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());

        System.out.println(timestamp);

    }

}
