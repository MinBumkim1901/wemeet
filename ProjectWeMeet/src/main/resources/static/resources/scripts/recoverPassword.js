const recoverPassword = document.getElementById('verifyCode');
const passwordReset = document.getElementById('passwordReset');
const passwordInput = document.querySelector('input[name="password"]');
const openEye = document.querySelector('.openEye');
const closeEye = document.querySelector('.closeEye');

let code;

window.onload = function() {
    recoverPassword.style.transitionDuration = '300ms'; // 애니메이션 지속 시간 설정
    recoverPassword.style.transitionTimingFunction = 'ease'; // 타이밍 함수 설정
    recoverPassword.style.transitionProperty = 'opacity'; // 타이밍 함수 설정
    recoverPassword.style.opacity = '1';
}

recoverPassword.hide = () =>{
    recoverPassword.classList.add('visible');
}

passwordReset.show = () => {
    passwordReset.classList.add('visible');
}

openEye.addEventListener("click", function () {
    openEye.style.display = "none";
    closeEye.style.display = "block";
    passwordInput.type = 'password';
});

closeEye.addEventListener("click", function () {
    closeEye.style.display = "none";
    openEye.style.display = "block";
    passwordInput.type = 'text';
});
recoverPassword.warning = recoverPassword.querySelector('[rel="codeWarning"]');
passwordReset.warning = passwordReset.querySelector('[rel="passwordWarning"]');

function showWarning(element, text) {
    element.innerText = text;
    element.classList.add('visible');
} //warning text 보이게함

function hideElement(element) {
    element.classList.remove('visible');
} //warning text 삭제


recoverPassword.onsubmit = e => {
    e.preventDefault();
    if(recoverPassword['EmailCode'].value === ''){
        showWarning(recoverPassword.warning, "인증번호가 비어있습니다");
        recoverPassword['EmailCode'].classList.add('_invalid');
        recoverPassword['EmailCode'].focus();
        recoverPassword['EmailCode'].select();
        return;
    }

    let emailCode = document.getElementById("verifyCode").elements["EmailCode"].value;
    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('email', recoverPassword['email'].value);
    formData.append('code', recoverPassword['EmailCode'].value);
    formData.append('salt', recoverPassword['salt'].value);
    xhr.open('POST', '/recoverAccount/emailCodeRec');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                        showWarning(recoverPassword.warning, "인증 코드가 올바르지 않습니다. 코드를 다시 확인해 주세요.");
                        recoverPassword['EmailCode'].classList.add('_invalid');
                        recoverPassword['EmailCode'].focus();
                        recoverPassword['EmailCode'].select();
                        break;
                    case 'failure_expired':
                        showWarning(recoverPassword.warning, "인증 코드가 만료되었습니다. 이메일 인증을 새로 해주세요.");
                        recoverPassword['EmailCode'].classList.add('_invalid');
                        recoverPassword['EmailCode'].focus();
                        recoverPassword['EmailCode'].select();
                        break;
                    case 'success':
                        passwordReset['code'].value = recoverPassword['EmailCode'].value;
                        recoverPassword.style.display = 'none';
                        passwordReset.show();
                        break;
                    default:
                        alert("서버 오류.");
                }
            } else {
                alert("통신 오류.");
            }
        }
    }
    xhr.send(formData);
}



// 두 번째 폼에 EmailCode 값을 설정합니다.
passwordReset.onsubmit = e => {
    e.preventDefault();

    if (passwordReset['password'].value === '') {
        showWarning(passwordReset.warning, "비밀번호가 비어있습니다");
        passwordReset['passwordAgain'].classList.remove('_invalid');
        passwordReset['password'].classList.add('_invalid');
        passwordReset['password'].focus();
        passwordReset['password'].select();
        return;
    }

    else if (!new RegExp('^([\\da-zA-Z`~!@#$%^&*()\\-_=+\\[{\\]};:\'",<.>/?]{8,50})$').test(passwordReset['password'].value)) {
        showWarning(passwordReset.warning,"비밀번호가 규칙에 맞지않습니다");
        passwordReset['passwordAgain'].classList.remove('_invalid');
        passwordReset['password'].classList.add('_invalid');
        passwordReset['password'].focus();
        passwordReset['password'].select();
        return;
    }

    else if (passwordReset['passwordAgain'].value === '') {
        showWarning(passwordReset.warning,"비밀번호 재입력이 비어있습니다");
        passwordReset['password'].classList.remove('_invalid');
        passwordReset['passwordAgain'].classList.add('_invalid');
        passwordReset['passwordAgain'].focus();
        passwordReset['passwordAgain'].select();
        return;
    }

    else if (passwordReset['passwordAgain'].value !== passwordReset['password'].value) {
        showWarning(passwordReset.warning,"비밀번호가 일치하지 않습니다");
        passwordReset['password'].classList.remove('_invalid');
        passwordReset['passwordAgain'].classList.add('_invalid');
        passwordReset['passwordAgain'].focus();
        passwordReset['passwordAgain'].select();
        return;
    }

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('email', passwordReset['email'].value);
    formData.append('code',passwordReset['code'].value ); // 수정된 부분: EmailCode 값을 추가합니다.
    formData.append('salt', passwordReset['salt'].value);
    formData.append('password', passwordReset['password'].value);
    xhr.open('PATCH', '/recoverAccount/recoverPassword');
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'failure':
                        dialogCover.show();
                        dialogLayer.show({
                            title: 'We-Meet',
                            content: '다시 시도해주세요',
                            onConfirm: e => {
                                e.preventDefault();
                                dialogCover.hide();
                                dialogLayer.hide();
                            }
                        });
                        break;
                    case 'success':
                        dialogCover.show();
                        dialogLayer.show({
                            title: 'We-Meet',
                            content: '비밀번호가 변경되었습니다',
                            onConfirm: e => {
                                location.href = '/'
                                e.preventDefault();
                                dialogCover.hide();
                                dialogLayer.hide();
                            }
                        });
                        break;
                    default:
                        alert('서버가 알 수 없는 응답을 반환하였습니다. 잠시 후 다시 시도해 주세요.');
                }
            } else {
                alert('서버와 통신하지 못하였습니다. 잠시 후 다시 시도해 주세요.');
            }
        }
    }
    xhr.send(formData);
}
