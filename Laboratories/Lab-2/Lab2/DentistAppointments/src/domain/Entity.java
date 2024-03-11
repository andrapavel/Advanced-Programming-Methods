package domain;

public abstract class Entity implements Identifiable<Integer>
{
    private final Integer ID;

    public Entity(int ID)
    {
        this.ID = ID;
    }

    @Override
    public Integer getID()
    {
        return ID;
    }
}
