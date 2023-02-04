
function isPrime(a) {
    if (a <= 1) { return false; }
    else if (a % 2 == 0) { return a == 2; }
    else {
        let limit = Math.sqrt(a);
        for (i = 3; i <= limit; i += 2) {
            if (a % i == 0) { return false; }
        }
        return true;
    }
}

function test() {
    console.log("Hello world!");
    let begin_time = Date.now();
    let count = 0;
    for (let j = 0; j < 10000; j++) {
        count = 0;
        for (let i = 1; i < 10000; i++) {
            let prime = isPrime(i);
            if (prime) count++;
        }
    }
    let end_time = Date.now();
    console.log((end_time - begin_time) + " ms");
    console.log(count + " primes found");
}

test();