package Model.User;

import Model.Workers.Admin;
import Model.Workers.Manager;
import Model.Workers.StandardWorker;
import Model.Workers.WorkerTypes;

import javax.persistence.*;

/*
    Before someone can login, or do some actions, they need to "Become-A" worker type,
    That is, we do not immediately expect someone "To-Be-A" admin or manager type, so
    This class is really the handle for the basic functionalities of any User of the system,
    In addition to the functionalities that would be added on if they acquire a manager, admin,
    Or standard worker type.

 */
@Entity
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int UID;

    @OneToOne
    private WorkerTypes userWorkerType;                 //This user becomes bound to this worker, because they are it


    /*Make this User become a standard worker as a default*/
    public Users()
    {
        this.userWorkerType = new StandardWorker(this);
    }

    /*Set or reset strategy*/
    public void setStrategy(WorkerTypes type)
    {
            if(type instanceof Manager)
            {
                this.userWorkerType = new Manager(this);
            }
            else if(type instanceof Admin)
            {
                this.userWorkerType = new Admin(this);
            }
            else if(type instanceof  StandardWorker)
            {
                this.userWorkerType = new StandardWorker(this);
            }
    }

}
