package Users;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;

/*
    This class implements the functionalities of a worker.
 */
public class Worker extends User
{
    /*Stack alignment, reduce padding by big types being on top. Access speed is also top priority */
    private ArrayList<String> Messages = null;
    private String Username = null;
    private String Password = null;

    @Id @GeneratedValue
    private Long  id;

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
