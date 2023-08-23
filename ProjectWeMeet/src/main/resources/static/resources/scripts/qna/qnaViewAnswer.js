const notAnswerForm = document.getElementById('notAnswerForm');

notAnswerForm.onsubmit = e => {
    if(notAnswerForm['answer'].value === ''){
        alert('답변을 작성해주세요');
        notAnswerForm['answer'].focus();
        return false;
    }

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('answer', notAnswerForm['answer'].value);
    xhr.open('POST', `/qnaAnswer?index=${notAnswerForm['index'].value}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                if (xhr.responseText === 'true') {
                    window.location.href = `/qnaView?index=${notAnswerForm['index'].value}`;
                } else {
                }
            } else {

            }
        }
    };
    xhr.send(formData);
}