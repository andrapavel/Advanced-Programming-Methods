class NoVarargs
{
    public int sum(int a, int b)
    {
        return a + b;
    }

    public int sum(int a, int b, int c)
    {
        return a + b + c;
    }
}

public class Varargs {
    public int sum(int ... args)
    {
        int s = 0;
        for (int x: args)
            s += x;
        return s;
    }

    public int sum(boolean allArgs, int ... args)
    /**
     * Input: allArgs - if true, all arguments will be considered.
     *                - else, all arguments, except the first one.
     */
    {
        int s = 0;
        if (allArgs) {
            for (int x : args)
                s += x;
            return s;
        }
        else
        {
            for (int i = 1; i < args.length; i++)
                s += args[i];
        }
        return s;
    }

    public static void main(String[] args) {
        NoVarargs noVarArgsObject = new NoVarargs();
        System.out.println(noVarArgsObject.sum(1, 2));
        System.out.println(noVarArgsObject.sum(1, 2, 3));
        System.out.println();

        Varargs varArgsObject = new Varargs();
        System.out.println(varArgsObject.sum());
        System.out.println(varArgsObject.sum(1));
        System.out.println(varArgsObject.sum(1, 2));
        System.out.println(varArgsObject.sum(1, 2, 3));
        System.out.println(varArgsObject.sum(1, 2, 3, 4));
        System.out.println();

        System.out.println(varArgsObject.sum(true, 1, 2, 3, 4));
        System.out.println(varArgsObject.sum(false, 1, 2, 3, 4));
    }
}
