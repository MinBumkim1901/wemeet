const qnaPatchForm = document.getElementById('qnaPatch');
const qnaPatch = qnaPatchForm.querySelector('[rel="patch"]');


qnaPatchForm.onsubmit = e => {
    e.preventDefault();
    if (qnaPatchForm['qna_type'].value === '') {
        alert('문의유형을 선택해주세요');
        qnaPatchForm['qna_type'].focus();
        return true;
    }
    if (qnaPatchForm['title'].value === '') {
        alert('제목을 작성해주세요');
        qnaPatchForm['title'].focus();
        return true;
    }
    if (qnaPatchForm['content'].value === '') {
        alert('문의내용을 작성해주세요');
        qnaPatchForm['content'].focus();
        return true;
    }

    const patchIndex = qnaPatch.dataset.index;

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('qna_type', qnaPatchForm['qna_type'].value);
    formData.append('title', qnaPatchForm['title'].value);
    formData.append('content', qnaPatchForm['content'].value);
    xhr.open('PATCH', `/qnaView/patch?index=${patchIndex}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseText = xhr.responseText;
                if (responseText === 'true') {
                    window.location.href = '/qnaView/?index=' + patchIndex; //수정되면 게시판 바로 이동
                } else {
                    alert('수정에 실패했습니다');
                }
            } else {
            }
        }
    }
    xhr.send(formData);
}


