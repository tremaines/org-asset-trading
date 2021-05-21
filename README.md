# Electronic-Asset-Trading-Platform

### Database Info

- Install MariaDB from [this location](https://mariadb.org/download/).
- During install, take note of the port MariaDB listens to. Also make sure you remember the password you use for the root user.
- Hopefully the dependencies should be for Java to talk to MariaDB. If not, go to `File -> Project Structure` click `Libraries` click the plus icon and search for `org.mariadb.jdbc:mariadb` and choose the most recent stable build.
- Add the required details to the dbserver.props file (port, username and password)
- The database should be created as should all the tables when you run from IntelliJ

### To get started with the database

I found it easiest just to manually add a unit first and then add an admin user either through the terminal or the GUI that comes with MariaDB.
Add the first unit: 
```sql
INSERT INTO units(unit_name, credits) VALUES ("IT Administration", 1000);
```

The amount of credits doesn't really matter but "IT Administration" is important as that is the organisational unit I have coded as the one admins need to be a part of.

Add an admin account:
```sql
INSERT INTO users(user_name, admin_status, unit, password) VALUES ('username', true, 1, 'hashedpassword');
```

There are other fields in `users` like first name, email, etc. but they allow nulls. The database stores and compares the hashed password when logging in from our program so make sure you hash your password using the method in the `Users.java` class and copy-paste that value into the SQL database.

After that you can make more units, users, etc. through our program gui :)


### Running the Server and Client

In IntelliJ, you just have to run the ```main``` method in the ```RunServer``` class, then run the ```main``` method in ```AssetTradingGUI```to use the client side of the project. You can allow ```AssetTradingGUI``` to run multiple instances to simulate multiple clients being connected to the server at once.
