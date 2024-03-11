public class MaxDouble
{
    public double getMax(double[] arr)
    {
        if (arr == null || arr.length == 0)
        {
            throw new IllegalArgumentException("Array is empty or null.");
        }

        double max = arr[0]; // Initialize max to the first element of the array

        for (int i = 1; i < arr.length; i++)
        {
            if (arr[i] > max)
            {
                max = arr[i];
            }
        }

        return max;

    }

    public static void main(String[] args)
    {
        MaxDouble maxDoubleFinder = new MaxDouble();

        if (args.length == 0)
        {
            System.out.println("No input provided. Please provide double values as command-line parameters.");
            return;
        }

        double[] doubleArray = new double[args.length];

        try
        {
            for (int i = 0; i < args.length; i++)
            {
                doubleArray[i] = Double.parseDouble(args[i]);
            }
        }
        catch (NumberFormatException e)
        {
            System.out.println("One or more provided values are not valid doubles.");
            return;
        }

        double max = maxDoubleFinder.getMax(doubleArray);
        System.out.println("Maximum value: " + max);
    }
}