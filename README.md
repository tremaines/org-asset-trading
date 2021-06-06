# Electronic-Asset-Trading-Platform

### Database Info

- Install MariaDB from [this location](https://mariadb.org/download/).
- During install, take note of the port MariaDB listens to. Also make sure you remember the password you use for the root user.
- Hopefully the dependencies should already be present for Java to talk to MariaDB. If not, go to `File -> Project Structure` click `Libraries` click the plus icon and search for `org.mariadb.jdbc:mariadb` and choose the most recent stable build.
- Add the required details to the dbserver.props file (port, username and password)

### Running the Server and Client

1. Ensure a MariaDB server is running and you have the correct port and login details recorded in the dbserver.props file.
2. Run the ```main``` method in the ```RunServer``` class. This should auto-create the schema and tables in the database, as well as a default unit (IT Admin) and default user (username: ```admin``` password: ```password```).
3. Run the ```main``` method in ```AssetTradingGUI```to use the client side of the project.  You can allow ```AssetTradingGUI``` to run multiple instances to simulate multiple clients being connected to the server at once.
