package org.mattrr78.passwordgenenv;

public class Footnote {

    private final String version;

    private final String ipAddress;

    public Footnote(String version, String ipAddress) {
        this.version = version;
        this.ipAddress = ipAddress;
    }

    public String getVersion() {
        return version;
    }

    public String getIpAddress() {
        return ipAddress;
    }

}
