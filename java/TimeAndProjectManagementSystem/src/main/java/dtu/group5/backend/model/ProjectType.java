package dtu.group5.backend.model;

public enum ProjectType {

    CLIENT, INTERN;

    public static ProjectType fromString(String type) {
        if (type == null) return null;
        return switch (type.toUpperCase()) {
            case "CLIENT" -> CLIENT;
            case "INTERN" -> INTERN;
            default -> null;
        };
    }

    public String getFormattedName() {
        return switch (this) {
            case CLIENT -> "Client";
            case INTERN -> "Intern";
        };
    }
}
