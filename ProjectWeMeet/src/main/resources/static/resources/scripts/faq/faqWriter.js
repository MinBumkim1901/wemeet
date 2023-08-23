const faqWriteForm = document.getElementById('faqWriter');

ClassicEditor.create(faqWriteForm['content'], {}); //파일 업로드


faqWriteForm.onsubmit = e => {
    if (faqWriteForm['title'].value === '') {
        e.preventDefault();
        faqWriteForm['title'].focus();
        alert('제목을 입력해 주세요');
        return false;
    }
    if (faqWriteForm['content'].value === '') {
        e.preventDefault();
        faqWriteForm['content'].focus();
        alert('내용을 입력해 주세요');
        return false;
    }
}


