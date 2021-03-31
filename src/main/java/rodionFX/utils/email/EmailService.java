package rodionFX.utils.email;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

public class EmailService {
    public static void openEmailWindows(List<String> mailTo,
                                        String subject,
                                        String text,
                                        String filesToZipDir) throws IOException, URISyntaxException {
        String uriStr = String.format("mailto:%s?subject=%s&body=%s&attachment=%s",
                join(",", mailTo), // use semicolon ";" for Outlook!
                subject,
                text,
                urlEncode("file:///" + filesToZipDir));
        Desktop.getDesktop().mail(new URI(uriStr));
    }

    private static String urlEncode(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8)
                .replace("+", "%20");
    }

    public static String join(String sep, Iterable<?> objs) {
        StringBuilder sb = new StringBuilder();
        for (Object obj : objs) {
            if (sb.length() > 0) sb.append(sep);
            sb.append(obj);
        }
        return sb.toString();
    }

    public static void openMailLinux(String to, String subject, String body, String filesToZipDir) throws IOException {
//        StringBuilder mailto = new StringBuilder(
//                "mailto:" + to +
//                        "?subject=" + subject +
//                        "&body=" + body
//        );
//
//        mailto.append("&attach=").append(urlEncode(filesToZipDir));
//        try {
//            // For using the parameters check the following description
//            Runtime.getRuntime().exec("xdg-open " + mailto);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        String enc = "UTF-8";
        String url = "mailto:" +
                URLEncoder.encode(to, enc) +
                "?subject=" + URLEncoder.encode(subject, enc) +
                "&from=" + URLEncoder.encode("", enc) +
                "&attachment=" + URLEncoder.encode(filesToZipDir, enc);
        Runtime.getRuntime().exec("xdg-open " + url);
    }
}
