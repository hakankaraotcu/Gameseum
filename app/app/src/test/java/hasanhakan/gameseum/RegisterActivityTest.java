package hasanhakan.gameseum;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static org.junit.Assert.assertEquals;

public class RegisterActivityTest {
    Pattern pattern = Pattern.compile("^.+@.+\\..+$");
    String[] Emails = new String[]{"hasan@gmail.com", "hakan@gmail.com", "hasanarcas@gmail.com"};

    String[] passwords = new String[] {"123456", "123456", "12345678"};
    String[] passwordsAgain = new String[] {"123456", "123456", "12345678"};

    @Test
    public void userIsValid() {
        Matcher matcher1 = pattern.matcher(Emails[0]);
        Matcher matcher2 = pattern.matcher(Emails[1]);
        Matcher matcher3 = pattern.matcher(Emails[2]);
        assertEquals(matcher1.matches(), true);
        assertEquals(matcher2.matches(), true);
        assertEquals(matcher3.matches(), true);

    }
    @Test
    public void passwordMatches(){
        for(int i=0; i < passwords.length;i++){
            if(passwords[i].equals(passwordsAgain[i]))
                assertEquals(true, true);
            else
                assertEquals(false,true);
        }
    }
    @Test
    public void passwordIsValid(){
        for(int i=0; i < passwords.length;i++){
            if(passwords[i].length()<6)
                assertEquals(false,true);
            else
                assertEquals(true,true);
        }
    }
}