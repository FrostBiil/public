package dtu.group5.backend.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class Project implements IModel {
  private final int projectNumber;
  private String title;
  private String description;
  private Date startDate;
  private Date endDate;
  private Coworker projectLeader;
  private ProjectStatus status = ProjectStatus.NOT_STARTED;
  private ProjectType type = ProjectType.INTERN;

  private final Set<Coworker> coworkers = new HashSet<>();

  public Project(int projectNumber, String title) {
    this.projectNumber = projectNumber;
    this.title = title;
  }

  // Constructor for deserialization
  public Project(int projectNumber, String title, String description, Date startDate, Date endDate, Coworker projectLeader, Set<Coworker> coworkers, ProjectStatus status, ProjectType type) {
    this.projectNumber = projectNumber;
    this.title = title;
    this.description = description;
    this.startDate = startDate;
    this.endDate = endDate;
    this.projectLeader = projectLeader;
    this.coworkers.addAll(coworkers);
    this.status = status;
    this.type = type;
  }

  public int getProjectNumber() { return projectNumber; }
  public String getTitle() { return title; }
  public String getDescription() { return description; }
  public Date getStartDate() { return startDate; }
  public Date getEndDate() { return endDate; }
  public Coworker getProjectLeader() { return projectLeader; }

  public ProjectStatus getStatus() {
    return status;
  }

  public Set<Coworker> getCoworkers() { return coworkers; }

  public ProjectType getType() {
    return type;
  }

  public void setTitle(String title) { this.title = title; }
  public void setDescription(String description) { this.description = description; }
  public void setStartDate(Date startDate) { this.startDate = startDate; }
  public void setEndDate(Date endDate) { this.endDate = endDate; }
  public void setProjectLeader(Coworker projectLeader) {
    this.projectLeader = projectLeader;
  }

  public void setStatus(ProjectStatus status) {
    this.status = status;
  }

  public void setType(ProjectType type) {
    this.type = type;
  }

  public void addCoworker(Coworker coworker) {
    coworkers.add(coworker);
  }

  public void removeCoworker(Coworker coworker) {
    coworkers.removeIf(c -> c.getInitials().equals(coworker.getInitials()));
  }

  public enum ProjectStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED;

    public static ProjectStatus fromString(String status) {
        if (status == null)
            return null;
      for (ProjectStatus s : ProjectStatus.values()) {
        if (s.name().equalsIgnoreCase(status)) {
          return s;
        }
      }
      return null;
    }

    public String getFormattedName() {
      return this.name().replace("_", " ").toLowerCase();
    }
  }
}
