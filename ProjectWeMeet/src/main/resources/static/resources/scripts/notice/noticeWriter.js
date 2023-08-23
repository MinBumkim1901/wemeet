const noticeWriteForm = document.getElementById('notice-writer');


ClassicEditor.create(noticeWriteForm['content'], {
    simpleUpload: {
        uploadUrl: '/uploadImage'
    }
});

noticeWriteForm.onsubmit = e => {
    if (noticeWriteForm['title'].value === '') {
        noticeWriteForm['title'].focus();
        alert('제목을 입력해 주세요');
        return false; // 폼 제출 중단
    }

    if (noticeWriteForm['content'].value === '') {
        noticeWriteForm['content'].focus();
        alert('내용을 입력해 주세요');
        return false; // 폼 제출 중단
    }
}


