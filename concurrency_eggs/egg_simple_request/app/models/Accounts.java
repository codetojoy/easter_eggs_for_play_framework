
package models;

import java.util.List;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.toList;

public class Accounts {
    public static List<Account> genAccounts(int numAccounts) {
        return IntStream.range(0, numAccounts)
                        .mapToObj(i -> {
                            int id = i;
                            String name = "acct-" + (5150 + i);
                            String address = i + "_Longworth_Ave";
                            return new Account(id, name, address);
                        }).collect(toList());
    }
}
