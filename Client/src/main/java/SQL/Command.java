package SQL;

@FunctionalInterface
public interface Command {
    public boolean execute();
}
