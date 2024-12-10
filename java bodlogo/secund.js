var x = 30 ;

function nur(){document.writeln("2. Secundiin bodlogo<br>");

function a(x){
    if(x>=0){
    setTimeout(function nur(){
        document.writeln(x--);
        a(x);
    },1000);
}
}
a(x);
};
function resetS(){
    x=0;
}
