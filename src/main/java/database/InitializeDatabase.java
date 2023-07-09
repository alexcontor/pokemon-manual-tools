package database;

import org.testng.annotations.Test;

public class InitializeDatabase {

    @Test
    public void initialize() {
        Database.createNewDatabase();
    }

}
