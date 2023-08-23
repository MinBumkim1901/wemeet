const eventPatchForm = document.getElementById('eventPatch');
const eventPatch = eventPatchForm.querySelector('[rel="patch"]');


ClassicEditor.create(eventPatchForm['content'], {
    simpleUpload: {
        uploadUrl: '/eventUploadImage'
    }
});

eventPatchForm.onsubmit = e => {
    e.preventDefault()

    if (eventPatchForm['title'].value === '') {
        eventPatchForm['title'].focus();
        alert('제목을 입력해 주세요');
        return true;
    }
    if (eventPatchForm['content'].value === '') {
        eventPatchForm['content'].focus();
        alert('내용을 입력해주세요');
        return true;
    }
    const index = eventPatch.dataset.index;

    const xhr = new XMLHttpRequest();
    const formData = new FormData();
    formData.append('title',eventPatchForm['title'].value)
    formData.append('content',eventPatchForm['content'].value)
    xhr.open('PATCH', `/eventView/patch?index=${index}`);
    xhr.onreadystatechange = () => {
        if (xhr.readyState === XMLHttpRequest.DONE) {
            if (xhr.status >= 200 && xhr.status < 300) {
                const responseObject = JSON.parse(xhr.responseText);
                switch (responseObject.result) {
                    case 'success':
                        alert('수정에 성공했습니다');
                        window.location.href = '/eventView?index=' + index; //수정되면 게시판 바로 이동
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


