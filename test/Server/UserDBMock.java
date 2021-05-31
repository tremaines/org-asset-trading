package Server;

import Client.User;

import java.util.ArrayList;

public class UserDBMock implements UserDB{

    private final ArrayList<User> users;

    public UserDBMock(ArrayList<User> users) {
        this.users = users;
    }

    @Override
    public boolean checkUsername(String username) {
        return false;
    }

    @Override
    public User getUser(String username) {
        for(User user : users) {
            if(user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public void update(User user) {
        for(int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())){
                users.set(i, user);
                return;
            }
        }
    }
}
