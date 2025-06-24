package dtu.group5.backend.model;

import java.util.HashSet;
import java.util.Set;

public abstract class BaseActivity implements IModel {
    private final String title;
    private boolean finished;
    private final Set<Coworker> assignedCoworkers = new HashSet<>();
    private String description;

    public BaseActivity(String title, boolean finished, String description, Set<Coworker> coworkersToAssign) {
        this.title = title;
        this.finished = finished;
        this.description = description;
        this.assignedCoworkers.addAll(coworkersToAssign);
    }

    public boolean isCoworkerAssigned(Coworker coworker) {
        for(Coworker c: assignedCoworkers) {
            if (c.getInitials().equals(coworker.getInitials())) {
                return true;
            }
        }
        return false;
    }

    public String getTitle() {
        return title;
    }

    public boolean isFinished() {
        return finished;
    }

    public Set<Coworker> getAssignedCoworkers() {
        return assignedCoworkers;
    }

    public String getDescription() {
        return description;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addAssignedCoworker(Coworker coworker) {
        assignedCoworkers.add(coworker);
    }

    public abstract Object getStartDate();

    public abstract Object getEndDate();
}
