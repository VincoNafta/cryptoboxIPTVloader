package sk.vinconafta.cryptoboxIPTV;

public class IPTVstream {
    private String channelName;
    private String stream;

    public IPTVstream(String channelName, String stream) {
        this.channelName = channelName;
        this.stream = stream;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getChannelName() {
        return this.channelName;
    }

    public String getStream() {
        return this.stream;
    }

    public String getToCFG() {
        return "I: " + this.stream + " " + this.channelName;
    }
}
