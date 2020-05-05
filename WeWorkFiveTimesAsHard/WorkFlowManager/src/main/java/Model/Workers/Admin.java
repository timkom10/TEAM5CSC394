package Model.Workers;

import Model.User.Users;

import javax.persistence.Entity;

@Entity
public class Admin extends WorkerTypes {


    public Admin()
    {
        this.user = null;
    }

    public Admin(Users user)
    {
        this.user = user;
    }

    @Override
    public void sendMessage(Users user) {

    }

    @Override
    public void receiveMessage(Users user) {

    }

    @Override
    public void viewProjects(Users user) {

    }

    @Override
    public void viewTasks() {

    }
}
