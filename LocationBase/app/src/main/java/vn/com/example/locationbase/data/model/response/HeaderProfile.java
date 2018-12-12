package vn.com.example.locationbase.data.model.response;


public class HeaderProfile {
    private String fullName;
    private String avatarUrl;
    private String coverUrl;
    private String phone;

    public HeaderProfile() {
    }

    public HeaderProfile(String fullName, String avatarUrl, String coverUrl, String phone) {
        this.fullName = fullName;
        this.avatarUrl = avatarUrl;
        this.coverUrl = coverUrl;
        this.phone = phone;
    }

    public String getFullName() {

        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
