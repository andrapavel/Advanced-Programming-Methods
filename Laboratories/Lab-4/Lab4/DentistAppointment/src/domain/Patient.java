package domain;

import java.io.Serializable;
import java.util.Objects;

public class Patient implements Identifiable<Integer> , Serializable
{
    Integer ID;
    private String name;
    private String dentalCondition;


    public Patient(Integer ID, String name, String dentalCondition)
    {
        //super(id); //calling the constructor from the base class
        this.ID = ID;
        this.name = name;
        this.dentalCondition = dentalCondition;
    }

    //GETTERS
    public String getName()
    {
        return this.name;
    }

    public String getDentalCondition(){return this.dentalCondition;}

    //SETTERS

    @Override
    public Integer getID()
    {
        return ID;
    }

    @Override
    public void setID(Integer ID)
    {
        this.ID = ID;
    }


    public void setName(String name1)
    {
        this.name = name1;
    }

    public void setDentalCondition(String dentalCondition)
    {
        this.dentalCondition = dentalCondition;
    }

    @Override
    public String toString()
    {
        return "Patient{" +
                "ID=" + ID +
                ", name='" + name + '\'' +
                ", dental condition='" + dentalCondition + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof Patient d))
            return false;
        return Objects.equals(d.ID, this.ID) && d.name.equals(this.name);
    }

}
