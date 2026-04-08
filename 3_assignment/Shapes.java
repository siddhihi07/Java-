class Shapes {

    Shapes(double r) {
        double area = 3.14 * r * r;
        System.out.println("Circle Area = " + area);
    }

    Shapes(double l, double b) {
        double area = l * b;
        System.out.println("Rectangle Area = " + area);
    }

    double area(int b, int h) {
        return 0.5 * b * h;
    }

    double area(int s) {
        return s * s;
    }

    public static void main(String[] args) {

        Shapes c = new Shapes(5);      
        Shapes r = new Shapes(4, 6);   

        Shapes obj = new Shapes(1);   

        double triangle = obj.area(5, 10);
        System.out.println("Triangle Area = " + triangle);

        double square = obj.area(4);
        System.out.println("Square Area = " + square);
    }
}
