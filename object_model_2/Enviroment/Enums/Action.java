package Enviroment.Enums;

public enum Action{
    HITTING("is hitting"),
    DISPERSING("is dispersing in"),
    FLOATING("is floating in the "),
    FLYING("is flyin on the "),
    OBSERVE(" is observing a "),
    DECIDESPIT(" is deciding  Sleeping"),
    SIT(" is sitting for a long time"),
    NOTICE(" is noticing something"),
    CONCIDERE(" is considering something"),
    REALIZE(" is realizing something"),
    CONCLUDE(" is concluding");
    private final String description;

    Action(String description) {
        this.description = description;
    
    }

    @Override
    public String toString() {
        return this.description;
    }
}