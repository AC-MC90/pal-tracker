package io.pivotal.pal.tracker;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

public class JdbcTimeEntryRepository implements TimeEntryRepository {

    private JdbcTemplate jdbcTemplate;

    public JdbcTimeEntryRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {

        String createSql = "Insert into time_entries (project_id, user_id, date, hours) values (?,?,?,?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement ps = con.prepareStatement(createSql, new String[]{"project_id", "user_id", "date", "hours"});
                        ps.setLong(1, timeEntry.getProjectId());
                        ps.setLong(2, timeEntry.getUserId());
                        ps.setDate(3, Date.valueOf(timeEntry.getDate()));
                        ps.setLong(4, timeEntry.getHours());
                        return ps;
                    }
                }, keyHolder);

        timeEntry.setId(keyHolder.getKey().longValue());
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        String retrieveSql = "Select * from time_entries where id = ?";
        try {
            return jdbcTemplate.queryForObject(retrieveSql, new BeanPropertyRowMapper<TimeEntry>(TimeEntry.class), id);
        }catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<TimeEntry> list() {
        String retrieveSql = "Select * from time_entries";
        return jdbcTemplate.query(retrieveSql, new BeanPropertyRowMapper<TimeEntry>(TimeEntry.class));
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        String updateSql = "Update time_entries set project_id = ?, user_id = ?, date = ?, hours = ? where id = ?";

        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement ps = con.prepareStatement(updateSql, new String[]{"project_id", "user_id", "date", "hours", "id"});
                        ps.setLong(1, timeEntry.getProjectId());
                        ps.setLong(2, timeEntry.getUserId());
                        ps.setDate(3, Date.valueOf(timeEntry.getDate()));
                        ps.setLong(4, timeEntry.getHours());
                        ps.setLong(5, id);
                        return ps;
                    }
                });
        timeEntry.setId(id);
        return timeEntry;
    }

    @Override
    public void delete(long id) {
        String deleteSQL = "Delete from time_entries where id = ?";

        jdbcTemplate.update(
                new PreparedStatementCreator() {
                    @Override
                    public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                        PreparedStatement ps = con.prepareStatement(deleteSQL, new String[]{"id"});
                        ps.setLong(1, id);
                        return ps;
                    }
                });
    }
}
