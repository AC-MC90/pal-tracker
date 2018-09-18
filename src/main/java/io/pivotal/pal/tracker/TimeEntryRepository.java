package io.pivotal.pal.tracker;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TimeEntryRepository {
    public abstract TimeEntry create(TimeEntry any);

    public abstract TimeEntry find(long l) ;

    public abstract List<TimeEntry> list() ;

    public abstract TimeEntry update(long eq, TimeEntry any) ;

    public abstract void delete(long l) ;

}
