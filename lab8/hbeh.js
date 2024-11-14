document.writeln("<h2>Bodlogo 4</h2>");
let arr = [];
for (var i = 0; i < 5; i++) {
  arr[i] = parseInt(window.prompt((i+1) + "-r toog oruul: "));
}
console.log(arr);

function XIEX(a, b) {
  while (b != 0) {
    const temp = b;
    b = a % b;
    a = temp;
  }
  return a;
}

function XBEX(a, b) {
  return (a * b) / XIEX(a, b);
}

function arrayXBEX(arr) {
  let acc = arr[0];

  for (let i = 1; i < arr.length; i++) {
    acc = XBEX(acc, arr[i]);
  }

  return acc;
}
const r = arrayXBEX(arr);

document.writeln("Xamgiin baga yeronhii hubaagdagch: " + r);