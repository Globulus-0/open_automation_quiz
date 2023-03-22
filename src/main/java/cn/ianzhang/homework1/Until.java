package cn.ianzhang.homework1;
import com.google.common.net.InternetDomainName;

import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Until {
    public static String urlTest(String urlstr) {
        try {
            URL url = new URL(urlstr);
            String host = url.getHost();
            InternetDomainName domainName = InternetDomainName.from(host);
            String top = domainName.topPrivateDomain().toString();
            return top;
        } catch (Exception e) {
            return null;
        }
    }

    public static Map<String, Long> getNumbers(List<String> nameLists) {
        Map<String, Long> nameMap = nameLists.stream().collect(Collectors.groupingBy(p -> p, Collectors.counting()));
        return nameMap;
    }
}
