public class Main {
    public static void main(String[] args) {
        Database db1 = Database.getInstance();
        Database db2 = Database.getInstance();
        System.out.println("Database Singleton test: " + (db1 == db2));

        Admin myAdmin = new Admin();
        myAdmin.login = "rustem_boss";
        myAdmin.password = "123456";

        boolean isLogged = myAdmin.authenticate("rustem_boss", "123456");
        System.out.println("Login test: " + isLogged);

        if (isLogged) {
            Student s1 = new Student();
            s1.login = "fariza_student";
            myAdmin.addUser(s1);
            myAdmin.viewLogs();
        }
    }
}