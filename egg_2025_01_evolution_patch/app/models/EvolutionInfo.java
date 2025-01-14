package models;

public class EvolutionInfo {
    private int revision;
    private String sqlUp;
    private String sqlDown;
    private String hash;

    public EvolutionInfo(int revision, String sqlUp, String sqlDown, String hash) {
        this.revision = revision;
        this.sqlUp = sqlUp;
        this.sqlDown = sqlDown;
        this.hash = hash;
    }

    protected String getAbbreviated(String s, int length) {
        if (s == null || s.isEmpty()) {
            return "";
        }

        if (s.length() <= length) {
            return s;
        }

        return s.substring(0, length) + " ...";
    }

    public String getAbbreviatedSqlUp(int length) {
        return getAbbreviated(sqlUp, length);
    }

    public String getAbbreviatedSqlDown(int length) {
        return getAbbreviated(sqlDown, length);
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public String getSqlUp() {
        return sqlUp;
    }

    public void setSqlUp(String sqlUp) {
        this.sqlUp = sqlUp;
    }
    public String getSqlDown() {
        return sqlDown;
    }

    public void setSqlDown(String sqlDown) {
        this.sqlDown = sqlDown;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}

