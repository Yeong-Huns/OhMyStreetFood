<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <style>
        #playBtn {
            width: 180px;
            height: 60px;
            font-size: 20px;
            font-weight: bold;
            background-color: tomato;
            color: white;
            border-style: none;
            border-radius: 8px;
            margin: 30px 0px;
            cursor: pointer;
        }

        #msg {
            margin: 0 auto;
            text-align: center;
            font-size: 30px;
            font-weight: bold;
            margin-top: 20px;
            width: 300px;
            height: 80px;
            color: tomato;
        }

        #imgTd {
            width: 50px;
        }

        #scoreTd {
            text-align: center;
            width: 50px;
            font-size: 30px;
            font-weight: bold;
        }

        #playTd {
            text-align: right;
        }

        #header {
            margin: 0 auto;
            margin-top: 30px;
            text-align: center;
            width: 700px;
            height: 200px;
        }

        #appleTd {
            width: 30px;
            height: 30px;
            font-size: 20px;
            font-size: 24px;
            padding: 0px;
            border-spacing: 0px;
        }

        #content {
            margin: 0 auto;
            text-align: left;
        }

        #appleImg {
            width: 50px;
        }

        #snake {
            border-collapse: collapse;
            width: 600px;
            height: 600px;
        }

        #snake td {
            border: 1px solid lightgray;
            background-color: rgb(245, 244, 244);
        }

        #snake .snakeBody {
            background-color: rgb(2, 191, 2);
        }

        #snake .snakeHead {
            background-color: rgba(15, 117, 4, 0.84);
        }

        #snake .item {
            background-color: tomato;
        }

        .error-icon {
            font-size: 100px;
            color: #ccc;
            cursor: pointer;
            transition: transform 0.2s, color 0.2s;
            text-align: center;
            margin: 0 auto;
        }

        .error-icon:hover {
            color: #333;
            transform: scale(1.1);
        }
    </style>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <script>
        let size = 17;
        let snakesize = 4;
        let dir = 1;
        let snakeY = [0, 0, 0, 0];
        let snakeX = [0, 1, 2, 3];
        let $myGame = null;
        let $table = null;
        let $tr = null;
        let $td = null;
        let $data = [];
        let item = 9;
        let gameover = false;
        let myInterval = null;
        let myTimeout = null;
        let count = 0;
        let time = 3;
        let max = -1;
        let clickCount = 0;
        let clickTimer = null;

        function init() {
            for (let i = 0; i < size; i++) {
                let $temp = [];
                for (let j = 0; j < size; j++) {
                    $temp.push(0);
                }
                $data.push($temp);
            }
            $table = document.querySelector(`#snake`);
            for (let i = 0; i < snakesize; i++) {
                $data[snakeY[i]][snakeX[i]] = i + 1;
                $table.children[snakeY[i]].children[snakeX[i]].setAttribute(`class`, `snakeBody`);
            }
            $table.children[snakeY[snakesize - 1]].children[snakeX[snakesize - 1]].setAttribute(`class`, `snakeHead`);

            setitemS();
        }

        function settable() {
            $myGame = document.querySelector(`#myGame`);
            $table = document.createElement(`table`);
            $table.id = `snake`;
            for (let i = 0; i < size; i++) {
                $tr = document.createElement(`tr`);
                for (let j = 0; j < size; j++) {
                    $td = document.createElement(`td`);
                    $tr.append($td);
                }
                $table.append($tr);
            }
            $myGame.append($table);
        }

        function setitem() {
            while (true) {
                let r1 = Math.floor(Math.random() * size);
                let r2 = Math.floor(Math.random() * size);
                if ($data[r1][r2] == 0) {
                    $data[r1][r2] = item;
                    let $table = document.querySelector(`#snake`);
                    $table.children[r1].children[r2].setAttribute(`class`, `item`);
                    break;
                }
            }
        }

        function setitemS() {
            for (let i = 0; i < 3; i++) {
                while (true) {
                    let r1 = Math.floor(Math.random() * size);
                    let r2 = Math.floor(Math.random() * size);
                    if ($data[r1][r2] == 0) {
                        $data[r1][r2] = item;
                        let $table = document.querySelector(`#snake`);
                        $table.children[r1].children[r2].setAttribute(`class`, `item`);
                        break;
                    }
                }
            }
        }

        function snakemove() {
            if (count == 10) {
                clearInterval(myInterval);
                myInterval = setInterval(snakemove, 90)
            }
            if (count == 20) {
                clearInterval(myInterval);
                myInterval = setInterval(snakemove, 80)
            }
            if (count == 30) {
                clearInterval(myInterval);
                myInterval = setInterval(snakemove, 70)
            }
            if (count == 40) {
                clearInterval(myInterval);
                myInterval = setInterval(snakemove, 60)
            }
            if (count == 50) {
                clearInterval(myInterval);
                myInterval = setInterval(snakemove, 50)
            }
            if (gameover == true) {
                if (max < count) {
                    max = count
                    alert(`최고기록 달성!! `+ max +`개의 사과를 먹었습니다!`)
                } else { alert(`아쉬운 결과네요.. 당신의 최고기록은`+ max +`개 입니다.`) }
                clearInterval(myInterval);
                setcount();
            }
            $table = document.querySelector(`#snake`)
            let tempy = snakeY[snakesize - 1];
            let tempx = snakeX[snakesize - 1];
            if (dir == 0) tempy--;
            else if (dir == 1) tempx++;
            else if (dir == 2) tempy++;
            else if (dir == 3) tempx--;

            if (tempy >= size || tempy < 0) {
                gameover = true;
                return;
            }
            if (tempx >= size || tempx < 0) {
                gameover = true;
                return;
            }
            if ($data[tempy][tempx] != 0 && $data[tempy][tempx] != item) {
                gameover = true;
                return;
            }

            //아이템 먹었을 경우
            if ($data[tempy][tempx] == item) {
                snakeY.unshift(tempy);
                snakeX.unshift(tempx);
                snakesize++;
                count++;
                scoreTd.innerText = count;
                setitem();
            }

            for (let i = 0; i < snakesize; i++) {
                $data[snakeY[i]][snakeX[i]] = 0;
                $table.children[snakeY[i]].children[snakeX[i]].setAttribute(`class`, "");
            }
            $table.children[snakeY[snakesize - 1]].children[snakeX[snakesize - 1]].setAttribute(`class`, "");

            for (let i = 1; i < snakesize; i++) {
                snakeY[i - 1] = snakeY[i];
                snakeX[i - 1] = snakeX[i];
            }
            snakeY[snakesize - 1] = tempy;
            snakeX[snakesize - 1] = tempx;

            for (let i = 0; i < snakesize; i++) {
                $data[snakeY[i]][snakeX[i]] = i + 1;
                $table.children[snakeY[i]].children[snakeX[i]].setAttribute(`class`, `snakeBody`);
            }
            $table.children[snakeY[snakesize - 1]].children[snakeX[snakesize - 1]].setAttribute(`class`, `snakeHead`); // 머리 지정
        }

        function gameStart() {
            myInterval = setInterval(snakemove, 100);
            document.querySelector("#playBtn").setAttribute("disabled", true);
            document.querySelector("#playBtn").style.background = "lightgray";
        }

        window.addEventListener(`keydown`, (a) => {
            let key = a.code;
            if (key == `ArrowLeft` && dir != 1) dir = 3;
            if (key == `ArrowUp` && dir != 2) dir = 0;
            if (key == `ArrowRight` && dir != 3) dir = 1;
            if (key == `ArrowDown` && dir != 0) dir = 2;
        })

        function setcount() {
            if (time >= 0) {
                document.querySelector(`#msg`).innerHTML = `Game Over<br>` + time;
                myTimeout = setTimeout(setcount, 1000);
                time--;
            } else {
                clearTimeout(myTimeout);
                restart();
            }
        }

        function restart() {
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
            $table = document.querySelector(`#snake`);

            for (let i = 0; i < size; i++) {
                for (let j = 0; j < size; j++) {
                    $table.children[i].children[j].setAttribute(`class`, "")
                }
            }
            init();
            document.querySelector("#playBtn").removeAttribute(`disabled`, true);
            document.querySelector("#playBtn").style.background = "coral"; // 버튼색 원상복구
            document.querySelector(`#scoreTd`).innerText = 0;
            document.querySelector(`#msg`).innerHTML = ``
        }

        document.addEventListener('DOMContentLoaded', (event) => {
            settable();
            init();

            const errorIcon = document.getElementById('error-icon');
            let clickCount = 0;
            let clickTimer = null;

            errorIcon.addEventListener('mouseover', () => {
                errorIcon.innerText = '🤕';
            });

            errorIcon.addEventListener('mouseout', () => {
                errorIcon.innerText = '🥲';
            });

            errorIcon.addEventListener('click', () => {
                clickCount++;
                if (clickCount === 1) {
                    clickTimer = setTimeout(() => {
                        clickCount = 0;
                    }, 1000);
                } else if (clickCount === 4) {
                    clearTimeout(clickTimer);
                    window.location.href = '/'; // 이동할 페이지 URL
                }
            });
        });
    </script>
</head>
<body>
<table id="header">
    <tr>
        <td colspan="3" class="error-icon" id="error-icon" textalign="center">🥲</td>
    </tr>
    <tr>
        <td id="appleTd">&#127823;</td>
        <td id="scoreTd">0</td>
        <td id="playTd">
            <button id="playBtn" onclick="gameStart()">▷ Play</button>
        </td>
    </tr>
</table>
<table id="content">
    <tr>
        <td id="myGame" colspan="3"></td>
    </tr>
</table>
<div id="msg"></div>
</body>
</html>
