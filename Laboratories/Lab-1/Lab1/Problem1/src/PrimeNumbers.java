public class PrimeNumbers
{
    public boolean isPrime(int n)
    {
        if (n <= 1)
            return false;
        if (n == 2 || n == 3)
            return true;
        if (n % 2 == 0 || n % 3 == 0)
            return false;
        for (int i = 5; i <= Math.sqrt(n); i += 6)
        {
            if (n % i == 0 || n % (i + 2) == 0)
                return false;
        }

        return true;
    }

    public static void main(String[] args)
    {
        PrimeNumbers primeChecker = new PrimeNumbers();

        if (args.length == 0)
        {
            System.out.println("Please provide integers as command-line parameters.");
            return;
        }

        for (String arg : args)
        {
            try
            {
                int n = Integer.parseInt(arg);
                boolean isPrime = primeChecker.isPrime(n);
                if (isPrime)
                    System.out.print(n + " ");
            }
            catch (NumberFormatException e)
            {
                System.out.println(arg + " is not a valid integer.");
            }
        }
    }
}