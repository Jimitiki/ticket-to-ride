package delta.monstarz;

public abstract class BaseCommand {
    private String name;
    private String username;
    private String gameID;

    public abstract void execute();
}
