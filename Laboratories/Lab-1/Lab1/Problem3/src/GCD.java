public class GCD
{
    public int gcd(int a, int b)
    {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }
    public int findGCD(int[] arr)
    {
        if (arr.length == 0)
            return 0; // Return 0 for an empty array (no valid GCD)

        int result = arr[0];

        for (int i = 1; i < arr.length; i++)
        {
            result = gcd(arr[i], result);

            if (result == 1)
                return 1;
        }

        return result;
    }

    public static void main(String[] args)
    {
        GCD gcdCalculator = new GCD();

        if (args.length == 0)
        {
            System.out.println("No input provided. Please provide integers as command-line parameters.");
            return;
        }

        int[] intArray = new int[args.length];

        try
        {
            for (int i = 0; i < args.length; i++)
            {
                intArray[i] = Integer.parseInt(args[i]);
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("One or more provided values are not valid integers.");
            return;
        }

        int result = gcdCalculator.findGCD(intArray);
        System.out.println("GCD of the provided integers: " + result);
    }
}