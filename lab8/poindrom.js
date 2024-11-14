document.writeln("<h2>Bodlogo 1</h2>");
let a = parseInt(window.prompt("shalgah too:(polindrome) "));
let sum = 0;
let n = a;
while (n > 0) {
  sum += n % 10;
  n = Math.floor(n / 10);
}
let reversed = 0;
let temp = sum;
while (temp > 0) {
  let k = temp % 10;
  reversed = 10 * reversed + k;
  temp = Math.floor(temp / 10);
}

if (sum === reversed) {
  document.writeln("Palindrome mun\n");
} else {
  document.writeln("Palindrome bish\n");
}