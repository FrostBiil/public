package dtu.group5.backend.model;

import java.util.Date;
import java.util.Set;

public class FixedActivity extends BaseActivity {
    private Date startDate;
    private Date endDate;

    public FixedActivity(String title,  boolean finished, String description, Date startDate, Date endDate, Coworker coworker) {
        super(title,  finished, description, Set.of(coworker));
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Related to the database and is not tested
    public FixedActivity(String title, boolean finished, String description, Set<Coworker> coworkersToAssign, Date startDate, Date endDate) {
        super(title, finished, description, coworkersToAssign);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
