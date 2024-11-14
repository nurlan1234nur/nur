document.writeln("<h2>Bodlogo 5</h2>");
var number = parseInt(window.prompt("Yzguur oloh too: "));
var currentHour = new Date().getHours();
var result;
if (currentHour >= 6 && currentHour < 18) {
  result = number * number;
  document.writeln("Ugluu: " + number + " toonii 2 zereg ni: " + result);
} else {
  result = Math.sqrt(number);
  document.writeln("Oroi: " + number + " toonii yazguur ni: " + result);
}