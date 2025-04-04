function fact() {
   var a = parseInt(window.prompt("a-г оруул"), 10); 

   function fact(a) {
       if (a === 0) {
           return 1;
       } else {
      return a * fact(a - 1);
       }
   }
document.writeln(fact(a));
}
