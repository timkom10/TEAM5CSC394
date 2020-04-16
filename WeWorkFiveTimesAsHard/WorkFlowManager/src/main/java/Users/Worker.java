package Users;

import java.util.ArrayList;

/*
    This class implements the functionalities of a worker.
 */
public class Worker extends User
{

    private ArrayList<String> Messages = null;

    /*Send a message to a manager a user or an admin */
    void sendMessage()
    {
        /*Add support for typing*/
    }

    /*Receive a message from someone */
    void  receiveMessage(String mes)
    {
        if(Messages == null){Messages = new ArrayList<String>();}

        Messages.add(mes);
    }

    /* View your message inbox */
    void viewMessages()
    {
        /*Add nice display button?*/
    }


}
