package Users;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;

/*
    This class lists all of the basic functionalities that a standard worker, manager and admin have
 */
@Data
abstract class User
{
   abstract void sendMessage();
   abstract void receiveMessage(String mes);
   abstract void viewMessages();
}
