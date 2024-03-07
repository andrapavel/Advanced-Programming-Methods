public class Main
{
    public static void main(String[] args)
    {
        // printing
        System.out.println("Hello World!");
        // ---------------------------------------------------------------------------------

        // variables and constants
        int x = 10;
        final int MAX = 32000;
        final int MIN;
        MIN = -32000;
        // MIN = 200; // ERROR

        // ---------------------------------------------------------------------------------
        // Arrays
        float[] vec = new float[10];
        System.out.println("Vector capacity: " + vec.length);
        float aux[];
        // System.out.println(aux.length); // ERROR, aux has not been initialised
        aux = vec;
        System.out.println("Auxiliary vector capacity: " + aux.length); // vec and aux now refer the same array
        // vec.length = 5; // ERROR - we cannot assign a new dimension

        int another_vec[] = {1, 2, 3, 4};
        System.out.println("Element on position 2: " + another_vec[2]);
        // System.out.println("Element on position 10: " + another_vec[10]); // ERROR - index out of bounds exception

        // Bidimensional vector
        int[][] bidimensional_vect = new int[5][4];
        bidimensional_vect[0][3] = 20;
        bidimensional_vect[2][2] = 40;
        for (int i = 0; i < 5; i++)
        {
            for (int j = 0; j < 4; j++)
                System.out.print(bidimensional_vect[i][j] + " ");
            System.out.println();
        }

        // non-rectangular bidimensional vector
        int[][] a = new int[5][];
        for(int i = 0; i < 3; i++)
            a[i] = new int[i + 1];
        System.out.println(a.length);
        System.out.println(a[0].length);
        System.out.println(a[1].length);
        System.out.println(a[2].length);

        // ---------------------------------------------------------------------------------
        // Strings
        String str1 = new String("Hello"); // new String object
        String str2 = new String("Hello"); // new String object
        System.out.println(str1 == str2);
        System.out.println(str1.equals(str2));

        String str3 = "Hello"; // String literal
        String str4 = "Hello"; // String literal
        System.out.println(str3 == str4); // gets an existing object from an existing String Pool (cache)
        // String Pool in Java is a special storage space in Java Heap memory where string literals are stored.
        System.out.println(str3.equals(str4));

        // ---------------------------------------------------------------------------------

        // working with arguments
        if (args.length < 2)
            return;
        String first_arg = args[0];
        String second_arg = args[1];
        int first = Integer.parseInt(first_arg);
        for (int i = 0; i < first; i++)
            System.out.println(second_arg);
    }
}
