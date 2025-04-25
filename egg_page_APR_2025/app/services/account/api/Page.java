package services.account.api;

import java.util.*;

public class Page<T> {
    private List<T> records;
    private boolean isPoisonPill;

    public Page(List<T> records) {
        this.records = records;
        this.isPoisonPill = records.isEmpty();
    }

    public List<T> getRecords() {
        return records;
    }

    public boolean isPoisonPill() {
        return isPoisonPill;
    }
}
