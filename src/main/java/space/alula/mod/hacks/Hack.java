package space.alula.mod.hacks;

public abstract class Hack {
    private final String name;

    public Hack(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract void onEnable();

    public abstract void onDisable();
}
