// TestingPrimes.cpp : This file contains the 'main' function. Program execution begins and ends there.
//

#include <iostream>

bool isPrime(int a) {
    if (a <= 1) { return false; }
    else if (a % 2 == 0) { return a == 2; }
    else {
        double limit = sqrt(a);
        for (int i = 3; i <= limit; i += 2) {
            if (a % i == 0) { return false; }
        }
        return true;
    }
}


unsigned long millis()
{
    return (clock() * 1000) / CLOCKS_PER_SEC;
}

int main()
{
    std::cout << "Hello World!\n";

    int begin_time = millis();
    int count = 0;
    for (int j = 0; j < 10000; j++) {
        count = 0;
        for (int i = 1; i < 10000; i++) {
            bool prime = isPrime(i);
            if (prime) count++;
        }
    }
    int end_time = millis();
    std::cout << (end_time - begin_time);
    std::cout << " ms\n";
    std::cout << count << " primes found\n";
}

// Run program: Ctrl + F5 or Debug > Start Without Debugging menu
// Debug program: F5 or Debug > Start Debugging menu

// Tips for Getting Started: 
//   1. Use the Solution Explorer window to add/manage files
//   2. Use the Team Explorer window to connect to source control
//   3. Use the Output window to see build output and other messages
//   4. Use the Error List window to view errors
//   5. Go to Project > Add New Item to create new code files, or Project > Add Existing Item to add existing code files to the project
//   6. In the future, to open this project again, go to File > Open > Project and select the .sln file
