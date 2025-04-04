document.writeln("<h2>Bodlogo 2</h2>");
      let p = parseInt(window.prompt("zamiig oruul: "));
      let wolf = 25;
      let rabbit = 18;
      let V = wolf - rabbit;
      let tHours = p / V;
      let hours = Math.floor(tHours);
      let minutes = Math.floor((tHours - hours) * 60);
      let seconds = Math.floor(((tHours - hours) * 60 - minutes) * 60);
      document.writeln(hours+" tsag "+ minutes + " min " + seconds + " sec-d guitsene"
      );