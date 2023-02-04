import math
import time

def millis():
    return time.time_ns() / 1000 / 1000

def isPrime(a):
    if a <= 1:
        return False
    elif a % 2 == 0:
        return a == 2
    else:
        i = 3
        limit = math.sqrt(a)
        while i <= limit:
            if a % i == 0:
                return False
            i += 2
        return True
        
print("Hello world!\n")
begin_time = millis()
count = 0
for j in range(0, 10000):
    count = 0
    for i in range(1, 10000):
        prime = isPrime(i)
        if prime:
            count = count + 1
end_time = millis()
print(end_time - begin_time, "ms")
print(count, "primes found")
