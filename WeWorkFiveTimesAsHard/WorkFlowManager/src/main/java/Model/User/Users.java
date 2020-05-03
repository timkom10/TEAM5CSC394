package Model.User;

import Model.Workers.WorkerTypes;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

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
    private int UID;

    @OneToOne
    private WorkerTypes userWorkerType;                 //This user becomes bound to this worker, because they are it



}
