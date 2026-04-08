class Hillstations {

    void famousfood() {
        System.out.println("Famous food of hillstation");
    }

    void famousfor() {
        System.out.println("Famous for beauty");
    }
}

class Manali extends Hillstations {

    void famousfood() {
        System.out.println("Siddu");
    }

    void famousfor() {
        System.out.println("Snow");
    }
}

class Mussoorie extends Hillstations {

    void famousfood() {
        System.out.println("Momos");
    }

    void famousfor() {
        System.out.println("Hills");
    }
}

class Gulmarg extends Hillstations {

    void famousfood() {
        System.out.println("Kashmiri food");
    }

    void famousfor() {
        System.out.println("Skiing");
    }
}

class Main {
    public static void main(String[] args) {

        Hillstations h;

        h = new Manali();
        h.famousfood();
        h.famousfor();

        h = new Mussoorie();
        h.famousfood();
        h.famousfor();

        h = new Gulmarg();
        h.famousfood();
        h.famousfor();
    }
}