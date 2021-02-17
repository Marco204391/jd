package s14.dao;

public class Region {
    private long id;
    private String regionName;


    public Region(long id, String regionName) {
        this.id=id;
        this.regionName=regionName;
    }

    public Region() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    @Override
    public String toString() {
        return "Region{" +
                "id=" + id +
                ", regionName='" + regionName + '\'' +
                '}';
    }
}
