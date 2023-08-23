const qnaWrite = document.getElementById('qnaWriter');
const qnaWriterS = document.getElementById('qnaWriterS');

if(window.innerWidth < 514) {
    qnaWrite.onsubmit = e => {
        if (qnaWrite['qna_type'].value === '') {
            alert('문의유형을 선택해주세요');
            qnaWrite['qna_type'].focus();
            return false; // 폼 제출 중단
        }

        if (qnaWrite['title'].value === '') {
            alert('제목을 작성해주세요');
            qnaWrite['title'].focus();
            return false; // 폼 제출 중단
        }

        if (qnaWrite['content'].value === '') {
            alert('문의내용을 작성해주세요');
            qnaWrite['content'].focus();
            return false; // 폼 제출 중단
        }
    }
}

if(window.innerWidth >= 514){
    qnaWriterS.onsubmit = e => {
        if (qnaWriterS['qna_type'].value === '') {
            alert('문의유형을 선택해주세요');
            qnaWriterS['qna_type'].focus();
            return false; // 폼 제출 중단
        }

        if (qnaWriterS['title'].value === '') {
            alert('제목을 작성해주세요');
            qnaWriterS['title'].focus();
            return false; // 폼 제출 중단
        }

        if (qnaWriterS['content'].value === '') {
            alert('문의내용을 작성해주세요');
            qnaWriterS['content'].focus();
            return false; // 폼 제출 중단
        }
    }
}