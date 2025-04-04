   var n;
window.prompt(<p>n:</p>);
n = parseInt;
    var sum = 0;  // Initialize sum to 0
    while (n !== 0) {
        sum += n % 10;  // Add the last digit to sum
        n = Math.floor(n / 10);  // Remove the last digit
    }
    document.writeln("result:"+sum);