
package services;

import javax.inject.Inject;

public class FooService extends BaseFooService {

    @Inject
    public FooService(ConfigService configService) {
        super(configService);
    }

    public String externalApiCall(int id) {
        // pathogen
        int delayInMillis = super.getDelayInMillis();
        try { Thread.sleep(delayInMillis); } catch (Exception ex) {}
        return "" + id + "-" + new java.util.Date().toString();
    }

    /*
     * silly method
     * param input string
     *
     * return string with no spaces and doubled !
     * e.g. "abc def!" => "abcdef!!"
     */
    public String foo(String s) {
        StringBuilder builder = new StringBuilder();

        if (s != null) {
            if (!s.isEmpty()) {
                // 'char' is a String here
                String[] chars = s.split("");

                for (var c : chars) {
                    if (! c.equals(" ")) {
                        builder.append(c);
                    } else if (c.equals("!")) {
                        builder.append(c);
                        builder.append(c);
                    }
                }
            } else {
                System.err.println("WARN empty arg");
            }
        } else {
            throw new IllegalArgumentException("null arg");
        }

        return builder.toString();
    }
}
