package ao.co.isptec.aplm.binasjc_app;

public class support_string_helper {

    public static boolean regexEmailValidationPattern(String email) {

        String regex = "([a-zA-Z0-9]+(?:[-_+\\.][a-zA-Z0-9]+)*)@([a-zA-Z0-9]+(?:[.-][a-zA-Z0-9]+)*\\.[a-zA-Z]{2,})";

        return email.matches(regex);
    }

}
