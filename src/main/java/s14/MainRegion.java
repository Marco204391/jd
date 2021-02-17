package s14;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import s14.dao.Region;
import s14.dao.RegionDao;

public class MainRegion {
    private static Logger log = LoggerFactory.getLogger(MainRegion.class);

    public static void main(String[] args) {
        RegionDao df = new RegionDao();

        Region r = new Region();
        df.save(r);

    }
}
