using System.Diagnostics;

Console.WriteLine("Hello, World!");

static bool isPrime(int a)
{
    if (a <= 1) { return false; }
    else if (a % 2 == 0) { return a == 2; }
    else
    {
        var limit = Math.Sqrt(a);
        for (int i = 3; i <= limit; i += 2)
        {
            if (a % i == 0) { return false; }
        }
        return true;
    }
}

var sw = new Stopwatch();
sw.Start();

int count = 0;
for (int j = 0; j < 10000; j++)
{
    count = 0;
    for (int i = 1; i < 10000; i++)
    {
        bool prime = isPrime(i);
        if (prime) count++;
    }
}
sw.Stop();
Console.WriteLine($"{sw.ElapsedMilliseconds} ms");
Console.WriteLine($"{count} primes found");
