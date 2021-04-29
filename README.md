# Electronic-Asset-Trading-Platform

### Database Info

- Install MariaDB from [this location](https://mariadb.org/download/).
- During install, take note of the port MariaDB listens to. Also make sure you remember the password you use for the root user.
- Hopefully the dependencies should be for Java to talk to MariaDB. If not, go to `File -> Project Structure` click `Libraries` click the plus icon and search for `org.mariadb.jdbc:mariadb` and choose the most recent stable build.
- Add the required details to the dbserver.props file (port, username and password)
  - The username and password for the root MariaDB user account that you put in props will be the first 'user' added to the database as well, so you can use those details to log in to the trading platform
- The database should be created as should all the tables when you run from IntelliJ
