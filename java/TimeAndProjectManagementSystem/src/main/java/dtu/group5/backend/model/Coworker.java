package dtu.group5.backend.model;

// Made by Jacob (s246077)
public class Coworker implements IModel {
  private final String initials;
  private String name;
  private final int maxActivities = 20;

  // Made by Jacob (s246077)
  public Coworker(String initials, String name) {
    this.initials = initials;
    this.name = name;
  }

  // Made by Jacob (s246077)
  public String getInitials() { return initials; }

  // Made by Jacob (s246077)
  public String getName() { return name; }

  // Made by Jacob (s246077)
  public int getMaxActivities() { return maxActivities; }

  // Made by Jacob (s246077)
  public void setName(String name) {
    this.name = name;
  }

  // Made by Mattias (s245759)
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Coworker other = (Coworker) obj;
    return this.initials.equals(other.initials);
  }

  // Made by Mattias (s245759)
  @Override
  public int hashCode() {
    return initials.hashCode();
  }

}
