package dtu.group5.backend.model;

public class Coworker implements IModel {
  private final String initials;
  private String name;
  private final int maxActivities = 20;

  public Coworker(String initials, String name) {
    this.initials = initials;
    this.name = name;
  }

  public String getInitials() { return initials; }

  public String getName() { return name; }

  public int getMaxActivities() { return maxActivities; }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Coworker other = (Coworker) obj;
    return this.initials.equals(other.initials);
  }

  @Override
  public int hashCode() {
    return initials.hashCode();
  }

}
