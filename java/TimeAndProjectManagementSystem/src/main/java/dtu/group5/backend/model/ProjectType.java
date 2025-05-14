package dtu.group5.backend.model;

// Made by Elias (241121)
public enum ProjectType {

    CLIENT, INTERN;

    // Made by Elias (241121)
    public static ProjectType fromString(String type) {
        if (type == null) return null;
        return switch (type.toUpperCase()) {
            case "CLIENT" -> CLIENT;
            case "INTERN" -> INTERN;
            default -> null;
        };
    }

    // Made by Elias (241121)
    public String getFormattedName() {
        return switch (this) {
            case CLIENT -> "Client";
            case INTERN -> "Intern";
        };
    }
}
