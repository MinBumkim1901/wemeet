const faqPatchForm = document.getElementById('faqPatch');
const faqPatch = faqPatchForm.querySelector('[rel="patch"]');

ClassicEditor.create(faqPatchForm['content'], {}); //파일 업로드

faqPatchForm.onsubmit = e => {
    e.preventDefault()
    if (faqPatchForm['title'].value === '') {
        faqPatchForm['title'].focus();
        alert('제목을 입력해주세요');
        return true;
    }
    if (faqPatchForm['content'].value === '') {
        faqPatchForm['content'].focus();
        alert('내용을 입력해주세요');
        return true;
    }
    const index = faqPatch.dataset.index;

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('title',faqPatchForm['title'].value)
    formData.append('content',faqPatchForm['content'].value)
    xhr.open('PATCH', `/faqView/patch?index=${index}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'success':
                        alert('수정에 성공했습니다');
                        window.location.href = '/faqView?index=' + index; //수정되면 게시판 바로 이동
                        break;
                    case 'failure':
                        alert('수정에 실패했습니다');
                        break;
                    default:
                        alert('알수없는문제가 생겼습니다. 다시한번 시도해주세요.')
                }
            } else {
                alert('서버에 문제가 생겼습니다. 나중에 다시 시도해 주세요')
            }
        }
    };
    xhr.send(formData);
}



