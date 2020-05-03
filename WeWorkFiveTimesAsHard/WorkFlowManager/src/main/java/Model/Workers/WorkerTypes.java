package Model.Workers;

/*
    Outlines the basic functionalities we would expect from a worker, regardless of type (admin, manager, standard)
 */

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;

@Entity
@Inheritance(strategy =  InheritanceType.TABLE_PER_CLASS)
public abstract class WorkerTypes
{

    @Id
    protected int WorkerID;                                    //Becomes the primary key of admin, manager, standard


    public abstract void sendMessage();
    public abstract void receiveMessage();
    public abstract void viewProjects();

}
