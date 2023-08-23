const adminForm = document.getElementById('main');
const deleteUserButton = adminForm.querySelector('[rel="delete"]');


adminForm.onsubmit = e => {
    const email = deleteUserButton.dataset.index;
    e.preventDefault();
    if (confirm('삭제하시겠습니까?')) {
         const xhr = new XMLHttpRequest();
         const formData = new FormData();
         xhr.open('DELETE',`admin?email=${email}`);
         xhr.onreadystatechange = () => {
          if(xhr.readyState === XMLHttpRequest.DONE){
             if(xhr.status >= 200 && xhr.status<300) {
                 window.location.href='/admin';
             }else {

             }
            }
         };
         xhr.send();
    } else {
        alert('삭제를 취소합니다');
    }
}