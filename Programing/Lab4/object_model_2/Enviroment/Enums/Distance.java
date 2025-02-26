package Enviroment.Enums;

public enum Distance {
    SOFARAWAY("very far away"),
    FARAWAY("far away"),
    MEDIUM("at a medium distance"),
    CLOSE("close"),
    VERYCLOSE("very close"),
    CONTACT("in direct contact");

    private final String description;

    // Constructor del enum
    Distance(String description) {
        this.description = description;
    }

    // Método para obtener la descripción
    public String getDescription() {
        return this.description;
    }

    @Override
    public String toString() {
        return this.description;
    }
}
