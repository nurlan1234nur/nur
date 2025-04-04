document.writeln("<h2>Bodlogo 3</h2>");
toot = parseInt(window.prompt("Baishinii tootiig oruul: "));
let davhar = 9;
let orts = 3;
let ail = 4;
toot -= 1;
if(toot<=108){
var N = toot / (davhar * ail) + 1;
document.writeln(parseInt(N) + "-r orts");
var M = (toot % (davhar * ail)) / ail + 1;
document.writeln(parseInt(M) + "-r davhar");
var L = (toot % ail) + 1;
document.writeln(parseInt(L) + "-r haalga");
}
else
{document.writeln("toot oldsongui")}