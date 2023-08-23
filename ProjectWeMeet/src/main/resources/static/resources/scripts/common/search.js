const search = document.getElementById('search');

search.onsubmit = e => {
    if(search['q'].value === ''){
        alert('검색어를 입력해주세요');
        search['q'].focus();
        return false;
    }
}
