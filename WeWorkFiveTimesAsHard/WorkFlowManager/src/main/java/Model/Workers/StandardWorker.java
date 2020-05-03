package Model.Workers;

import javax.persistence.Entity;

/*
    Standard worker, not an admin, not a manager, a grunt assigned to a manager, and a project
 */
@Entity
public class StandardWorker extends WorkerTypes {


    @Override
    public void sendMessage() {
            
    }

    @Override
    public void receiveMessage() {

    }

    @Override
    public void viewProjects() {

    }
}
