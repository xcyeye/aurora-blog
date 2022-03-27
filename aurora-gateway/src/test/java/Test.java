import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author qsyyke
 */


public class Test {
    public static void main(String[] args) {
        PasswordEncoder pa = new BCryptPasswordEncoder();
        String encode = pa.encode("123456");
        System.out.println(encode);
        System.out.println(pa.matches("123456", encode));
    }
}
