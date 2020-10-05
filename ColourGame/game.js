function getRandomIntInclusive(min, max) {
    min = Math.ceil(min);
    max = Math.floor(max);
    return Math.floor(Math.random() * (max - min + 1)) + min; //The maximum is inclusive and the minimum is inclusive 
  }
  var score=document.querySelector("#score");
  var score1=document.querySelector("#score").textContent;
  score1=0;
  score.textContent=0;
  var v1=document.querySelector("#v1");
  var v2=document.querySelector("#v2");
  var v3=document.querySelector("#v3");
  var c1=document.querySelector("#c1");
  var c2=document.querySelector("#c2");
  var c3=document.querySelector("#c3");
  var c4=document.querySelector("#c4");
  var c5=document.querySelector("#c5");
  var c6=document.querySelector("#c6");
  var easy=document.querySelector("#easy");
  var hard=document.querySelector("#hard");
  var med=document.querySelector("#med");
  var level=0;
  easy.addEventListener("click",function(){
      level=0;
      newcolore();
  })
  med.addEventListener("click",function(){
      level=1;
      newcolorm();
  })
  hard.addEventListener("click",function(){
      level=2;
      newcolorh();
  })
  
  function scoreplus(){
  if(level==0)
      score1+=100;
      else if(level==1)
          score1+=150;
      else if(level==2)
          score1+=200;
      score.textContent=score1;
  }
  function scoreminus(){
  if(level==0)
      score1-=50;
      else if(level==1)
          score1-=75;
      else if(level==2)
          score1-=100;
      score.textContent=score1;
  }
  function refresh(){
      if(level==0)
      newcolore();
      else if(level==1)
          newcolorm();
      else if(level==2)
          newcolorh();
  }
  function newcolore(){
  var x=getRandomIntInclusive(0,255);
  var y=getRandomIntInclusive(0,255);
  var z=getRandomIntInclusive(0,255);
  v1.textContent=x
  v2.textContent=y;
  v3.textContent=z;
  c1.style.backgroundColor="rgb("+x+","+y+","+z+")";
  c2.style.backgroundColor="rgb("+getRandomIntInclusive(0,255)+","+getRandomIntInclusive(0,255)+","+getRandomIntInclusive(0,255)+")";
  c3.style.backgroundColor="rgb("+getRandomIntInclusive(0,255)+","+getRandomIntInclusive(0,255)+","+getRandomIntInclusive(0,255)+")";
  c4.style.backgroundColor="rgb("+getRandomIntInclusive(0,255)+","+getRandomIntInclusive(0,255)+","+getRandomIntInclusive(0,255)+")";
  c5.style.backgroundColor="rgb("+getRandomIntInclusive(0,255)+","+getRandomIntInclusive(0,255)+","+getRandomIntInclusive(0,255)+")";
  c6.style.backgroundColor="rgb("+getRandomIntInclusive(0,255)+","+getRandomIntInclusive(0,255)+","+getRandomIntInclusive(0,255)+")";
  }
  function newcolorm(){
  var x=getRandomIntInclusive(0,255);
  var y=getRandomIntInclusive(0,255);
  var z=getRandomIntInclusive(0,255);
  v1.textContent=x
  v2.textContent=y;
  v3.textContent=z;
  c1.style.backgroundColor="rgb("+x+","+y+","+z+")";
  c2.style.backgroundColor="rgb("+x+","+getRandomIntInclusive(0,255)+","+getRandomIntInclusive(0,255)+")";
  c3.style.backgroundColor="rgb("+getRandomIntInclusive(0,255)+","+y+","+getRandomIntInclusive(0,255)+")";
  c4.style.backgroundColor="rgb("+getRandomIntInclusive(0,255)+","+getRandomIntInclusive(0,255)+","+z+")";
  c5.style.backgroundColor="rgb("+getRandomIntInclusive(x,255)+","+getRandomIntInclusive(y,255)+","+getRandomIntInclusive(z,255)+")";
  c6.style.backgroundColor="rgb("+getRandomIntInclusive(0,x)+","+getRandomIntInclusive(0,y)+","+getRandomIntInclusive(0,z)+")";
  }
  function newcolorh(){
  var x=getRandomIntInclusive(0,255);
  var y=getRandomIntInclusive(0,255);
  var z=getRandomIntInclusive(0,255);
  v1.textContent=x
  v2.textContent=y;
  v3.textContent=z;
  c1.style.backgroundColor="rgb("+x+","+y+","+z+")";
  c2.style.backgroundColor="rgb("+getRandomIntInclusive(x-100,x+100)+","+y+","+z+")";
  c3.style.backgroundColor="rgb("+getRandomIntInclusive(x-100,x+100)+","+getRandomIntInclusive(y-100,y+100)+","+z+")";
  c4.style.backgroundColor="rgb("+getRandomIntInclusive(x-100,x+100)+","+getRandomIntInclusive(y-100,y+100)+","+getRandomIntInclusive(z-100,z+100)+")";
  c5.style.backgroundColor="rgb("+getRandomIntInclusive(x-100,x+100)+","+y+","+getRandomIntInclusive(z-100,z+100)+")";
  c6.style.backgroundColor="rgb("+x+","+getRandomIntInclusive(y-100,y+100)+","+getRandomIntInclusive(z-100,z+100)+")";
  }
  newcolorh();
  
  c1.addEventListener("click",function(){
      scoreplus();
      refresh();
      
  })
  c2.addEventListener("click",function(){
      scoreminus();
      refresh();
  })
  c3.addEventListener("click",function(){
      scoreminus();
      refresh();
  })
  c4.addEventListener("click",function(){
      scoreminus();
      refresh();
  })
  c5.addEventListener("click",function(){
      scoreminus();
      refresh();
  })
  c6.addEventListener("click",function(){
      scoreminus();
      refresh();
  })