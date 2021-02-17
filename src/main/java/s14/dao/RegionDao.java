package s14.dao;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class RegionDao implements Dao<Region> {
    private static final Logger LOG = LoggerFactory.getLogger(RegionDao.class);

    private static final String GET_BY_PK = "SELECT region_id, region_name FROM regions WHERE region_id = ?";
    private static final String GET_ALL = "SELECT region_id, region_name FROM regions";
    private static final String INSERT = "INSERT INTO regions(region_id, region_name) VALUES (?, ?)";
    private static final String UPDATE = "UPDATE regions SET region_id= ?, region_name = ? WHERE region_id = ?";
    private static final String DELETE = "DELETE FROM regions WHERE region_id = ?";


    @Override
    public List<Region> getAll() {

        List<Region> results = new ArrayList<Region>();

        try (Connection conn = Connector.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(GET_ALL)) {
            while (rs.next()) {
                Region region = new Region(rs.getLong(1), rs.getString(2));
                results.add(region);
            }
        } catch (SQLException se) {
            LOG.error("Can't get all coders", se);
        }
        return results;
    }

    @Override
    public Optional<Region> get(long id) {
        try (Connection conn = Connector.getConnection(); //
             PreparedStatement ps = conn.prepareStatement(GET_BY_PK)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Region my = new Region(rs.getLong(1), rs.getString(2));
                    return Optional.of(my);
                }
            }
        } catch (SQLException se) {
            LOG.error("Can't get region " + id, se);
        }
        return Optional.empty();
    }

    public Region legacyGet(long id) {
        try (Connection conn = Connector.getConnection(); //
             PreparedStatement ps = conn.prepareStatement(GET_BY_PK)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Region(rs.getLong(1), rs.getString(2));
                }
            }
        } catch (SQLException se) {
            LOG.error("Can't get region " + id, se);
        }

        return null;
    }

    @Override
    public void save(Region region) {
        try (Connection conn = Connector.getConnection(); //
             PreparedStatement ps = conn.prepareStatement(INSERT)) {
            ps.setLong(1, region.getId());
            ps.setString(2, region.getRegionName());
            ps.executeUpdate();
        } catch (SQLException se) {
            LOG.error("Can't save region " + region.getId(), se);
        }
    }

    @Override
    public void update(Region region) {
        try (Connection conn = Connector.getConnection(); //
             PreparedStatement ps = conn.prepareStatement(UPDATE)) {
            int count = ps.executeUpdate();
            if (count != 1) {
                LOG.warn("Updated " + count + " lines for " + region);
            }
        } catch (SQLException se) {
            LOG.error("Can't update region " + region.getId(), se);
        }
    }

    @Override
    public void delete(long id) {
        try (Connection conn = Connector.getConnection(); //
             PreparedStatement ps = conn.prepareStatement(DELETE)) {
            ps.setLong(1, id);
            int count = ps.executeUpdate();
            if (count != 1) {
                LOG.warn("Deleted " + count + " lines for " + id);
            }
        } catch (SQLException se) {
            LOG.error("Can't delete region " + id, se);
        }
        
    }
}
