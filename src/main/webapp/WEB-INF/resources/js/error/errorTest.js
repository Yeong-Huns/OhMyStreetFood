
    let size = 17; 
    let snakesize = 4;
    let dir = 1; 
    let snakeY = [0, 0, 0, 0]; 
    let snakeX = [0, 1, 2, 3]; 
    let $myGame = document.querySelector(`#myGame`); 
    let $table = document.createElement(`table`); 
    let $tr = document.createElement(`tr`); 
    let $td = document.createElement(`td`); 
    let $data = []; 
    let item = 9; 
    let gameover = false; 
    let myInterval = null; 
    let myTimeout = null;
    let count = 0; 
    let time = 3;
    let max = -1;

    
    function init(){ 
        for(let i = 0; i < size; i++){
            $temp = [];
        for(let j = 0; j < size; j++){
            $temp.push(0);
        }
        $data.push($temp);
    }   
        $table = document.querySelector(`#snake`) 
        for(let i = 0; i < snakesize; i++){ 
            $data[snakeY[i]][snakeX[i]] = i + 1; 
            $table.children[snakeY[i]].children[snakeX[i]].setAttribute(`class`, `snakeBody`); 
        }
        $table.children[snakeY[snakesize - 1]].children[snakeX[snakesize - 1]].setAttribute(`class`, `snakeHead`); 

        setitemS(); 
    
}
     //1
function settable(){ 
        $myGame = document.querySelector(`#myGame`) 
        $table = document.createElement(`table`) 
        $table.id = `snake` 
        for(let i = 0; i < size; i++){ 
        $tr = document.createElement(`tr`);
        for(let j = 0; j < size; j++){
            $td = document.createElement(`td`);
            $tr.append($td) 
        }
        $table.append($tr) 
    }
    $myGame.append($table); 
}

    function setitem(){ 
        while(true){ 
        let r1 = Math.floor(Math.random() * size); 
        let r2 = Math.floor(Math.random() * size); 
        if($data[r1][r2] == 0){ 
        $data[r1][r2] = item; 
        let $table = document.querySelector(`#snake`); 
        $table.children[r1].children[r2].setAttribute(`class`, `item`); 
        break;
    }
    }
    }
    function setitemS(){ 
        for(let i = 0; i < 3; i++){
        while(true){ 
        let r1 = Math.floor(Math.random() * size); 
        let r2 = Math.floor(Math.random() * size); 
        if($data[r1][r2] == 0){ 
        $data[r1][r2] = item; 
        let $table = document.querySelector(`#snake`); 
        $table.children[r1].children[r2].setAttribute(`class`, `item`); 
        break;
    }
    }
}
    }


    function snakemove(){ 
        if(count == 10){
            clearInterval(myInterval);
            myInterval = setInterval(snakemove, 90)
        }
        if(count == 20){
            clearInterval(myInterval);
            myInterval = setInterval(snakemove, 80)
        }
        if(count == 30){
            clearInterval(myInterval);
            myInterval = setInterval(snakemove, 70)
        }
        if(count == 40){
            clearInterval(myInterval);
            myInterval = setInterval(snakemove, 60)
        }
        if(count == 50){
            clearInterval(myInterval);
            myInterval = setInterval(snakemove, 50)
        }
        if(gameover == true){ 
            if(max < count){
            max = count
            alert(`최고기록 달성!! ${max}개의 사과를 먹었습니다!`)
        } else {alert(`아쉬운 결과네요.. 당신의 최고기록은 ${max}개 입니다.`)}
            clearInterval(myInterval);
            setcount();

        }
        $table = document.querySelector(`#snake`)
        let tempy = snakeY[snakesize - 1]; 
        let tempx = snakeX[snakesize - 1]; 
        if(dir == 0) tempy--; 
        else if(dir == 1) tempx++; 
        else if(dir == 2) tempy++; 
        else if(dir == 3) tempx--; 
        
        if(tempy >= size || tempy < 0){ 
            gameover = true;
            return;
        }
        if(tempx >= size || tempy < 0){ 
            gameover = true;
            return;
        }
        if($data[tempy][tempx] != 0 && $data[tempy][tempx] != item){ 
            gameover = true;
            return;
        }
        
        
        //아이템 먹엇을경우 
        if($data[tempy][tempx] == item){ 
            snakeY.unshift(tempy) 
            snakeX.unshift(tempx) 
            snakesize++; 
            count++ 
            scoreTd.innerText = count; 
            setitem(); 
        }
        
        for(let i = 0; i < snakesize; i++){ 
            $data[snakeY[i]][snakeX[i]] = 0; 
            $table.children[snakeY[i]].children[snakeX[i]].setAttribute(`class`, ""); 
        }
        $table.children[snakeY[snakesize - 1]].children[snakeX[snakesize - 1]].setAttribute(`class`, ""); 

        
        
        for(let i = 1; i < snakesize; i++){ 
            snakeY[i - 1] = snakeY[i]; 
            snakeX[i - 1] = snakeX[i];
        } 
        snakeY[snakesize - 1] = tempy; 
        snakeX[snakesize - 1] = tempx; 


        
        for(let i = 0; i < snakesize; i++){
            $data[snakeY[i]][snakeX[i]] = i + 1; 
            $table.children[snakeY[i]].children[snakeX[i]].setAttribute(`class`, `snakeBody`); 
        }
        $table.children[snakeY[snakesize - 1]].children[snakeX[snakesize - 1]].setAttribute(`class`, `snakeHead`); // 머리지정

    }
    
function gameStart() { 
    myInterval = setInterval(snakemove, 100); 

    document.querySelector("#playBtn").setAttribute("disabled", true); 
    document.querySelector("#playBtn").style.background = "lightgray"; 
}
window.addEventListener(`keydown`, (a)=>{
        let key = a.code;
        if(key == `ArrowLeft` && dir != 1) dir = 3;
        if(key == `ArrowUp` && dir != 2) dir = 0;
        if(key == `ArrowRight` && dir != 3) dir = 1;
        if(key == `ArrowDown` && dir != 0) dir = 2;
        console.log(`dir = ${dir}`)
    })
    function setcount(){
        if(time >= 0){
    document.querySelector(`#msg`).innerHTML = `Game Over<br>` + time  
        myTimeout = setTimeout(setcount, 1000);
        time--;
    } else {
        clearTimeout(myTimeout)
        restart();
    }

    }
    function restart(){

    size = 17; 
    snakesize = 4; 
    dir = 1; 
    snakeY = [0, 0, 0, 0]; 
    snakeX = [0, 1, 2, 3]; 
    $data = []; 
    item = 9; 
    gameover = false; 
    myInterval = null; 
    count = 0; 
    time = 3;
    $table = document.querySelector(`#snake`)
    
    for(let i = 0; i < size; i ++){
        for(let j = 0; j < size; j++){
            $table.children[i].children[j].setAttribute(`class`, "")
        }
    }
    init();
    document.querySelector("#playBtn").removeAttribute(`disabled`, true);
    document.querySelector("#playBtn").style.background = "coral"; //버튼색 원상복구
    document.querySelector(`#scoreTd`).innerText = 0;
    document.querySelector(`#msg`).innerHTML = ``
    }
     ////////////////////////////////////////////////////////////////////////////////////////////////////////////

    /*
    01230
    00040
    00000
    00000

    00120
    00030
    00040
    00000
    00000

    00010
    00020
    00030
    00040
    00000
    */
   
settable();
init();
    