import java.text.MessageFormat;

public class FormatTest {

    public static void simple(String content, Object... args) {
        System.out.println(new MessageFormat(String.valueOf(content)).format(args));
    }

    public static void main(String[] args) {
        simple("&a{0} {0} {1}!", "hello", "elo");
    }

}
