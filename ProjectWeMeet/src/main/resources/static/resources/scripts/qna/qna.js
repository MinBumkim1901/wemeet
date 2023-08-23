const td = document.querySelectorAll('.td');

td.forEach(td => {
    td.addEventListener('click', () => {
       window.location.href = '/';
    });
});