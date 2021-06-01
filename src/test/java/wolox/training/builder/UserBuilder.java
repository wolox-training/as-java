package wolox.training.builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import wolox.training.models.User;

public class UserBuilder {

    public static List<User> listUsers(){
        List<User> users = new ArrayList<>();
        users.add(user());
        return users;
    }

    public static User user(){
        User user = new User();
        user.setUsername("andreysanp");
        user.setName("andrey");
        user.setId(1L);
        user.setBirthdate(LocalDate.of(1996,02,16));
        return user;
    }

}
