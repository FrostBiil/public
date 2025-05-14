package dtu.group5.backend.model;

import java.util.Date;
import java.util.Set;

// Made by Elias (241121)
public class FixedActivity extends BaseActivity {
    private Date startDate;
    private Date endDate;

    // Made by Elias (241121)
    public FixedActivity(String title,  boolean finished, String description, Date startDate, Date endDate, Coworker coworker) {
        super(title,  finished, description, Set.of(coworker));
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Made by Elias (241121)
    // Related to the database and is not tested
    public FixedActivity(String title, boolean finished, String description, Set<Coworker> coworkersToAssign, Date startDate, Date endDate) {
        super(title, finished, description, coworkersToAssign);
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Made by Elias (241121)
    public Date getStartDate() {
        return startDate;
    }

    // Made by Elias (241121)
    public Date getEndDate() {
        return endDate;
    }

    // Made by Elias (241121)
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    // Made by Elias (241121)
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
